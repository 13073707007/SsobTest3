package com.loongme.details;

import java.net.URLEncoder;

import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
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
 * 修改性别
 * 
 * @author xywy
 * 
 */
public class SexActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_sex;
	private Button btn_sex_submit;
	// private EditText et_sex;
	private String Id;
	private SharedPreferences mPreferences;
	private RadioGroup group;
	private String str;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sex);
		mPreferences = getSharedPreferences("USER", MODE_PRIVATE);
		Id = mPreferences.getString("Id", "");
		initview();
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				// 获取变更后的选中项的ID
				int radioButtonId = group.getCheckedRadioButtonId();
				// 根据ID获取RadioButton的实例
				RadioButton rb = (RadioButton) SexActivity.this
						.findViewById(radioButtonId);
				str = (String) rb.getText();
			}
		});
	}

	private void initview() {
		// TODO Auto-generated method stub
		btn_sex_submit = (Button) findViewById(R.id.btn_sex_submit);
		// <<<<<<< .mine
		// iv_sex = (ImageView) findViewById(R.id.iv_sex);
		// et_sex = (EditText) findViewById(R.id.et_sex);
		// =======
		// et_sex = (EditText) findViewById(R.id.et_sex);
		group = (RadioGroup) this.findViewById(R.id.rg_group);
		iv_sex = (ImageView) findViewById(R.id.iv_sex);
		// >>>>>>> .r40
		btn_sex_submit.setOnClickListener(this);
		iv_sex.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_sex_submit:
			PostUploading();
			break;
		// <<<<<<< .mine
		// case R.id.iv_sex:
		// startActivity(new Intent(getApplicationContext(),
		// MeDataActivity.class));
		// finish();
		// break;
		// =======
		case R.id.iv_sex:
			startActivity(new Intent(getApplicationContext(),
					MeDataActivity.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
			break;

		// >>>>>>> .r40
		default:
			break;
		}
	}

	private void PostUploading() {
		// TODO Auto-generated method stub
		String url = "http://120.24.37.141:8080/LoginAndResigister/UpdateSexServlet";
		HttpUtils httpUtils = new HttpUtils(5000);
		RequestParams params = new RequestParams("utf-8");
		params.addHeader(
				"Content-Type",
				"application/json;charset=UTF-8;text/json;text/javascript;application/soap+xml;text/html;text/xml;text/plain");
		JSONObject obj = new JSONObject();
		try {
			obj.put("userId", Id);
			// <<<<<<< .mine
			// obj.put("userSex",
			// URLEncoder.encode(et_sex.getText().toString(), "utf-8"));
			// =======
			obj.put("userSex", URLEncoder.encode(str, "utf-8"));
			// >>>>>>> .r40
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
						return;
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
										"修改昵称失败!!", 0).show();
							}
						}
					}
				});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		startActivity(new Intent(getApplicationContext(), MeDataActivity.class));
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
		finish();
		return super.onKeyDown(keyCode, event);
	}
}
