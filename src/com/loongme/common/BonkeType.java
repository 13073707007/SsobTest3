package com.loongme.common;

/**
 * @author 作者:zhaojianyu
 * @version 创建时间：2016-4-26 下午6:49:25 类说明:申请帮客的用户类型
 */
public enum BonkeType {

	Personal("个人", 1), Merchant("商家", 2);

	private String vaule;
	private int code;

	private BonkeType(String vaule, int code) {
		this.vaule = vaule;
		this.code = code;
	}

	public static String valueByCode(int code) {
		for (BonkeType e : values()) {
			if (e.code == code) {
				return e.vaule;
			}
		}

		return null;
	}

	public String getVaule() {
		return vaule;
	}

	public void setVaule(String vaule) {
		this.vaule = vaule;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
