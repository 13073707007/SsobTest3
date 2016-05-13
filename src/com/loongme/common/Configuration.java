package com.loongme.common;

import java.io.File;

import android.os.Environment;

/**
 * @author 作者:zhaojianyu
 * @version 创建时间：2016-4-26 上午10:49:55 类说明:
 */
public class Configuration {
	/** App标记 */
	public final static String TAG = "SSBon";
	/** SD卡根路径 */
	public static final String SDCARD_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath();
	/** 业务逻辑文件缓存路径 */
	public static final String BUSINESS_CACHE_PATH = "business";
	/** 错误日志保存路径 */
	public static final String ERROR_LOG_DIR = SDCARD_PATH + File.separator
			+ TAG + File.separator + "errorlogs";
	/** 营业执照缓存路径 */
	public static final String BUSLICENSE_SAVEPATH = SDCARD_PATH
			+ File.separator + TAG + File.separator + "buslicense.png";
	/** 身份证正面 */
	public static final String IDCARD_MAINSIDE_SAVEPATH = SDCARD_PATH
			+ File.separator + TAG + File.separator + "idcard_mainside.png";
	/** 身份证反面 */
	public static final String IDCARD_REVERSESIDE_SAVEPATH = SDCARD_PATH
			+ File.separator + TAG + File.separator + "idcard_reverseside.png";
	/** 服务封面 */
	public static final String SERVER_COVER_SAVEPATH = SDCARD_PATH
			+ File.separator + TAG + File.separator + "server_cover.png";
	/** 工作照或作品图 */
	public static final String SERVER_WORK_SAVEPATH = SDCARD_PATH
			+ File.separator + TAG + File.separator + "server_work.png";

	// ****************************keys****************************
	public static final String SYSTEM_PRE = "SystemPreference";// 应用信息
	public static final String ACCOUNT = "AccountInfo";// 保存用户信息的key
	public static final String BONKE_STATUS = "BonkeStatus";// 保存申请帮客状态的key

	// ****************************RequestCode4Intent****************************
	public static final int SELECT_SERVER_COVER = 996;// 提交服务选择封面
	public static final int SELECT_SERVER_WORK = 997;// 提交服务选择工作照获作品图
	public static final int SELECT_IDCARD_MAINSIDE = 998;// 选择身份证正面
	public static final int SELECT_IDCARD_REVERSESIDEE = 999;// 选择身份证反面
	public static final int SELECT_LICENSE = 1000;// 选择营业执照
	public static final int REQUEST_BUSDES = 1001;// 填写商家描述
	public static final int REQUEST_BUSINTRODUCE = 1002;// 填写商家详细介绍
	public static final int REQUEST_PERSONAL_DES = 1003;// 填写个人描述
	public static final int REQUEST_PERSONAL_INTRODUCE = 1004;// 填写个人详细介绍
	public static final int REQUEST_APPLYBONKE = 1005;// 个人信息页点击申请帮客

	// ****************************API****************************
	public static final String Host = "172.16.80.136";// 正式服务器 测试：172.16.80.136
														// 正式：120.24.37.141
	public static final String Port = "8080";// 端口一样
	// 发布服务
	public static final String API_Submit_Server = "http://" + Host + ":"
			+ Port + "/LoginAndResigister/provideService";

}
