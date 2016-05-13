package com.loongme.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.loongme.activity.R;
import com.loongme.base.BaseActivity;
import com.loongme.personage.GuestActivity;
import com.loongme.util.UIUtils;

/**
 * 商家描述
 * 
 * @author xywy
 * 
 */
public class MerDesActivity extends BaseActivity implements OnClickListener {
	private Button btn_sj_send;
	private ImageView iv_fanhumd;
	private EditText et_mer_des_three, et_mer_des_two, et_mer_des_one;
	private String merchant_describe;
	private String et_mer_des_one2;
	private String et_mer_des_two2;
	private String et_mer_des_three2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mer_des);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		et_mer_des_one = (EditText) findViewById(R.id.et_mer_des_one);
		et_mer_des_two = (EditText) findViewById(R.id.et_mer_des_two);
		et_mer_des_three = (EditText) findViewById(R.id.et_mer_des_three);
		btn_sj_send = (Button) findViewById(R.id.btn_sj_send);
		iv_fanhumd = (ImageView) findViewById(R.id.iv_fanhumd);
		iv_fanhumd.setOnClickListener(this);
		btn_sj_send.setOnClickListener(this);
		et_mer_des_one2 = et_mer_des_one.getText().toString();
		et_mer_des_two2 = et_mer_des_two.getText().toString();
		et_mer_des_three2 = et_mer_des_three.getText().toString();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_sj_send:
			merchant_describe = et_mer_des_one.getText().toString() + ","
					+ et_mer_des_two.getText().toString() + ","
					+ et_mer_des_three.getText().toString();
			// System.out.println("aaaaaa" + merchant_describe);
			// SharedPreferences sf2 = getSharedPreferences("merdes",
			// MODE_APPEND);
			// Editor editor2 = sf2.edit();
			// editor2.putString("busDesc", merchant_describe);
			// editor2.commit();
			// Intent it = new Intent();
			// it.setClass(this, GuestActivity.class);
			// startActivity(it);
			setResult(RESULT_OK,
					new Intent().putExtra("busDesc", merchant_describe));
			UIUtils.hideSoftKeyBoard(this, getCurrentFocus());
			// startActivity(new Intent(getApplicationContext(),
			// GuestActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			finish();
			break;
		case R.id.iv_fanhumd:
			UIUtils.hideSoftKeyBoard(this, getCurrentFocus());
			// startActivity(new Intent(getApplicationContext(),
			// GuestActivity.class));
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
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
			UIUtils.hideSoftKeyBoard(this, getCurrentFocus());
			startActivity(new Intent(getApplicationContext(),
					GuestActivity.class));
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
