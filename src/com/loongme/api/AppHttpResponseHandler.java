package com.loongme.api;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * @author 作者:zhaojianyu
 * @version 创建时间：2016-4-26 下午2:07:03 类说明:
 */
public class AppHttpResponseHandler extends RequestCallBack<String> {

	private AppHttpResponseCallBack callBack;
	private int flagRequest;

	public AppHttpResponseHandler(AppHttpResponseCallBack callBack, int flagRequest) {
		super();
		this.callBack = callBack;
		this.flagRequest = flagRequest;
	}

	@Override
	public void onStart() {
		super.onStart();
		callBack.handleStart(flagRequest);
	}

	@Override
	public void onSuccess(ResponseInfo<String> responseInfo) {
		// 做一下通用的逻辑处理

		// ......

		callBack.handleSucess(responseInfo.statusCode, responseInfo.result, flagRequest);
	}

	@Override
	public void onFailure(HttpException exception, String arg1) {

		callBack.hanleFailure(exception, arg1, flagRequest);
	}

}
