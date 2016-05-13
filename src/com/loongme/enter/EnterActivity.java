package com.loongme.enter;

import java.io.File;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

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
import com.loongme.common.Configuration;
import com.loongme.util.UIUtils;

public class EnterActivity extends BaseActivity implements OnClickListener,
		PlatformActionListener {
	private Button btn_login, btn_forget_password, btn_enter, btn_login2;
	private Button btn_weixin, btn_QQ, btn_xinlang;
	private SharedPreferences mPreferences;
	private Editor editor;
	private EditText et_phone1, et_pwd1;
	private String iphone;
	private String pwd1;
	private String ipwd;
	private String verifypho;
	private String verifypwd;
	private ImageView iv_return;
	private Platform weibo;
	String id, name, description, profile_image_url;

	// ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_enter);
		initView();
		ShareSDK.initSDK(this, "1297452235");
	}

	private void initView() {
		// TODO Auto-generated method stub
		iv_return = (ImageView) findViewById(R.id.iv_return);
		btn_enter = (Button) findViewById(R.id.btn_enter);
		et_phone1 = (EditText) findViewById(R.id.et_phone1);
		et_pwd1 = (EditText) findViewById(R.id.et_pwd1);
		btn_forget_password = (Button) findViewById(R.id.btn_forget_password);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_login2 = (Button) findViewById(R.id.btn_login2);
		btn_QQ = (Button) findViewById(R.id.btn_QQ);
		btn_weixin = (Button) findViewById(R.id.btn_weixin);
		btn_xinlang = (Button) findViewById(R.id.btn_xinlang);
		btn_enter.setOnClickListener(this);
		btn_forget_password.setOnClickListener(this);
		btn_login.setOnClickListener(this);
		btn_login2.setOnClickListener(this);
		btn_weixin.setOnClickListener(this);
		btn_xinlang.setOnClickListener(this);
		btn_QQ.setOnClickListener(this);
		iv_return.setOnClickListener(this);
	}

	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobiles) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
		String telRegex = "[1][34578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_enter:
			verifypho = et_phone1.getText().toString();
			verifypwd = et_pwd1.getText().toString();
			if (isMobileNO(verifypho) == false) {
				Toast.makeText(getApplicationContext(), "请正确输入你的手机号",
						Toast.LENGTH_LONG).show();
			} else if (verifypwd.length() < 6) {
				Toast.makeText(getApplicationContext(), "密码长度为6~20之间", 0)
						.show();
			} else if (verifypwd.length() > 20) {
				Toast.makeText(getApplicationContext(), "密码长度为6~20之间", 0)
						.show();
			} else {
				// dialog = ProgressDialog.getInstance(this);
				// dialog.show();
				initEnter();
			}

			break;
		case R.id.btn_forget_password:
			startActivity(new Intent(getApplicationContext(),
					ForpwdActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			finish();
			break;
		case R.id.btn_login:
			startActivity(new Intent(getApplicationContext(),
					PhoneActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			finish();
			break;
		case R.id.btn_login2:
			startActivity(new Intent(getApplicationContext(),
					PhoneActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			finish();
			break;
		case R.id.btn_weixin:
			startActivity(new Intent(getApplicationContext(),
					BoundActivity.class));
			finish();
			break;
		case R.id.btn_QQ:
			startActivity(new Intent(getApplicationContext(),
					BoundActivity.class));
			finish();
			break;
		case R.id.btn_xinlang:
			weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
			weibo.SSOSetting(false);
			weibo.setPlatformActionListener(this); // 设置分享事件回调
			weibo.showUser(null);
			weibo.authorize();
			break;
		case R.id.iv_return:
			startActivity(new Intent(getApplicationContext(),
					UnderstanderDemo.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
			break;
		default:
			break;
		}
	}
	private void initEnter() {
		// TODO Auto-generated method stub
		HttpUtils httpUtils = new HttpUtils();
		RequestParams params = new RequestParams();
		String url = "http://120.24.37.141:8080/LoginAndResigister/UserLoginServlet";
		JSONObject object = new JSONObject();
		try {
			object.put("userPhone", verifypho);
			object.put("userPassword", verifypwd);
			object.put("relationOpenid", "");
			params.addQueryStringParameter("requestMessage", object.toString());
			params.setContentType("application/json");
			params.addHeader(
					"Content-Type",
					"application/json;text/json;text/javascript;application/soap+xml;text/html;text/xml;text/plain");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		progressDialog.show();
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					private Editor editor;

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						progressDialog.dismiss();
						Toast.makeText(getApplicationContext(), "登录失败!", 0)
								.show();
						return;
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						System.out.println("------loginresult-----"
								+ arg0.result);
						progressDialog.dismiss();
						if (arg0.statusCode == 200) {
							if (arg0.result == null) {
								UIUtils.showToast(EnterActivity.this, "登录失败");
								return;
							}
							User user = JSON.parseObject(arg0.result,
									User.class);
							com.alibaba.fastjson.JSONObject JsonBean = JSON
									.parseObject(arg0.result);
							int status = JsonBean.getInteger("status");
							String userId = JsonBean.getString("userId");
							mPreferences = getSharedPreferences("USER",
									MODE_APPEND);
							editor = mPreferences.edit();
							editor.putString("Id", userId);
							editor.commit();
							if (status == 1) {
								// Toast.makeText(getApplicationContext(),
								// "登录成功",
								// Toast.LENGTH_LONG).show();
								// 保存用户信息
								Helpers.saveUserInfoToLocal(EnterActivity.this,
										user);
								// 创建用户缓存文件路径
								File file = new File(Configuration.SDCARD_PATH
										+ File.separator + Configuration.TAG
										+ File.separator
										+ user.getUserNickname());
								if (!file.exists()) {
									file.mkdir();
								}
								startActivity(new Intent(
										getApplicationContext(),
										UnderstanderDemo.class));
								overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
								finish();
							} else {
								Toast.makeText(getApplicationContext(),
										"账号或密码错误", Toast.LENGTH_LONG).show();
							}
						}
					}
				});
	}

	// 取消方法
	@Override
	public void onCancel(Platform arg0, int arg1) {
		// TODO Auto-generated method stub
		// startActivity(new Intent(getApplicationContext(),
		// BoundActivity.class));
		// finish();
		Toast.makeText(getApplicationContext(), "取消授权", 0).show();
	}

	// 成功方法
	@Override
	public void onComplete(Platform arg0, int arg1, HashMap<String, Object> res) {
		// TODO Auto-generated method stub
		// 解析部分用户资料字段
		id = res.get("id").toString();// ID
		name = res.get("name").toString();// 用户名
		description = res.get("description").toString();// 描述
		profile_image_url = res.get("profile_image_url").toString();// 头像链接
		String str = "ID: " + id + ";\n" + "用户名： " + name + ";\n" + "描述："
				+ description + ";\n" + "用户头像地址：" + profile_image_url;
		System.out.println("用户资料: " + str);
		SharedPreferences sf = getSharedPreferences("MOBSSO", MODE_APPEND);
		Editor e = sf.edit();
		e.putString("id", id);
		e.putString("name", name);
		e.putString("description", description);
		e.putString("profile_image_url", profile_image_url);
		e.commit();
		if (weibo.isValid()) {
			weibo.removeAccount();
		}
		initbound();
	}

	private void initbound() {
		// TODO Auto-generated method stub
		String url = "http://120.24.37.141:8080/LoginAndResigister/CheckRelationServlet";
		HttpUtils utils = new HttpUtils(5000);
		RequestParams params = new RequestParams();
		JSONObject obj = new JSONObject();
		SharedPreferences mPreferences = getSharedPreferences("MOBSSO",
				MODE_APPEND);
		String openid = mPreferences.getString("id", "");
		try {
			obj.put("openID", openid);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.addQueryStringParameter("requestMessage", obj.toString());
		utils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "查询绑定信息失败!!", 0).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> a) {
				// TODO Auto-generated method stub
				if (a.statusCode == 200) {
					System.out.println("-----返回绑定信息----" + a.result);
					com.alibaba.fastjson.JSONObject json = JSON
							.parseObject(a.result);
					int status = json.getInteger("status");
					if (status == 0) {
						startActivity(new Intent(getApplicationContext(),
								BoundActivity.class));
						finish();
					} else {
						startActivity(new Intent(getApplicationContext(),
								UnderstanderDemo.class));
						finish();
					}
				}
			}
		});
		// -----返回绑定信息----{"userPhone":null,"userPassword":null,
		// "userNickname":null,"userType":null,"relationType":null,"relationAcct":null,
		// "relationIcon":null,"userSex":null,"userId":0,"userPicture":null,"status":0}

	}

	// 失败方法
	@Override
	public void onError(Platform arg0, int arg1, Throwable arg2) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "授权失败", 0).show();

	}
}
