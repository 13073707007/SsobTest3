package com.loongme.com.model;

import java.io.Serializable;
import java.util.List;

public class chatBean implements Serializable {
	private static final String TAG = "chatBean";

	/**
	 * 聊天人姓名
	 */
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String url;
	public String textTitle;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTextTitle() {
		return textTitle;
	}

	public void setTextTitle(String textTitle) {
		this.textTitle = textTitle;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public String getTextAddress() {
		return textAddress;
	}

	public void setTextAddress(String textAddress) {
		this.textAddress = textAddress;
	}
	public String textContent;
	public String textAddress;
	private List<String[]> data;

	public List<String[]> getData() {
		return data;
	}

	public void setData(List<String[]> data) {
		this.data = data;
	}

	/**
	 * 消息发送时间
	 */
	private String date;
	/**
	 * 消息内容
	 */
	private String text;
	/**
	 * true为机器人的应答消息，false为我的提问
	 */
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	private String userPicture;

	public String getUserPicture() {
		return userPicture;
	}

	public void setUserPicture(String userPicture) {
		this.userPicture = userPicture;
	}
	

}
