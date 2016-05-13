package com.loongme.com.model;

public class Data {
	private int iv1;
	private String title;
	private String data;
	private String message;
	public int getIv1() {
		return iv1;
	}
	public void setIv1(int iv1) {
		this.iv1 = iv1;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Data(int iv1, String title, String data, String message) {
		super();
		this.iv1 = iv1;
		this.title = title;
		this.data = data;
		this.message = message;
	}

}
