package com.loongme.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.loongme.activity.R;
import com.loongme.base.BaseActivity;
import com.loongme.enter.EnterActivity;
import com.loongme.enter.ForpwdActivity;

public class ResetActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_fanc;
	private Button btn_loginc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_reset);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		iv_fanc = (ImageView) findViewById(R.id.iv_fanc);
		btn_loginc = (Button) findViewById(R.id.btn_loginc);
		iv_fanc.setOnClickListener(this);
		btn_loginc.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_fanc:
			startActivity(new Intent(getApplicationContext(),
					ForpwdActivity.class));
			finish();
			break;
		case R.id.btn_loginc:
			startActivity(new Intent(getApplicationContext(),
					EnterActivity.class));
			finish();
			break;

		default:
			break;
		}
	}

}
