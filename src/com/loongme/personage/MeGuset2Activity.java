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
import com.loongme.details.PerDataActivity;
import com.loongme.util.StringUtil;
import com.loongme.util.UIUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * @author 个人
 * 
 */
public class MeGuset2Activity extends BaseActivity implements OnClickListener {
	private ImageView iv_fanhuim, iv_pers_Picture;
	private Button bt_release2;
	private RelativeLayout rll_fw, rll_tz, rll_sz_pers, rll_tj, rll_yj,
			rl_me_help_guest2;
	private TextView personalName;
	private TextView personalJob;
	private User user;
	private ImageView personalPhoto;
	private String nickname, persJob, persPicture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_me2_guset);
		user = Helpers.getUserInfo(this);
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
	// object.put("userId", "5");
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// params.addQueryStringParameter("requestMessage", object.toString());
	// httpUtils.send(HttpMethod.POST, url, params,
	// new RequestCallBack<String>() {
	//
	// private String nickname;
	// private String persJob;
	// private Bitmap persPicture;
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
	// System.out.println("" + arg0.result);
	// com.alibaba.fastjson.JSONObject json = JSON
	// .parseObject(arg0.result);
	// nickname = json.getString("userNickname");
	// JSONArray jsonArray = JSON.parseObject(arg0.result)
	// .getJSONArray("persMsgs");
	// for (int i = 0; i < jsonArray.size(); i++) {
	// com.alibaba.fastjson.JSONObject jsonbean = jsonArray
	// .getJSONObject(i);
	// persJob = jsonbean.getString("persJob");
	// }
	// tv_subname.setText(nickname);
	// tv_persJob.setText(persJob);
	// }
	// }
	// });
	// }
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
	// >>>>>>> .r26
	//
	// <<<<<<< .mine
	// =======
	//
	// @Override
	// public void onFailure(HttpException arg0, String arg1) {
	// // TODO Auto-generated method stub
	// Toast.makeText(getApplicationContext(),
	// arg0.getExceptionCode(), 0).show();
	// dialog.dismiss();
	// }
	//
	// @Override
	// public void onSuccess(ResponseInfo<String> arg0) {
	// // TODO Auto-generated method stub
	// if (arg0.statusCode == 200) {
	// System.out.println("" + arg0.result);
	// com.alibaba.fastjson.JSONObject json = JSON
	// .parseObject(arg0.result);
	// nickname = json.getString("userNickname");
	// persPicture=json.getString("userPicture");
	// JSONArray jsonArray = JSON.parseObject(arg0.result)
	// .getJSONArray("persMsgs");
	// for (int i = 0; i < jsonArray.size(); i++) {
	// com.alibaba.fastjson.JSONObject jsonbean = jsonArray
	// .getJSONObject(i);
	// persJob = jsonbean.getString("persJob");
	// BitmapUtils bitmapUtils=new BitmapUtils(MeGuset2Activity.this);
	// bitmapUtils.display(iv_pers_Picture, persPicture);
	// }
	// tv_subname.setText(nickname);
	// tv_persJob.setText(persJob);
	// dialog.dismiss();
	// }
	// }
	// });
	// }
	//
	// >>>>>>> .r26
	private void initView() {
		personalName = (TextView) findViewById(R.id.tv_subname);
		personalJob = (TextView) findViewById(R.id.tv_persJob);
		personalPhoto = (ImageView) findViewById(R.id.iv_pers_Picture);
		// tv_subname=(TextView) findViewById(R.id.tv_subname);
		// tv_persJob=(TextView) findViewById(R.id.tv_persJob);
		iv_fanhuim = (ImageView) findViewById(R.id.iv_fanhuim);
		iv_pers_Picture = (ImageView) findViewById(R.id.iv_pers_Picture);
		bt_release2 = (Button) findViewById(R.id.bt_release2);
		rll_fw = (RelativeLayout) findViewById(R.id.rll_fw);
		rll_tz = (RelativeLayout) findViewById(R.id.rll_tz);
		rll_sz_pers = (RelativeLayout) findViewById(R.id.rll_sz_pers);
		rll_tj = (RelativeLayout) findViewById(R.id.rll_tj);
		rll_yj = (RelativeLayout) findViewById(R.id.rll_yj);
		rl_me_help_guest2 = (RelativeLayout) findViewById(R.id.rl_me_help_guest2);
		iv_fanhuim.setOnClickListener(this);
		bt_release2.setOnClickListener(this);
		rll_fw.setOnClickListener(this);
		rll_tz.setOnClickListener(this);
		rll_sz_pers.setOnClickListener(this);
		rll_tj.setOnClickListener(this);
		rll_yj.setOnClickListener(this);
		rl_me_help_guest2.setOnClickListener(this);

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
		case R.id.rll_sz_pers:
			startActivity(new Intent(getApplicationContext(), SetPersActivity.class));
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
		case R.id.rl_me_help_guest2:
			startActivity(new Intent(getApplicationContext(),
					PerDataActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.bt_release2:
			startActivity(new Intent(getApplicationContext(),
					ReleaseActivity.class));
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
			object.put("userId", Helpers.getUserInfo(MeGuset2Activity.this)
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
						UIUtils.showToast(MeGuset2Activity.this, "获取用户信息失败");
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						progressDialog.dismiss();
						if (arg0.statusCode == 200) {
							if (arg0.result == null) {
								UIUtils.showToast(MeGuset2Activity.this,
										"获取个人信息失败");
								return;
							}
							user = JSON.parseObject(arg0.result, User.class);
							personalName.setText(user.getUserNickname());
							if (user.getPersMsgs().size() > 0) {
								personalJob.setText(user.getPersMsgs().get(0)
										.getPersJob());
							}
							if (!StringUtil.isEmpty(user.getUserPicture())) {
								ImageLoader.getInstance().displayImage(
										user.getUserPicture(), personalPhoto);
							}
						}
					}
				});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startActivity(new Intent(getApplicationContext(), UnderstanderDemo.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
