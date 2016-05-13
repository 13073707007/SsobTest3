package com.loongme.util;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

public class GetUserNumber {
	/**
	 * 获取用户sim卡信息，手机号，运营商
	 * 
	 * @author xiexs
	 */
	public String getNumber(Context mContext, String TAG) {
		String yunyingshang = null;
		TelephonyManager tele = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		//String info = tele.getSubscriberId();
		//System.out.println(info);
		/*if (info.startsWith("46000") || info.startsWith("46002")) {
			yunyingshang = "中国移动";
		} else if (info.startsWith("46001")) {
			yunyingshang = "中国联通";
		} else if (info.startsWith("46003")) {
			yunyingshang = "中国电信";
		}*/
		//Log.e(TAG, "运营商是 " + yunyingshang);

		String number = tele.getLine1Number();
		if (number == null) {
			Log.e(TAG, "number is null");
		} else {
			Log.e(TAG, "NUMBER IS " + number);
		}
		return number;
	}
}
