package com.rmkj.microcap.modules.returnFeeMoney.entity;/**
 * Created by Administrator on 2017/9/21.
 */

import com.rmkj.microcap.common.bean.DataEntity;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 返手续费提现类
 * @author k
 * @create -09-21-16:15
 **/

public class TradeReturnFeeRecord extends DataEntity{

    //流水号
    private String serialNo;
    //提现金额
    @Range(min = 1, message = "提现金额最低为1元")
    @NotNull
    private BigDecimal money;
    //提现手续费
    private BigDecimal fee;
    //提现区分：1:市场管理部、2:会员单位、3:代理商
    private String type;
    //第三方流水号
    private String thirdSerialNo;
    //渠道
    private String channel;
    //状态 0 处理中 1 成功 2 失败
    private String status;
    //审核状态 0 处理中 1 成功 2 失败
    private String reviewStatus;
    //审核时间
    private Date reviewTime;
    //备注
    private String remark;
    //创建时间
    private Date createTime;

    //完成时间
    private Date completeTime;
    //姓名
    @NotNull
    private String chnName;
    //银行卡号
    @NotNull
    private String bankAccount;
    //银行名称
    @NotNull
    private String bankName;
    //开户行名称
    @NotNull
    private String openBankName;
    //省份
    private String province;
    //城市
    private String city;
    //失败原因
    private String failureReason;
    //联行号
    private String lianHangNo;
    //市场管理部id
    private String centerId;
    //会员单位id
    private String unitsId;
    //代理商id
    private String agentId;

    private String feeType;

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    //角色id 1:市场管理部 4:代理商 5:会员单位
    private String roleId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getType() {
        return type;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getThirdSerialNo() {
        return thirdSerialNo;
    }

    public void setThirdSerialNo(String thirdSerialNo) {
        this.thirdSerialNo = thirdSerialNo;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public String getChnName() {
        return chnName;
    }

    public void setChnName(String chnName) {
        this.chnName = chnName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getOpenBankName() {
        return openBankName;
    }

    public void setOpenBankName(String openBankName) {
        this.openBankName = openBankName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getLianHangNo() {
        return lianHangNo;
    }

    public void setLianHangNo(String lianHangNo) {
        this.lianHangNo = lianHangNo;
    }

    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public String getUnitsId() {
        return unitsId;
    }

    public void setUnitsId(String unitsId) {
        this.unitsId = unitsId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}
