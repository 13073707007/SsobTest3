package com.loongme.personage;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import com.loongme.activity.R;
import com.loongme.adapter.MyAdapterL;
import com.loongme.base.BaseActivity;
import com.loongme.com.model.Data;

/**
 * 通知消息
 * 
 * @author xywy
 * 
 */
public class MessageActivity extends BaseActivity implements OnClickListener {
	private ListView lv;
	private List<Data> list;
	private MyAdapterL adapter;
	private ImageView iv_fanhuim1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_message);
		// lv = (ListView) findViewById(R.id.lv);
		// adapter = new MyAdapterL(this);
		// lv.setAdapter(adapter);
		// getData();
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		iv_fanhuim1 = (ImageView) findViewById(R.id.iv_fanhuim1);
		iv_fanhuim1.setOnClickListener(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startActivity(new Intent(getApplicationContext(), PersonageActivity.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_fanhuim1:
			startActivity(new Intent(getApplicationContext(), PersonageActivity.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
			break;

		default:
			break;
		}
	}
}
