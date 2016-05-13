package com.loongme.util;

public class WXPay {
	// PayRep
	/** 商家在微信开放平台申请的应用id */
	public String appId;
	/** 商户id */
	public String partnerId;
	/** 预支付订单 */
	public String prepayId;
	/** 随机串，防重发 */
	public String nonceStr;
	/** 时间戳，防重发 */
	public String timeStamp;
	/** 商家根据文档填写的数据和签名 */
	public String packageValue;
	/** 商家根据微信开放平台文档对数据做的签名 */
	public String sign;
	// PayResp
	/** 预支付订单 */
	// public String prepayId;
	/** 返回给商家的信息 */
	public String returnKey;
	/**
	 * 第三方app自定义字符串，微信不作解析，在回调时带回给第三方
	 * 
	 * 注意：字符串长度不能超过1024
	 */
	public String extData;

}
