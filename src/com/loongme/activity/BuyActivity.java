package com.loongme.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.loongme.activity.R;
import com.loongme.base.BaseActivity;

public class BuyActivity extends BaseActivity {

	private View popupwidow;
	private View vLocation;
	private PopupWindow popselector;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_buy);
		initLayout();
	}

	/**
	 * 初始化页面
	 */
	private void initLayout() {
		// TODO Auto-generated method stub
		TextView tx = (TextView) findViewById(R.id.tv_title);
		tx.setText("购买服务");
		findViewById(R.id.btn_back).setVisibility(View.VISIBLE);
		findViewById(R.id.btn_back).setOnClickListener(onclick);
		findViewById(R.id.btn_setting).setVisibility(View.GONE);
		findViewById(R.id.buyButton).setOnClickListener(onclick);
	}

	/**
	 * 按钮单击监听
	 */
	OnClickListener onclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.buyButton:
				vLocation = v;
				initPopWindows(v);
				lnitClickListen();
				// Intent intent = new Intent();
				// intent.setClass(BuyActivity.this, PayAlipayActivity.class);
				// startActivity(intent);
				// finish();
				break;
			case R.id.btn_back:
				finish();
				break;
			}
		}
	};

	/**
	 * 初始化弹出窗口
	 * 
	 * @param v
	 */
	protected void initPopWindows(View v) {
		// TODO Auto-generated method stub
		if (popselector == null) {
			int temp;
			temp = this.getWindowManager().getDefaultDisplay().getWidth();
			int screenWidth = temp * 8 / 9;
			LayoutInflater mLayoutInflater = LayoutInflater.from(this);
			popupwidow = mLayoutInflater.inflate(R.layout.pay_choose, null);
			popselector = new PopupWindow(popupwidow, screenWidth, LayoutParams.WRAP_CONTENT, true);
		}
		ColorDrawable cd = new ColorDrawable(0x000000);
		popselector.setBackgroundDrawable(cd);
		popselector.showAtLocation((View) v.getParent(), Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
		// 产生背景变暗效果
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.4f;
		getWindow().setAttributes(lp);
		popselector.setOutsideTouchable(true);
		popselector.setFocusable(true);
		popselector.update();
		popselector.setOnDismissListener(new OnDismissListener() {
			/*  */
			public void onDismiss() {
				// TODO Auto-generated method stub
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
			}
		});
	}

	/**
	 * 设置按钮单击监听
	 */
	protected void lnitClickListen() {
		popupwidow.findViewById(R.id.pay_alipay).setOnClickListener(popOnclick);
		popupwidow.findViewById(R.id.pay_weixin).setOnClickListener(popOnclick);
	}

	/**
	 * 选择支付方式监听
	 */
	OnClickListener popOnclick = new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.pay_alipay:
				popselector.dismiss();
				Intent alipy = new Intent();
				alipy.setClass(BuyActivity.this, PayAlipayActivity.class);
				startActivity(alipy);
				finish();
				break;
			case R.id.pay_weixin:
				popselector.dismiss();
				finish();
				break;
			}
		}
	};
}
