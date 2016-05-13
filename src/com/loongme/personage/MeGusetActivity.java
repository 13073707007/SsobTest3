package com.loongme.personage;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
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
import com.loongme.details.MerDataActivity;
import com.loongme.util.StringUtil;
import com.loongme.util.UIUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * @author 商家
 * 
 */
public class MeGusetActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_fanhuim, iv_sub_Picture;
	private Button bt_release;
	private RelativeLayout rll_fw, rll_tz, rll_sub_sz, rll_tj, rll_yj,
			rl_me_help_guest;
	private TextView merName;
	private TextView merAddress;
	private User user;
	private ImageView userPhoto;
	String userPicture, busName, busAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_me_guset);
		initView();
		initJSON();
	}

	// private void initData() {
	// // TODO Auto-generated method stub
	// String url =
	// "http://120.24.37.141:8080/LoginAndResigister/PersonMessageServlet";
	// HttpUtils httpUtils = new HttpUtils(5000);
	// RequestParams params = new RequestParams();
	// SharedPreferences preferences = getSharedPreferences("USER",
	// MODE_APPEND);
	// String userId = preferences.getString("userId", "");
	// JSONObject object = new JSONObject();
	// try {
	// object.put("userId", userId);
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// params.addQueryStringParameter("requestMessage", object.toString());
	// httpUtils.send(HttpMethod.POST, url, params,
	// new RequestCallBack<String>() {
	//
	// private String busName;
	// private String busAddress;
	//
	// @Override
	// public void onFailure(HttpException arg0, String arg1) {
	// // TODO Auto-generated method stub
	// Toast.makeText(getApplicationContext(),
	// arg0.getExceptionCode(), 0).show();
	// }
	//
	// @Override
	// public void onSuccess(ResponseInfo<String> arg0) {
	// // TODO Auto-generated method stub
	// if (arg0.statusCode == 200) {
	// com.alibaba.fastjson.JSONArray json = JSON
	// .parseObject(arg0.result).getJSONArray("busMsgs");
	// for (int i = 0; i < json.size(); i++) {
	// com.alibaba.fastjson.JSONObject jsonbean=json.getJSONObject(i);
	// busName = jsonbean.getString("busName");
	// busAddress = jsonbean.getString("busAddress");
	// }
	// tv_nickname.setText(busName);
	// tv_persjob.setText(busAddress);
	// }
	// }
	// });
	// }

	private void initView() {
		merName = (TextView) findViewById(R.id.tv_nickname);
		merAddress = (TextView) findViewById(R.id.tv_persjob);
		userPhoto = (ImageView) findViewById(R.id.iv_sub_Picture);
		// tv_nickname=(TextView) findViewById(R.id.tv_nickname);
		// tv_persjob=(TextView) findViewById(R.id.tv_persjob);
		iv_fanhuim = (ImageView) findViewById(R.id.iv_fanhuim);
		iv_sub_Picture = (ImageView) findViewById(R.id.iv_sub_Picture);
		bt_release = (Button) findViewById(R.id.bt_release);
		rll_fw = (RelativeLayout) findViewById(R.id.rll_fw);
		rll_tz = (RelativeLayout) findViewById(R.id.rll_tz);
		rll_sub_sz = (RelativeLayout) findViewById(R.id.rll_sub_sz);
		rll_tj = (RelativeLayout) findViewById(R.id.rll_tj);
		rll_yj = (RelativeLayout) findViewById(R.id.rll_yj);
		rl_me_help_guest = (RelativeLayout) findViewById(R.id.rl_me_help_guest);
		iv_fanhuim.setOnClickListener(this);
		bt_release.setOnClickListener(this);
		rll_fw.setOnClickListener(this);
		rll_tz.setOnClickListener(this);
		rll_sub_sz.setOnClickListener(this);
		rll_tj.setOnClickListener(this);
		rll_yj.setOnClickListener(this);
		rl_me_help_guest.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_fanhuim:
			startActivity(new Intent(getApplicationContext(), UnderstanderDemo.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
			break;
		case R.id.rll_fw:
			startActivity(new Intent(getApplicationContext(),
					MeserveActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.rll_tz:
			startActivity(new Intent(getApplicationContext(),
					Message2Activity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.rll_sub_sz:
			startActivity(new Intent(getApplicationContext(), SetSubActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.rll_tj:
			startActivity(new Intent(getApplicationContext(),
					Recommend2Activity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.rll_yj:
			startActivity(new Intent(getApplicationContext(),
					Idea2Activity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.bt_release:
			startActivity(new Intent(getApplicationContext(),
					ReleaseActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			// finish();
			break;
		case R.id.rl_me_help_guest:
			startActivity(new Intent(getApplicationContext(),
					MerDataActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		default:
			break;
		}
	}

	private void initJSON() {
		String url = "http://120.24.37.141:8080/LoginAndResigister/PersonMessageServlet";
		HttpUtils httpUtils = new HttpUtils(5000);
		RequestParams params = new RequestParams();
		params.setContentType("application/json");
		params.addHeader(
				"Content-Type",
				"application/json;text/json;text/javascript;application/soap+xml;text/html;text/xml;text/plain");
		JSONObject object = new JSONObject();
		try {
			object.put("userId", Helpers.getUserInfo(MeGusetActivity.this)
					.getUserId());
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
						UIUtils.showToast(MeGusetActivity.this, "获取用户信息失败");
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						progressDialog.dismiss();
						if (arg0.statusCode == 200) {
							if (arg0.result == null) {
								UIUtils.showToast(MeGusetActivity.this,
										"获取个人信息失败");
								return;
							}
							user = JSON.parseObject(arg0.result, User.class);
							if (user.getBusMsgs().size() > 0) {
								merName.setText(user.getBusMsgs().get(0)
										.getBusName());
								merAddress.setText(user.getBusMsgs().get(0)
										.getBusAddress());
							}
							if (!StringUtil.isEmpty(user.getUserPicture())) {
								ImageLoader.getInstance().displayImage(
										user.getUserPicture(), userPhoto);
							}
						}
					}
				});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startActivity(new Intent(getApplicationContext(), UnderstanderDemo.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
