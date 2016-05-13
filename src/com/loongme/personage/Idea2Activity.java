package com.loongme.personage;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.loongme.activity.R;
import com.loongme.base.BaseActivity;

/**
 * 意见反馈
 * 
 * @author xywy
 * 
 */
public class Idea2Activity extends BaseActivity implements OnClickListener {
	private ImageView iv_fanhuii2;
	private Button bt_idea_send2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_idea2);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		iv_fanhuii2 = (ImageView) findViewById(R.id.iv_fanhuii2);
		bt_idea_send2 = (Button) findViewById(R.id.bt_idea_send2);
		iv_fanhuii2.setOnClickListener(this);
		bt_idea_send2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_fanhuii2:
//			startActivity(new Intent(getApplicationContext(), MeGuset2Activity.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
			break;
		case R.id.bt_idea_send2:
//			startActivity(new Intent(getApplicationContext(),
//					MeGusetActivity.class));
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
//			startActivity(new Intent(getApplicationContext(), MeGuset2Activity.class));
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
