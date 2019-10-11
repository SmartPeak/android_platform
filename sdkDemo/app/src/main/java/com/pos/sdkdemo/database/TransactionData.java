package com.pos.sdkdemo.database;

import android.util.Log;

import com.basewin.database.model.BaseModel;

/**
 * Copyright © 2016 盛本信息科技有限公司. All rights reserved.
 * 
 * @ClassName: TransactionData
 * @Description: TODO
 * @author: liudeyu
 * @date: 2016年3月7日 下午11:07:13
 */
public class TransactionData extends BaseModel {
	private String trace; /* 流水号 */

	private String merchant_name; /* 商户名称 */

	private String merchant_no; /* 商户号 */

	private String terminal_no; /* 终端号 */

	private int func; /* 交易类型 消费，撤销，退货 */

	private String card_number; /* 卡号 */

	private String operatorNo; /* 操作员号 */

	private String exp_date; /* 有效期 */

	private String batch_no; /* 批次号 */

	private String auth_no; /* 授权号 */

	private String date_time; /* 交易时间 */

	private String amount; /* 交易金额 */

	private String ticket_no; /* 票据号 */

	private String referenceNo; /* 参考号 */

	private int status; /* 状态 */
	


	/**
	 * @return the trace
	 */
	public String getTrace() {
		return trace;
	}

	/**
	 * @param trace
	 *            the trace to set
	 */
	public void setTrace(String trace) {
		this.trace = trace;
	}

	/**
	 * @return the merchant_name
	 */
	public String getMerchant_name() {
		return merchant_name;
	}

	/**
	 * @param merchant_name
	 *            the merchant_name to set
	 */
	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}

	/**
	 * @return the merchant_no
	 */
	public String getMerchant_no() {
		return merchant_no;
	}

	/**
	 * @param merchant_no
	 *            the merchant_no to set
	 */
	public void setMerchant_no(String merchant_no) {
		this.merchant_no = merchant_no;
	}

	/**
	 * @return the terminal_no
	 */
	public String getTerminal_no() {
		return terminal_no;
	}

	/**
	 * @param terminal_no
	 *            the terminal_no to set
	 */
	public void setTerminal_no(String terminal_no) {
		this.terminal_no = terminal_no;
	}

	/**
	 * @return the func
	 */
	public int getFunc() {
		return func;
	}

	/**
	 * @param func
	 *            the func to set
	 */
	public void setFunc(int func) {
		this.func = func;
	}

	/**
	 * @return the card_number
	 */
	public String getCard_number() {
		return card_number;
	}

	/**
	 * @param card_number
	 *            the card_number to set
	 */
	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}

	/**
	 * @return the operatorNo
	 */
	public String getOperatorNo() {
		return operatorNo;
	}

	/**
	 * @param operatorNo
	 *            the operatorNo to set
	 */
	public void setOperatorNo(String operatorNo) {
		this.operatorNo = operatorNo;
	}

	/**
	 * @return the exp_date
	 */
	public String getExp_date() {
		return exp_date;
	}

	/**
	 * @param exp_date
	 *            the exp_date to set
	 */
	public void setExp_date(String exp_date) {
		this.exp_date = exp_date;
	}

	/**
	 * @return the batch_no
	 */
	public String getBatch_no() {
		return batch_no;
	}

	/**
	 * @param batch_no
	 *            the batch_no to set
	 */
	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	/**
	 * @return the auth_no
	 */
	public String getAuth_no() {
		return auth_no;
	}

	/**
	 * @param auth_no
	 *            the auth_no to set
	 */
	public void setAuth_no(String auth_no) {
		this.auth_no = auth_no;
	}

	/**
	 * @return the date_time
	 */
	public String getDate_time() {
		return date_time;
	}

	/**
	 * @param date_time
	 *            the date_time to set
	 */
	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the ticket_no
	 */
	public String getTicket_no() {
		return ticket_no;
	}

	/**
	 * @param ticket_no
	 *            the ticket_no to set
	 */
	public void setTicket_no(String ticket_no) {
		this.ticket_no = ticket_no;
	}

	/**
	 * @return the referenceNo
	 */
	public String getReferenceNo() {
		Log.d("pGlobalTransactionData", "获取参考号 = " + referenceNo);
		return referenceNo;
	}

	/**
	 * @param referenceNo
	 *            the referenceNo to set
	 */
	public void setReferenceNo(String referenceNo) {
		Log.d("GlobalTransactionData", "设置参考号 = " + referenceNo);
		this.referenceNo = referenceNo;
	}

	public void clear() {
		setAmount("");
		setAuth_no("");
		setBatch_no("");
		setCard_number("");
		setDate_time("");
		setExp_date("");
		setFunc(-1);
		setMerchant_name("");
		setMerchant_no("");
		setOperatorNo("");
		setReferenceNo("");
		setTerminal_no("");
		setTicket_no("");
		setTrace("");
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/*
	 * (non Javadoc)
	 * 
	 * @Title: toString
	 * 
	 * @Description: TODO
	 * 
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TransactionData [trace=" + trace + ", merchant_name=" + merchant_name + ", merchant_no=" + merchant_no + ", terminal_no=" + terminal_no + ", func=" + func + ", card_number=" + card_number + ", operatorNo=" + operatorNo + ", exp_date=" + exp_date + ", batch_no=" + batch_no + ", auth_no=" + auth_no + ", date_time=" + date_time + ", amount=" + amount + ", ticket_no=" + ticket_no + ", referenceNo=" + referenceNo  + ", status=" + status + "]";
	}

}
