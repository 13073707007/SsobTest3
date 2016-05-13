package com.loongme.activity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.AlarmClock;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUnderstander;
import com.iflytek.cloud.SpeechUnderstanderListener;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.TextUnderstander;
import com.iflytek.cloud.TextUnderstanderListener;
import com.iflytek.cloud.UnderstanderResult;
import com.iflytek.sunflower.FlowerCollector;
import com.loongme.activity.R;
import com.loongme.adapter.chatAdapter;
import com.loongme.base.AppManager;
import com.loongme.business.Helpers;
import com.loongme.com.model.Bean;
import com.loongme.com.model.User;
import com.loongme.com.model.chatBean;
import com.loongme.fragment.Call;
import com.loongme.fragment.Health;
import com.loongme.fragment.Life;
import com.loongme.fragment.Recommend;
import com.loongme.fragment.Together;
import com.loongme.fragment.Youth;
import com.loongme.fragment.Call.CallListener;
import com.loongme.fragment.Health.HealthListener;
import com.loongme.fragment.Life.LifeListener;
import com.loongme.fragment.Recommend.IBtnCallListener;
import com.loongme.fragment.Together.TogetherListener;
import com.loongme.fragment.Youth.YouthListener;
import com.loongme.personage.MeGuset2Activity;
import com.loongme.personage.MeGusetActivity;
import com.loongme.personage.PersonageActivity;
import com.loongme.setting.UnderstanderSettings;
import com.loongme.util.DeviceUtil;
import com.loongme.util.JSONUtil;
import com.loongme.util.NetWork;
import com.loongme.util.Share;
import com.loongme.util.Spilt;
import com.loongme.util.SsbService;
import com.loongme.view.CustomDialog;
import com.loongme.view.ProgressDialog;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.update.UmengUpdateAgent;

public class UnderstanderDemo extends FragmentActivity implements
		OnClickListener, IBtnCallListener, HealthListener, CallListener,
		LifeListener, TogetherListener, YouthListener {
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	private ProgressDialog instance;
	private WebView webview;
	// 60个服务分类界面的显示状态
	boolean flag = true;
	// footerView的添加状态
	private boolean footerViewState = true;
	// 测试使用
	private String ceshi;
	private String ceshi1;
	SsbService iService = new SsbService();
	// 是否向服务器发送地理信息
	private boolean isLocationThreadRun = true;
	// 发送地理位置的线程
	private locationThread local = new locationThread();
	// 地理信息线程发送次数
	private int locationThreadRunount = 1;
	// 选择职业跳转的网址
	private final String occupationUrl = "http://ssbon.com/ssbonzmz/keywords/u.php?wxopenid=";
	// 判断用户是否需要提醒
	private boolean isNotice = true;
	// 弹出提示框
	private Builder noticeBuilder;
	// 版本号
	private int versionCode;
	// 提醒弹出框
	private AlertDialog noticeDialog;
	// 提示框标识
	private int noticeInt = 1;
	public static Bean bean;
	private String callName, callNumber, callMessage;
	private String finalWeicome;
	private int runLocalCount = 1;
	private String IMEI;
	public static boolean isSpeak = true;
	private boolean isContainsUrl;
	private boolean isDisCuss = false;
	private String discuss;// 留言信息
	private Double lon;// 地理位置信息 lon经度 lat纬度
	private Double lat;
	private String locationResult;// 返回的地理信息结果
	private boolean isLocation = true;// 是否调用发送地理信息位置
	private boolean isStartSpeak = true;
	private View vLocation;
	private PopupWindow popuWindow1;
	private View contentView1;
	private List<Object> listSpilt = new ArrayList<Object>();
	private boolean isTextSend;
	private boolean isContacts;
	private boolean isClock;
	private String content;
	private EditText etInput;
	LinearLayout lt_textWrite;
	LinearLayout lt_voice;
	private boolean isSend;
	// 缓冲进度
	private int mPercentForBuffering = 0;
	// 播放进度
	private int mPercentForPlaying = 0;
	// 默认发音人
	private String voicer = "xiaoyan";
	private String voicer1[] = { "vixy", "vixm", "vixl", "vixr", "vixyun",
			"xiaoyu" };
	private String[] voicers = new String[] { "普通话", "粤语", "台湾普通话", "四川话",
			"东北话", "男声" };
	// 引擎类型
	private String mEngineType = SpeechConstant.TYPE_CLOUD;
	// 语音合成对象
	public static SpeechSynthesizer mTts;
	/* 获取的屏幕宽度 */
	private int screenWidth;
	/* 获取的屏幕高度 */
	private int screenHeight;
	private View popupWindow_view;
	private View numberDialog;
	private PopupWindow popselector;
	// private ChatTask chattask;
	private final int TYPE_RECEIVE_TXT = 0;
	private final int TYPE_LIST = 2;
	private final int TYPE_SEND_TXT = 1;
	private chatBean Bean;
	List<chatBean> list = new ArrayList<chatBean>();
	private String receiveMsg;
	private String receiveSpiltOutUrl;
	private chatAdapter adapter;
	private static final int SUCCESS = 2;
	private static final int CALLORSENDFAIL = 6;
	private static final int SETCLOCK = 9;
	private static final int SEND = 4;
	private static final int FAIL = 3;
	private static final int WELCOME = 5;
	private static final int IS_NEW_MASSGAE = 10;// 收到新的服务消息
	private static final int AFTER_PIS = 12;// 调用锁定订单接口之后
	private String result = "";
	private String text;
	private String time;
	private String clockMeaasge;
	public static final String PREFS_NAME = "PreferencesFile";
	public static final int CONNENTED = 0;
	public static final int UPDATALOG = 1;
	public static final int FAILCONTACTS = 7;
	public static final int CLOCK = 11;
	public static final int SETTEXTPIC = 8;
	// 服务器ip地址
	// private String ip = "114.113.148.187";
	private String logMsg = "";
	// private iService.getSocket() iService.getSocket();
	private BufferedWriter writer;
	private InetSocketAddress isa = null;
	private String receiveMsgFromServ;
	// public static final int port = 7977;
	// private static final String TAGsocket = "SocketConnect";
	// private String num = "13518035521";
	// private String nba = "订酒店";
	String a;
	// 地理位置信息
	String msg;
	// 调取Pis_Get_App_Info接口的信息
	String msgPis;
	String msg2;
	// 调用Pis_Lock_User_Needs锁定订单接口
	String Tmsg;
	// 向服务器发送的欢迎信息
	private String weicomeMsg;
	// 服务器返回的欢迎界面信息
	private String getFromSerciceMsg;
	// 获取发送位置信息后服务器返回的消息
	private String getNoticeServiceMsg;
	// 获取发送位置信息后服务器返回的消息，并分离消息
	private String[] getNoticeServicemsg1 = { "1", "1", "1" };
	// 获取锁定订单后的信息
	private String getAfterPisMsg;
	// 获取锁定订单后处理过的信息
	private String getAfterPisMsg1;
	private ListView listView;
	private static String TAG = "UnderstanderDemo";
	// 语义理解对象（语音到语义）。
	private SpeechUnderstander mSpeechUnderstander;
	// 语义理解对象（文本到语义）。
	private TextUnderstander mTextUnderstander;
	private Toast mToast;
	// private EditText mUnderstanderText;

	private SharedPreferences mSharedPreferences;
	private SharedPreferences uSharedPreferences;
	private Button btn_setting;
	private LocationClient mLocationClient;
	private AlertDialog builder;
	private ConnectivityManager connectivityManager;
	private NetworkInfo info;
	private Button btn_send;
	private Button btn_more;
	private boolean isHaveAdr = false;
	private boolean isInUnderstander = true;// 判断是否在当前活动下
	List<Fragment> frags = new ArrayList<Fragment>();
	private ViewPager vPager;
	private LinearLayout ll_tj, ll_jk, ll_sm, ll_sh, ll_yq, ll_nq;
	private ImageView iv_tj, iv_jk, iv_sm, iv_sh, iv_yq, iv_nq;
	private TextView tv_tj, tv_jk, tv_sm, tv_sh, tv_yq, tv_nq;
	private Button btnMore, bt_set, btnMore2;
	private Button buttonSend;
	private LinearLayout ll_sige;
	private Button btn_zixun;
	private ImageView bt_horn;
	private ImageView bt_bi_horn;

	private float mCurrentCheckedRadioLeft;
	private HorizontalScrollView mHorizontalScrollView;
	private View llWebviewfooterView;
	List<String> list2 = new ArrayList<>();

	@SuppressLint("ShowToast")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		AppManager.getAppManager().addActivity(this);
		setContentView(R.layout.activity_main);
		llWebviewfooterView = View.inflate(getApplicationContext(),
				R.layout.webview, null);
		getIMEINum();
		initViewpager();

		SharedPreferences mPreferences = getSharedPreferences("AA",
				MODE_PRIVATE);
		voicer = mPreferences.getString("voicer", "");
		isSpeak = true;
		// 友盟更新代理
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);
		SpeechUtility.createUtility(UnderstanderDemo.this, "appid="
				+ getString(R.string.app_id));
		mTts = SpeechSynthesizer.createSynthesizer(this, mTtsInitListener);
		// 初始化对象
		mSpeechUnderstander = SpeechUnderstander.createUnderstander(this,
				speechUnderstanderListener);
		mTextUnderstander = TextUnderstander.createTextUnderstander(this,
				textUnderstanderListener);
		mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
		setImageLoad();
		registerNet();
		boolean isNetAvili = checkNet();

		if (isNetAvili) {
			String getBundle = getBundle();
			if (getBundle == null) {

				if (IMEI != null) {
					if (isLocation) {
						getLocation();
					}
					initLayout();
				} else {
					Log.e(TAG, "imei为空值!!");
					Toast.makeText(UnderstanderDemo.this, "获取不到设备imei码",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				System.out.println("从快捷搜索传来的值");
				initLayout();
				initView();
				mTts = SpeechSynthesizer.createSynthesizer(this,
						mTtsInitListener);
				// 初始化对象
				mSpeechUnderstander = SpeechUnderstander.createUnderstander(
						this, speechUnderstanderListener);
				mTextUnderstander = TextUnderstander.createTextUnderstander(
						this, textUnderstanderListener);
				mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
				setImageLoad();
			}
		} else {
			Toast.makeText(UnderstanderDemo.this, "请检查您的网络连接",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void initViewpager() {
		// TODO Auto-generated method stub
		mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView1);
		bt_set = (Button) findViewById(R.id.bt_set);
		ll_sige = (LinearLayout) findViewById(R.id.ll_sige);
		btnMore = (Button) findViewById(R.id.btn_more);
		btnMore2 = (Button) findViewById(R.id.btn_more2);
		buttonSend = (Button) findViewById(R.id.btn_send);
		btn_zixun = (Button) findViewById(R.id.btn_zixun);
		bt_set.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				User user = Helpers.getUserInfo(UnderstanderDemo.this);
				if (user.getUserType().equals("普通用户")
						|| user.getUserType().equals("普通")) {
					startActivity(new Intent(getApplicationContext(),
							PersonageActivity.class));
					overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				} else if (user.getUserType().equals("个人帮客")) {
					startActivity(new Intent(getApplicationContext(),
							MeGuset2Activity.class));
					overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				} else if (user.getUserType().equals("商户帮客")) {
					startActivity(new Intent(getApplicationContext(),
							MeGusetActivity.class));
					overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				} else {
					startActivity(new Intent(getApplicationContext(),
							PersonageActivity.class));
					overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				}
			}
		});
		btnMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				EditText edit = (EditText) findViewById(R.id.btn_Speak);
//				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//				imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
				if (flag) {
					ll_sige.setVisibility(View.VISIBLE);
					flag = false;
				} else {
					ll_sige.setVisibility(View.GONE);
					flag = true;
				}
			}
		});
		btnMore2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				EditText edit = (EditText) findViewById(R.id.et_textinput);
