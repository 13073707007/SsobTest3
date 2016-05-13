package com.loongme.personage;

import java.net.URLEncoder;

import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
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

/**
 * 
 * @author 个性宣言
 * 
 */
public class ManifestoActivity extends BaseActivity implements OnClickListener {
	ImageView iv_fanhuima;
	Button btn_manifesto_submit;
	EditText ed_manifesto;
	private String manifesto;
	private String Id;
	private SharedPreferences mPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_manifesto);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		iv_fanhuima = (ImageView) findViewById(R.id.iv_fanhuima);
		btn_manifesto_submit = (Button) findViewById(R.id.btn_manifesto_submit);
		ed_manifesto = (EditText) findViewById(R.id.ed_manifesto);
		iv_fanhuima.setOnClickListener(this);
		btn_manifesto_submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_fanhuima:
			startActivity(new Intent(getApplicationContext(),
					MeDataActivity.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
			break;
		case R.id.btn_manifesto_submit:
			postUploading();
			break;
		default:
			break;
		}
	}

	private void postUploading() {
		// TODO Auto-generated method stub
		String url = "http://120.24.37.141:8080/LoginAndResigister/UpdateDeclarationServlet";
		HttpUtils httpUtils = new HttpUtils(5000);
		RequestParams params = new RequestParams("utf-8");
		params.setContentType("application/json;charset=UTF-8");
		params.addHeader(
				"Content-Type",
				"application/json;charset=UTF-8;text/json;text/javascript;application/soap+xml;text/html;text/xml;text/plain");
		JSONObject obj = new JSONObject();
		mPreferences = getSharedPreferences("USER", MODE_PRIVATE);
		Id = mPreferences.getString("Id", "");
		try {
			manifesto = ed_manifesto.getText().toString();
			String valueManifesto = URLEncoder.encode(manifesto, "utf-8");
			obj.put("userId", Id);
			obj.put("persDeclaration", valueManifesto);
			System.out.println("请求值:" + obj.toString());
		} catch (Exception e) {
			// TODO: handle exception
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
							System.out.println("返回数据::" + arg0.result);
							SharedPreferences mPreferences = getSharedPreferences(
									"message", MODE_APPEND);
							Editor editor = mPreferences.edit();
							editor.putString("manifesto", manifesto);
							editor.commit();
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
										"提交失败!!!", 0).show();
							}
						}
					}
				});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startActivity(new Intent(getApplicationContext(),
					MeDataActivity.class));
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
