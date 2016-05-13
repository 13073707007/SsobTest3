package com.loongme.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class ServerUtil {
	private Handler hd;
	public ServerUtil(Handler hd) {
		super();
		this.hd = hd;
	}
	//下载文本数据
	public void downloadData(final  String url) {
		new Thread() {
			public void run() {
				String data = null;
				Message msg=new Message();
				try {
					// 启动httpclient
					HttpClient client = new DefaultHttpClient();
					// 创建请求对象
					HttpGet req = new HttpGet(url);
					// 发送请求,获取服务器返回的数据
					HttpResponse res = client.execute(req);
					HttpEntity entity = res.getEntity();
					// 转化为String
					data = EntityUtils.toString(entity);
					//需要判断三种状态
                    if (data==null) {
                    	msg.what=0;
					}else{
						msg.what=1;
						msg.obj=data;
					}
				} catch (Exception e) {
					msg.what=-1;
					e.printStackTrace();
				}
				hd.sendMessage(msg);
			};
		}.start();

		 
	}
}
