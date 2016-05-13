package com.loongme.personage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.loongme.activity.R;
import com.loongme.adapter.ClassifyListAdapter;
import com.loongme.adapter.ClassifySubAdapter;
import com.loongme.base.AppManager;
import com.loongme.com.model.SSBonServer;
import com.loongme.com.model.SSBonServerSub;
import com.loongme.common.Configuration;
import com.loongme.details.ServerActivity;
import com.loongme.util.UIUtils;
import com.loongme.view.CustomProgressDialog;

/**
 * 服务分类
 * 
 * @author xywy
 * 
 */
public class ReleaseActivity extends FragmentActivity implements
		OnClickListener, OnItemClickListener {
	private ListView llistview1, sublistView;
	ImageView iv_fanhuire1;
	private List<String> list, sublist, sublist2, sublist3, sublist4, sublist5,
			sublist6;
	private ClassifyListAdapter classifyListAdapter;
	private ClassifySubAdapter classifySubAdapter;
	private List<SSBonServer> datas;
	private List<SSBonServerSub> subs;
	private SSBonServer currentSSBonServer;
	private SSBonServerSub currentSSBonServerSub;
	private CustomProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_release);
		AppManager.getAppManager().addActivity(this);
		datas = new ArrayList<SSBonServer>();
		subs = new ArrayList<SSBonServerSub>();
		initView();
	}

	private void initView() {
		iv_fanhuire1 = (ImageView) findViewById(R.id.iv_fanhuire1);
		iv_fanhuire1.setOnClickListener(this);
		llistview1 = (ListView) findViewById(R.id.llistview1);
		sublistView = (ListView) findViewById(R.id.sublistView);
		llistview1.setOnItemClickListener(this);
		sublistView.setOnItemClickListener(sublistview);
		classifyListAdapter = new ClassifyListAdapter(ReleaseActivity.this,
				datas);
		llistview1.setAdapter(classifyListAdapter);
		classifySubAdapter = new ClassifySubAdapter(ReleaseActivity.this, subs);
		sublistView.setAdapter(classifySubAdapter);
		progressDialog = new CustomProgressDialog(this, "加载中...",
				R.anim.loading);
		initData();

	}

	private void initData() {
		progressDialog.show();
		String url = "http://120.24.37.141:8080/LoginAndResigister/serverType";
		HttpUtils httpUtils = new HttpUtils(5000);
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				progressDialog.dismiss();
				Toast.makeText(getApplicationContext(),
						arg0.getExceptionCode(), 0).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				progressDialog.dismiss();
				try {
					if (arg0.result == null) {
						UIUtils.showToast(ReleaseActivity.this, "请求失败请检查网络");
						return;
					}
					JSONObject object = new JSONObject(arg0.result);
					List<SSBonServer> list = JSON.parseArray(
							object.getString("servertype"), SSBonServer.class);
					// 刷新服务列表
					if (datas.size() != 0) {
						datas.clear();
					}
					datas.addAll(list);
					classifyListAdapter.notifyDataSetChanged();
					currentSSBonServer = datas.get(0);
					// 刷新子类列表
					if (subs.size() != 0) {
						subs.clear();
					}
					subs.addAll(datas.get(0).getSubName());
					classifySubAdapter.notifyDataSetChanged();

				} catch (Exception e) {
					e.printStackTrace();
				}
				// <<<<<<< .mine
				// =======
				// classifyListAdapter = new ClassifyListAdapter(
				// ReleaseActivity.this, list);
				// llistview1.setAdapter(classifyListAdapter);
				// sublistView.setAdapter(new ClassifySubAdapter(
				// ReleaseActivity.this, sublist));
				// >>>>>>> .r26
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_fanhuire1:
			deleteServerImageCache();
//			startActivity(new Intent(getApplicationContext(), MeGuset2Activity.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			deleteServerImageCache();
//			startActivity(new Intent(getApplicationContext(), MeGuset2Activity.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		currentSSBonServer = datas.get(position);
		if (subs.size() != 0) {
			subs.clear();
		}
		subs.addAll(datas.get(position).getSubName());
		classifyListAdapter.setSelectedPosition(position);
		classifyListAdapter.notifyDataSetChanged();
		classifySubAdapter.notifyDataSetChanged();
		// switch (position) {
		// case 0:
		// sublistView.setAdapter(new ClassifySubAdapter(ReleaseActivity.this,
		// sublist));
		//
		// break;
		// case 1:
		// sublistView.setAdapter(new ClassifySubAdapter(ReleaseActivity.this,
		// sublist2));
		// break;
		// case 2:
		// sublistView.setAdapter(new ClassifySubAdapter(ReleaseActivity.this,
		// sublist3));
		// break;
		// case 3:
		// sublistView.setAdapter(new ClassifySubAdapter(ReleaseActivity.this,
		// sublist4));
		// break;
		// case 4:
		// sublistView.setAdapter(new ClassifySubAdapter(ReleaseActivity.this,
		// sublist5));
		// break;
		// case 5:
		// sublistView.setAdapter(new ClassifySubAdapter(ReleaseActivity.this,
		// sublist6));
		// break;
		//
		// default:
		// break;
		// }
	}

	OnItemClickListener sublistview = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			currentSSBonServerSub = subs.get(position);
			Intent intent = new Intent(ReleaseActivity.this,
					ServerActivity.class);
			intent.putExtra("serverName", currentSSBonServer.getName());
			intent.putExtra("subServerName", currentSSBonServerSub.getName());
			startActivity(intent);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		}
	};

	@Override
	protected void onDestroy() {
		AppManager.getAppManager().finishActivity(this);
		super.onDestroy();
	}

	private void deleteFileCache(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
	}

	private void deleteServerImageCache() {
		deleteFileCache(Configuration.SERVER_COVER_SAVEPATH);
		deleteFileCache(Configuration.SERVER_WORK_SAVEPATH);
	}
}
