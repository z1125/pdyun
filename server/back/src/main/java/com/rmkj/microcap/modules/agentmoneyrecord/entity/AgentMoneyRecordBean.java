package com.rmkj.microcap.modules.agentmoneyrecord.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rmkj.microcap.common.bean.DataEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
* Created by Administrator on 2016-11-7.
*/
public class AgentMoneyRecordBean extends DataEntity {
    //流水号
    private String serialNo;
    //会员用户id
    private String agentId;
    //金额
    private BigDecimal money;
    //手续费
    private BigDecimal fee;
    //类型：0 充值 1 提现
    private Integer type;
    //第三方流水号
    private String thirdSerialNo;
    //状态 0 处理中 1 成功 2 失败
    private Integer status;
    //创建时间
    private Date createTime;
    //完成时间
    private Date completeTime;
    //姓名
    private String chnName;
    //银行名称
    private String bankName;
    //银行卡号
    private String bankAccount;
    //失败原因
    private String failureReason;
	//备注
	private String remark;
	//经纪人姓名
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSerialNo(String serialNo){
		this.serialNo=serialNo;
	}
	public String getSerialNo(){
		return this.serialNo;
	}
	public void setAgentId(String agentId){
		this.agentId=agentId;
	}
	public String getAgentId(){
		return this.agentId;
	}
	public void setMoney(BigDecimal money){
		this.money=money;
	}
	public BigDecimal getMoney(){
		return this.money;
	}
	public void setFee(BigDecimal fee){
		this.fee=fee;
	}
	public BigDecimal getFee(){
		return this.fee;
	}
	public void setType(Integer type){
		this.type=type;
	}
	public Integer getType(){
		return this.type;
	}
	public void setThirdSerialNo(String thirdSerialNo){
		this.thirdSerialNo=thirdSerialNo;
	}
	public String getThirdSerialNo(){
		return this.thirdSerialNo;
	}
	public void setStatus(Integer status){
		this.status=status;
	}
	public Integer getStatus(){
		return this.status;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime(){
		return this.createTime;
	}
	public void setCompleteTime(Date completeTime){
		this.completeTime=completeTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCompleteTime(){
		return this.completeTime;
	}
	public void setChnName(String chnName){
		this.chnName=chnName;
	}
	public String getChnName(){
		return this.chnName;
	}
	public void setBankName(String bankName){
		this.bankName=bankName;
	}
	public String getBankName(){
		return this.bankName;
	}
	public void setBankAccount(String bankAccount){
		this.bankAccount=bankAccount;
	}
	public String getBankAccount(){
		return this.bankAccount;
	}
	public void setFailureReason(String failureReason){
		this.failureReason=failureReason;
	}
	public String getFailureReason(){
		return this.failureReason;
	}

}
