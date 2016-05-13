package com.loongme.personage;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.loongme.activity.R;
import com.loongme.adapter.MyAdapterLL;
import com.loongme.api.DeleteServerSucessCallBack;
import com.loongme.base.BaseActivity;
import com.loongme.business.Helpers;
import com.loongme.com.model.Data2;
import com.loongme.com.model.MyServer;
import com.loongme.util.UIUtils;

/**
 * 我的服务
 * 
 * @author xywy
 * 
 */
public class MeserveActivity extends BaseActivity implements OnClickListener,
		DeleteServerSucessCallBack {
	private ListView lvserve;
	private List<Data2> lists;
	private MyAdapterLL adapter;
	private ImageView iv_fanhuims;
	private List<MyServer> datas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_meserve);
		lvserve = (ListView) findViewById(R.id.lvserve);
		datas = new ArrayList<MyServer>();
		adapter = new MyAdapterLL(datas, this, this);
		lvserve.setAdapter(adapter);
		initView();
		parseHttpRequest();

		lvserve.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});
	}

	private void initView() {
		iv_fanhuims = (ImageView) findViewById(R.id.iv_fanhuims);
		iv_fanhuims.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_fanhuims:
			finish();
			break;

		default:
			break;
		}
	}

	private void parseHttpRequest() {
		String url = "http://120.24.37.141:8080/LoginAndResigister/ProvideInfoServlet";
		HttpUtils httpUtils = new HttpUtils(10000);
		RequestParams params = new RequestParams("UTF-8");
		JSONObject object = new JSONObject();
		try {
			object.put("userId", Helpers.getUserInfo(this).getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		params.addBodyParameter("requestMessage", object.toString());
		progressDialog.show();
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						progressDialog.dismiss();
						UIUtils.showToast(MeserveActivity.this, "请求数据失败，请重试");
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						progressDialog.dismiss();
						if (arg0.result == null) {
							UIUtils.showToast(MeserveActivity.this,
									"请求数据失败，请重试！");
						}
						try {
							if (arg0.statusCode == 200) {
								JSONObject object = new JSONObject(arg0.result);
								if (object.getInt("status") != 1) {
									UIUtils.showToast(MeserveActivity.this,
											"请求数据失败");
									return;
								}
								List<MyServer> list = JSON.parseArray(
										object.getString("provideMsg"),
										MyServer.class);
								if (datas.size() != 0) {
									datas.clear();
								}
								datas.addAll(list);
								adapter.notifyDataSetChanged();

							} else {
								UIUtils.showToast(MeserveActivity.this,
										"请求数据失败，请重试");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startActivity(new Intent(getApplicationContext(), MeGuset2Activity.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onDeleteServerSucess() {
		adapter.notifyDataSetChanged();
	}

}
