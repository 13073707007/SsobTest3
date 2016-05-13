package com.loongme.api;

import com.lidroid.xutils.exception.HttpException;

/**
 * @author 作者:zhaojianyu
 * @version 创建时间：2016-4-26 下午2:10:16 类说明:
 */
public interface AppHttpResponseCallBack {

	public void handleStart(int flagRequest);

	/**
	 * 成功
	 * 
	 * @param statusCode
	 *            状态码
	 * @param result
	 *            结果字符串
	 * @param flagRequest
	 *            请求标记
	 */
	public void handleSucess(int statusCode, String result, int flagRequest);

	/**
	 * 失败
	 */
	public void hanleFailure(HttpException exception, String arg1, int flagRequest);
}
