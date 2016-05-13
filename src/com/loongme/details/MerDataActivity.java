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
import com.loongme.personage.MeGusetActivity;
import com.loongme.view.ProgressDialog;

/**
 * 
 * 我的帮客资料商家
 */
public class MerDataActivity extends BaseActivity implements OnClickListener {
	ImageView iv_fanhuimd, iv_card;
	private TextView tv_sub_name, tv_address, tv_user_name, tv_user_phone,
			tv_busGhone, tv_sub_describe, tv_sub_Introduce;
	private String busAddress, busName, busLicense, busIntroduce, busDesc,
			busGhone, busContact;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mer_data);
		initView();
		dialog = ProgressDialog.getInstance(this);
		dialog.show();
		initData();
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
							String phone = json.getString("userPhone");
							JSONArray array = JSON.parseObject(arg0.result)
									.getJSONArray("busMsgs");
							for (int i = 0; i < array.size(); i++) {
								busName = array.getJSONObject(i).getString(
										"busName");
								busAddress = array.getJSONObject(i).getString(
										"busAddress");
								busLicense = array.getJSONObject(i).getString(
										"busLicense");
								busIntroduce = array.getJSONObject(i)
										.getString("busIntroduce");
								busDesc = array.getJSONObject(i).getString(
										"busDesc");
								busGhone = array.getJSONObject(i).getString(
										"busGhone");
								busContact = array.getJSONObject(i).getString(
										"busContact");
								BitmapUtils();
							}
							tv_address.setText(busAddress);
							tv_busGhone.setText(busGhone);
							tv_sub_describe.setText(busDesc);
							tv_sub_Introduce.setText(busIntroduce);
							tv_sub_name.setText(busName);
							tv_user_name.setText(busContact);
							tv_user_phone.setText(phone);
							dialog.dismiss();
						}
					}

					private void BitmapUtils() {
						// TODO Auto-generated method stub
						BitmapUtils bitmapUtils = new BitmapUtils(
								MerDataActivity.this);
						bitmapUtils.display(iv_card, busIntroduce);
					}
				});
	}

	private void initView() {
		// TODO Auto-generated method stub
		iv_fanhuimd = (ImageView) findViewById(R.id.iv_fanhuimd);
		iv_card = (ImageView) findViewById(R.id.iv_card);
		tv_sub_name = (TextView) findViewById(R.id.tv_sub_name);
		tv_address = (TextView) findViewById(R.id.tv_address);
		tv_user_name = (TextView) findViewById(R.id.tv_user_name);
		tv_user_phone = (TextView) findViewById(R.id.tv_user_phone);
		tv_busGhone = (TextView) findViewById(R.id.tv_busGhone);
		tv_sub_describe = (TextView) findViewById(R.id.tv_sub_describe);
		tv_sub_Introduce = (TextView) findViewById(R.id.tv_sub_Introduce);
		iv_fanhuimd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_fanhuimd:
			startActivity(new Intent(getApplicationContext(),
					MeGusetActivity.class));
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
			startActivity(new Intent(getApplicationContext(),
					MeGusetActivity.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
