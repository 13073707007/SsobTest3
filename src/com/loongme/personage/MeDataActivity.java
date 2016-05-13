package com.loongme.personage;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.loongme.activity.R;
import com.loongme.base.BaseActivity;
import com.loongme.details.HeadPortraitActivity;
import com.loongme.details.NicknameActivity;
import com.loongme.details.SexActivity;
import com.loongme.view.ProgressDialog;

/**
 * 
 * @author 个人资料
 * 
 */
public class MeDataActivity extends BaseActivity implements OnClickListener {
	private RelativeLayout rl_manifesto, rl_head_portrait, rl_nickname, rl_sex;
	private ImageView iv_fanhuimd, iv_hoad_por;
	private TextView tv_message_manifesto, tv_message_nickname, tv_message_sex,
			tv_message_iphone;
	private SharedPreferences mPreferences;
	private String nickname, userPicture, manifesto, phone, sex, Id;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_me_data);
		initView();
		initJSON();
		dialog = ProgressDialog.getInstance(this);
		dialog.show();
	}

	private void initJSON() {
		// TODO Auto-generated method stub
		mPreferences = getSharedPreferences("USER", MODE_APPEND);
		Id = mPreferences.getString("Id", "");
		String url = "http://120.24.37.141:8080/LoginAndResigister/PersonMessageServlet";
		HttpUtils httpUtils = new HttpUtils(5000);
		RequestParams params = new RequestParams();
		params.setContentType("application/json");
		params.addHeader(
				"Content-Type",
				"application/json;text/json;text/javascript;application/soap+xml;text/html;text/xml;text/plain");
		JSONObject object = new JSONObject();
		try {
			object.put("userId", Id);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.addQueryStringParameter("requestMessage", object.toString());
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(),
								arg0.getExceptionCode(), Toast.LENGTH_SHORT)
								.show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						if (arg0.statusCode == 200) {
							com.alibaba.fastjson.JSONObject JsonBean = JSON
									.parseObject(arg0.result);
							nickname = JsonBean.getString("userNickname");
							sex = JsonBean.getString("userSex");
							phone = JsonBean.getString("userPhone");
							manifesto = JsonBean.getString("declaration");
							userPicture = JsonBean.getString("userPicture");
							BitmapUtils();
							mPreferences = getSharedPreferences("message",
									MODE_APPEND);
							Editor editor = mPreferences.edit();
							editor.putString("sex", sex);
							editor.commit();
							tv_message_nickname.setText(nickname);
							tv_message_sex.setText(sex);
							tv_message_iphone.setText(phone);
							tv_message_manifesto.setText(manifesto);
							dialog.dismiss();
						}
					}

					private void BitmapUtils() {
						// TODO Auto-generated method stub
						BitmapUtils bitmapUtils = new BitmapUtils(
								MeDataActivity.this);
						bitmapUtils.display(iv_hoad_por, userPicture);
					}
				});
	}

	private void initView() {
		tv_message_sex = (TextView) findViewById(R.id.tv_message_sex);
		tv_message_iphone = (TextView) findViewById(R.id.tv_message_iphone);
		tv_message_manifesto = (TextView) findViewById(R.id.tv_message_manifesto);
		rl_manifesto = (RelativeLayout) findViewById(R.id.rl_manifesto);
		rl_head_portrait = (RelativeLayout) findViewById(R.id.rl_head_portrait);
		rl_nickname = (RelativeLayout) findViewById(R.id.rl_nickname);
		rl_sex = (RelativeLayout) findViewById(R.id.rl_sex);
		iv_fanhuimd = (ImageView) findViewById(R.id.iv_fanhuimd);
		iv_hoad_por = (ImageView) findViewById(R.id.iv_hoad_por);
		rl_manifesto = (RelativeLayout) findViewById(R.id.rl_manifesto);
		tv_message_nickname = (TextView) findViewById(R.id.tv_message_nickname);
		rl_manifesto.setOnClickListener(this);
		rl_sex.setOnClickListener(this);
		rl_head_portrait.setOnClickListener(this);
		rl_nickname.setOnClickListener(this);
		iv_fanhuimd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_manifesto:
			startActivity(new Intent(getApplicationContext(),
					ManifestoActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.rl_sex:
			startActivity(new Intent(getApplicationContext(), SexActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.rl_head_portrait:
			startActivity(new Intent(getApplicationContext(),
					HeadPortraitActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.rl_nickname:
			startActivity(new Intent(getApplicationContext(),
					NicknameActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.iv_fanhuimd:
			startActivity(new Intent(getApplicationContext(), PersonageActivity.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
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
			startActivity(new Intent(getApplicationContext(), PersonageActivity.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
