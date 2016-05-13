package com.loongme.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.loongme.activity.R;
import com.loongme.adapter.ViewPagerAdapter;
import com.loongme.base.BaseActivity;
import com.loongme.enter.EnterActivity;
import com.loongme.enter.PhoneActivity;

public class GuidePageActivity extends BaseActivity implements
		OnPageChangeListener {
	private ViewPagerAdapter vpAdapter;
	private ViewPager viewpager;
	private List<View> views;
	private ImageView[] dots;
	private ImageView start_into;
	private Button btn_view_register, btn_view_login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guidepage);
		initView();
		// initdot();
	}

	private void initView() {
		LayoutInflater inflater = LayoutInflater.from(this);

		views = new ArrayList<View>();
		views.add(inflater.inflate(R.layout.activity_viewpager_one, null));
		views.add(inflater.inflate(R.layout.activity_viewpage_two, null));
		views.add(inflater.inflate(R.layout.activity_viewpager_three, null));
		views.add(inflater.inflate(R.layout.activity_viewpager_four, null));
		vpAdapter = new ViewPagerAdapter(views, this);
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		viewpager.setAdapter(vpAdapter);
		start_into = (ImageView) views.get(3).findViewById(R.id.iv_four);
		btn_view_login = (Button) views.get(3)
				.findViewById(R.id.btn_view_login);
		btn_view_register = (Button) views.get(3).findViewById(
				R.id.btn_view_register);
		btn_view_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent startIntent1 = new Intent(GuidePageActivity.this,
						EnterActivity.class);
				startActivity(startIntent1);
				finish();
			}
		});
		btn_view_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent startIntent2 = new Intent(GuidePageActivity.this,
						PhoneActivity.class);
				startActivity(startIntent2);
				finish();
			}
		});
		start_into.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// SharedPreferences sp = getSharedPreferences(CST.WELCOME, 0);
				// Editor editor = sp.edit();
				// editor.putBoolean(CST.ISFIRSTIN, false);
				// editor.commit();
				Intent startIntent = new Intent(GuidePageActivity.this,
						UnderstanderDemo.class);
				startActivity(startIntent);
				finish();

			}
		});

		viewpager.setOnPageChangeListener(this);
	}

	/*
	 * private void initdot() {
	 * 
	 * dots = new ImageView[views.size()]; for(int i=0;i<views.size();i++) {
	 * dots[i] = (ImageView)findViewById(ids[i]); } }
	 */

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

	}

}
