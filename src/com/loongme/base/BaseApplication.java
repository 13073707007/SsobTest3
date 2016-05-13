package com.loongme.base;

import java.io.File;

import android.app.Application;
import android.content.Context;

import com.iflytek.cloud.SpeechUtility;
import com.loongme.activity.R;
import com.loongme.common.Configuration;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * @author 作者:zhaojianyu
 * @version 创建时间：2016-4-26 下午1:22:50 类说明:
 */
public class BaseApplication extends Application {

	private static BaseApplication instance;

	// 单例模式中获取唯一的ExitApplication实例
	public static BaseApplication getInstance() {
		if (null == instance) {
			instance = new BaseApplication();
		}
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// 崩溃处理器，会在硬件上保持崩溃日志
		CrashHandler.getInstance().init(getApplicationContext());

		// 初始化UniversalImageLoader
		initImageLoader(getApplicationContext());

		// 初始化缓存路径
		initCacheDire();

		// 应用程序入口处调用,避免手机内存过小，杀死后台进程,造成SpeechUtility对象为null
		// 注意：此接口在非主进程调用会返回null对象，如需在非主进程使用语音功能，请增加参数：SpeechConstant.FORCE_LOGIN+"=true"
		// 参数间使用“,”分隔。
		// 设置你申请的应用appid
		SpeechUtility.createUtility(getApplicationContext(), "appid="
				+ getString(R.string.app_id));
	}

	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024)
				// 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	public static void initCacheDire() {
		File file = new File(Configuration.SDCARD_PATH + File.separator
				+ Configuration.TAG);
		if (!file.exists()) {
			file.mkdir();
		}
	}

}
