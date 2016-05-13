package com.loongme.util;

import java.util.UUID;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class DeviceUtil {
	
	private static final String tag = "DeviceUtil";
	private Context context;

	/**
	 * deviceID的组成为：渠道标志+识别符来源标志+hash后的终端识别符
	 * 
	 * 渠道标志为： 1，andriod（a）
	 * 
	 * 识别符来源标志： 1， wifi mac地址（wifi）； 2， IMEI（imei）； 3， 序列号（sn）； 4，
	 * id：随机码。若前面的都取不到时，则随机生成一个随机码，需要缓存。
	 * 
	 * @param context
	 * @return
	 */
	public static String getDeviceId(Context context) {

		StringBuilder deviceId = new StringBuilder();
		// 渠道标志
//		deviceId.append("a");
		try {
			
			// IMEI（imei）
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			String imei = tm.getDeviceId();
			if (!isEmpty(imei)) {
//				deviceId.append("imei");
				deviceId.append(imei);
				Log.e("getDeviceId : ", deviceId.toString());
				return deviceId.toString();
			}

			// wifi mac地址
			WifiManager wifi = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = wifi.getConnectionInfo();
			String wifiMac = info.getMacAddress();
			if (!isEmpty(wifiMac)) {
//				deviceId.append("wifi");
				deviceId.append(wifiMac);
				Log.e("getDeviceId : ", deviceId.toString());
				return deviceId.toString();
			}

			// 序列号（sn）
			String sn = tm.getSimSerialNumber();
			if (!isEmpty(sn)) {
//				deviceId.append("sn");
				deviceId.append(sn);
				Log.e("getDeviceId : ", deviceId.toString());
				return deviceId.toString();
			}

			// 如果上面都没有， 则生成一个id：随机码
			String uuid = getUUID(context);
			if (!isEmpty(uuid)) {
				deviceId.append("id");
				deviceId.append(uuid);
				Log.e("getDeviceId : ", deviceId.toString());
				return deviceId.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			deviceId.append("id").append(getUUID(context));
		}

		Log.e("getDeviceId : ", deviceId.toString());

		return deviceId.toString();

	}

	
	private static boolean isEmpty(String str){
		if(str != null){
			if("".equals(str)){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	}
	
	 public static String getUUID(Context context){
			SharePreferenceUtil sp = new SharePreferenceUtil(context);
			String s = sp.getPreferences("UUID").getString("UUID", "");
			if("".equals(s)){
				s = UUID.randomUUID().toString();
				sp.setPreferences("UUID", "UUID", s);
			}
	        //去掉“-”符号
	        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
	    }

}
