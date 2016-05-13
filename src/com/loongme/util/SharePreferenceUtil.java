package com.loongme.util;



import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtil {
	private Context context;

	public SharePreferenceUtil(Context context) {
		this.context = context;
	}

	public SharedPreferences getPreferences(String SPName) {
		return context.getSharedPreferences(SPName, 0);
	}

	public void setPreferences(String SPName, String param, Object value) {
		String className = value.getClass().getSimpleName();
		if (className.equals("String")) {
			getPreferences(SPName).edit()
					.putString(param, String.valueOf(value)).commit();
		} else if (className.equals("Integer") || className.equals("int")) {
			getPreferences(SPName).edit()
					.putInt(param, Integer.parseInt(String.valueOf(value)))
					.commit();
		} else if (className.equals("Float")) {
			getPreferences(SPName).edit()
					.putFloat(param, Float.parseFloat(String.valueOf(value)))
					.commit();
		} else if (className.equals("Long")) {
			getPreferences(SPName).edit()
					.putLong(param, Long.parseLong(String.valueOf(value)))
					.commit();
		} else if (className.equals("Boolean")) {
			getPreferences(SPName)
					.edit()
					.putBoolean(param,
							Boolean.parseBoolean(String.valueOf(value)))
					.commit();
		}
	}
	

    
}
