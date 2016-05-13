package com.loongme.api;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * @author 作者:zhaojianyu
 * @version 创建时间：2016-4-26 下午1:38:56 类说明:
 */
public class AppHttpClient {

	private static AppHttpClient instance;

	private AppHttpClient() {
		super();
	}

	public static AppHttpClient getInstacne() {
		if (instance == null) {
			instance = new AppHttpClient();
		}
		return instance;
	}

	public void load(String url, RequestParams params,
			AppHttpResponseCallBack callBack, int flagRequest) {
		HttpUtils httpUtils = new HttpUtils(50000);
		AppHttpResponseHandler handler = new AppHttpResponseHandler(callBack,
				flagRequest);
		if (params == null) {
			httpUtils.send(HttpMethod.GET, url, handler);
		} else {
			httpUtils.send(HttpMethod.POST, url, handler);
		}

	}

}
