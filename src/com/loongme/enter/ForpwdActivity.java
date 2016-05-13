package com.loongme.enter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.loongme.activity.R;
import com.loongme.base.BaseActivity;

public class ForpwdActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_fanb;
	private Button btn_stepa, btn_cordb;
	private EditText et_phoneb, et_cordb;
	private TextView nowb;

	private String iPhone;
	private String iCord;
	private int time = 60;
	private boolean flag = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_forpwd);
		initView();
		initSDK();
	}

	private void initSDK() {
		// TODO Auto-generated method stub
		SMSSDK.initSDK(this, "10b6280b3b5c0",
				"0a53b1dbde76e24a8997cfefc2b2e87a");

		EventHandler en = new EventHandler() {
			@Override
			public void afterEvent(int event, int result, Object data) {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}
		};
		SMSSDK.registerEventHandler(en);
	}

	private void initView() {
		// TODO Auto-generated method stub
		iv_fanb = (ImageView) findViewById(R.id.iv_fanb);
		btn_stepa = (Button) findViewById(R.id.btn_stepa);
		et_phoneb = (EditText) findViewById(R.id.et_phoneb);
		et_cordb = (EditText) findViewById(R.id.et_cordb);
		btn_cordb = (Button) findViewById(R.id.btn_cordb);
		nowb = (TextView) findViewById(R.id.nowb);
		iv_fanb.setOnClickListener(this);
		btn_stepa.setOnClickListener(this);
		btn_cordb.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_fanb:
			startActivity(new Intent(getApplicationContext(),
					EnterActivity.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
			break;
		case R.id.btn_stepa:
			if (!TextUtils.isEmpty(et_cordb.getText().toString().trim())) {
				if (et_cordb.getText().toString().trim().length() == 4) {
					iCord = et_cordb.getText().toString().trim();
					SMSSDK.submitVerificationCode("86", iPhone, iCord);
					flag = false;
				} else {
					Toast.makeText(getApplicationContext(), "请输入完整验证码",
							Toast.LENGTH_LONG).show();
					et_cordb.requestFocus();
				}
			} else {
				Toast.makeText(getApplicationContext(), "请输入验证码",
						Toast.LENGTH_LONG).show();
				et_cordb.requestFocus();
			}
			break;
		case R.id.btn_cordb:
			if (!TextUtils.isEmpty(et_phoneb.getText().toString().trim())) {
				if (et_phoneb.getText().toString().trim().length() == 11) {
					iPhone = et_phoneb.getText().toString().trim();
					SMSSDK.getVerificationCode("86", iPhone);
					et_cordb.requestFocus();
					btn_cordb.setVisibility(View.GONE);
				} else {
					Toast.makeText(getApplicationContext(), "请输入完整电话号码",
							Toast.LENGTH_LONG).show();
					et_phoneb.requestFocus();
				}
			} else {
				Toast.makeText(getApplicationContext(), "请输入你的电话号码",
						Toast.LENGTH_LONG).show();
				et_phoneb.requestFocus();
			}
			break;

		default:
			break;
		}
	}

	private void reminderText() {
		nowb.setVisibility(View.VISIBLE);
		handlerText.sendEmptyMessageDelayed(1, 1000);
	}

	Handler handlerText = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				if (time > 0) {
					nowb.setText(time + "秒后重新发送");
					time--;
					handlerText.sendEmptyMessageDelayed(1, 1000);
				} else {
					time = 60;
					nowb.setVisibility(View.GONE);
					btn_cordb.setVisibility(View.VISIBLE);
				}
			} else {
				et_cordb.setText("");
				time = 60;
				nowb.setVisibility(View.GONE);
				btn_cordb.setVisibility(View.VISIBLE);
			}
		};
	};

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int event = msg.arg1;
			int result = msg.arg2;
			Object data = msg.obj;
			Log.e("event", "event=" + event);
			if (result == SMSSDK.RESULT_COMPLETE) {
				if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
					Toast.makeText(getApplicationContext(), "验证码校验成功",
							Toast.LENGTH_SHORT).show();
					handlerText.sendEmptyMessage(2);
					SharedPreferences mPreferences = getSharedPreferences(
							"USER", MODE_APPEND);
					Editor editor = mPreferences.edit();
					editor.putString("userPhone", et_phoneb.getText()
							.toString());
					editor.commit();
					startActivity(new Intent(getApplicationContext(),
							ResetActivity.class));
					overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
					finish();
				} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {// 服务器验证码发送成功
					reminderText();
					Toast.makeText(getApplicationContext(), "验证码已经发送",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				if (flag) {
					btn_cordb.setVisibility(View.VISIBLE);
					Toast.makeText(getApplicationContext(), "验证码获取失败，请重新获取",
							Toast.LENGTH_SHORT).show();
					et_phoneb.requestFocus();
				} else {
					((Throwable) data).printStackTrace();
					Toast.makeText(getApplicationContext(), "验证码错误",
							Toast.LENGTH_SHORT).show();
				}
			}
		};
	};
}
