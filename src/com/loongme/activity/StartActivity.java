package com.loongme.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

import com.loongme.activity.R;
import com.loongme.base.BaseActivity;

public class StartActivity extends BaseActivity {
	SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				start();
			}
		}, 500);
	}
	@SuppressLint("WorldReadableFiles")
	@SuppressWarnings("deprecation")
	private void start() {
		// 读取SharedPreferences中需要的数据
		preferences = getSharedPreferences("count", MODE_WORLD_READABLE);
		int count = preferences.getInt("count", 0);
		Editor editor =preferences.edit();
		// 判断程序与第几次运行，如果是第一次运行则跳转到引导页面
		if (count == 0) {
			Intent intent = new Intent();
			intent.setClass(StartActivity.this, GuidePageActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			finish();
		} else {
			Intent intent = new Intent();
			intent.setClass(StartActivity.this, UnderstanderDemo.class);
			startActivity(intent);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			finish();
		}
		// 存入数据
		editor.putInt("count", ++count);
		// 提交修改
		editor.commit();
	}
	/**
	 * 当设置开屏可点击时，需要等待跳转页面关闭后，再切换至您的主窗口。故此时需要增加waitingOnRestart判断。
	 * 另外，点击开屏还需要在onRestart中调用jumpWhenCanClick接口。
	 */
	public boolean waitingOnRestart = false;

	private void jumpWhenCanClick() {
		Log.d("test", "this.hasWindowFocus():" + this.hasWindowFocus());
		if (this.hasWindowFocus() || waitingOnRestart) {
			start();
		} else {
			waitingOnRestart = true;
		}

	}

}