//				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//				imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
				if (flag) {
					ll_sige.setVisibility(View.VISIBLE);
					flag = false;
				} else {
					ll_sige.setVisibility(View.GONE);
					flag = true;
				}
			}
		});
		bt_horn = (ImageView) findViewById(R.id.bt_horn);
		bt_bi_horn = (ImageView) findViewById(R.id.bt_bi_horn);
		bt_horn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bt_bi_horn.setVisibility(View.VISIBLE);
				bt_horn.setVisibility(View.GONE);
				// mTts.stopSpeaking();
				if (mTts.isSpeaking()) {
					isSpeak = false;
					mTts.stopSpeaking();
				}
			}
		});
		bt_bi_horn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bt_horn.setVisibility(View.VISIBLE);
				bt_bi_horn.setVisibility(View.GONE);

				if (!mTts.isSpeaking()) {
					isSpeak = true;
					mTts.resumeSpeaking();
				}
			}
		});
		vPager = (ViewPager) findViewById(R.id.vpager);
		ll_tj = (LinearLayout) findViewById(R.id.ll_tj);
		ll_jk = (LinearLayout) findViewById(R.id.ll_jk);
		ll_sm = (LinearLayout) findViewById(R.id.ll_sm);
		ll_sh = (LinearLayout) findViewById(R.id.ll_sh);
		ll_yq = (LinearLayout) findViewById(R.id.ll_yq);
		ll_nq = (LinearLayout) findViewById(R.id.ll_nq);

		// 文字
		tv_tj = (TextView) findViewById(R.id.tv_tj);
		tv_jk = (TextView) findViewById(R.id.tv_jk);
		tv_sm = (TextView) findViewById(R.id.tv_sm);
		tv_sh = (TextView) findViewById(R.id.tv_sh);
		tv_yq = (TextView) findViewById(R.id.tv_yq);
		tv_nq = (TextView) findViewById(R.id.tv_nq);

		// 图片
		iv_tj = (ImageView) findViewById(R.id.iv_tj);
		iv_jk = (ImageView) findViewById(R.id.iv_jk);
		iv_sm = (ImageView) findViewById(R.id.iv_sm);
		iv_sh = (ImageView) findViewById(R.id.iv_sh);
		iv_yq = (ImageView) findViewById(R.id.iv_yq);
		iv_nq = (ImageView) findViewById(R.id.iv_nq);

		BottomLayoutListener listener = new BottomLayoutListener();
		ll_tj.setOnClickListener(listener);
		ll_jk.setOnClickListener(listener);
		ll_sm.setOnClickListener(listener);
		ll_sh.setOnClickListener(listener);
		ll_yq.setOnClickListener(listener);
		ll_nq.setOnClickListener(listener);

		frags.add(new Recommend());
		frags.add(new Health());
		frags.add(new Call());
		frags.add(new Life());
		frags.add(new Together());
		frags.add(new Youth());
		MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), frags);
		vPager.setAdapter(adapter);
		vPager.setCurrentItem(0);

		vPager.setOnPageChangeListener(new OnPageChangeListener() {
			AnimationSet mAnimationSet = new AnimationSet(true);
			TranslateAnimation mTranslateAnimation;

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				resetImageViewSrc();
				switch (position) {
				case 0:
					mHorizontalScrollView.smoothScrollTo(0, 0);
					tv_tj.setTextColor(Color.parseColor("#00ced1"));
					iv_tj.setImageResource(R.drawable.tuijain);
					break;
				case 1:
					mHorizontalScrollView.smoothScrollTo(100, 0);
					tv_jk.setTextColor(Color.parseColor("#00ced1"));
					iv_jk.setImageResource(R.drawable.jian);
					break;
				case 2:
					mHorizontalScrollView.smoothScrollTo(200, 100);
					tv_sm.setTextColor(Color.parseColor("#00ced1"));
					iv_sm.setImageResource(R.drawable.shang);
					break;
				case 3:
					mHorizontalScrollView.smoothScrollTo(300, 200);
					tv_sh.setTextColor(Color.parseColor("#00ced1"));
					iv_sh.setImageResource(R.drawable.sheng);
					break;
				case 4:
					mHorizontalScrollView.smoothScrollTo(400, 300);
					tv_yq.setTextColor(Color.parseColor("#00ced1"));
					iv_yq.setImageResource(R.drawable.yi);
					break;
				case 5:
					mHorizontalScrollView.smoothScrollTo(500, 400);
					tv_nq.setTextColor(Color.parseColor("#00ced1"));
					iv_nq.setImageResource(R.drawable.nian);
					break;

				default:
					break;
				}
				// currentIndex = position;
			}

			private void resetImageViewSrc() {
				// TODO Auto-generated method stub
				iv_tj.setImageResource(R.drawable.tuijianhui);
				iv_jk.setImageResource(R.drawable.jiankang);
				iv_sm.setImageResource(R.drawable.shangmen);
				iv_sh.setImageResource(R.drawable.shenghuo);
				iv_yq.setImageResource(R.drawable.yiqi);
				iv_nq.setImageResource(R.drawable.nianqing);
				tv_jk.setTextColor(Color.BLACK);
				tv_tj.setTextColor(Color.BLACK);
				tv_sm.setTextColor(Color.BLACK);
				tv_sh.setTextColor(Color.BLACK);
				tv_yq.setTextColor(Color.BLACK);
				tv_nq.setTextColor(Color.BLACK);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffPx) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	class BottomLayoutListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.ll_tj:
				mCurrentCheckedRadioLeft = getResources().getDimension(
						R.dimen.rdo1);
				mHorizontalScrollView.smoothScrollTo(0, 0);
				vPager.setCurrentItem(0, true);
				break;
			case R.id.ll_jk:
				mCurrentCheckedRadioLeft = getResources().getDimension(
						R.dimen.rdo2);
				mHorizontalScrollView.smoothScrollTo(100, 0);
				vPager.setCurrentItem(1, true);
				break;
			case R.id.ll_sm:
				mCurrentCheckedRadioLeft = getResources().getDimension(
						R.dimen.rdo3);
				mHorizontalScrollView.smoothScrollTo(200, 100);
				vPager.setCurrentItem(2, true);
				break;
			case R.id.ll_sh:
				mCurrentCheckedRadioLeft = getResources().getDimension(
						R.dimen.rdo4);
				mHorizontalScrollView.smoothScrollTo(300, 200);
				vPager.setCurrentItem(3, true);
				break;
			case R.id.ll_yq:
				mCurrentCheckedRadioLeft = getResources().getDimension(
						R.dimen.rdo5);
				mHorizontalScrollView.smoothScrollTo(400, 300);
				vPager.setCurrentItem(4, true);
				break;
			case R.id.ll_nq:
				mCurrentCheckedRadioLeft = getResources().getDimension(
						R.dimen.rdo6);
				mHorizontalScrollView.smoothScrollTo(500, 400);
				vPager.setCurrentItem(5, true);
				break;

			default:
				break;
			}
		}

	}

	class MyAdapter extends FragmentPagerAdapter {
		List<Fragment> list;

		public MyAdapter(FragmentManager fm, List list) {
			super(fm);
			// TODO Auto-generated constructor stub
			this.list = list;
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return list.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();

		}

	}

	/**
	 * 实现监听，注册广播
	 */
	private void registerNet() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);// 监听的消息名字,跟下面的广播名字一致
		registerReceiver(mReceiver, intentFilter);
	}

	/**
	 * 科大讯飞文字识别监听
	 */
	private TextUnderstanderListener textListener = new TextUnderstanderListener() {

		public void onResult(final UnderstanderResult result) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (null != result) {
						// 显示
						text = "";
						text = result.getResultString();
						if (!TextUtils.isEmpty(text)) {
							// mUnderstanderText.setText(text);
							Parse(text);
						}
					} else {
						Log.d(TAG, "understander result:null");
						showTip("识别结果不正确。");
					}
				}
			});
		}

		public void onError(SpeechError error) {
			showTip("onError Code：" + error.getErrorCode());
		}
	};

	/**
	 * 初始化监听器（文本到语义）。
	 */
	private InitListener textUnderstanderListener = new InitListener() {

		@Override
		public void onInit(int code) {
			Log.d(TAG, "textUnderstanderListener init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
				showTip("初始化失败,错误码：" + code);
			}
		}
	};

	/**
	 * 监听网络状态，若无网络，则提示
	 */
	BroadcastReceiver mReceiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
				Log.d("mark", "网络状态已经改变");
				connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				info = connectivityManager.getActiveNetworkInfo();
				if (info != null && info.isAvailable()) {
					String name = info.getTypeName();
					Log.d("mark", "当前网络名称：" + name);
				} else {
					Toast.makeText(UnderstanderDemo.this, "无网络连接，请检查网络设备",
							Toast.LENGTH_SHORT).show();
				}
			}
		}
	};
	private AlertDialog mDialog;
	private chatAdapter onlyRightAdapter;

	/**
	 * 检查网络连接
	 */
	private boolean checkNet() {
		NetWork net = new NetWork();
		boolean isNetWorkCon = net.isNetworkAvailable(UnderstanderDemo.this);
		return isNetWorkCon;
	}

	/**
	 * 获取手机唯一识别imei码
	 */
	private void getIMEINum() {
		TelephonyManager tm = (TelephonyManager) this
				.getSystemService(TELEPHONY_SERVICE);
		// IMEI = tm.getDeviceId();
		IMEI = DeviceUtil.getDeviceId(UnderstanderDemo.this);
		if (IMEI != null) {
			if ("".equals(IMEI)) {
				IMEI = "358733050263717";
			}
		} else {
			IMEI = "358733050263717";
		}
	}

	/**
	 * 获取地理位置
	 */
	private void getLocation() {
		if (isLocation) {
			mLocationClient = new LocationClient(this.getApplicationContext());
			mLocationClient.registerLocationListener(new MyLocationListener());
			InitLocation();
			mLocationClient.start();
		}
		// isLocation = false;
		init();
	}

	/**
	 * 初始化位置
	 */
	private void InitLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		// option.setCoorType(tempcoor);//返回的定位结果是百度经纬度，默认值gcj02
		int span = 15000;// 15秒定位一次
		option.setScanSpan(span);// 设置发起定位请求的间隔时间
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}

	/**
	 * 实现定位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			System.out.println("这是第" + runLocalCount + "次定位回调监听");
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				// 运营商信息
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
			}
			if (location.getAddrStr() != null && location.getLatitude() != 0.0
					&& location.getLongitude() != 0.0) {
				locationResult = location.getAddrStr();
				lat = location.getLatitude();
				lon = location.getLongitude();
				msg = "Ssbon_Location_WX  {COMM_INFO {BUSI_CODE 10011} {REGION_ID A} {COUNTY_ID A00} {OFFICE_ID 22342} {OPERATOR_ID 43643} {CHANNEL A2} {OP_MODE SUBMIT}} { {XW_OPENID "
						+ IMEI
						+ "} {LOC_X "
						+ lat
						+ "} {LOC_Y "
						+ lon
						+ "} {USER_ADDR "
						+ locationResult
						+ "} {VERSION "
						+ versionCode + "} }";
				System.out.println("地址是" + locationResult);
				System.out.println("经纬度是" + lat);
				System.out.println("经纬度是" + lon);
				if (runLocalCount == 1) {
					// 发送地理信息线程启动
					local.start();
					runLocalCount++;
					System.out.println("run Thread in MyLocationListener");
				} else {
					runLocalCount++;
				}
				if (!isHaveAdr) {
					initView();
					isHaveAdr = true;
				}
			} else {
				// Toast.makeText(UnderstanderDemo.this, "定位失败，请检查网络设置",
				// Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void setImageLoad() {
		File cacheDir = StorageUtils.getOwnCacheDirectory(
				getApplicationContext(), "imageloader/Cache");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				UnderstanderDemo.this)
				.threadPoolSize(3)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.threadPoolSize(3)
				// 线程池内加载的数量
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// 将保存的时候的URI名称用MD5 加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.memoryCache(new WeakMemoryCache())
				.denyCacheImageMultipleSizesInMemory()
				.memoryCacheSize(2 * 1024 * 1024)
				.memoryCacheExtraOptions(480, 800)
				.discCacheFileCount(100)
				.discCache(new UnlimitedDiscCache(cacheDir))
				// 自定义缓存路径
				.discCacheSize(50 * 1024 * 1024)
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.imageDownloader(
						new BaseImageDownloader(this, 5 * 1000, 30 * 1000))
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.writeDebugLogs().build();
		ImageLoader.getInstance().init(config);// 全局初始化此配置
	}

	/**
	 * 获取快捷搜索传过来的bundle,进行页面的跳转
	 */
	private String getBundle() {
		Intent intent = getIntent();
		if (intent != null) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				String message = bundle.getString("temp");
				if (message != null) {
					// result = message;
					/*
					 * Message msg1 = new Message(); msg1.what = SEND;
					 * mHandler.sendMessage(msg1);
					 */
					ret = mTextUnderstander.understandText(message,
							textListener);
					// result = message;
					return message;
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else {
			System.out.println("intent为空!!!");
			return null;
		}
	}

	@Override
	public void transfermsg(String str) {
		etInput.setText(str);
		etInput = (EditText) findViewById(R.id.et_textinput);
		content = etInput.getText().toString().trim();
		if (content.equals("") || content == null) {
			Toast.makeText(UnderstanderDemo.this, "请输入文字", Toast.LENGTH_SHORT)
					.show();

		} else {
			ret = mTextUnderstander.understandText(content, textListener);
		}
		etInput.setText("");
	}
//	创建存储send字段的集合
	List<String> receiveContentList = new ArrayList<>();
	@Override
//	添加send出去的字段至集合
	public void Putcontent(String str){
		receiveContentList.add(str);
	}
	@Override
	public void displayWebView(String str) {
		// TODO dispayWebView
//		创建list集合 用来newa dapter时传入参数
		List<chatBean> list = new ArrayList<chatBean>();
		if (receiveContentList!=null) {
			for (String rcs : receiveContentList) {
				chatBean sendBean = new chatBean();
				sendBean.setText(rcs);
				sendBean.setType(TYPE_SEND_TXT);
				list.add(sendBean);
			}
		}
		//创建本方法中使用的adapter
		onlyRightAdapter = new chatAdapter(this, list, result);
//		adapter.notifyDataSetChanged();
//		listView.setSelection(listView.getCount() - 1);
		

		llWebviewfooterView.measure(0, 0);

		// <<<<<<< .mine
		// WebView webview = (WebView)
		// llWebviewfooterView.findViewById(R.id.web);
		// =======
		//
		// >>>>>>> .r24

		// int height = llWebview.getMeasuredHeight();
		if (footerViewState) {
			// 如果第一次加载 则进来显示
			addWebview(str, webview, llWebviewfooterView);

			footerViewState = false;

			// Log.d("111", "11111111111111111");

		} else {

			// 之前有过webview的显示
			if (llWebviewfooterView != null) {

				listView.removeFooterView(llWebviewfooterView);
				// listView.invalidate();

				Log.d("222", "22222222222222222222222");

				webview.setVisibility(View.INVISIBLE);

				addWebview(str, webview, llWebviewfooterView);

				/*listView.setAdapter(adapter);

				if (null != adapter) {
					adapter.notifyDataSetChanged();
				}*/

			}

		}

	}

	private void addWebview(String str, final WebView webview, View llWebview) {

		listView.addFooterView(llWebview);

		// listView.addFooterView(webview);

		Log.d("000", "+++++++++++++++++++++++");
		// webview.requestFocusFromTouch();

		webview.setWebViewClient(new WebViewClient()

		{
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				// view.loadUrl(url);
				if (url.startsWith("tel:")) {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri
							.parse(url));
					startActivity(intent);
				} else if (url.startsWith("http:") || url.startsWith("https:")) {
					view.loadUrl(url);
				} else {
					view.loadUrl(url);
				}
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				showDialog();

				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				hideDialog();
				webview.setVisibility(View.VISIBLE);
				super.onPageFinished(view, url);
			}

			// <<<<<<< .mine
			// showDialog();
			// =======
			//
			// >>>>>>> .r24

			// @Override
			// public boolean shouldOverrideKeyEvent(WebView view, KeyEvent
			// event) {
			// // TODO Auto-generated method stub
			// if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
			// webview.goBack();
			// return true;
			// }
			// return super.shouldOverrideKeyEvent(view, event);
			// }

		});
		WebSettings webSetting = webview.getSettings();
		// webSetting.setJavaScriptEnabled(true);
		// 自适应图片大小
		// webSetting.setUseWideViewPort(false);
		// 是否支持利用缓存加载
		webSetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		// 是否支持缩放
		webSetting.setSupportZoom(true);
		webview.loadUrl(str);
		
		listView.setAdapter(onlyRightAdapter);
		// if()
		if (null != onlyRightAdapter) {
			onlyRightAdapter.notifyDataSetChanged();
		}
		listView.setSelection(listView.getCount() - 1);
	}

	private void hideDialog() {
		// TODO Auto-generated method stub
		instance.dismiss();
	}

	private void showDialog() {
		instance = ProgressDialog.getInstance(this);
		instance.show();

		/*
		 * mDialog = new AlertDialog.Builder(this).create();
		 * 
		 * Window dialogWindow = mDialog.getWindow();
		 * 
		 * WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		 * dialogWindow.setGravity(Gravity.LEFT|Gravity. TOP); lp.x = 450; //
		 * 新位置X坐标 lp.y = 450; // 新位置Y坐标 lp.width = 300; // 宽度 lp.height = 300;
		 * // 高度 lp.alpha = 0.7f; // 透明度
		 * 
		 * 
		 * 
		 * 将对话框的大小按屏幕大小的百分比设置
		 * 
		 * WindowManager m = getWindowManager(); Display d =
		 * m.getDefaultDisplay(); // 获取屏幕宽、高用 WindowManager.LayoutParams p =
		 * getWindow().getAttributes(); // 获取对话框当前的参数值 p.height = (int)
		 * (d.getHeight() * 0.6); // 高度设置为屏幕的0.6 p.width = (int) (d.getWidth() *
		 * 0.65); // 宽度设置为屏幕的0.65 dialogWindow.setAttributes(lp); //
		 * dialogWindow.setAttributes(p);
		 * 
		 * mDialog.show();
		 * 
		 * // 注意此处要放在show之后 否则会报异常
		 * mDialog.setContentView(R.layout.loading_process_dialog_anim);
		 */
	}

	@Override
	public void healthfermsg(String str) {
		etInput.setText(str);
		etInput = (EditText) findViewById(R.id.et_textinput);
		content = etInput.getText().toString().trim();
		if (content.equals("") || content == null) {
			Toast.makeText(UnderstanderDemo.this, "请输入文字", Toast.LENGTH_SHORT)
					.show();

		} else {
			ret = mTextUnderstander.understandText(content, textListener);
		}
		etInput.setText("");
	}

	@Override
	public void transfermsg3(String str) {
		etInput.setText(str);
		etInput = (EditText) findViewById(R.id.et_textinput);
		content = etInput.getText().toString().trim();
		if (content.equals("") || content == null) {
			Toast.makeText(UnderstanderDemo.this, "请输入文字", Toast.LENGTH_SHORT)
					.show();

		} else {
			ret = mTextUnderstander.understandText(content, textListener);
		}
		etInput.setText("");
	}

	@Override
	public void transfermsg4(String str) {
		etInput.setText(str);
		etInput = (EditText) findViewById(R.id.et_textinput);
		content = etInput.getText().toString().trim();
		if (content.equals("") || content == null) {
			Toast.makeText(UnderstanderDemo.this, "请输入文字", Toast.LENGTH_SHORT)
					.show();

		} else {
			ret = mTextUnderstander.understandText(content, textListener);
		}
		etInput.setText("");
	}

	@Override
	public void transfermsg5(String str) {
		etInput.setText(str);
		etInput = (EditText) findViewById(R.id.et_textinput);
		content = etInput.getText().toString().trim();
		if (content.equals("") || content == null) {
			Toast.makeText(UnderstanderDemo.this, "请输入文字", Toast.LENGTH_SHORT)
					.show();

		} else {
			ret = mTextUnderstander.understandText(content, textListener);
		}
		etInput.setText("");
	}

	@Override
	public void transfermsg6(String str) {
		etInput.setText(str);
		etInput = (EditText) findViewById(R.id.et_textinput);
		content = etInput.getText().toString().trim();
		if (content.equals("") || content == null) {
			Toast.makeText(UnderstanderDemo.this, "请输入文字", Toast.LENGTH_SHORT)
					.show();

		} else {
			ret = mTextUnderstander.understandText(content, textListener);
		}
		etInput.setText("");
	}

	private void initView() {
		welcomeThread tTask = new welcomeThread();
		tTask.start();
	}

	class welcomeThread extends Thread {

		public void run() {
			weicomeMsg = "Pis_Welcome_First  {COMM_INFO {BUSI_CODE 10011} {REGION_ID A} {COUNTY_ID A00} "
					+ "{OFFICE_ID 22342} {OPERATOR_ID 43643} {CHANNEL A2} {OP_MODE SUBMIT}} {   {XW_OPENID "
					+ IMEI + "} }";
			try {
				// 等待，接收来自服务器返回的消息
				// Thread.sleep(1000);
				iService.connecttoserver();
				System.out.println("run welcomeThread");
				if (iService.getSocket().isConnected()) {
					iService.SendMsg(iService.getSocket(), weicomeMsg);
				}
				getFromSerciceMsg = iService.ReceiveMsg(iService.getSocket());
				if (getFromSerciceMsg != null) {
					Spilt sp = new Spilt();
					if (getFromSerciceMsg.contains("ROBOT_OUTPUT")) {
						System.out.println("getFromSerciceMsg : "
								+ getFromSerciceMsg);
						getFromSerciceMsg = sp.spiltWelcomeString(
								getFromSerciceMsg, TAG);
						/*
						 * Spanned isWelcome = Html.fromHtml(spResult);
						 * finalWeicome = String.valueOf(isWelcome.toString());
						 */
						System.out.println("这是欢迎页的推荐！！！！" + getFromSerciceMsg);
						Message msgWelcome = new Message();
						msgWelcome.what = WELCOME;
						mHandler.sendMessage(msgWelcome);
					} else {

					}
				} else {
					Message msgWelcome = new Message();
					msgWelcome.what = FAIL;
					mHandler.sendMessage(msgWelcome);
				}
				// writer.close();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
	}

	/**
	 * 留言
	 */
	private void leaveMessage() {
		// list.clear();//不清空数据
		chatBean BeanMessage = new chatBean();
		BeanMessage.setText("您想跟我们说什么呢？");
		BeanMessage.setType(0);
		list.add(BeanMessage);
		adapter = new chatAdapter(this, list, result);
		listView.setAdapter(adapter);
		listView.setSelection(adapter.getCount() - 1);
		// DoDiscuss();
		isDisCuss = true;
		initLayout();
	}

	/**
	 * 初始化Layout。
	 */
	private void initLayout() {
		if (isDisCuss) {
			webview = (WebView) llWebviewfooterView.findViewById(R.id.web);
			findViewById(R.id.btn_send).setOnClickListener(textInput);
			findViewById(R.id.et_textinput).setOnClickListener(textInput);
			findViewById(R.id.et_textinput).setOnFocusChangeListener(
					onFocusAutoClearHintListener);// editview点击时隐藏Hint，失去焦点后恢复hint
			findViewById(R.id.btn_littlevoice).setOnClickListener(textInput);
			lt_textWrite = (LinearLayout) findViewById(R.id.lt_textWrite);
			lt_voice = (LinearLayout) findViewById(R.id.lt_voice);
			findViewById(R.id.btn_textinput).setOnClickListener(textInput);
			// findViewById(R.id.btn_quick).setOnClickListener(onQuickClick);
			btn_setting = (Button) findViewById(R.id.btn_setting);
			btn_setting.setOnClickListener(onclick);
//			findViewById(R.id.btn_Speak).setOnClickListener(this);
			Button btn_speak=(Button) findViewById(R.id.btn_Speak);
			
		} else {
			webview = (WebView) llWebviewfooterView.findViewById(R.id.web);
			findViewById(R.id.btn_send).setOnClickListener(textInput);
			findViewById(R.id.et_textinput).setOnClickListener(textInput);
			findViewById(R.id.et_textinput).setOnFocusChangeListener(
					onFocusAutoClearHintListener);// editview点击时隐藏Hint，失去焦点后恢复hint
			findViewById(R.id.btn_littlevoice).setOnClickListener(textInput);
			lt_textWrite = (LinearLayout) findViewById(R.id.lt_textWrite);
			lt_voice = (LinearLayout) findViewById(R.id.lt_voice);
			findViewById(R.id.btn_textinput).setOnClickListener(textInput);
			// findViewById(R.id.btn_quick).setOnClickListener(onQuickClick);
			btn_setting = (Button) findViewById(R.id.btn_setting);
			btn_setting.setOnClickListener(onclick);
			listView = (ListView) findViewById(R.id.listview);
			btn_speak = (Button) findViewById(R.id.btn_Speak);
			btn_speak.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					if (event.getAction()==MotionEvent.ACTION_DOWN) {
						btn_speak.setText("松开 发送");
						System.out.println("-----长点击button-----");
						
						if (mTts.isSpeaking()) {
							isSpeak = false;
							mTts.stopSpeaking();
							mTts.resumeSpeaking();
						}
							setParam();
							if (mSpeechUnderstander.isUnderstanding()) {// 开始前检查状态
								mSpeechUnderstander.stopUnderstanding();
								showTip("停止录音");
							} else {
								ret = mSpeechUnderstander
										.startUnderstanding(mRecognizerListener);
								if (ret != 0) {
									showTip("语义理解失败,错误码:" + ret);
								} else {
									showTip(getString(R.string.text_begin));
								}
						}
					}else if (event.getAction()==MotionEvent.ACTION_UP) {
						btn_speak.setText("按住 说话");
						System.out.println("-----放开button-----");
						setParam();
//						mTts.resumeSpeaking();
						if (!mTts.isSpeaking()) {
							isSpeak = true;
							mTts.resumeSpeaking();
						}
					}
					return true;
				}
			});

			// mUnderstanderText = (EditText)
			// findViewById(R.id.understander_text);

			// findViewById(R.id.understander_stop).setOnClickListener(this);
			// findViewById(R.id.understander_cancel).setOnClickListener(this);

			mSharedPreferences = getSharedPreferences(
					UnderstanderSettings.PREFER_NAME, Activity.MODE_PRIVATE);
			uSharedPreferences = getSharedPreferences("username",
					MODE_WORLD_READABLE);
		}
		etInput = (EditText) findViewById(R.id.et_textinput);
		/*
		 * etInput.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub
		 * 
		 * } });
		 */
		etInput.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!TextUtils.isEmpty(s)) {
					btnMore2.setVisibility(View.GONE);
					buttonSend.setVisibility(View.VISIBLE);
				} else {
					btnMore2.setVisibility(View.VISIBLE);
					buttonSend.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	/**
	 * editText点击时隐藏Hint，失去焦点后恢复hint
	 */
	OnFocusChangeListener onFocusAutoClearHintListener = new OnFocusChangeListener() {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			EditText textView = (EditText) v;
			String hint;
			if (hasFocus) {
				hint = textView.getHint().toString();
				textView.setTag(hint);
				textView.setHint("");
				// 隐藏分类服务

				ll_sige.setVisibility(View.GONE);
				flag = true;
			} else {
				hint = textView.getTag().toString();
				textView.setHint(hint);
			}
		}
	};

	android.view.View.OnClickListener textInput = new OnClickListener() {

		@Override
		/**
		 * 设置界面底部按钮功能，包括切换语音与文字输入，发送文字内容
		 */
		public void onClick(View v) {
			switch (v.getId()) {
			// 输入想说的话的按钮
			case R.id.btn_littlevoice:
				lt_textWrite.setVisibility(View.GONE);
				lt_voice.setVisibility(View.VISIBLE);
				break;
			// 切换到文字输入的按钮
			case R.id.btn_textinput:
				lt_textWrite.setVisibility(View.VISIBLE);
				lt_voice.setVisibility(View.GONE);
				break;

			/*
			 * // 点击文本输入框 case R.id.et_textinput:
			 * ll_sige.setVisibility(View.GONE); flag = true; break; // 点击speak框
			 * case R.id.btn_Speak: ll_sige.setVisibility(View.GONE); flag =
			 * true; break;
			 */

			case R.id.btn_send:
				etInput = (EditText) findViewById(R.id.et_textinput);
				content = etInput.getText().toString().trim();
				if (content.equals("") || content == null) {
					Toast.makeText(UnderstanderDemo.this, "请输入文字",
							Toast.LENGTH_SHORT).show();

				} else {
					ret = mTextUnderstander.understandText(content,
							textListener);
					if (llWebviewfooterView!=null) {
						listView.removeFooterView(llWebviewfooterView);
					}
					Putcontent(content);
					//我们
					Log.d("666", ""+list);
					if (adapter!=null) {
						adapter.notifyDataSetChanged();
						listView.setSelection(listView.getCount() - 1);
						listView.setAdapter(adapter);
					}
					

				}
				etInput.setText("");
				// 隐藏掉软键盘
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(etInput.getWindowToken(), 0);
				break;
			}

		}
	};

	//
	public void test() {
		ret = mTextUnderstander.understandText(content, textListener);
	}

	OnClickListener onclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.out.println("SHHSSHSHHS");
			vLocation = v;
			initPopWindows(v);
			ltPopClickListen();
		}
	};
	/**
	 * 点击按钮后跳转到QuickSeach
	 */
	OnClickListener onQuickClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent();
			intent.setClass(UnderstanderDemo.this, QuickSearch.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	};

	/**
	 * 初始化右上角菜单的布局
	 */
	private void ltPopClickListen() {
		popupWindow_view.findViewById(R.id.lt_leaveMessage).setOnClickListener(
				popClick);
		popupWindow_view.findViewById(R.id.lt_aboutUs).setOnClickListener(
				popClick);
		popupWindow_view.findViewById(R.id.lt_exit)
				.setOnClickListener(popClick);
		popupWindow_view.findViewById(R.id.lt_share).setOnClickListener(
				popClick);
		popupWindow_view.findViewById(R.id.choose_voicer).setOnClickListener(
				popClick);
		popupWindow_view.findViewById(R.id.choose_occupation)
				.setOnClickListener(popClick);
		popupWindow_view.findViewById(R.id.lt_register).setOnClickListener(
				popClick);
		popupWindow_view.findViewById(R.id.lt_logout).setOnClickListener(
				popClick);
		popupWindow_view.findViewById(R.id.lt_pay).setOnClickListener(popClick);
		popupWindow_view.findViewById(R.id.lt_buy).setOnClickListener(popClick);
		popupWindow_view.findViewById(R.id.lt_OrderManager).setOnClickListener(
				popClick);
		popupWindow_view.findViewById(R.id.lt_buy_keyword).setOnClickListener(
				popClick);
	}

	OnClickListener popClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.lt_leaveMessage:
				leaveMessage();
				popselector.dismiss();
				break;
			case R.id.lt_exit:
				// UnderstanderDemo.this.finish();//只能退出当前的activity
				new AlertDialog.Builder(UnderstanderDemo.this)
						.setTitle("主人，确认要退出吗")
						// .setMessage("确定吗？")
						.setPositiveButton("是",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface arg0,
											int arg1) {
										// exitActivity();
										System.exit(0);
									}
								}).setNegativeButton("否", null).show();
				popselector.dismiss();
				// System.exit(0);
				break;
			case R.id.lt_share:
				popselector.dismiss();
				Share share = new Share();
				share.shareMsg("事事帮", "事事帮", "我正在使用事事帮语音助手给你发来消息，欢迎百度搜索下载！",
						null, UnderstanderDemo.this);
				break;
			case R.id.choose_occupation:
				popselector.dismiss();
				Intent intent = new Intent();
				intent.setClass(UnderstanderDemo.this, ShowInfo.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("uri", occupationUrl + IMEI);
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			case R.id.lt_register:
				popselector.dismiss();
				Intent register = new Intent();
				register.setClass(UnderstanderDemo.this, RegisterActivity.class);
				// Bundle bundle1 = new Bundle();
				// bundle1.putSerializable("uri", occupationUrl + "//" +
				// IMEI);
				// register.putExtras(bundle1);
				startActivity(register);
				// showTip("跳转到注册页面");
				break;
			case R.id.lt_logout:
				// 退出把share preference里面的用户名设置为空
				SharedPreferences preferences2 = getSharedPreferences(
						"username", Activity.MODE_PRIVATE);
				Editor editor = preferences2.edit();
				// 存入数据
				editor.putString("username", "");
				// 提交修改
				editor.commit();
				popselector.dismiss();

				// 把退出换成登陆
				popupWindow_view.findViewById(R.id.lt_logout).setVisibility(
						View.GONE);
				popupWindow_view.findViewById(R.id.lt_register).setVisibility(
						View.VISIBLE);
				break;
			case R.id.choose_voicer:
				popselector.dismiss();
				new AlertDialog.Builder(UnderstanderDemo.this)
						.setTitle("请选择发音类型")
						.setItems(voicers,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										switch (which) {
										case 0:
											voicer = voicer1[0];
											seTTtParam();
											break;
										case 1:
											voicer = voicer1[1];
											seTTtParam();
											break;
										case 2:
											voicer = voicer1[2];
											seTTtParam();
											break;
										case 3:
											voicer = voicer1[3];
											seTTtParam();
											break;
										case 4:
											voicer = voicer1[4];
											seTTtParam();
											break;
										case 5:
											voicer = voicer1[5];
											seTTtParam();
											break;
										}
										dialog.dismiss();
									}
								}).show();
				break;
			case R.id.lt_pay:
				popselector.dismiss();
				Intent payIntent = new Intent();
				payIntent.setClass(UnderstanderDemo.this,
						PayAlipayActivity.class);
				startActivity(payIntent);
				break;
			case R.id.lt_buy:
				popselector.dismiss();
				Intent buyIntent = new Intent();
				buyIntent.setClass(UnderstanderDemo.this, BuyActivity.class);
				startActivity(buyIntent);
				break;
			case R.id.lt_OrderManager:
				popselector.dismiss();
				// ((TextView)UnderstanderDemo.findViewById(R.id.title)).setText("订单管理");
				Intent orderIntent = new Intent();
				orderIntent.setClass(UnderstanderDemo.this,
						OrderFragmentActivity.class);
				// orderIntent.setClass(UnderstanderDemo.this,
				// OrderManagerActivity.class);
				startActivity(orderIntent);
				break;
			case R.id.lt_buy_keyword:
				popselector.dismiss();
				Intent buyKeywordIntent = new Intent();
				buyKeywordIntent.setClass(UnderstanderDemo.this,
						BuyKeywordActivity.class);
				startActivity(buyKeywordIntent);
				break;
			}
		}
	};

	private void initPopShareClick() {
		contentView1.findViewById(R.id.img_share_close).setOnClickListener(
				onSharePopClickListener);
		contentView1.findViewById(R.id.lt_Share_sinao).setOnClickListener(
				onSharePopClickListener);
		contentView1.findViewById(R.id.lt_Share_tengxun).setOnClickListener(
				onSharePopClickListener);
		contentView1.findViewById(R.id.lt_Share_weixin).setOnClickListener(
				onSharePopClickListener);
		contentView1.findViewById(R.id.lt_Share_friends).setOnClickListener(
				onSharePopClickListener);
	}

	/**
	 * 分享按钮的监听
	 */
	OnClickListener onSharePopClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.img_share_close:
				popuWindow1.dismiss();
				break;
			case R.id.lt_Share_sinao:
				Toast.makeText(UnderstanderDemo.this, "此功能还在完善中",
						Toast.LENGTH_SHORT).show();
				break;
			case R.id.lt_Share_tengxun:
				Toast.makeText(UnderstanderDemo.this, "此功能还在完善中",
						Toast.LENGTH_SHORT).show();
				break;
			case R.id.lt_Share_weixin:
				Toast.makeText(UnderstanderDemo.this, "此功能还在完善中",
						Toast.LENGTH_SHORT).show();
				break;
			case R.id.lt_Share_friends:
				Toast.makeText(UnderstanderDemo.this, "此功能还在完善中",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	/**
	 * 分享菜单的特效设置
	 * 
	 * @param parent
	 */
	private void initPopuWindow1(View parent) {
		if (popuWindow1 == null) {
			LayoutInflater mLayoutInflater = LayoutInflater.from(this);
			contentView1 = mLayoutInflater.inflate(
					R.layout.activity_share_popwindows, null);
			popuWindow1 = new PopupWindow(contentView1,
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		ColorDrawable cd = new ColorDrawable(0x000000);
		popuWindow1.setBackgroundDrawable(cd);
		// 产生背景变暗效果
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.4f;
		getWindow().setAttributes(lp);

		popuWindow1.setOutsideTouchable(true);
		popuWindow1.setFocusable(true);
		popuWindow1.showAtLocation((View) parent.getParent(), Gravity.CENTER
				| Gravity.CENTER_HORIZONTAL, 0, 0);

		popuWindow1.update();
		popuWindow1.setOnDismissListener(new OnDismissListener() {

			// 在dismiss中恢复透明度
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
			}
		});
	}

	/**
	 * 初始化右上角弹出菜单
	 * 
	 * @param parent
	 */
	private void initPopWindows(View parent) {
		if (popselector == null) {
			int temp;
			temp = this.getWindowManager().getDefaultDisplay().getWidth();
			screenWidth = temp / 2;
			screenHeight = this.getWindowManager().getDefaultDisplay()
					.getHeight() / 3;
			LayoutInflater mLayoutInflater = LayoutInflater.from(this);
			popupWindow_view = mLayoutInflater.inflate(
					R.layout.activity_popwindows, null);
			popselector = new PopupWindow(popupWindow_view, screenWidth,
					LayoutParams.WRAP_CONTENT, true);
		}
		ColorDrawable cd = new ColorDrawable(0x000000);
		popselector.setBackgroundDrawable(cd);
		// 产生背景变暗效果
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.1f;
		getWindow().setAttributes(lp);
		popselector.setOutsideTouchable(true);
		popselector.setFocusable(true);
		popselector.showAsDropDown(btn_setting);
		popselector.update();
		popselector.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
			}
		});
		// 注册登录与退出按钮的显示与隐藏
		if (isLogin()) {
			popupWindow_view.findViewById(R.id.lt_register).setVisibility(
					View.GONE);
			popupWindow_view.findViewById(R.id.lt_logout).setVisibility(
					View.VISIBLE);
		} else {
			popupWindow_view.findViewById(R.id.lt_logout).setVisibility(
					View.GONE);
			popupWindow_view.findViewById(R.id.lt_register).setVisibility(
					View.VISIBLE);
		}
	}

	/**
	 * 初始化监听器（语音到语义）。
	 */
	private InitListener speechUnderstanderListener = new InitListener() {
		@Override
		public void onInit(int code) {
			Log.d(TAG, "speechUnderstanderListener init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
				showTip("初始化失败,错误码：" + code);
			}
		}
	};

	/**
	 * 初始化监听器（文本到语义）。
	 */
	/*
	 * private InitListener textUnderstanderListener = new InitListener() {
	 * 
	 * @Override public void onInit(int code) { Log.d(TAG,
	 * "textUnderstanderListener init() code = " + code); if (code !=
	 * ErrorCode.SUCCESS) { showTip("初始化失败,错误码：" + code); } } };
	 */

	int ret = 0;// 函数调用返回值
	OnClickListener onclickFor = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			System.out.println("发送吧");
		}
	};
 
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		// 开始语音理解
		case R.id.btn_Speak:
			findViewById(R.id.btn_Speak);
			CustomDialog.Builder builder = new CustomDialog.Builder(this);
			// builder.create().findViewById(R.id.positiveButton).setOnClickListener(onclickFor);
			builder.setPositiveButton("发送",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							System.out.println("点击事件,发送了");
							isSend = true;
						}
					});

			builder.setNegativeButton("取消",
					new android.content.DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							System.out.println("点击事件,取消了");
							isSend = false;
						}
					});
			// builder.create().show();
			setParam();
			if (mSpeechUnderstander.isUnderstanding()) {// 开始前检查状态
				mSpeechUnderstander.stopUnderstanding();
				showTip("停止录音");
			} else {
				ret = mSpeechUnderstander
						.startUnderstanding(mRecognizerListener);
				if (ret != 0) {
					showTip("语义理解失败,错误码:" + ret);
				} else {
					showTip(getString(R.string.text_begin));
				}
			}

			break;
		/*
		 * // 停止语音理解 case R.id.understander_stop:
		 * mSpeechUnderstander.stopUnderstanding(); showTip("停止语义理解"); break; //
		 * 取消语音理解 case R.id.understander_cancel: mSpeechUnderstander.cancel();
		 * showTip("取消语义理解"); break;
		 */
		default:
			break;
		}
	}

	/**
	 * 解析json字符串
	 * 
	 * @param Jsonmsg
	 * @author xiexs
	 */
	private void Parse(String Jsonmsg) {
		bean = new JSONUtil<String, Bean>()
				.JsonStrToObject(Jsonmsg, Bean.class);
		Log.i(TAG, "正在解析json字符串");
		if (bean != null) {
			String Beanname = null;
			String Beanservice = null;
			if (bean.service != null) {
				Beanservice = bean.service;
				if (Beanservice.equals("schedule")) {
					isClock = true;
					if (bean.semantic.slots != null) {
						if (bean.semantic.slots.datetime != null) {
							if (bean.semantic.slots.datetime.time != null) {
								time = bean.semantic.slots.datetime.time;
								clockMeaasge = bean.semantic.slots.content;
								Message msgClock = new Message();
								msgClock.what = SETCLOCK;
								mHandler.handleMessage(msgClock);
								Log.e(TAG, "设置闹钟");
							}
						} else {
							Message msgText = new Message();
							msgText.what = SUCCESS;
							mHandler.sendMessage(msgText);
							Log.e(TAG, "this is bean :" + bean.text);
							result = bean.text;
						}
					}
				} else if (Beanservice.equals("telephone")) {
					if (bean.semantic.slots != null) {
						isContacts = true;
						callName = bean.semantic.slots.name;
						callNumber = bean.semantic.slots.code;
						if (callNumber != null) {
							if ("".equals(callNumber)) {
								callNumber = getContactPhoneNumberByName(this,
										callName);
								if (callNumber != null
										|| !callNumber.equals("")) {
									// 用intent启动拨打电话
									Intent intent = new Intent(
											Intent.ACTION_DIAL,
											Uri.parse("tel:" + callNumber));
									startActivity(intent);
								}
							} else {
								// 用intent启动拨打电话
								Intent intent = new Intent(Intent.ACTION_DIAL,
										Uri.parse("tel:" + callNumber));

								startActivity(intent);
							}
						} else {
							callNumber = getContactPhoneNumberByName(this,
									callName);
							if (callNumber == null) {
								Message msgCall = new Message();
								msgCall.what = CALLORSENDFAIL;
								mHandler.sendMessage(msgCall);
							} else {
								Intent intent = new Intent(Intent.ACTION_DIAL,
										Uri.parse("tel:" + callNumber));
								startActivity(intent);
							}

						}

					} else {
						Message msg = new Message();
						msg.what = CALLORSENDFAIL;
						mHandler.sendMessage(msg);
					}
				} else if (Beanservice.equals("message")) {
					if (bean.semantic.slots != null) {
						isContacts = true;
						Uri uri;
						callName = bean.semantic.slots.name;
						callNumber = bean.semantic.slots.code;
						callMessage = bean.semantic.slots.content;
						if (callName == null) {
							uri = Uri.parse("smsto:" + callNumber);
						} else {
							uri = Uri.parse("smsto:" + callName);
							callNumber = getContactPhoneNumberByName(this,
									callName);
							// 调动Intent
							if (callNumber == null || callNumber.equals("")) {
							} else {
								Intent intent = new Intent(
										Intent.ACTION_SENDTO, uri);
								intent.putExtra("sms_body", callMessage);
								startActivity(intent);
							}

						}
					}
				}
				Message msgText = new Message();
				msgText.what = SUCCESS;
				mHandler.sendMessage(msgText);
				Log.e(TAG, "this is bean :" + bean.text);
				result = bean.text;
			} else {
				Message msgText = new Message();
				msgText.what = SUCCESS;
				mHandler.sendMessage(msgText);
				Log.e(TAG, "this is bean :" + bean.text);
				result = bean.text;
			}

			// init();
		} else {
			Message msg = new Message();
			msg.what = FAIL;
			mHandler.sendMessage(msg);
		}
	}

	/**
	 * 识别回调。
	 */
	private SpeechUnderstanderListener mRecognizerListener = new SpeechUnderstanderListener() {

		@Override
		public void onResult(final UnderstanderResult result) {
			Log.e(TAG, "talking onResult");
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (null != result) {
						// 显示
						text = "";
						text = result.getResultString();
						if (!TextUtils.isEmpty(text)) {
							// mUnderstanderText.setText(text);
							Parse(text);
						}
					} else {
						// showTip("识别结果不正确。");
						mToast.setText("识别结果不正确。");
						mToast.show();
					}
				}
			});
		}

		@Override
		public void onVolumeChanged(int v) {
			// showTip("当前音量为 ：" + v + "分贝");
			mToast.setText("正在录音，当前音量为 ：" + v + "分贝");
			mToast.show();
		}

		@Override
		public void onEndOfSpeech() {
			// showTip("结束");
			mToast.setText("结束");
			mToast.show();
			findViewById(R.id.btn_Speak);
		}

		@Override
		public void onBeginOfSpeech() {
			showTip("请开始说话");
			mToast.setText("开始说话");
			mToast.show();
		}

		@Override
		public void onError(SpeechError error) {
			// showTip("onError Code：" + error.getErrorCode());
			showTip("请检查您的网络连接");
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
		}
	};

	/**
	 * 初始化参数，跑线程连服务端
	 */
	private void init() {
		if (isLocation) {
			isLocation = false;
			// System.out.println("地址是init" + locationResult);
			a = converMsgToGBK(result);
			System.out.println(a);
		} else {
			a = converMsgToGBK(result);
			msgPis = "Pis_Get_App_Info {COMM_INFO {BUSI_CODE 10011} {REGION_ID A} {COUNTY_ID A00} {OFFICE_ID robot} {OPERATOR_ID 44444} {CHANNEL A2} {OP_MODE SUBMIT}} { {ROLE {1}} {WORDS {"
					+ a
					+ "}} {ROBOT_TYPE {0}} {XW_OPENID "
					+ IMEI
					+ "} {SELFID " + IMEI + "} }";
			Log.i(TAG, msgPis);
			tcpClient tcp = new tcpClient(msgPis);
			tcp.start();
		}

	}

	/**
	 * 留言跑的
	 */
	private void initLeaveMessage() {
		discuss = "Pis_Robot_Feedback {COMM_INFO {BUSI_CODE 10011} {REGION_ID A} {COUNTY_ID A00} {OFFICE_ID 22342} {OPERATOR_ID 43643} {CHANNEL A2} {OP_MODE SUBMIT}} {   {XW_OPENID "
				+ IMEI + "}  {SUGGEST " + result + "}}";
		leaveMessageThread message = new leaveMessageThread();
		message.start();
		setReceiveView();
		isDisCuss = false;
	}

	/**
	 * 初期化监听。
	 */
	private InitListener mTtsInitListener = new InitListener() {
		@Override
		public void onInit(int code) {
			Log.d(TAG, "InitListener init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
				showTip("初始化失败,错误码：" + code);
			}
		}
	};

	/**
	 * 设置科大讯飞语音参数
	 */
	private void seTTtParam() {
		// 清空参数
		mTts.setParameter(SpeechConstant.PARAMS, null);
		// 设置合成
		if (mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
			mTts.setParameter(SpeechConstant.ENGINE_TYPE,
					SpeechConstant.TYPE_CLOUD);
			// 设置发音人
			mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);

			// 设置语速
			mTts.setParameter(SpeechConstant.SPEED,
					mSharedPreferences.getString("speed_preference", "50"));

			// 设置音调
			mTts.setParameter(SpeechConstant.PITCH,
					mSharedPreferences.getString("pitch_preference", "50"));

			// 设置音量
			mTts.setParameter(SpeechConstant.VOLUME,
					mSharedPreferences.getString("volume_preference", "50"));

			// 设置400电话读出来的是电话，而不是数字
			mTts.setParameter(SpeechConstant.PARAMS, "rdn=2");

			// 设置播放器音频流类型
			mTts.setParameter(SpeechConstant.STREAM_TYPE,
					mSharedPreferences.getString("stream_preference", "3"));
		} else {
			mTts.setParameter(SpeechConstant.ENGINE_TYPE,
					SpeechConstant.TYPE_LOCAL);
			// 设置发音人 voicer为空默认通过语音+界面指定发音人。
			mTts.setParameter(SpeechConstant.VOICE_NAME, "");
		}
	}

	/**
	 * 合成回调监听。
	 */
	private SynthesizerListener mTtsListener = new SynthesizerListener() {
		@Override
		public void onSpeakBegin() {
			showTip("开始播放");
		}

		@Override
		public void onSpeakPaused() {
			showTip("暂停播放");
		}

		@Override
		public void onSpeakResumed() {
			showTip("继续播放");
		}

		@Override
		public void onBufferProgress(int percent, int beginPos, int endPos,
				String info) {
			mPercentForBuffering = percent;
			showTip(String.format(getString(R.string.tts_toast_format),
					mPercentForBuffering, mPercentForPlaying));
		}

		@Override
		public void onSpeakProgress(int percent, int beginPos, int endPos) {
			mPercentForPlaying = percent;
			showTip(String.format(getString(R.string.tts_toast_format),
					mPercentForBuffering, mPercentForPlaying));
		}

		@Override
		public void onCompleted(SpeechError error) {
			if (error == null) {
				showTip("播放完成");
				if (noticeDialog != null) {
					noticeDismiss nRun = new noticeDismiss();
					nRun.start();
				}
			} else if (error != null) {
				showTip(error.getPlainDescription(true));
			}
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {

		}
	};

	/**
	 * 留言信息跑的线程
	 */
	class leaveMessageThread extends Thread {

		@Override
		public void run() {
			try {
				// 等待，接收来自服务器返回的消息
				iService.connecttoserver();

				System.out.println("run thread");
				if (iService.getSocket().isConnected()) {
					// click();
					iService.SendMsg(iService.getSocket(), discuss);

				}
			} catch (Exception e) {

			}
		}

	}

	/**
	 * 设置闹钟跑的线程
	 */
	class SetclockThread extends Thread {
		public void run() {
			StartSetColck();
		}
	}

	/**
	 * 开始设置闹钟
	 */
	private void StartSetColck() {
		String[] timeTest = SpiltTime(time);
		String Hour = timeTest[0];
		String Minute = timeTest[1];
		int hour = 0;
		int minute = 0;
		try {
			hour = Integer.parseInt(Hour);
			minute = Integer.parseInt(Minute);
		} catch (Exception e) {

		}
		// String Sec = timeTest[2];
		Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
				.putExtra(AlarmClock.EXTRA_MESSAGE, clockMeaasge)
				.putExtra(AlarmClock.EXTRA_HOUR, hour)
				.putExtra(AlarmClock.EXTRA_MINUTES, minute);

		if (intent.resolveActivity(getPackageManager()) != null) {
			startActivity(intent);
		}
	}

	/**
	 * 解析时间
	 * 
	 * @param meaasge
	 * @return
	 */
	private String[] SpiltTime(String meaasge) {
		String[] clockTime = meaasge.split(":");
		return clockTime;
	}

	/**
	 * 跑线程发送地理位置信息到服务器
	 */
	class locationThread extends Thread {

		public void run() {
			while (isLocationThreadRun) {
				System.out.println("isLocationThreadRun is run \n已运行"
						+ locationThreadRunount + "次");
				locationThreadRunount++;
				try {
					// 等待，接收来自服务器返回的消息
					iService.connecttoserver();
					System.out.println("run locationThread");
					if (iService.getSocket().isConnected()) {
						// click();
						iService.SendMsg(iService.getSocket(), msg);
						System.out.println("had send ");
					}
					System.out.println("正在获取服务器信息");
					//-----2260-----
					getNoticeServiceMsg = iService.ReceiveMsg(iService
							.getSocket());
					System.out.println("获取服务器信息：" + getNoticeServiceMsg);
					if (getNoticeServiceMsg != null
							&& getNoticeServiceMsg.contains("ROBOTOUTPUT")) {
						mTts.startSpeaking(getNoticeServiceMsg, mTtsListener);
					System.out.println(getNoticeServiceMsg);
						noticeInt = 1;
						// 处理服务器返回的信息
						Spilt sp = new Spilt();
						getNoticeServicemsg1 = sp.spiltNoticeString(
								getNoticeServiceMsg, TAG);
						System.out.println("这是服务器返回关于ROBOTOUTPUT的信息："
								+ getNoticeServiceMsg);
						Message msg = new Message();
						msg.what = IS_NEW_MASSGAE;
						mHandler.sendMessage(msg);
						Log.i(TAG, getNoticeServiceMsg);
					} else {
						Log.i(TAG, "没获取到推送信息");
					}
				} catch (Exception e) {
					Log.e(TAG, e.toString());
					e.printStackTrace();
					System.out.print(e);
				}
				// 45秒发送一次
				try {
					sleep(45000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 线程，连接服务器，获取服务器返回数据
	 * 
	 * @author xiexs
	 * 
	 */
	class tcpClient extends Thread {
		String message;

		public tcpClient(String msg) {
			message = msg;
		}

		@Override
		public void run() {
			receiveMsg = null;
			try {
				// 等待，接收来自服务器返回的消息
				iService.connecttoserver();

				System.out.println("run thread");
				if (iService.getSocket().isConnected()) {
					// click();
					iService.SendMsg(iService.getSocket(), message);
				}
				receiveMsg = iService.ReceiveMsg(iService.getSocket());
				if (receiveMsg != null) {
					Spilt sp = new Spilt();
					// String[] spResult = sp.spiltString(receiveMsg, TAG);
					String[] spResult = sp.spiltFuckText(receiveMsg, TAG);
					if (spResult == null || spResult.equals("")) {

					} else {
						if (spResult[1] == null || spResult[1].equals("")) {

						} else {
							receiveMsg = spResult[1];
						}
						listSpilt = sp.spilt(receiveMsg, TAG);
						if (spResult[0] != null && !(spResult[0].equals(""))) {
							if (spResult[0].equals("text")) {
								if (receiveMsg != null) {
									Message msgText = new Message();
									msgText.what = UPDATALOG;
									mHandler.sendMessage(msgText);
									System.out.println("text is true");
								}
							} else if (spResult[0].equals("textpic")) {
								System.out.println("textpic is true");
								Message msgText = new Message();
								msgText.what = SETTEXTPIC;
								mHandler.sendMessage(msgText);
							}
						} else {
							spResult[0] = "text";
							if (receiveMsg != null) {
								Message msg = new Message();
								msg.what = UPDATALOG;
								mHandler.sendMessage(msg);
								System.out.println("text is true");
							}
						}
					}
				} else {
					receiveMsg = "您问的问题回答不上，换个试试";
				}
				// writer.close();
				// if (receiveMsg != null) {
				// receiveMsgFromServ = receiveMsg;
				// System.out.println("receiveFirst: " + receiveMsgFromServ);
				// iService.getSocket().close();
				// 将服务器返回的消息显示出来,发送成功获取信息
				// Message msg = new Message();
				// msg.what = UPDATALOG;
				// mHandler.sendMessage(msg);
				// }

			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case IS_NEW_MASSGAE:
				Log.i(TAG, "收到服务器新的消息");
				confirm(getNoticeServicemsg1, UnderstanderDemo.this);
				break;
			case SETCLOCK:
				Setclock();
				break;
			case CONNENTED:
				logMsg += "Server Connented !!";
				System.out.println(logMsg);
				break;
			case UPDATALOG:
				System.out.println("receive: " + receiveMsgFromServ);
				Log.e(TAG, "成功返回服务器信息");
				setReceiveView();
				break;
			case SUCCESS:
				findViewById(R.id.btn_Speak);
				Log.e(TAG, "成功获取到发送信息");
				setMyView();
				break;
			case FAIL:
				Log.e(TAG, "bean is null!");
				break;
			case SEND:
				// init();
				isTextSend = true;
				setMyView();
				break;
			case SETTEXTPIC:
				setTextPic();
				break;
			case WELCOME:
				setWeicomeView();
				break;
			case CALLORSENDFAIL:
				setCallOrSendFailView();
				break;
			case FAILCONTACTS:
				setFailContactsView();
				break;
			case CLOCK:
				System.out.println("设置闹钟");
				break;
			case AFTER_PIS:
				afterPisOrder();
				break;
			case 100:
				Toast.makeText(UnderstanderDemo.this, ceshi1, Toast.LENGTH_LONG)
						.show();
				break;
			}
		}
	};
	private Button btn_speak;

	private void setFailContactsView() {
		if (isContacts) {
			isContacts = false;
			if (callNumber == null || callNumber.equals("")) {
				chatBean receiveBean = new chatBean();
				receiveBean.setType(TYPE_RECEIVE_TXT);
				receiveMsg = "对不起，查找不到联系人";
				receiveBean.setText(receiveMsg);
				list.add(receiveBean);
				adapter.notifyDataSetChanged();
				listView.setSelection(listView.getCount() - 1);
			}
		}
	}

	private void setCallOrSendFailView() {
		Toast.makeText(UnderstanderDemo.this, "识别错误,solts是空的,请重试",
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * 设置欢迎界面
	 */
	private void setWeicomeView() {
		chatBean Beanfirst = new chatBean();
		Beanfirst.setText(getFromSerciceMsg);
		Beanfirst.setType(0);
		adapter = new chatAdapter(this, list, result);
		list.add(Beanfirst);
		adapter.notifyDataSetChanged();
		listView.setAdapter(adapter);
	}

	/**
	 * 设置闹钟
	 */
	private void Setclock() {
		try {
			SetclockThread thread = new SetclockThread();
			thread.start();
		} catch (Exception e) {
			Log.e(TAG, "闹钟线程出错了");
		}

	}

	private void setTextPic() {
		chatBean receiveBean = new chatBean();
		receiveBean.setType(TYPE_LIST);
		List<String[]> listtest = new ArrayList<String[]>();
		Map<String, String> map = new HashMap<String, String>();

		for (int i = 0; i < listSpilt.size(); i++) {
			String content[] = { "", "", "", "", "", "" };
			map = (Map<String, String>) listSpilt.get(i);
			System.out.println(map.size() + "<<这是map的长度");

			for (int j = 0; j < map.size(); j++) {
				content[j] = map.get("content" + j);
			}
			// downLoadImageTask.execute("");
			listtest.add(content);
			for (int k = 0; k < content.length; k++) {
				System.out.println("this is content" + k + content[k]);
			}

		}
		if (result.contains("。")) {
			int i = result.indexOf("。");
			result = result.substring(0, i);
		}
		receiveBean.setData(listtest);
		receiveBean.setText(result);
		list.add(receiveBean);

		// SharedPreferences sf=getPreferences(1);
		// String srr = sf.getString("AAAAAA", "default");
		// if (srr!=null) {
		//
		// list.add((Object)srr);
		// }

		adapter.notifyDataSetChanged();
		listView.setSelection(listView.getCount() - 1);
		listView.setClickable(true);
	}

	/**
	 * 设置留言返回信息
	 */
	private void setReceiveView() {
		if (isDisCuss) {
			chatBean receiveBean = new chatBean();
			receiveBean.setType(TYPE_RECEIVE_TXT);
			receiveBean.setText("您的留言我们已收到!");
			list.add(receiveBean);
			adapter.notifyDataSetChanged();
			listView.setSelection(listView.getCount() - 1);
			// startSpeak();
		} else {
			chatBean receiveBean = new chatBean();
			receiveBean.setType(TYPE_RECEIVE_TXT);
			if (receiveMsg == null || receiveMsg.equals("")) {
				// receiveBean.setText("对不起，您问的问题太难了，换个问题试试看");
				receiveMsg = "对不起，您问的问题太难了，换个问题试试";
				receiveBean.setText(receiveMsg);
			} else {
				if (receiveMsg.contains("<a href")) {
					receiveSpiltOutUrl = Spilt.spiltTextUrl2(receiveMsg);
					isContainsUrl = true;
				}
				receiveBean.setText(receiveMsg);
			}
			list.add(receiveBean);
			adapter.notifyDataSetChanged();
			listView.setSelection(listView.getCount() - 1);
			if (isSpeak) {
				startSpeak(receiveMsg);
			}
		}

	}

	/**
	 * 开始讲话判断
	 * 
	 * @param message
	 */
	private void startSpeak(String message) {
		Spanned receiveMes;
		String text;
		if (message.contains("zhidao")) {
			message = Spilt.spiltBaidu(message);
			seTTtParam();
			int code = mTts.startSpeaking(message.trim(), mTtsListener);
		}
		if (message.contains("<a href")) {
			receiveMes = Html.fromHtml(message);
			message = String.valueOf(receiveMes);
			// receiveMes = Spilt.spiltTextUrl3(message);
			seTTtParam();
			int code = mTts.startSpeaking(message, mTtsListener);
		} else {
			if (isStartSpeak) {
				seTTtParam();
				int code = mTts.startSpeaking(message, mTtsListener);
				if (code != ErrorCode.SUCCESS) {
					if (code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
						// 未安装则跳转到提示安装页面
						// mInstaller.install();
					} else {
						showTip("语音合成失败,错误码: " + code);
					}
				}
			} else {
				Toast.makeText(UnderstanderDemo.this, "关闭声音",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void setMyView() {
		chatBean sendBean = new chatBean();
		sendBean.setText(result);
		sendBean.setType(TYPE_SEND_TXT);
		list.add(sendBean);
		adapter = new chatAdapter(this, list, result);
		adapter.notifyDataSetChanged();
		listView.setSelection(listView.getCount() - 1);
		if (isTextSend) {
			System.out.println("不用改变麦克风背景");
		} else {
			findViewById(R.id.btn_Speak);
		}
		if (isDisCuss) {
			initLeaveMessage();
		} else {
			if (isContacts) {
				Message msg = new Message();
				msg.what = FAILCONTACTS;
				mHandler.sendMessage(msg);
			} else if (isClock) {
				Message msg = new Message();
				msg.what = CLOCK;
				mHandler.sendMessage(msg);
			} else {
				isLocation = false;
				init();
			}

		}

	}

	/*
	 * class ChatTask extends AsyncTask<String, Void, String> {
	 * 
	 * @Override protected void onPostExecute(String result) { chatBean
	 * receiveBean = new chatBean(); receiveBean.setType(TYPE_RECEIVE_TXT);
	 * receiveBean.setText(receiveMsg); list.add(receiveBean);
	 * adapter.notifyDataSetChanged(); listView.setSelection(listView.getCount()
	 * - 1); super.onPostExecute(result); }
	 * 
	 * @Override protected String doInBackground(String... params) { init();
	 * String result = ""; while (receiveMsg != null) { result = receiveMsg; }
	 * return result; }
	 * 
	 * }
	 */

	/**
	 * 字符串补齐
	 * 
	 * @param source
	 *            源字符串
	 * @param fillLength
	 *            补齐长度
	 * @param fillChar
	 *            补齐的字符
	 * @param isLeftFill
	 *            true为左补齐，false为右补齐
	 * @return
	 */
	public static String stringFill(String source, int fillLength,
			char fillChar, boolean isLeftFill) {
		if (source == null || source.length() >= fillLength)
			return source;

		StringBuilder result = new StringBuilder(fillLength);
		int len = fillLength - source.length();
		if (isLeftFill) {
			for (; len > 0; len--) {
				result.append(fillChar);
			}
			result.append(source);
		} else {
			result.append(source);
			for (; len > 0; len--) {
				result.append(fillChar);
			}
		}
		return result.toString();
	}

	/**
	 * 转化字符串格式为GBK
	 * 
	 * @param msg
	 *            目标字符串
	 */
	public String converMsgToGBK(String mssg) {
		String message;
		if (mssg == null) {
			return "";
		}
		try {
			byte[] bytesStr = mssg.getBytes();
			message = new String(bytesStr, "UTF-8");
			return message;
		} catch (Exception ex) {
			Log.e(TAG, "catch a Exception!!");
			return mssg;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.out.println("onDestroy");
		mTts.stopSpeaking();// 退出时停止讲话
		// 退出时释放连接
		mSpeechUnderstander.cancel();
		mSpeechUnderstander.destroy();
		if (mReceiver != null) {
			unregisterReceiver(mReceiver);
		}

		AppManager.getAppManager().finishActivity(this);
		/*
		 * if (mTextUnderstander.isUnderstanding()) mTextUnderstander.cancel();
		 * mTextUnderstander.destroy();
		 */
	}

	private void showTip(final String str) {
		// mToast.setText(str);
		// mToast.show();
	}

	/**
	 * 参数设置
	 * 
	 * @param param
	 * @return
	 */
	public void setParam() {
		String lag = mSharedPreferences.getString(
				"understander_language_preference", "mandarin");
		if (lag.equals("en_us")) {
			// 设置语言
			mSpeechUnderstander.setParameter(SpeechConstant.LANGUAGE, "en_us");
		} else {
			// 设置语言
			mSpeechUnderstander.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
			// 设置语言区域
			mSpeechUnderstander.setParameter(SpeechConstant.ACCENT, lag);
		}
		// 设置语音前端点
		mSpeechUnderstander.setParameter(SpeechConstant.VAD_BOS,
				mSharedPreferences.getString("understander_vadbos_preference",
						"4000"));
		// 设置语音后端点
		mSpeechUnderstander.setParameter(SpeechConstant.VAD_EOS,
				mSharedPreferences.getString("understander_vadeos_preference",
						"1000"));
		// 设置标点符号
		mSpeechUnderstander.setParameter(SpeechConstant.ASR_PTT,
				mSharedPreferences.getString("understander_punc_preference",
						"1"));
		// 设置音频保存路径
		mSpeechUnderstander.setParameter(SpeechConstant.ASR_AUDIO_PATH,
				Environment.getExternalStorageDirectory()
						+ "/iflytek/wavaudio.pcm");
	}

	@Override
	protected void onResume() {
		mTts.stopSpeaking();
		FlowerCollector.onResume(this);
		String temp = this.getIntent().getStringExtra("temp");
		System.out.println("onResume\ntemp:   " + temp);
		// isInUnderstander = true;
		super.onResume();
	}

	@Override
	protected void onPause() {
		System.out.println("onPause");
		mTts.stopSpeaking();
		// isInUnderstander = false;
		// 移动数据统计分析
		// FlowerCollector.onPageEnd("UnderstanderDemo");
		FlowerCollector.onPause(this);
		super.onPause();
	}

	/**
	 * 返回值弹出框显示然后跳转
	 */
	@SuppressLint("WrongViewCast")
	public void confirm(String massage[], Context context) {
		// 判断用户是否勾选不需要提醒
		// if(){
		// isNotice = false;
		// }
		switch (noticeInt) {
		case 1:
			Log.i(TAG, "开始运行弹出窗");
			if (!isInUnderstander) {
				break;
			}
			if (isNotice) {
				noticeBuilder = new AlertDialog.Builder(UnderstanderDemo.this);// getApplication().getApplicationContext()
				noticeBuilder.setTitle("提醒");
				noticeBuilder.setMessage(getNoticeServicemsg1[2].trim());
				LinearLayout view1 = (LinearLayout) this
						.findViewById(R.id.confirm_checkbox);
				noticeBuilder.setView(view1);
				noticeBuilder.setPositiveButton("确认",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								Tmsg = "Pis_Lock_User_Needs {COMM_INFO {BUSI_CODE 10011} {REGION_ID A} {COUNTY_ID A00} {OFFICE_ID 22342} {OPERATOR_ID 43643} {CHANNEL A2} {OP_MODE SUBMIT} } { {WXOPEN_ID "
										+ IMEI
										+ "} {CUST_OPEN_ID "
										+ getNoticeServicemsg1[1]
										+ "} {MESSAGE_ID "
										+ getNoticeServicemsg1[0] + "} }";
								System.out.println("Tmsg:" + Tmsg);
								if (getNoticeServicemsg1[1] != null
										&& getNoticeServicemsg1[0] != null
										&& IMEI != null) {
									/* 向服务器发送信息 */
									try {
										iService.connecttoserver();
										if (iService.getSocket().isConnected()) {
											iService.SendMsg(
													iService.getSocket(), Tmsg);
											Log.i(TAG, "已发送" + Tmsg);
											Toast.makeText(
													UnderstanderDemo.this,
													"已发送", Toast.LENGTH_LONG)
													.show();
										} else {
											Toast.makeText(
													UnderstanderDemo.this,
													"服务器繁忙", Toast.LENGTH_LONG)
													.show();
										}
										/* 获取服务器信息并操作 */
										System.out.println("正在获取服务器信息");
										getAfterPisMsg = iService
												.ReceiveMsg(iService
														.getSocket());
										System.out.println("获取服务器信息："
												+ getAfterPisMsg);
										if (getAfterPisMsg != null
												&& getAfterPisMsg
														.contains("ROBOT_OUTPUT")) {
											// 处理服务器返回的信息
											Spilt sp = new Spilt();
											getAfterPisMsg1 = sp
													.spiltWelcomeString(
															getAfterPisMsg, TAG);// 需要分离
											System.out
													.println("getAfterPisMsg1:"
															+ getAfterPisMsg1);// 3
											Message msgAfter = new Message();
											msgAfter.what = AFTER_PIS;
											mHandler.sendMessage(msgAfter);
											Log.i(TAG, "getAfterPisMsg1:"
													+ getAfterPisMsg1);
										} else {
											Log.i(TAG, "没获取到推送信息");
										}
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} else {
									Toast.makeText(UnderstanderDemo.this,
											"参数错误", Toast.LENGTH_LONG).show();
								}

							}
						});
				noticeBuilder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub

							}
						});
				// 开始运行子线程
				noticeDialog = noticeBuilder.show();
				if (isLocationThreadRun) {
					mTts.startSpeaking(getNoticeServicemsg1[2].trim(),
							mTtsListener);
					Log.i(TAG, "语音念出来了");
				}
			}
			break;
		case 2:
			if (null != noticeDialog) {
				if (noticeDialog.isShowing()) {
					noticeDialog.dismiss();
					System.out.println("消失");
				}
				noticeDialog = null;
			}
			break;
		}
	}

	/**
	 * 20秒后关闭弹出框的计时
	 * 
	 * @author chun
	 * 
	 */
	class noticeDismiss extends Thread {
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("线程开始运行");
			try {
				System.out.println("线程开始暂停");
				sleep(10000);
				System.out.println("暂停结束");
				noticeInt = 2;
				Message msg = new Message();
				msg.what = IS_NEW_MASSGAE;
				mHandler.sendMessage(msg);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 显示调用Pis_Lock_User_Needs接口后的信息
	 */
	public void afterPisOrder() {
		// 显示在主页面中
		System.out.println("开始显示调用Pis_Lock_User_Needs接口后的信息");
		System.out.println("result:" + result);
		chatBean Beanfirst = new chatBean();
		Beanfirst.setText(getAfterPisMsg1);
		Beanfirst.setType(0);
		list.add(Beanfirst);
		adapter = new chatAdapter(this, list, result);
		adapter.notifyDataSetChanged();
		listView.setSelection(listView.getCount() - 1);
		listView.setAdapter(adapter);
	}

	/**
	 * 判断是否登陆
	 * 
	 * @return
	 */
	public boolean isLogin() {
		boolean isLogin = false;
		if (null != uSharedPreferences) {
			String name = uSharedPreferences.getString("username", "");
			if (null != name && "" != name) {
				isLogin = true;
				return isLogin;
			} else {
				isLogin = false;
				return isLogin;
			}
		}
		return isLogin;
	}

	/*
	 * 根据电话号码取得联系人姓名
	 */
	public static String getContactPhoneNumberByName(Context context,
			String name) {
		String[] projection = { ContactsContract.PhoneLookup.DISPLAY_NAME,
				ContactsContract.CommonDataKinds.Phone.NUMBER };

		// 将自己添加到 msPeers 中
		Cursor cursor = context.getContentResolver()
				.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						projection, // Which
									// columns
									// to
									// return.
						ContactsContract.PhoneLookup.DISPLAY_NAME + " = '"
								+ name + "'", // WHERE
												// clause.
						null, // WHERE clause value substitution
						null); // Sort order.

		if (cursor == null) {
			Log.d(TAG, "getPeople null");
			return null;
		}
		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToPosition(i);

			// 取得联系人名字
			int nameFieldColumnIndex = cursor
					.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
			String number = cursor.getString(nameFieldColumnIndex);
			return number;
		}
		return null;
	}

	/**
	 * 退出程序
	 */
	private void exitActivity() {
		isLocationThreadRun = false;
		// 关闭定位的线程
		mLocationClient.stop();
		UnderstanderDemo.this.finish();
		AppManager.getAppManager().finishAllActivity();
		android.os.Process.killProcess(android.os.Process.myPid());
		// System.exit(0);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (webview!=null&&(keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
			webview.goBack();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			mTts.stopSpeaking();
			new AlertDialog.Builder(UnderstanderDemo.this)
					.setTitle("主人，确认要退出吗")
					// .setMessage("确定吗？")
					.setPositiveButton("是",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface arg0,
										int arg1) {
									exitActivity();
									// System.exit(0);
								}
							}).setNegativeButton("否", null).show();
		}

		return super.onKeyDown(keyCode, event);
	}

}
