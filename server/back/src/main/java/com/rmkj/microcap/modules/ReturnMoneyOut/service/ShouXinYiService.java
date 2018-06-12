package com.rmkj.microcap.modules.ReturnMoneyOut.service;

import com.rmkj.microcap.common.constants.ProjectConstants;
import com.rmkj.microcap.common.modules.money.out.WithdrawalsBean;
import com.rmkj.microcap.common.modules.money.out.yizhifu.CheckUtils;
import com.rmkj.microcap.common.modules.money.out.yizhifu.MoneyService;
import com.rmkj.microcap.common.utils.Utils;
import com.rmkj.microcap.modules.ReturnMoneyOut.dao.IReturnMoneyOutDao;
import com.rmkj.microcap.modules.ReturnMoneyOut.entity.ReturnMoneyOutBean;
import com.rmkj.microcap.modules.moneyrecord.entity.MoneyRecordForOutBean;
import com.rmkj.microcap.modules.user.dao.IUserDao;
import com.rmkj.microcap.modules.usermessage.entity.UserMessageBean;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShouXinYiService {

    private final Logger Log = Logger.getLogger(ShouXinYiService.class);

    @Autowired
    private IReturnMoneyOutDao iReturnMoneyOutDao;

    @Autowired
    private MoneyService moneyService;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private BaseService baseService;

    /**
     * 主动定频查询提现结果
     * 不开事务
     * 单笔处理，提现失败 更新提现记录表->更新用户余额(小概率更新失败)->添加变更记录
     */
    //@Scheduled(initialDelay = 10000, fixedDelay = 30000)
    public void checkOutResult(){
        List<MoneyRecordForOutBean> list = iReturnMoneyOutDao.findNoResult2Check();
        if(ProjectConstants.PRO_DEBUG){
            return;
        }
        list.forEach(returnMoneyOutBean -> {
            // 0-未处理， 1-已成功 2-处理中 3-已失败 4-待处理（自动处理程序状态） 8-没有该用户标识对应的代付记录 9-查询失败 提现结果查询
            String result = moneyService.queryResult(returnMoneyOutBean.getId());
            if("1".equals(result)){
                // 成功
                iReturnMoneyOutDao.updateSuccess(returnMoneyOutBean.getId());
                sendMessageToUser(returnMoneyOutBean.getUserId(), "您有一笔".concat(returnMoneyOutBean.getMoney().toString())
                        .concat("元的佣金提现成功处理，手续费").concat(returnMoneyOutBean.getFee().toString()).concat("元，流水号：")
                        .concat(returnMoneyOutBean.getSerialNo()));
            }else if("3".equals(result)){
                baseService.returnMoney2User(returnMoneyOutBean);
                sendMessageToUser(returnMoneyOutBean.getUserId(), "您有一笔".concat(returnMoneyOutBean.getMoney().toString())
                        .concat("元的佣金提现处理失败，流水号：").concat(returnMoneyOutBean.getSerialNo()));
            }else{

            }
        });
    }

    /**
     * 通知用户
     * @param userId
     * @param content
     */
    private void sendMessageToUser(String userId, String content){
        UserMessageBean userMessageBean = new UserMessageBean();
        userMessageBean.setId(Utils.uuid());
        userMessageBean.setUserId(userId);
        userMessageBean.setTitle("提现");
        userMessageBean.setContent(content);
        userMessageBean.setReadStatus(0);
        userMessageBean.setType(0);
        userMessageBean.setCreateTime(new Date());
        userDao.insertUserMessage(userMessageBean);
    }

    /**
     * 走三方提现接口
     * id用英文逗号连接的字符串
     * @return
     */
    public String batchOut(String[] idArr){
        final int[] count = {0};
        if(idArr.length != 0){
            List<WithdrawalsBean> list = iReturnMoneyOutDao.queryInfoForOut(Arrays.asList(idArr));
            StringBuilder sb = new StringBuilder();
            double[] totalMoney = new double[1];
            // 手续费怎么扣除
            list.forEach(returnMoneyOutBean -> {
                sb.append("$").append(returnMoneyOutBean.getBankAccount())
                        .append("|").append(returnMoneyOutBean.getChnName())
                        .append("|").append(returnMoneyOutBean.getOpenBankName())
                        .append("|").append(returnMoneyOutBean.getProvince())
                        .append("|").append(returnMoneyOutBean.getCity())
                        .append("|").append(String.format("%1.02f", returnMoneyOutBean.getMoney()))
                        .append("|").append(returnMoneyOutBean.getId())
                        .append("|").append(returnMoneyOutBean.getLianHangNo() == null ? CheckUtils.getLianHangHao(returnMoneyOutBean.getBankName()) : returnMoneyOutBean.getLianHangNo());
                count[0]++;
                totalMoney[0] += returnMoneyOutBean.getMoney().doubleValue();
            });
            String overMoney = moneyService.queryOverMoney();
            if(overMoney == null){
                return "代付余额查询失败！";
            }
            if(Double.parseDouble(overMoney) < count[0]*2+totalMoney[0]){
                return "余额不足，剩余资金为".concat(overMoney).concat("元！");
            }
            sb.insert(0, "".concat(count[0] +"").concat("|").concat(String.format("%1.02f", totalMoney[0])).concat("|").concat(moneyService.batchNo()));
            if(!ProjectConstants.PRO_DEBUG){
                String[] results = moneyService.batchOut(sb.toString());
                if(!"0".equals(results[0])){
//                    list.forEach(returnMoneyOutBean -> {
//                        returnMoney2User(returnMoneyOutBean);
//                        sendMessageToUser(returnMoneyOutBean.getUserId(), "您有一笔".concat(returnMoneyOutBean.getMoney().toString())
//                                .concat("元的佣金提现处理失败，流水号：").concat(returnMoneyOutBean.getSerialNo()));
//                    });
                    return "处理失败！".concat(results[1]);
                }
            }
        }
        return "成功处理".concat(count[0]+"笔提现！");
    }

    public String reviewPass(String ids){
        // 出金审核通过
        String[] idArr = null;
        if(StringUtils.isBlank(ids)){
            idArr = new String[0];
        }else{
            idArr = ids.split(",");
        }
        String batchOut = batchOut(idArr);
        if(batchOut.startsWith("成功处理") && idArr.length != 0){
            iReturnMoneyOutDao.pass(Arrays.asList(idArr));
        }
        return batchOut;
    }

    public String reviewNoPass(String ids, String failureReason){
        return baseService.reviewNoPass(ids, failureReason);
    }

}
