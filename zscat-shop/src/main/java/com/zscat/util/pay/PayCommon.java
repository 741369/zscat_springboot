package com.zscat.util.pay;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * @author ihui
 * 支付公用参数
 */

public class PayCommon implements Serializable{
  /**
   * 支付订单号
   */
  private String outTradeNo;
  /**
   * 服务器异步通知页面路径
   */
  private String notifyUrl;
  /**
   * 服务器同步通知页面路径
   */
  private String returnUrl;
  /**
	* 支付金额
	*/
  private BigDecimal payAmount;
public String getOutTradeNo() {
	return outTradeNo;
}
public void setOutTradeNo(String outTradeNo) {
	this.outTradeNo = outTradeNo;
}
public String getNotifyUrl() {
	return notifyUrl;
}
public void setNotifyUrl(String notifyUrl) {
	this.notifyUrl = notifyUrl;
}
public String getReturnUrl() {
	return returnUrl;
}
public void setReturnUrl(String returnUrl) {
	this.returnUrl = returnUrl;
}
public BigDecimal getPayAmount() {
	return payAmount;
}
public void setPayAmount(BigDecimal payAmount) {
	this.payAmount = payAmount;
}

  
  
}
