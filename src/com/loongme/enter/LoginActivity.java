package com.loongme.enter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

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
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.loongme.activity.AgreementActivity;
import com.loongme.activity.R;
import com.loongme.activity.UnderstanderDemo;
import com.loongme.base.BaseActivity;
import com.loongme.view.ProgressDialog;

public class LoginActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_fan;
	private Button btn_logina;
	private EditText et_pwd, et_pwd2;
	private TextView tv_agreement;
	private SharedPreferences mPreferences;
	private Editor editor;
	private String phone;
	private String et2;
	private String et1;
	private String url;
	ProgressDialog dialog;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		dialog = ProgressDialog.getInstance(LoginActivity.this);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		iv_fan = (ImageView) findViewById(R.id.iv_fan);
		tv_agreement = (TextView) findViewById(R.id.tv_agreement);
		btn_logina = (Button) findViewById(R.id.btn_logina);
		et_pwd = (EditText) findViewById(R.id.et_pwd);
		et_pwd2 = (EditText) findViewById(R.id.et_pwd2);
		iv_fan.setOnClickListener(this);
		btn_logina.setOnClickListener(this);
		tv_agreement.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),
						AgreementActivity.class));
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_fan:
			startActivity(new Intent(getApplicationContext(),
					PhoneActivity.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
			break;
		case R.id.btn_logina:
			et1 = et_pwd.getText().toString();
			et2 = et_pwd2.getText().toString();
			if (!et1.equals(et2)) {
				Toast.makeText(getApplicationContext(), "密码不一致", 1).show();
			} else {
				mPreferences = getSharedPreferences("Phone", MODE_PRIVATE);
				phone = mPreferences.getString("Phone", "");
				dialog.show();
				HttpPostData();
			}
			break;
		default:
			break;
		}
	}

	private void HttpPostData() {
		// "http://172.16.80.136:8080/LoginAndResigisterTest/register";
		String url = "http://120.24.37.141:8080/LoginAndResigister/register";
		HttpUtils httpUtils = new HttpUtils(5000);
		RequestParams params = new RequestParams("UTF-8");
		params.setContentType("application/json;charset=UTF-8");
		params.addHeader(
				"Content-Type",
				"application/json;charset=UTF-8;text/json;text/javascript;application/soap+xml;text/html;text/xml;text/plain");
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		JSONObject obj3 = new JSONObject();
		try {
			obj.put("userType", URLEncoder.encode("普通", "UTF-8"));
			obj.put("userPhone", phone);
			obj.put("userPassword", et1);
			obj.put("userNickname", URLEncoder.encode("小帮", "UTF-8"));
			obj.put("ifRelation", "0");
			obj2.put("relationType", "safs");
			obj2.put("relationAcct", "001654");
			obj2.put("relationOpenid", "001654");
			obj2.put("relationIcon", "fss");
			obj2.put("userSex", URLEncoder.encode("男", "UTF-8"));
			obj3.put("tmUserRegister", obj);
			obj3.put("tmRelationMsg", obj2);
			System.out.println("::" + obj3.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		params.addQueryStringParameter("requestMessage", obj3.toString());
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						Toast.makeText(getApplicationContext(), "注册失败!!请检查网络",
								Toast.LENGTH_LONG).show();
						return;
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						if (arg0.statusCode == 200) {
							String ss;
							try {
								ss = new String(arg0.result.getBytes(), "utf-8");
								ss = URLDecoder.decode(ss, "utf-8");
								com.alibaba.fastjson.JSONObject JsonBean = JSON
										.parseObject(ss);
								int status = JsonBean.getInteger("status");
								if (status == 1) {
									// Toast.makeText(getApplicationContext(),
									// "注册成功", Toast.LENGTH_LONG).show();
									startActivity(new Intent(
											getApplicationContext(),
											UnderstanderDemo.class));
									overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
									dialog.dismiss();
									finish();
								} else {
									dialog.dismiss();
									Toast.makeText(getApplicationContext(),
											"该手机号已注册!!", Toast.LENGTH_LONG)
											.show();
								}
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});
	}
}
