package com.loongme.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.loongme.activity.R;
import com.loongme.base.BaseActivity;
import com.loongme.personage.MeGuset2Activity;
import com.loongme.personage.ReleaseActivity;

/**
 * 发布服务
 * 
 * @author xywy
 * 
 */
public class Server2Activity extends BaseActivity implements OnClickListener {
	ImageView iv_fanhuise2;
	Button server_send2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_server2);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		iv_fanhuise2 = (ImageView) findViewById(R.id.iv_fanhuise2);
		server_send2 = (Button) findViewById(R.id.server_send2);
		iv_fanhuise2.setOnClickListener(this);
		server_send2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_fanhuise2:
			startActivity(new Intent(getApplicationContext(),
					ReleaseActivity.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
			break;
		case R.id.server_send2:
			startActivity(new Intent(getApplicationContext(),
					MeGuset2Activity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
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
					ReleaseActivity.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
