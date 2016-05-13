package com.loongme.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.loongme.activity.R;
import com.loongme.base.BaseActivity;
import com.loongme.util.DeviceUtil;
import com.loongme.util.Spilt;
import com.loongme.util.SsbService;

public class RegisterActivity extends BaseActivity {

	SsbService service = new SsbService();
	private static final int SUCCESS = 10;
	private static final int FAIL = 11;
	private static final int COUNTDOWN_END = -8;
	private static final int COUNTDOWN = -9;
	private final static int DATE_DIALOG = 21;
	private String IMEI;
	// 选择框选择的内容
	private String phone;
	private String qq;
	private String birthday;
	private String password;
	private String passwordTwo;
	private String item;
	private String code;
	private Calendar c = null;
	private String birthdayYear;
	private String birthdayMonth;
	private String birthdayDay;
	EditText phoneView;
	EditText qqView;
	EditText passwordView;
	EditText passwordTwoView;
	EditText birthdayView;
	EditText codeView;
	Button chooseBirthdayBt;
	Button verificationSend;
	Button chooseIDImage;
	ImageView IDImage;
	View choose_business;
	Toast mToast;
	TableRow rowQq;
	TableRow rowPhone;
	TableRow rowCode;
	// 发送到服务器的信息
	private String msg;
	// 接收服务器的信息
	private String receiveMsg;
	// 服务器消息的反馈
	private String getReceiveMsg;
	private final String TAG = "REGISTER";
	// 短信验证
	private final String SMS_APP_ID = "953564c806ed";
	private final String SMS_APP_SECRET = "ee2eeead52b7bd4452818c60948b5c62";
	int i;
	// 判断验证码是否正确
	private boolean verificationIsTure = false;
	// 判断选择方式是QQ还是手机
	private boolean isQq = false;
	// 手指向右滑动时的最小速度
	private static final int XSPEED_MIN = 200;
	// 手指向右滑动时的最小距离
	private static final int XDISTANCE_MIN = 150;
	// 记录手指按下时的横坐标。
	private float xDown;
	// 记录手指移动时的横坐标。
	private float xMove;
	// 用于计算手指滑动的速度。
	private VelocityTracker mVelocityTracker;

