package com.loongme.personage;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.loongme.activity.R;
import com.loongme.base.BaseActivity;

public class Message2Activity extends BaseActivity implements OnClickListener {
	private ImageView iv_fanhuim2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_message2);
		// lv = (ListView) findViewById(R.id.lv);
		initView();
		// adapter = new MyAdapterL(this);
		// lv.setAdapter(adapter);
		// getData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		iv_fanhuim2 = (ImageView) findViewById(R.id.iv_fanhuim2);
		iv_fanhuim2.setOnClickListener(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startActivity(new Intent(getApplicationContext(), MeGuset2Activity.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_fanhuim2:
//			startActivity(new Intent(getApplicationContext(), MeGuset2Activity.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
			break;

		default:
			break;
		}
	}
}
