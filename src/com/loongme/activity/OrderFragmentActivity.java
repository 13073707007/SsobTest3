package com.loongme.activity;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loongme.activity.R;
import com.loongme.base.BaseActivity;
import com.loongme.view.AcceptFragment;
import com.loongme.view.SubsFragment;

public class OrderFragmentActivity extends BaseActivity implements
		OnClickListener {

	private AcceptFragment mTab01;
	private SubsFragment mTab02;
	/**
	 * 底部四个按钮
	 */
	private LinearLayout mTabBtnAccept;
	private LinearLayout mTabBtnSubs;
	/**
	 * 用于对Fragment进行管理
	 */
	private FragmentManager fragmentManager;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_order_new);
		initViews();
		fragmentManager = getFragmentManager();
		// 默认显示第一个界面
		setTabSelection(0);
	}

	private void initViews() {
		// 标题栏按钮
		this.findViewById(R.id.btn_back).setVisibility(View.VISIBLE);
		this.findViewById(R.id.btn_setting).setVisibility(View.GONE);
		this.findViewById(R.id.btn_back).setOnClickListener(
				new OnClickListener() {
					public void onClick(View arg0) {
						finish();
					}
				});
		// 选项卡
		mTabBtnAccept = (LinearLayout) findViewById(R.id.order_accept);
		mTabBtnSubs = (LinearLayout) findViewById(R.id.order_subs);
		// 设置选项卡监听
		mTabBtnAccept.setOnClickListener(this);
		mTabBtnSubs.setOnClickListener(this);
	}

	/**
	 * 选项卡单击执行操作
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.order_accept:
			setTabSelection(0);
			break;
		case R.id.order_subs:
			setTabSelection(1);
			break;
		default:
			break;
		}
	}

	/**
	 * 根据传入的index参数来设置选中的tab页。
	 * 
	 */
	private void setTabSelection(int index) {
		// 重置按钮
		resetBtn();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
		case 0:
			// 当点击了消息tab时，改变控件的图片和文字颜色
			mTabBtnAccept.setBackgroundResource(R.drawable.btn_tab_p_orange);
			((TextView) mTabBtnAccept.findViewById(R.id.order_accept_text))
					.setTextColor(getResources().getColor(R.color.orange_wrong));
			if (mTab01 == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				mTab01 = new AcceptFragment();
				transaction.add(R.id.id_content, mTab01);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(mTab01);
				// 判断mTab01是否需要刷新一遍
				if (mTab02 != null) {
					if (mTab02.getIsFresh()) {
						mTab01.freshInterface();
						mTab02.setIsFresh(false);
					}
				}
			}
			break;
		case 1:
			// 当点击了消息tab时，改变控件的图片和文字颜色
			mTabBtnSubs.setBackgroundResource(R.drawable.btn_tab_p_orange);
			((TextView) mTabBtnSubs.findViewById(R.id.order_subs_text))
					.setTextColor(getResources().getColor(R.color.orange_wrong));
			((TextView) mTabBtnSubs.findViewById(R.id.order_subs_text))
					.setText("发布的订单");
			if (mTab02 == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				mTab02 = new SubsFragment();
				transaction.add(R.id.id_content, mTab02);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(mTab02);
				// 判断mTab02是否需要刷新一遍
				if (mTab01 != null) {
					if (mTab01.getIsFresh()) {
						mTab02.freshInterface();
						mTab01.setIsFresh(false);
					}
				}
			}
			break;
		}
		transaction.commit();
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void resetBtn() {
		mTabBtnAccept.setBackgroundResource(R.color.white);
		((TextView) mTabBtnAccept.findViewById(R.id.order_accept_text))
				.setTextColor(getResources().getColor(R.color.black));

		mTabBtnSubs.setBackgroundResource(R.color.white);
		((TextView) mTabBtnSubs.findViewById(R.id.order_subs_text))
				.setTextColor(getResources().getColor(R.color.black));
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	@SuppressLint("NewApi")
	private void hideFragments(FragmentTransaction transaction) {
		if (mTab01 != null) {
			transaction.hide(mTab01);
		}
		if (mTab02 != null) {
			transaction.hide(mTab02);
		}
	}
}