	// SsbService service;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		getIMEINum();
		// 初始化界面
		initLayout();
		SMSSDK.initSDK(this, SMS_APP_ID, SMS_APP_SECRET);
		EventHandler eventHandler = new EventHandler() {
			/**
			 * 在操作之后被触发
			 * 
			 * @param event
			 *            参数1
			 * @param result
			 *            参数2 SMSSDK.RESULT_COMPLETE表示操作成功，为SMSSDK.
			 *            RESULT_ERROR表示操作失败
			 * @param data
			 *            事件操作的结果
			 */
			@Override
			public void afterEvent(int event, int result, Object data) {
				if (result == SMSSDK.RESULT_COMPLETE) {
					// 回调完成
					if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
						// 提交验证码成功
					} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
						// 获取验证码成功
					} else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
						// 返回支持发送验证码的国家列表
					}
				} else {
					((Throwable) data).printStackTrace();
				}
				Message msg = new Message();
				msg.what = event;
				msg.arg2 = result;
				msg.obj = data;
				mHandler.sendMessage(msg);
			}
		};
		// 注册回调监听接口
		SMSSDK.registerEventHandler(eventHandler);

	}

	/**
	 * 初始化界面
	 */
	private void initLayout() {
		TextView tx = (TextView) findViewById(R.id.tv_title);
		tx.setText("注册");
		phoneView = (EditText) findViewById(R.id.register_phone);
		codeView = (EditText) findViewById(R.id.register_verification_code);
		qqView = (EditText) findViewById(R.id.register_qq);
		passwordView = (EditText) findViewById(R.id.register_password);
		passwordTwoView = (EditText) findViewById(R.id.register_passwordTwo);
		birthdayView = (EditText) findViewById(R.id.register_birthday);
		verificationSend = (Button) findViewById(R.id.register_verification_send);
		chooseBirthdayBt = (Button) findViewById(R.id.register_choose_birthday);
		chooseIDImage = (Button) findViewById(R.id.register_choose_ID_image);
		IDImage = (ImageView) findViewById(R.id.register_ID_image);
		choose_business = findViewById(R.id.register_choose_business);

		rowQq = (TableRow) findViewById(R.id.register_row_qq);
		rowPhone = (TableRow) findViewById(R.id.register_row_phone);
		rowCode = (TableRow) findViewById(R.id.register_row_code);
		// editview点击时隐藏Hint，失去焦点后恢复hint
		findViewById(R.id.register_birthday).setOnFocusChangeListener(
				onFocusChage);
		findViewById(R.id.register_phone)
				.setOnFocusChangeListener(onFocusChage);
		findViewById(R.id.register_password).setOnFocusChangeListener(
				onFocusChage);
		findViewById(R.id.register_passwordTwo).setOnFocusChangeListener(
				onFocusChage);
		findViewById(R.id.register_verification_code).setOnFocusChangeListener(
				onFocusChage);

		findViewById(R.id.btn_setting).setVisibility(View.GONE);
		findViewById(R.id.btn_back).setVisibility(View.VISIBLE);
		findViewById(R.id.btn_login).setVisibility(View.VISIBLE);
		// 设置按钮监听
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		findViewById(R.id.btn_login).setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				finish();
			}
		});
		chooseBirthdayBt.setOnClickListener(onclick);
		choose_business.setOnClickListener(onclick);
		chooseIDImage.setOnClickListener(onclick);
		findViewById(R.id.register_submit).setOnClickListener(onclick);
		findViewById(R.id.register_verification_send).setOnClickListener(
				onclick);

		/* 设置性别下拉菜单 */
		List<String> list = new ArrayList<String>();
		list.add("男");
		list.add("女");
		Spinner mySpinner = (Spinner) findViewById(R.id.register_sex);
		// 第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				RegisterActivity.this, android.R.layout.simple_spinner_item,
				list);
		// 第三步：为适配器设置下拉列表下拉时的菜单样式。
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 第四步：将适配器添加到下拉列表上
		mySpinner.setAdapter(adapter);
		mySpinner.setOnItemSelectedListener(sexListener);

		/* 设置注册方式下拉菜单 */
		List<String> listWay = new ArrayList<String>();
		listWay.add("手机号码注册");
		listWay.add("QQ号注册");
		Spinner twoSpinner = (Spinner) findViewById(R.id.register_choose_way_spiner);
		// 第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
		final ArrayAdapter<String> adapterWay = new ArrayAdapter<String>(
				RegisterActivity.this, android.R.layout.simple_spinner_item,
				listWay);
		// 第三步：为适配器设置下拉列表下拉时的菜单样式。
		adapterWay
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 第四步：将适配器添加到下拉列表上
		twoSpinner.setAdapter(adapterWay);
		twoSpinner.setOnItemSelectedListener(chooseWayListener);
	}

	/**
	 * 界面点击监听
	 */
	OnClickListener onclick = new OnClickListener() {
		public void onClick(View v) {
			// 通过sdk发送短信验证
			switch (v.getId()) {
			case R.id.btn_back:
				finish();
				break;
			case R.id.register_verification_send:
				phone = phoneView.getText().toString().trim();
				if (phone.length() != 11) {
					Toast.makeText(RegisterActivity.this, "手机号码格式错误",
							Toast.LENGTH_LONG).show();
					break;
				}
				phone = phoneView.getText().toString().trim();
				SMSSDK.getVerificationCode("86", phone);
				verificationSend.setClickable(false);
				verificationSend.setText("30s后可重新发送");

				new Thread(new Runnable() {
					@Override
					public void run() {
						for (i = 30; i > 0; i--) {
							mHandler.sendEmptyMessage(COUNTDOWN);
							if (i <= 0) {
								break;
							}
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						mHandler.sendEmptyMessage(COUNTDOWN_END);
					}
				}).start();
				break;
			case R.id.register_submit:
				if (!isQq) {
					if (!verificationIsTure) {
						failNotice(R.id.register_notice, "手机验证码错误");
						Log.i(TAG, "手机验证码错误");
						break;
					}
					Log.i(TAG, "手机验证码验证成功");
					phone = phoneView.getText().toString().trim();
					if (phone.equals("")) {
						failNotice(R.id.register_notice, "手机号码不能为空");
						break;
					}
				} else {
					qq = qqView.getText().toString().trim();
					if (qq.equals("")) {
						failNotice(R.id.register_notice, "QQ号码不能为空");
						break;
					}
				}
				password = passwordView.getText().toString().trim();
				if (password.equals("")) {
					failNotice(R.id.register_notice, "密码不能为空");
					break;
				}
				String number;
				if (isQq) {
					number = qq;
				} else {
					number = phone;
				}
				if (chooseBirthdayBt.getText().toString().trim()
						.equals("点击选择出生日期")) {
					failNotice(R.id.register_notice, "请选择出生日期");
					break;
				}
				// birthday = birthdayView.getText().toString().trim();
				msg = "Pis_User_New {COMM_INFO {BUSI_CODE 10011} {REGION_ID A} {COUNTY_ID A00} {OFFICE_ID 22342} {OPERATOR_ID 43643} {CHANNEL A2} {OP_MODE SUBMIT} } { {TEL_NUM "
						+ number
						+ "} {PASSWD "
						+ password
						+ "} {WXOPEN_ID "
						+ IMEI
						+ "} {BIRTH "
						+ birthday
						+ "} {GENDER "
						+ item
						+ "} }";
				Log.i(TAG, msg);
				// 发送信息到服务器
				try {
					// 等待，接收来自服务器返回的消息
					service.connecttoserver();
					System.out.println("run thread");
					if (service.getSocket().isConnected()) {
						service.SendMsg(service.getSocket(), msg);
						Log.i(TAG, msg);
					}
				} catch (Exception e) {
					e.printStackTrace();
					Log.e(TAG, e.toString());
				}
				// 接收服务器信息
				try {
					receiveMsg = service.ReceiveMsg(service.getSocket());
					if (receiveMsg != null && receiveMsg.contains("REG_RESULT")) {
						Spilt sp = new Spilt();
						System.out.println(receiveMsg);
						getReceiveMsg = sp.spiltGeneralString(receiveMsg,
								"REG_RESULT", TAG);
						System.out.println("-----------------注册返回信息"
								+ getReceiveMsg);
						System.out.println("------------这是注册！！！！" + receiveMsg);
						if (getReceiveMsg.trim().equals("0")) {
							Message msg = new Message();
							msg.what = SUCCESS;
							// 注册成功后把手机号码保存到share preference里面
							SharedPreferences preferences = getSharedPreferences(
									"username", Activity.MODE_PRIVATE);
							Editor editor = preferences.edit();
							// 存入数据
							editor.putString("username", number);
							// 提交修改
							editor.commit();
							mHandler.sendMessage(msg);
						} else if (getReceiveMsg.trim().equals("1")) {
							Message msg = new Message();
							msg.what = FAIL;
							mHandler.sendMessage(msg);
						}

					} else {
						Message msg = new Message();
						msg.what = FAIL;
						mHandler.sendMessage(msg);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e(TAG, e.toString());
				}
				// 判断参数是否有误
			case R.id.register_choose_birthday:
				showDialog(DATE_DIALOG);
				break;
			case R.id.register_choose_business:
				if (findViewById(R.id.register_business1).getVisibility() == View.GONE) {
					choose_business.setBackground(getResources().getDrawable(
							R.drawable.umeng_update_btn_check_on_holo_light));
					findViewById(R.id.register_business1).setVisibility(
							View.VISIBLE);
					findViewById(R.id.register_business2).setVisibility(
							View.VISIBLE);
					findViewById(R.id.register_business3).setVisibility(
							View.VISIBLE);
				} else if (findViewById(R.id.register_business1)
						.getVisibility() == View.VISIBLE) {
					choose_business.setBackground(getResources().getDrawable(
							R.drawable.umeng_update_btn_check_off_holo_light));
					findViewById(R.id.register_business1).setVisibility(
							View.GONE);
					findViewById(R.id.register_business2).setVisibility(
							View.GONE);
					findViewById(R.id.register_business3).setVisibility(
							View.GONE);
				}
				break;
			case R.id.register_choose_ID_image:
				pickPhoto();
				break;
			}

		}
	};
	/**
	 * 使用handler进行异步处理
	 */
	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SUCCESS:
				Log.e(TAG, "注册成功");
				registerSuccess();
				break;
			case FAIL:
				Log.e(TAG, "注册失败");
				registerFail();
				break;
			case COUNTDOWN:
				verificationSend.setText(i + "s后可重新发送");
				break;
			case COUNTDOWN_END:
				verificationSend.setText("获取验证码");
				verificationSend.setClickable(true);
				break;
			case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:// 提交验证码成功
				if (msg.arg2 == SMSSDK.RESULT_COMPLETE) {
					verificationIsTure = true;
				} else if (msg.arg2 == SMSSDK.RESULT_ERROR) {
					failNotice(R.id.register_notice, "验证码错误");
				}
				Log.i(TAG, "验证码返回值： " + msg.arg2);
				break;
			case SMSSDK.EVENT_GET_VERIFICATION_CODE:// 已经发送验证码
				Toast.makeText(getApplicationContext(), "验证码已经发送",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	/**
	 * editText点击时隐藏Hint，失去焦点后恢复hint
	 */
	OnFocusChangeListener onFocusAutoClearHintListener = new OnFocusChangeListener() {

		public void onFocusChange(View v, boolean hasFocus) {
			EditText textView = (EditText) v;
			String hint;
			if (hasFocus) {
				hint = textView.getHint().toString();
				textView.setTag(hint);
				textView.setHint("");
			} else {
				hint = textView.getTag().toString();
				textView.setHint(hint);
			}
		}
	};

	/**
	 * 光标离开输入框监听
	 */
	OnFocusChangeListener onFocusChage = new OnFocusChangeListener() {

		public void onFocusChange(View view, boolean hasFocus) {
			// TODO Auto-generated method stub
			if (!hasFocus) {
				switch (view.getId()) {
				case R.id.register_phone:
					if (!isQq) {
						break;
					}
					phone = phoneView.getText().toString().trim();
					String text = ((TextView) findViewById(R.id.register_notice))
							.getText().toString();
					if (phone.length() != 11) {
						failNotice(R.id.register_notice, "手机号码格式错误，请输入正确的手机号码");
					} else if (text.equals("手机号码格式错误，请输入正确的手机号码")) {
						failNotice(R.id.register_notice, "");
					}
					break;
				case R.id.register_passwordTwo:
					password = passwordView.getText().toString().trim();
					passwordTwo = passwordTwoView.getText().toString().trim();
					String text1 = ((TextView) findViewById(R.id.register_notice))
							.getText().toString();
					if (!password.equals(passwordTwo)) {
						failNotice(R.id.register_notice, "两次密码不一致");
					} else if (text1.equals("两次密码不一致")) {
						failNotice(R.id.register_notice, "");
					}
					break;
				case R.id.register_verification_code:
					code = codeView.getText().toString().trim();
					phone = phoneView.getText().toString().trim();
					if (phone.length() == 11) {
						SMSSDK.submitVerificationCode("86", phone, code);
					}
					break;
				}
			}
		}
	};

	/**
	 * 自制弹出框
	 * 
	 * @param str
	 *            弹出的内容
	 */
	@SuppressWarnings("unused")
	private void showTip(String str) {
		mToast.setText(str);
		mToast.setDuration(Toast.LENGTH_LONG);
		mToast.show();
	}

	/**
	 * 注册成功后跳转
	 */
	protected void registerSuccess() {
		// TODO Auto-generated method stub
		Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
		// 验证通过之后，将smssdk注册代码注销
		SMSSDK.unregisterAllEventHandler();
		this.finish();
	}

	/**
	 * 注册失败后显示失败原因
	 */
	protected void registerFail() {
		TextView massageText = (TextView) findViewById(R.id.register_notice);
		String massage = "该手机号码已被注册";
		massageText.setVisibility(View.VISIBLE);
		massageText.setText(massage);
	}

	/**
	 * 注册信息错误提醒
	 * 
	 * @param r
	 *            显示的框的编号
	 * @param context
	 *            显示的信息
	 */
	protected void failNotice(int r, String context) {
		TextView massageText = (TextView) findViewById(r);
		massageText.setVisibility(View.VISIBLE);
		massageText.setText(context);
	}

	private void getIMEINum() {
		// TelephonyManager tm = (TelephonyManager) this
		// .getSystemService(TELEPHONY_SERVICE);
		// IMEI = tm.getDeviceId();
		IMEI = DeviceUtil.getDeviceId(RegisterActivity.this);
		if (IMEI != null) {
			if ("".equals(IMEI)) {
				IMEI = "358733050263717";
			}
		} else {
			IMEI = "358733050263717";
		}
	}

	/**
	 * 注册成功后跳转
	 * 
	 * @param message
	 * @param context
	 * @param cls
	 * @param name
	 */
	@SuppressWarnings("unused")
	private void startIntent(String message, Context context, Class<?> cls,
			String name) {
		Bundle bundle = new Bundle();
		bundle.putString(name, message);
		Intent intent = new Intent();
		intent.putExtras(bundle);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setClass(context, cls);
		startActivity(intent);
		finish();
	}

	/**
	 * 性别单选框的监听
	 */
	Spinner.OnItemSelectedListener sexListener = new Spinner.OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			/* 将所选mySpinner 的值带入myTextView 中 */
			// myTextView.setText("您选择的是："+ adapter.getItem(arg2));
			/* 将mySpinner 显示 */
			switch (arg2) {
			case 0:
				item = "1";// 男
				break;
			case 1:
				item = "0";// 女
				break;
			}
			arg0.setVisibility(View.VISIBLE);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			item = "1";
		}
	};

	/**
	 * 注册方式的选择单选框的监听
	 */
	Spinner.OnItemSelectedListener chooseWayListener = new Spinner.OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			/* 将所选mySpinner 的值带入myTextView 中 */
			// myTextView.setText("您选择的是："+ adapter.getItem(arg2));
			/* 将mySpinner 显示 */
			switch (arg2) {
			case 0:
				rowQq.setVisibility(View.GONE);
				rowPhone.setVisibility(View.VISIBLE);
				rowCode.setVisibility(View.VISIBLE);
				isQq = false;
				break;
			case 1:
				rowQq.setVisibility(View.VISIBLE);
				rowPhone.setVisibility(View.GONE);
				rowCode.setVisibility(View.GONE);
				isQq = true;
				break;
			}
			arg0.setVisibility(View.VISIBLE);
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	};

	/**
	 * 创建日期选择对话框
	 */
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case DATE_DIALOG:
			c = Calendar.getInstance();
			// Calendar.getInstance().setTime(date);
			dialog = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {
						public void onDateSet(DatePicker dp, int year,
								int month, int dayOfMonth) {
							chooseBirthdayBt.setText(year + "年" + (month + 1)
									+ "月" + dayOfMonth + "日");
							if (month + 1 < 10) {
								birthdayDay = "0" + (month + 1);
							} else {
								birthdayDay = "" + (month + 1);
							}
							birthday = year + "" + birthdayDay + ""
									+ dayOfMonth + "";
						}
					}, c.get(Calendar.YEAR), // 传入年份
					c.get(Calendar.MONTH), // 传入月份
					c.get(Calendar.DAY_OF_MONTH) // 传入天数
			);
			break;
		}
		return dialog;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			doPhoto(requestCode, data);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 选择图片后，获取图片的路径
	 * 
	 * @param requestCode
	 * @param data
	 */
	private void doPhoto(int requestCode, Intent data) {
		if (requestCode == SELECT_PIC_BY_PICK_PHOTO) // 从相册取图片，有些手机有异常情况，请注意
		{
			if (data == null) {
				Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
				return;
			}
			photoUri = data.getData();
			if (photoUri == null) {
				Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
				return;
			} else {
				Bitmap image;
				try {
					// 这个方法是根据Uri获取Bitmap图片的静态方法
					image = MediaStore.Images.Media.getBitmap(
							this.getContentResolver(), photoUri);
					if (image != null) {
						IDImage.setImageBitmap(image);
						IDImage.setVisibility(View.VISIBLE);
						System.out.println("shb=============	===");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		String[] pojo = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
		if (cursor != null) {
			int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
			cursor.moveToFirst();
			picPath = cursor.getString(columnIndex);
			cursor.close();
		}
		Log.i(TAG, "imagePath = " + picPath);
		// if (picPath != null
		// && (picPath.endsWith(".png") || picPath.endsWith(".PNG")
		// || picPath.endsWith(".jpg") || picPath.endsWith(".JPG"))) {
		// lastIntent.putExtra(KEY_PHOTO_PATH, picPath);
		// setResult(Activity.RESULT_OK, lastIntent);
		// finish();
		// } else {
		// Toast.makeText(this, "选择图片文件不正确", Toast.LENGTH_LONG).show();
		// }
	}

	/***
	 * 从相册中取图片
	 */
	private void pickPhoto() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
	}

	/***
	 * 使用相册中的图片
	 */
	public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
	/** 获取到的图片路径 */
	private String picPath;

	private Intent lastIntent;

	private Uri photoUri;
	/***
	 * 从Intent获取图片路径的KEY
	 */
	public static final String KEY_PHOTO_PATH = "photo_path";
}
