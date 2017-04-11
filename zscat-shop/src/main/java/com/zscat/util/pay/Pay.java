package com.zscat.util.pay;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 支付信息实体
 * @author zscat
 */

public class Pay  implements Serializable{
	
	/**
	 * 单号
	 */
	private String paySn;
	
	/**
	 * 支付金额
	 */
	private BigDecimal payAmount;
	
	
	/**
	 * 支付描述
	 */
	private String beWrite;
	
	/**
	 * 支付订单状态
	 */
	private Integer orderState;
	
	/**
	 * 支付状态:0:未支付,1:已支付
	 */
	private Integer payState;

	public String getPaySn() {
		return paySn;
	}

	public void setPaySn(String paySn) {
		this.paySn = paySn;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public String getBeWrite() {
		return beWrite;
	}

	public void setBeWrite(String beWrite) {
		this.beWrite = beWrite;
	}

	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public Integer getPayState() {
		return payState;
	}

	public void setPayState(Integer payState) {
		this.payState = payState;
	}
	
}
