package com.loongme.personage;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.loongme.business.Helpers;
import com.loongme.com.model.User;
import com.loongme.common.BonkeStatus;
import com.loongme.common.Configuration;
import com.loongme.enter.EnterActivity;
import com.loongme.util.StringUtil;
import com.loongme.view.ProgressDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 个人信息
 * 
 * @author xywy
 * 
 */
public class PersonageActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_fanhuip, iv_hoad_portrait;
	private Button bt_guest;
	private RelativeLayout rl_dd, rl_sz, rl_scj, rl_tzxx, rl_tjhy, rl_yjfk,
			rl_me;
	private LinearLayout ll_login, ll_not_login;
	private TextView tv_nick_name, tv_iphone;
	private String Id;
	private SharedPreferences mPreferences;
	private String nickname, Picture, iphone, userPicture;
	private int userId;
	ProgressDialog dialog;
	LinearLayout tv_login_register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_personage);
		dialog = ProgressDialog.getInstance(this);
		mPreferences = getSharedPreferences("USER", MODE_PRIVATE);
		Id = mPreferences.getString("Id", "");
		initJSON();
		initView();

	}

	private void initJSON() {
		User user = Helpers.getUserInfo(this);
		if (!user.getUserType().equals("普通用户")
				&& !user.getUserType().equals("个人帮客")
				&& !user.getUserType().equals("商户帮客")
				&& !user.getUserType().equals("普通")) {
			return;
		}
		String url = "http://120.24.37.141:8080/LoginAndResigister/PersonMessageServlet";
		HttpUtils httpUtils = new HttpUtils(10000);
		RequestParams params = new RequestParams();
		params.setContentType("application/json");
		params.addHeader(
				"Content-Type",
				"application/json;text/json;text/javascript;application/soap+xml;text/html;text/xml;text/plain");
		JSONObject object = new JSONObject();
		mPreferences = getSharedPreferences("message", MODE_APPEND);
		nickname = mPreferences.getString("nickname", "");
		iphone = mPreferences.getString("iphone", "");
		Picture = mPreferences.getString("userPicture", "");
		try {
			object.put("userId", Id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		params.addQueryStringParameter("requestMessage", object.toString());
		progressDialog.show();
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onFailure(HttpException arg0, String arg1) {
						progressDialog.dismiss();
						// UIUtils.showToast(PersonageActivity.this,
						// "获取用户信息失败");
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						System.out.println("------获取用户信息-----" + arg0.result);
						progressDialog.dismiss();
						if (arg0.statusCode == 200) {
							// if (arg0.result == null) {
							// UIUtils.showToast(PersonageActivity.this,
							// "获取个人信息失败");
							// return;
							// }
							User user = JSON.parseObject(arg0.result,
									User.class);
							com.alibaba.fastjson.JSONObject JsonBean = JSON
									.parseObject(arg0.result);
							userId = JsonBean.getInteger("userId");
							nickname = JsonBean.getString("userNickname");
							iphone = JsonBean.getString("userPhone");
							userPicture = JsonBean.getString("userPicture");
							Log.e("saddfcs", userPicture);
							mPreferences = getSharedPreferences("message",
									MODE_APPEND);
							Editor editor = mPreferences.edit();
							editor.putString("nickname", nickname);
							editor.putString("iphone", iphone);
							editor.putString("userPicture", userPicture);
							editor.commit();
							if (user.getAuditStatus() == BonkeStatus.BonkeChecking
									.getCode()) {
								bt_guest.setText("帮客审核中...");
								bt_guest.setClickable(false);
							} else if (user.getAuditStatus() == BonkeStatus.BonkeUnChecked
									.getCode()) {
								bt_guest.setText("审核失败...");
								bt_guest.setClickable(true);
							}else {
								bt_guest.setText("申请帮客");
								bt_guest.setClickable(true);
							}
							dialog.show();
							ll_login.setVisibility(View.GONE);
							ll_not_login.setVisibility(View.VISIBLE);
							tv_iphone.setText(iphone);
							tv_nick_name.setText(nickname);
							BitmapUtils();
							dialog.dismiss();
							rl_me.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									startActivity(new Intent(
											getApplicationContext(),
											MeDataActivity.class));
									overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
									finish();
								}
							});
						}
					}

					private void BitmapUtils() {
						if (!StringUtil.isEmpty(userPicture)) {
							ImageLoader.getInstance().displayImage(userPicture,
									iv_hoad_portrait);
						}
					}
				});
	}

	private void initView() {
		tv_iphone = (TextView) findViewById(R.id.tv_iphone);
		tv_nick_name = (TextView) findViewById(R.id.tv_nick_name);
		ll_login = (LinearLayout) findViewById(R.id.ll_login);
		ll_not_login = (LinearLayout) findViewById(R.id.ll_not_login);
		tv_login_register = (LinearLayout) findViewById(R.id.tv_login_register);
		rl_me = (RelativeLayout) findViewById(R.id.rl_me);
		iv_fanhuip = (ImageView) findViewById(R.id.iv_fanhuip);
		iv_hoad_portrait = (ImageView) findViewById(R.id.iv_hoad_portrait);
		bt_guest = (Button) findViewById(R.id.bt_guest);
		rl_sz = (RelativeLayout) findViewById(R.id.rl_sz);
		rl_yjfk = (RelativeLayout) findViewById(R.id.rl_yjfk);
		rl_tzxx = (RelativeLayout) findViewById(R.id.rl_tzxx);
		rl_tjhy = (RelativeLayout) findViewById(R.id.rl_tjhy);
		tv_login_register.setOnClickListener(this);
		iv_fanhuip.setOnClickListener(this);
		bt_guest.setOnClickListener(this);
		rl_sz.setOnClickListener(this);
		rl_yjfk.setOnClickListener(this);
		rl_tzxx.setOnClickListener(this);
		rl_tjhy.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_login_register:
			startActivity(new Intent(getApplicationContext(),
					EnterActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			finish();
			break;
		case R.id.iv_fanhuip:
			startActivity(new Intent(getApplicationContext(),
					UnderstanderDemo.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
			break;
		case R.id.rl_sz:
			startActivity(new Intent(getApplicationContext(), SetActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			finish();
			break;
		case R.id.bt_guest:
			Intent intent = new Intent(getApplicationContext(),
					GuestActivity.class);
			startActivityForResult(intent, Configuration.REQUEST_APPLYBONKE);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.rl_yjfk:
			startActivity(new Intent(getApplicationContext(),
					IdeaActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			finish();
			break;
		case R.id.rl_tzxx:
			startActivity(new Intent(getApplicationContext(),
					MessageActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			
			break;
		case R.id.rl_tjhy:
			startActivity(new Intent(getApplicationContext(),
					RecommendActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startActivity(new Intent(getApplicationContext(),
					UnderstanderDemo.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}
		if (requestCode == Configuration.REQUEST_APPLYBONKE) {
			// 刷新申请按钮
			int status = Helpers
					.getBonkeStatusFromLocal(PersonageActivity.this);
			if (status == BonkeStatus.BonkeChecking.getCode()) {
				// bt_guest.setBackground(getResources().getDrawable(
				// R.drawable.btn_applybonke_unclicked));
				bt_guest.setText("帮客审核中...");
				bt_guest.setClickable(false);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
