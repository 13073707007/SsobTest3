package com.loongme.personage;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
 * @author 意见反馈
 * 
 */
public class IdeaActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_fanhuii;
	private Button bt_idea_send;
	private EditText et_feedback, et_feedback_phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_idea);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		iv_fanhuii = (ImageView) findViewById(R.id.iv_fanhuii);
		bt_idea_send = (Button) findViewById(R.id.bt_idea_send);
		et_feedback = (EditText) findViewById(R.id.et_feedback);
		et_feedback_phone = (EditText) findViewById(R.id.et_feedback_phone);
		iv_fanhuii.setOnClickListener(this);
		bt_idea_send.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_fanhuii:
			startActivity(new Intent(getApplicationContext(),
					PersonageActivity.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
			break;
		case R.id.bt_idea_send:
			PostUploading();
			break;

		default:
			break;
		}
	}

	private void PostUploading() {
		// TODO Auto-generated method stub
		String url = "http://120.24.37.141:8080/LoginAndResigister/feedBack";
		HttpUtils httpUtils = new HttpUtils(5000);
		RequestParams params = new RequestParams("utf-8");
		params.addHeader(
				"Content-Type",
				"application/json;charset=UTF-8;text/json;text/javascript;application/soap+xml;text/html;text/xml;text/plain");
		JSONObject obj = new JSONObject();
		try {
			obj.put("fbContent", et_feedback.getText().toString());
			obj.put("fbContact", et_feedback_phone.getText().toString());
			System.out.println("请求数据::" + obj.toString());
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
							System.out.println("返回数据:" + arg0.result);
							startActivity(new Intent(getApplicationContext(),
									PersonageActivity.class));
							overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
							finish();
						}
					}
				});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startActivity(new Intent(getApplicationContext(),
					PersonageActivity.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
