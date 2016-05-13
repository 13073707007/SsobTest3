package com.loongme.personage;

import java.io.File;
import java.io.FileInputStream;

import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.loongme.activity.R;
import com.loongme.base.BaseActivity;
import com.loongme.business.Helpers;
import com.loongme.common.BonkeStatus;
import com.loongme.common.Configuration;
import com.loongme.common.UserType;
import com.loongme.details.MerDesActivity;
import com.loongme.details.MerIntActivity;
import com.loongme.details.PerDesActivity;
import com.loongme.details.PerIntActivity;
import com.loongme.util.StringUtil;
import com.loongme.util.UIUtils;
import com.loongme.view.CustomProgressDialog;

/**
 * 
 * @author 申请帮客
 * 
 */
public class GuestActivity extends BaseActivity implements OnClickListener {

	/** 申请帮客的用户类型 默认是商家 */
	private UserType bonkeType = UserType.Merchant;
	/** 加载框 */
	CustomProgressDialog progressDialog;

	private ImageView iv_fanhuig, iv_business_license, iv_identity_just,
			iv_identity_against;
	private RadioGroup group2;
	private RadioButton rb_gr, rb_sj;
	private LinearLayout ll_gr;
	private ScrollView sl_sj;
	private Button bt_submit, bt_submit2;
	private RelativeLayout rl_sj_ms, rl_sj_js, rl_gr_pj, rl_gr_js;
	private SharedPreferences mPreferences;
	private String Id;
	private EditText et_busName, et_busAddress, et_busContact, et_busPhone,
			et_busGhone;
	private String et_mer_des_one, et_mer_des_two, et_mer_des_three;
	private TextView aaaa;

	private EditText edit_personalName;
	private EditText edit_personalJob;
	private EditText edit_personalPhone;

	/**
	 * 个人申请参数
	 */
	private String personalName;// 个人真实姓名
	private String personalJob;// 个人职业
	private String personalMobile;// 个人手机号
	private String personalJudge;// 个人评价
	private String personalDes;// 个人详细介绍

