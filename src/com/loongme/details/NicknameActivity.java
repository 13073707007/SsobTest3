package com.loongme.details;

import java.net.URLEncoder;

import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.loongme.base.BaseActivity;
import com.loongme.personage.MeDataActivity;

/**
 * 修改名称
 * 
 * @author xywy
 * 
 */
public class NicknameActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_name;
	private Button btn_submit_name;
	private EditText et_nickname;
	private String Id;
	private SharedPreferences mPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_nickname);
		mPreferences = getSharedPreferences("USER", MODE_PRIVATE);
		Id = mPreferences.getString("Id", "");
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		iv_name = (ImageView) findViewById(R.id.iv_name);
		btn_submit_name = (Button) findViewById(R.id.btn_submit_name);
		et_nickname = (EditText) findViewById(R.id.et_nickname);
		iv_name.setOnClickListener(this);
		btn_submit_name.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_name:
			startActivity(new Intent(getApplicationContext(),
					MeDataActivity.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
			break;
		case R.id.btn_submit_name:
			PostUploading();
			break;

		default:
			break;
		}
	}

	private void PostUploading() {
		// TODO Auto-generated method stub
		String url = "http://120.24.37.141:8080/LoginAndResigister/UpdateNikenameServlet";
		HttpUtils httpUtils = new HttpUtils(5000);
		RequestParams params = new RequestParams("utf-8");
		params.addHeader(
				"Content-Type",
				"application/json;charset=UTF-8;text/json;text/javascript;application/soap+xml;text/html;text/xml;text/plain");
		JSONObject obj = new JSONObject();
		try {
			obj.put("nikename", URLEncoder.encode(et_nickname.getText()
					.toString(), "utf-8"));
			obj.put("userId", Id);
			System.out.println("请求值:" + obj.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		params.addQueryStringParameter("requestMessage", obj.toString());
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(),
								arg0.getExceptionCode(), 1).show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						if (arg0.statusCode == 200) {
							System.out.println("返回值:" + arg0.result);
							com.alibaba.fastjson.JSONObject JsonBean = JSON
									.parseObject(arg0.result);
							boolean status = JsonBean.getBoolean("status");
							if (status == true) {
								startActivity(new Intent(
										getApplicationContext(),
										MeDataActivity.class));
								overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
								finish();
							} else {
								Toast.makeText(getApplicationContext(),
										"修改昵称失败!!", 1).show();
							}
						}
					}
				});
	}
}
