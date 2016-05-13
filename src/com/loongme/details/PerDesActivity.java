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
import com.loongme.util.UIUtils;

/***
 * 自我评价
 * 
 * @author xywy
 * 
 */
public class PerDesActivity extends BaseActivity implements OnClickListener {
	private Button btn_gr_send;
	private ImageView iv_fanhupd;
	private EditText editText1, editText2, editText3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_per_des);
		initView();
	}

	private void initView() {
		btn_gr_send = (Button) findViewById(R.id.btn_gr_send);
		iv_fanhupd = (ImageView) findViewById(R.id.iv_fanhupd);
		editText1 = (EditText) findViewById(R.id.editText1);
		editText2 = (EditText) findViewById(R.id.editText2);
		editText3 = (EditText) findViewById(R.id.editText3);
		iv_fanhupd.setOnClickListener(this);
		btn_gr_send.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_gr_send:
			// startActivity(new Intent(getApplicationContext(),
			// GuestActivity.class));n
			String des = editText1.getText().toString() + ","
					+ editText2.getText().toString() + ","
					+ editText3.getText().toString();
			setResult(RESULT_OK, new Intent().putExtra("persDesc", des));
			UIUtils.hideSoftKeyBoard(this, getCurrentFocus());
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			finish();
			break;
		case R.id.iv_fanhupd:
			UIUtils.hideSoftKeyBoard(this, getCurrentFocus());
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
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
