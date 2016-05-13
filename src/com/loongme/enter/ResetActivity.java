package com.loongme.enter;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.loongme.activity.R;
import com.loongme.activity.UnderstanderDemo;
import com.loongme.base.BaseActivity;
import com.loongme.view.ProgressDialog;

public class ResetActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_fanc;
	private Button btn_loginc;
	private SharedPreferences mPreferences;
	private Editor editor;
	private String resetphone;
	private EditText resetpwd, resetpwd2;
	private String resetet2;
	private String resetet1;
	private String reseturl;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_reset);
		dialog = ProgressDialog.getInstance(ResetActivity.this);
		initView();
		mPreferences = getSharedPreferences("USER", MODE_APPEND);
		resetphone = mPreferences.getString("userPhone", "");
	}

	private void initView() {
		// TODO Auto-generated method stub
		iv_fanc = (ImageView) findViewById(R.id.iv_fanc);
		btn_loginc = (Button) findViewById(R.id.btn_loginc);
		resetpwd = (EditText) findViewById(R.id.resetpwd);
		resetpwd2 = (EditText) findViewById(R.id.resetpwd2);
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
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
			break;
		case R.id.btn_loginc:
			resetet1 = resetpwd.getText().toString();
			resetet2 = resetpwd2.getText().toString();
			if (resetet2.length() < 6) {
				Toast.makeText(getApplicationContext(), "密码长度为6~20之间", 0)
						.show();
			} else if (resetet2.length() > 20) {
				Toast.makeText(getApplicationContext(), "密码长度为6~20之间", 0)
						.show();
			} else if (!resetet1.equals(resetet2)) {
				Toast.makeText(getApplicationContext(), "密码不一致", 0).show();
			} else {
				dialog.show();
				HttpPostData();
			}
			break;
		default:
			break;
		}
	}

	private void HttpPostData() {
		// TODO Auto-generated method stub
		reseturl = "http://120.24.37.141:8080/LoginAndResigister/UpdatePasswordServlet";
		HttpUtils httpUtils = new HttpUtils(5000);
		RequestParams params = new RequestParams("UTF-8");
		params.setContentType("application.json;charset=utf-8");
		params.addHeader(
				"Content-Type",
				"application/json;charset=UTF-8;text/json;text/javascript;application/soap+xml;text/html;text/xml;text/plain");
		JSONObject object = new JSONObject();
		try {
			object.put("userPhone", resetphone);
			object.put("userPassword", resetet2);
			System.out.println("" + object.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.addQueryStringParameter("requestMessage", object.toString());
		httpUtils.send(HttpMethod.POST, reseturl, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						Toast.makeText(getApplicationContext(), "重置密码失败!!", 0)
								.show();
						return;
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						if (arg0.statusCode == 200) {
							System.out.println("----返回数据-----" + arg0.result);
							com.alibaba.fastjson.JSONObject json = JSON
									.parseObject(arg0.result);
							String userId = json.getString("userId");
							SharedPreferences pf = getSharedPreferences("USER",
									MODE_APPEND);
							Editor ed = pf.edit();
							ed.putString("userId", userId);
							ed.commit();
							System.out.println("----userId-----" + userId);
							// Toast.makeText(getApplicationContext(), "重置密码成功",
							// 0).show();
							startActivity(new Intent(getApplicationContext(),
									UnderstanderDemo.class));
							overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
							dialog.dismiss();
							finish();
						}
					}
				});

	}

}
