package com.loongme.details;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.loongme.activity.R;
import com.loongme.base.BaseActivity;
import com.loongme.business.Helpers;
import com.loongme.personage.MeGuset2Activity;
import com.loongme.view.ProgressDialog;

/**
 * 我的帮客资料 个人
 * 
 * @author xywy
 * 
 */
public class PerDataActivity extends BaseActivity implements OnClickListener {
	ImageView iv_fanhuipd, iv_pers_back, iv_pers_front;
	TextView pers_name, pers_Job, pers_phone, pers_desc, pers_introduce;
	private String persNname, persJob, persDesc, persIntroduce, persFront,
			persBack;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_per_data);
		initData();
		initView();
		dialog = ProgressDialog.getInstance(this);
		dialog.show();
	}

	private void initData() {
		// TODO Auto-generated method stub
		String url = "http://120.24.37.141:8080/LoginAndResigister/PersonMessageServlet";
		HttpUtils httpUtils = new HttpUtils(5000);
		RequestParams params = new RequestParams();
		JSONObject object = new JSONObject();
		try {
			object.put("userId", Helpers.getUserInfo(this).getUserId());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.addQueryStringParameter("requestMessage", object.toString());
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					private String persFront;
					private String persBack;

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(),
								arg0.getExceptionCode(), 0).show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						if (arg0.statusCode == 200) {
							com.alibaba.fastjson.JSONObject json = JSON
									.parseObject(arg0.result);
							String userphone = json.getString("userPhone");
							JSONArray array = JSON.parseObject(arg0.result)
									.getJSONArray("persMsgs");
							for (int i = 0; i < array.size(); i++) {
								persNname = array.getJSONObject(i).getString(
										"persNname");
								persJob = array.getJSONObject(i).getString(
										"persJob");
								persDesc = array.getJSONObject(i).getString(
										"persDesc");
								persIntroduce = array.getJSONObject(i)
										.getString("persIntroduce");
								persFront = array.getJSONObject(i).getString(
										"persFront");
								persBack = array.getJSONObject(i).getString(
										"persBack");
								BitmapUtils();
							}
							dialog.dismiss();
							pers_desc.setText(persDesc);
							pers_Job.setText(persJob);
							pers_name.setText(persNname);
							pers_introduce.setText(persIntroduce);
							pers_phone.setText(userphone);
						}
					}

					private void BitmapUtils() {
						// TODO Auto-generated method stub
						BitmapUtils bitmapUtils = new BitmapUtils(
								PerDataActivity.this);
						bitmapUtils.display(iv_pers_front, persFront);// 正面
						bitmapUtils.display(iv_pers_back, persBack);// 背面

					}
				});
	}

	private void initView() {
		// TODO Auto-generated method stub
		iv_fanhuipd = (ImageView) findViewById(R.id.iv_fanhuipd);
		iv_pers_back = (ImageView) findViewById(R.id.iv_pers_back);
		iv_pers_front = (ImageView) findViewById(R.id.iv_pers_front);
		pers_name = (TextView) findViewById(R.id.pers_name);
		pers_phone = (TextView) findViewById(R.id.pers_phone);
		pers_Job = (TextView) findViewById(R.id.pers_Job);
		pers_desc = (TextView) findViewById(R.id.pers_desc);
		pers_introduce = (TextView) findViewById(R.id.pers_introduce);
		iv_fanhuipd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_fanhuipd:
			startActivity(new Intent(getApplicationContext(), MeGuset2Activity.class));
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
			startActivity(new Intent(getApplicationContext(), MeGuset2Activity.class));
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
