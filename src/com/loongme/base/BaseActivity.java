package com.loongme.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.lidroid.xutils.exception.HttpException;
import com.loongme.activity.R;
import com.loongme.api.AppHttpResponseCallBack;
import com.loongme.view.CustomProgressDialog;

/**
 * @author 作者:zhaojianyu
 * @version 创建时间：2016-4-26 上午10:32:59 类说明:
 */
public class BaseActivity extends Activity implements OnClickListener,
		AppHttpResponseCallBack {

	protected CustomProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		progressDialog = new CustomProgressDialog(this, "加载中...",
				R.anim.loading);
		AppManager.getAppManager().addActivity(this);
		doinit();
	}

	private final void doinit() {
		initParas();
		initWidgets();
		regListener();
		initUI();
	}

	protected void initParas() {
		// 初始化参数，接收外界参数等
	}

	protected void initWidgets() {
		// 控件初始化
	}

	protected void regListener() {
		// 注册监听器
	}

	protected void initUI() {
		// 界面初始化
	}

	@Override
	protected void onStart() {
		// LogUtil.d(TAG, this.getClass().getSimpleName() +
		// " onStart() invoked!!");
		super.onStart();
	}

	@Override
	protected void onRestart() {
		// LogUtil.d(TAG, this.getClass().getSimpleName() +
		// " onRestart() invoked!!");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// LogUtil.d(TAG, this.getClass().getSimpleName() +
		// " onResume() invoked!!");

	}

	@Override
	protected void onPause() {
		super.onPause();
		// LogUtil.d(TAG, this.getClass().getSimpleName() +
		// " onPause() invoked!!");
	}

	@Override
	protected void onStop() {
		// LogUtil.d(TAG, this.getClass().getSimpleName() +
		// " onStop() invoked!!");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// LogUtil.d(TAG, this.getClass().getSimpleName() +
		// " onDestroy() invoked!!");
		AppManager.getAppManager().finishActivity(this);
		super.onDestroy();
	}

	public void finish() {
		super.finish();
		// 从右到左动画
		// overridePendingTransition(R.anim.push_right_in,
		// R.anim.push_right_out);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// 默认左上角的关闭
			this.finish();
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		return super.onMenuOpened(featureId, menu);
	}

	// @Override
	// public boolean handleSuccess(int statusCode, Header[] headers, JSONObject
	// jsonObject,int flagRequest) {
	// // 重写以处理成功情景
	// return true;
	// }
	//
	// @Override
	// public boolean handleFailure(int statusCode, Header[] headers, String
	// arg2, Throwable throwable,int flagRequest) {
	// // 重写以处理失败情景
	// return true;
	// }

	@Override
	public void handleStart(int flagRequest) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleSucess(int statusCode, String result, int flagRequest) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hanleFailure(HttpException exception, String arg1,
			int flagRequest) {
		// TODO Auto-generated method stub

	}
}
