package com.loongme.personage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.loongme.activity.R;
import com.loongme.adapter.MyAdapterM2;
import com.loongme.base.AppManager;
import com.loongme.fragment.ClassifyFragment2;

public class Release2Activity extends FragmentActivity implements
		OnItemClickListener, OnClickListener {
	/*
	 * 服务分类
	 */
	private String[] strs = { "心理师", "医生", "家政", "美食", "娱乐", "旅游", "汽车", "商务",
			"教育", "丽人", "摄影", "装修", "其他" };
	private ListView llistview;
	private MyAdapterM2 adapter;
	private ClassifyFragment2 classifyfragment;
	public static int mPosition;
	ImageView iv_fanhuire;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_release2);
		AppManager.getAppManager().addActivity(this);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		iv_fanhuire = (ImageView) findViewById(R.id.iv_fanhuire);
		iv_fanhuire.setOnClickListener(this);
		llistview = (ListView) findViewById(R.id.llistview);
		adapter = new MyAdapterM2(this, strs);
		llistview.setAdapter(adapter);
		llistview.setOnItemClickListener(this);
		classifyfragment = new ClassifyFragment2();
		FragmentTransaction fragmentTransaction = getSupportFragmentManager()
				.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_container, classifyfragment);
		Bundle bundle = new Bundle();
		bundle.putString(ClassifyFragment2.TAG2, strs[mPosition]);
		classifyfragment.setArguments(bundle);
		fragmentTransaction.commit();

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		mPosition = position;
		adapter.notifyDataSetChanged();
		for (int i = 0; i < strs.length; i++) {
			classifyfragment = new ClassifyFragment2();
			FragmentTransaction fragmentTransaction = getSupportFragmentManager()
					.beginTransaction();
			fragmentTransaction.replace(R.id.fragment_container,
					classifyfragment);
			Bundle bundle = new Bundle();
			bundle.putString(ClassifyFragment2.TAG2, strs[position]);
			classifyfragment.setArguments(bundle);
			fragmentTransaction.commit();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_fanhuire:
			startActivity(new Intent(getApplicationContext(),
					MeGuset2Activity.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startActivity(new Intent(getApplicationContext(),
					MeGuset2Activity.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		AppManager.getAppManager().finishActivity(this);
		super.onDestroy();
	}
}
