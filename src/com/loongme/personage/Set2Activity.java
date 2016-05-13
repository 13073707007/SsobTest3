package com.loongme.personage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.loongme.activity.R;
import com.loongme.base.BaseActivity;
import com.loongme.enter.EnterActivity;

/**
 * 设置
 * 
 * @author xywy
 * 
 */
public class Set2Activity extends BaseActivity implements OnClickListener {
	private LinearLayout ll_fayin, ll_exit;
	private PopupWindow popselector;
	private ImageView iv_fanhui;
	private Button btn1, btn2, btn3, btn4;
	private RadioGroup group;
	private RadioButton btn_rb1, btn_rb2, btn_rb3, btn_rb4;
	// 语音合成对象
	public static SpeechSynthesizer mySynthesizer;
	// 引擎类型
	private String mEngineType = SpeechConstant.TYPE_CLOUD;
	// 默认发音人
	private String voicer = "xiaoyan";
	private String voicer1[] = { "vixy", "vixm", "vixl", "vixyun" };
	private SharedPreferences mSharedPreferences;
	private RelativeLayout rl_pu, rl_yue, rl_tai, rl_dong;
	private Button btn_cancel;
	private SharedPreferences mPreferences;
	private Editor editor;
	private TextView tv_type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_set);
		initView();
		// 语音初始化，在使用应用使用时需要初始化一次就好，如果没有这句会出现10111初始化失败
		SpeechUtility.createUtility(this, "appid=548ffc88");
		// 处理语音合成关键类
		mySynthesizer = SpeechSynthesizer.createSynthesizer(this,
				myInitListener);
		mPreferences = getSharedPreferences("AA", MODE_PRIVATE);
		editor = mPreferences.edit();
		type = mPreferences.getString("type", "");
		tv_type.setText(type);

	}

	private InitListener myInitListener = new InitListener() {
		@Override
		public void onInit(int code) {
			Log.d("mySynthesiezer:", "InitListener init() code = " + code);
		}
	};
	private String type;

	private void initView() {
		// TODO Auto-generated method stub
		ll_fayin = (LinearLayout) findViewById(R.id.ll_fayin);
		tv_type = (TextView) findViewById(R.id.tv_type);
		ll_exit = (LinearLayout) findViewById(R.id.ll_exit);
		iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		ll_fayin.setOnClickListener(this);
		ll_exit.setOnClickListener(this);
		iv_fanhui.setOnClickListener(this);

	}

	private void MyPopupWindow() {
		View view = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.popwindows, null);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.4f;
		getWindow().setAttributes(lp);
		btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		group = (RadioGroup) view.findViewById(R.id.group);
		btn_rb1 = (RadioButton) view.findViewById(R.id.btn_rb1);
		btn_rb2 = (RadioButton) view.findViewById(R.id.btn_rb2);
		btn_rb3 = (RadioButton) view.findViewById(R.id.btn_rb3);
		btn_rb4 = (RadioButton) view.findViewById(R.id.btn_rb4);
		btn_cancel.setOnClickListener(this);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == btn_rb1.getId()) {
					setVoicer();
					editor.putString("voicer", "vixy");
					editor.putString("type", "普通话");
					editor.commit();
					// btn_rb1.setChecked(true);
				} else if (checkedId == btn_rb2.getId()) {
					setVoicer();
					// btn_rb2.setChecked(true);
					editor.putString("voicer", "vixm");
					editor.putString("type", "粤语");
					editor.commit();
				} else if (checkedId == btn_rb3.getId()) {
					// btn_rb3.setChecked(true);
					setVoicer();
					editor.putString("voicer", "vixl");
					editor.putString("type", "台湾普通话");
					editor.commit();
				} else if (checkedId == btn_rb4.getId()) {
					setVoicer();
					editor.putString("voicer", "vixyun");
					editor.putString("type", "东北话");
					editor.commit();
					// btn_rb4.setChecked(true);
				}
			}

			private void setVoicer() {
				// TODO Auto-generated method stub
				// 设置发音人
				mySynthesizer.setParameter(SpeechConstant.VOICE_NAME, voicer);
				// 设置音调
				mySynthesizer.setParameter(SpeechConstant.PITCH, "50");
				// 设置音量
				mySynthesizer.setParameter(SpeechConstant.VOLUME, "50");
				mySynthesizer.setParameter(SpeechConstant.ENGINE_TYPE,
						SpeechConstant.TYPE_LOCAL);
				// 设置发音人 voicer为空默认通过语音+界面指定发音人。
				mySynthesizer.setParameter(SpeechConstant.VOICE_NAME, "");
			}
		});

		if (popselector == null) {
			popselector = new PopupWindow(Set2Activity.this);
			popselector.setBackgroundDrawable(new BitmapDrawable());
			popselector.setFocusable(true);
			popselector.setTouchable(true);
			popselector.setOutsideTouchable(true);
			popselector.setContentView(view);
			popselector.setWidth(LayoutParams.MATCH_PARENT);
			popselector.setHeight(LayoutParams.WRAP_CONTENT);

			// popselector.setAnimationStyle(R.style.popuStyle);
		}
		popselector.showAtLocation(ll_fayin, Gravity.BOTTOM, 0, 0);

		popselector.update();
		popselector.setOnDismissListener(new poponDismissListener());
	}

	class poponDismissListener implements OnDismissListener {

		@Override
		public void onDismiss() {
			// TODO Auto-generated method stub
			WindowManager.LayoutParams lp = getWindow().getAttributes();
			lp.alpha = 1f;
			getWindow().setAttributes(lp);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_fayin:
			MyPopupWindow();
			break;
		case R.id.ll_exit:
			SharedPreferences mPreferences = getSharedPreferences("USER",
					MODE_APPEND);
			Editor editor = mPreferences.edit();
			editor.clear();
			editor.commit();
			startActivity(new Intent(getApplicationContext(),
					EnterActivity.class));
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			finish();
			break;
		case R.id.iv_fanhui:
			startActivity(new Intent(getApplicationContext(),
					MeGusetActivity.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
			break;
		case R.id.btn_cancel:
			popselector.dismiss();
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
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