	/**
	 * 商家申请参数
	 */
	private String merchant_describe;// 商家描述
	// private String ed_introduce;
	private String merchant_introduce;// 商家详细介绍
	private String busGhone;// 商家座机
	private String busPhone;// 商家手机
	private String busContact;// 商家联系人
	private String busAddress;// 商家地址
	private String busName;// 商家姓名
	/** SharedPreferences中储存数据的路径 **/
	public final static String DATA_URL = "/data/data/";
	public final static String SHARED_MERDES_XML = "merdes.xml";
	private SharedPreferences spf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guest);
		initView();
		SharedPreferences();
	}

	private void SharedPreferences() {
		mPreferences = getSharedPreferences("USER", MODE_APPEND);
		Id = mPreferences.getString("Id", "");
		spf = getSharedPreferences("merdes", MODE_APPEND);
		merchant_introduce = spf.getString("busIntroduce", "");
		merchant_describe = spf.getString("busDesc", "");
	}

	private void initView() {
		progressDialog = new CustomProgressDialog(this, "数据加载中...",
				R.anim.loading);
		edit_personalName = (EditText) findViewById(R.id.editText1);
		edit_personalJob = (EditText) findViewById(R.id.editText2);
		edit_personalPhone = (EditText) findViewById(R.id.editText4);
		et_busName = (EditText) findViewById(R.id.et_busName);
		et_busAddress = (EditText) findViewById(R.id.et_busAddress);
		et_busContact = (EditText) findViewById(R.id.et_busContact);
		et_busPhone = (EditText) findViewById(R.id.et_busPhone);
		et_busGhone = (EditText) findViewById(R.id.et_busGhone);
		iv_business_license = (ImageView) findViewById(R.id.iv_business_license);
		iv_identity_just = (ImageView) findViewById(R.id.iv_identity_just);
		iv_identity_against = (ImageView) findViewById(R.id.iv_identity_against);
		iv_fanhuig = (ImageView) findViewById(R.id.iv_fanhuig);
		bt_submit = (Button) findViewById(R.id.bt_submit);
		bt_submit2 = (Button) findViewById(R.id.bt_submit2);
		rl_sj_ms = (RelativeLayout) findViewById(R.id.rl_sj_ms);
		rl_sj_js = (RelativeLayout) findViewById(R.id.rl_sj_js);
		rl_gr_pj = (RelativeLayout) findViewById(R.id.rl_gr_pj);
		rl_gr_js = (RelativeLayout) findViewById(R.id.rl_gr_js);
		iv_business_license.setOnClickListener(this);
		iv_identity_just.setOnClickListener(this);
		iv_identity_against.setOnClickListener(this);
		bt_submit.setOnClickListener(this);
		bt_submit2.setOnClickListener(this);
		iv_fanhuig.setOnClickListener(this);
		rl_gr_js.setOnClickListener(this);
		rl_gr_pj.setOnClickListener(this);
		rl_sj_js.setOnClickListener(this);
		rl_sj_ms.setOnClickListener(this);
		ll_gr = (LinearLayout) findViewById(R.id.ll_gr);
		sl_sj = (ScrollView) findViewById(R.id.sl_sj);
		group2 = (RadioGroup) findViewById(R.id.group2);
		rb_gr = (RadioButton) findViewById(R.id.rb_gr);
		rb_sj = (RadioButton) findViewById(R.id.rb_sj);
		group2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == rb_gr.getId()) {
					ll_gr.setVisibility(View.VISIBLE);
					sl_sj.setVisibility(View.GONE);
					bonkeType = UserType.Personal;
				} else if (checkedId == rb_sj.getId()) {
					ll_gr.setVisibility(View.GONE);
					sl_sj.setVisibility(View.VISIBLE);
					bonkeType = UserType.Merchant;
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_fanhuig:
			deleteCache(Configuration.BUSLICENSE_SAVEPATH);
			deleteCache(Configuration.IDCARD_MAINSIDE_SAVEPATH);
			deleteCache(Configuration.IDCARD_REVERSESIDE_SAVEPATH);
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
			break;
		case R.id.iv_business_license:
			BusinessLicenseActivity.requestCode = Configuration.SELECT_LICENSE;
			Intent intent = new Intent(getApplicationContext(),
					BusinessLicenseActivity.class);
			startActivityForResult(intent, Configuration.SELECT_LICENSE);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.iv_identity_just:
			BusinessLicenseActivity.requestCode = Configuration.SELECT_IDCARD_MAINSIDE;
			Intent intent1 = new Intent(getApplicationContext(),
					BusinessLicenseActivity.class);
			startActivityForResult(intent1,
					Configuration.SELECT_IDCARD_MAINSIDE);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.iv_identity_against:
			BusinessLicenseActivity.requestCode = Configuration.SELECT_IDCARD_REVERSESIDEE;
			Intent intent2 = new Intent(getApplicationContext(),
					BusinessLicenseActivity.class);
			startActivityForResult(intent2,
					Configuration.SELECT_IDCARD_REVERSESIDEE);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.rl_gr_js:
			startActivityForResult(new Intent(getApplicationContext(),
					PerIntActivity.class),
					Configuration.REQUEST_PERSONAL_INTRODUCE);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.rl_gr_pj:
			startActivityForResult(new Intent(getApplicationContext(),
					PerDesActivity.class), Configuration.REQUEST_PERSONAL_DES);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.rl_sj_js:
			startActivityForResult(new Intent(getApplicationContext(),
					MerIntActivity.class), Configuration.REQUEST_BUSINTRODUCE);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.rl_sj_ms:
			// SharedPreferences sf = getSharedPreferences("merdes",
			// MODE_APPEND);
			// Editor editor = sf.edit();
			// editor.putString("busName", et_busName.getText().toString());
			// editor.putString("busAddress",
			// et_busAddress.getText().toString());
			// editor.putString("busContact",
			// et_busContact.getText().toString());
			// editor.putString("busPhone", et_busPhone.getText().toString());
			// editor.putString("busGhone", et_busGhone.getText().toString());
			// editor.commit();
			startActivityForResult(new Intent(getApplicationContext(),
					MerDesActivity.class), Configuration.REQUEST_BUSDES);
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;
		case R.id.bt_submit:// 商家
			initMerchant();
			// <<<<<<< .mine
			startActivity(new Intent(getApplicationContext(),
					PersonageActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			finish();
			// =======
			// >>>>>>> .r40
			break;
		case R.id.bt_submit2:// 个人
			initPersonage();
			startActivity(new Intent(getApplicationContext(),
					PersonageActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			break;

		default:
			break;
		}
	}

	/**
	 * 个人帮客申请
	 */
	private void initPersonage() {

		personalName = edit_personalName.getText().toString();
		personalJob = edit_personalJob.getText().toString();
		personalMobile = edit_personalPhone.getText().toString();
		if (StringUtil.isEmpty(personalName)) {
			UIUtils.showToast(this, "请填写个人姓名");
			return;
		}
		if (StringUtil.isEmpty(personalJob)) {
			UIUtils.showToast(this, "请填写手机号职业");
			return;
		}
		if (StringUtil.isEmpty(personalMobile)) {
			UIUtils.showToast(this, "请填写手机号");
			return;
		}
		if (!StringUtil.isMobile(personalMobile)) {
			UIUtils.showToast(this, "手机号格式不正确");
			return;
		}
		File mainSide = new File(Configuration.IDCARD_MAINSIDE_SAVEPATH);
		if (!mainSide.exists()) {
			UIUtils.showToast(this, "请上传身份证正面");
			return;
		}
		File reverseSide = new File(Configuration.IDCARD_REVERSESIDE_SAVEPATH);
		if (!reverseSide.exists()) {
			UIUtils.showToast(this, "请上传身份证正面");
			return;
		}
		String url = "http://120.24.37.141:8080/LoginAndResigister/ser_register";
		HttpUtils httpUtils = new HttpUtils(10000);
		RequestParams params = new RequestParams("UTF-8");
		JSONObject merchantInfo = new JSONObject();
		JSONObject personalInfo = new JSONObject();
		JSONObject busReg = new JSONObject();
		try {
			merchantInfo.put("busName", null);
			// merchantInfo.put("busAddress", busAddress);
			// merchantInfo.put("busContact", busContact);
			// merchantInfo.put("busPhone", busPhone);
			// merchantInfo.put("busGhone", busGhone);
			// merchantInfo.put("busDesc", merchant_describe);
			// merchantInfo.put("busIntroduce", merchant_introduce);
			// merchantInfo.put("aliId", "ali");
			// merchantInfo.put("wechatId", "wechat");

			personalInfo.put("persName", personalName);
			personalInfo.put("persJob", personalJob);
			personalInfo.put("persDesc", personalDes);
			personalInfo.put("persPhone", personalMobile);
			// personalInfo.put("persBack", null);
			// personalInfo.put("persFront", null);
			personalInfo.put("persIntroduce", personalJudge);
			busReg.put("userId", Helpers.getUserInfo(this).getUserId());
			busReg.put("busReg", merchantInfo);
			busReg.put("persReg", personalInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		params.addBodyParameter("requestMessage", busReg.toString());
		// params.addBodyParameter("image0.png", "");
		try {
			params.addBodyParameter("image0.png",
					new FileInputStream(mainSide), mainSide.length());
			params.addBodyParameter("image1.png", new FileInputStream(
					reverseSide), reverseSide.length());
		} catch (Exception e) {
		}

		progressDialog.show();
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						progressDialog.dismiss();
						UIUtils.showToast(GuestActivity.this, "申请失败");
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						progressDialog.dismiss();
						if (arg0.result == null) {
							UIUtils.showToast(GuestActivity.this, "申请失败！");
						}
						try {
							if (arg0.statusCode == 200) {
								JSONObject obj = new JSONObject(arg0.result);
								if (obj.getInt("status") == 3) {
									UIUtils.showToast(GuestActivity.this,
											"审核已提交");
									// 提交审核成功
									deleteCache(Configuration.IDCARD_MAINSIDE_SAVEPATH);
									deleteCache(Configuration.IDCARD_REVERSESIDE_SAVEPATH);
									// 保存帮客状态
									Helpers.saveBonkeStatusToLocal(
											GuestActivity.this,
											BonkeStatus.BonkeChecking);
									setResult(RESULT_OK);
									finish();

								} else if (obj.getInt("status") == BonkeStatus.BonkeChecking
										.getCode()) {
									UIUtils.showToast(GuestActivity.this,
											BonkeStatus.BonkeChecking
													.getVaule());
								} else if (obj.getInt("status") == BonkeStatus.BonkeChecked
										.getCode()) {
									UIUtils.showToast(GuestActivity.this,
											BonkeStatus.BonkeChecked.getVaule());
								} else {
									UIUtils.showToast(GuestActivity.this,
											"申请失败");
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
	}

	/**
	 * 商家帮客申请
	 */
	private void initMerchant() {
		busName = et_busName.getText().toString();
		busAddress = et_busAddress.getText().toString();
		busContact = et_busContact.getText().toString();
		busPhone = et_busPhone.getText().toString();
		busGhone = et_busGhone.getText().toString();
		if (StringUtil.isEmpty(busName)) {
			UIUtils.showToast(this, "商家姓名不能为空");
			return;
		}
		if (StringUtil.isEmpty(busAddress)) {
			UIUtils.showToast(this, "商家地址不能为空");
			return;
		}
		if (StringUtil.isEmpty(busContact)) {
			UIUtils.showToast(this, "商家联系人不能为空");
			return;
		}
		if (StringUtil.isEmpty(busPhone)) {
			UIUtils.showToast(this, "商家手机不能为空");
			return;
		}
		if (StringUtil.isEmpty(busGhone)) {
			UIUtils.showToast(this, "商家座机不能为空");
			return;
		}
		if (!StringUtil.isMobile(busPhone)) {
			UIUtils.showToast(this, "商家手机号格式不正确");
			return;
		}
		File file = new File(Configuration.BUSLICENSE_SAVEPATH);
		if (!file.exists()) {
			UIUtils.showToast(this, "请上传营业执照");
			return;
		}
		String url = "http://120.24.37.141:8080/LoginAndResigister/ser_register";
		HttpUtils httpUtils = new HttpUtils(10000);
		RequestParams params = new RequestParams("UTF-8");
		JSONObject merchantInfo = new JSONObject();
		JSONObject personalInfo = new JSONObject();
		JSONObject busReg = new JSONObject();
		try {
			merchantInfo.put("busName", busName);
			merchantInfo.put("busAddress", busAddress);
			merchantInfo.put("busContact", busContact);
			merchantInfo.put("busPhone", busPhone);
			merchantInfo.put("busGhone", busGhone);
			merchantInfo.put("busDesc", merchant_describe);
			merchantInfo.put("busIntroduce", merchant_introduce);
			merchantInfo.put("aliId", "ali");
			merchantInfo.put("wechatId", "wechat");

			personalInfo.put("persName", null);
			// personalInfo.put("persJob", "");
			// personalInfo.put("persDesc", "");
			// personalInfo.put("persPhone", "");
			// personalInfo.put("persBack", null);
			// personalInfo.put("persFront", null);
			// personalInfo.put("persIntroduce", null);
			busReg.put("userId", Helpers.getUserInfo(this).getUserId());
			busReg.put("busReg", merchantInfo);
			busReg.put("persReg", personalInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		params.addBodyParameter("requestMessage", busReg.toString());
		// params.addBodyParameter("image0.png", "");
		try {
			params.addBodyParameter("image0.png", new FileInputStream(file),
					file.length());
		} catch (Exception e) {
		}

		progressDialog.show();
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						progressDialog.dismiss();
						UIUtils.showToast(GuestActivity.this, "申请失败");
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						progressDialog.dismiss();
						if (arg0.result == null) {
							UIUtils.showToast(GuestActivity.this, "申请失败！");
						}
						try {
							if (arg0.statusCode == 200) {
								JSONObject obj = new JSONObject(arg0.result);
								if (obj.getInt("status") == 3) {
									UIUtils.showToast(GuestActivity.this,
											"审核已提交");
									// 提交审核成功
									deleteCache(Configuration.BUSLICENSE_SAVEPATH);
									// 保存帮客状态
									Helpers.saveBonkeStatusToLocal(

									GuestActivity.this,
											BonkeStatus.BonkeChecking);
									setResult(RESULT_OK);
									finish();

								} else if (obj.getInt("status") == BonkeStatus.BonkeChecking
										.getCode()) {
									UIUtils.showToast(GuestActivity.this,
											BonkeStatus.BonkeChecking
													.getVaule());
								} else if (obj.getInt("status") == BonkeStatus.BonkeChecked
										.getCode()) {
									UIUtils.showToast(GuestActivity.this,
											BonkeStatus.BonkeChecked.getVaule());
								} else {
									UIUtils.showToast(GuestActivity.this,
											"申请失败");
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			deleteCache(Configuration.BUSLICENSE_SAVEPATH);
			deleteCache(Configuration.IDCARD_MAINSIDE_SAVEPATH);
			deleteCache(Configuration.IDCARD_REVERSESIDE_SAVEPATH);
			startActivity(new Intent(getApplicationContext(),
					PersonageActivity.class));
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
		// 选择商家营业执照
		if (requestCode == Configuration.SELECT_LICENSE) {
			Bitmap bitmap = BitmapFactory
					.decodeFile(Configuration.BUSLICENSE_SAVEPATH);
			iv_business_license.setImageBitmap(bitmap);
		}
		// 填写商家描述
		if (requestCode == Configuration.REQUEST_BUSDES) {
			merchant_describe = data.getExtras().getString("busDesc", "");
		}
		// 填写商家介绍
		if (requestCode == Configuration.REQUEST_BUSINTRODUCE) {
			merchant_introduce = data.getExtras().getString("busIntroduce", "");
		}
		// 填写个人描述
		if (requestCode == Configuration.REQUEST_PERSONAL_DES) {
			personalDes = data.getExtras().getString("persDesc", "");
		}
		// 填写个人介绍
		if (requestCode == Configuration.REQUEST_PERSONAL_INTRODUCE) {
			personalJudge = data.getExtras().getString("persIntroduce", "");
		}
		// 选择身份证正面
		if (requestCode == Configuration.SELECT_IDCARD_MAINSIDE) {
			Bitmap bitmap = BitmapFactory
					.decodeFile(Configuration.IDCARD_MAINSIDE_SAVEPATH);
			iv_identity_just.setImageBitmap(bitmap);
		}

		// 选择身份证反面
		if (requestCode == Configuration.SELECT_IDCARD_REVERSESIDEE) {
			Bitmap bitmap = BitmapFactory
					.decodeFile(Configuration.IDCARD_REVERSESIDE_SAVEPATH);
			iv_identity_against.setImageBitmap(bitmap);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 删除营业执照缓存
	 */
	private void deleteCache(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}

	}

}
