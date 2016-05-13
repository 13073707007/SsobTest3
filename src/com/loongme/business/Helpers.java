package com.loongme.business;

import java.io.File;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.loongme.com.model.User;
import com.loongme.common.BonkeStatus;
import com.loongme.common.Configuration;

/**
 * @author 作者:zhaojianyu
 * @version 创建时间：2016-4-26 上午11:22:40
 */
/**
 * @author Administrator
 * 
 *         弱业务处理帮助类
 */
public class Helpers {

	/**
	 * 保存用户信息到本地
	 */
	public static void saveUserInfoToLocal(Context context, User user) {
		SharedPreferences preferences = context.getSharedPreferences(
				Configuration.ACCOUNT, context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("userPhone", user.getUserPhone());
		editor.putString("userNickname", user.getUserNickname());
		editor.putString("userPassword", user.getUserPassword());
		editor.putString("userType", user.getUserType());
		editor.putString("relationType", user.getRelationType());
		editor.putString("relationAcct", user.getRelationAcct());
		editor.putString("relationIcon", user.getRelationIcon());
		editor.putString("userSex", user.getUserSex());
		editor.putString("userPicture", user.getUserPicture());
		editor.putInt("userId", user.getUserId());
		editor.putInt("auditStatus", user.getAuditStatus());
		editor.commit();

	}

	/**
	 * 保存帮客审核状态到本地
	 */
	public static void saveBonkeStatusToLocal(Context context,
			BonkeStatus bonkeStatus) {
		SharedPreferences preferences = context.getSharedPreferences(
				Configuration.SYSTEM_PRE, context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putInt(Configuration.BONKE_STATUS, bonkeStatus.getCode());
		editor.commit();

	}

	/**
	 * 获取帮客审核
	 */
	public static int getBonkeStatusFromLocal(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				Configuration.SYSTEM_PRE, context.MODE_PRIVATE);
		return preferences.getInt(Configuration.BONKE_STATUS, -1);
	}

	/**
	 * 得到用户信息
	 * 
	 * @param context
	 * @return
	 */
	public static User getUserInfo(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				Configuration.ACCOUNT, context.MODE_PRIVATE);
		String userPhone = preferences.getString("userPhone", "");
		String userNickname = preferences.getString("userNickname", "");
		String userPassword = preferences.getString("userPassword", "");
		String userType = preferences.getString("userType", "");
		String relationType = preferences.getString("relationType", "");
		String relationAcct = preferences.getString("relationAcct", "");
		String relationIcon = preferences.getString("relationIcon", "");
		String userSex = preferences.getString("userSex", "");
		String userPicture = preferences.getString("userPicture", "");
		int userId = preferences.getInt("userId", -1);
		int auditStatus = preferences.getInt("auditStatus", -1);
		User user = new User(userPhone, userNickname, userPassword, userType,
				relationType, relationAcct, relationIcon, userSex, userId,
				userPicture, auditStatus, null, null);
		return user;
	}

	/**
	 * 清空保存登录状态的文件
	 * 
	 * @param context
	 */
	public static void clearLoginStatusFile(Context context) {
		SharedPreferences settings = context.getSharedPreferences(
				Configuration.ACCOUNT, Context.MODE_PRIVATE);
		SharedPreferences.Editor editer = settings.edit();
		editer.clear();
		editer.commit();
	}

	/**
	 * 缓存业务逻辑数据
	 * 
	 * @param context
	 * @return
	 */
	public static String getBusinessCache(Context context) {
		User user = getUserInfo(context);
		return Configuration.SDCARD_PATH + File.separator + Configuration.TAG
				+ File.separator + user.getUserNickname() + File.separator
				+ "/business_cache";
	}

	/**
	 * 根据url和参数来生成一个key，用来在缓存中存储结果
	 * 
	 * @param urlAndParams
	 * @return
	 */
	public static String generateCacheKey(String urlAndParams) {
		return DiskCache.md5(DiskCache.md5(urlAndParams));
	}
}
