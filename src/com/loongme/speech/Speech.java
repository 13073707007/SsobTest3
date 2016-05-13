package com.loongme.speech;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.loongme.activity.R;
import com.loongme.base.BaseActivity;

public class Speech extends BaseActivity implements OnClickListener {
	private RecognizerDialog myDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		findViewById(R.id.btn_start).setOnClickListener(this);
		SpeechUtility.createUtility(Speech.this, "appid=" + getString(R.string.app_id));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_start:
			// findViewById(R.id.et_content);
			((TextView) findViewById(R.id.tv_content)).setText("");
			myRecognize();
			break;
		default:
			break;
		}
	}

	/*
	 * private SpeechListener loginListener = new SpeechListener() {
	 * 
	 * @Override public void onCompleted(SpeechError error) { if (error != null)
	 * { Toast.makeText(Speech.this, "login failed", Toast.LENGTH_SHORT)
	 * .show(); } else { Toast.makeText(Speech.this, "login success",
	 * Toast.LENGTH_SHORT) .show(); }
	 * 
	 * }
	 * 
	 * @Override public void onEvent(int arg0, Bundle arg1) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 * 
	 * @Override public void onBufferReceived(byte[] arg0) { // TODO
	 * Auto-generated method stub }
	 * 
	 * };
	 */

	private void myRecognize() {
		myDialog = new RecognizerDialog(this, null);
		// 设置引擎为转写
		myDialog.setParameter(SpeechConstant.DOMAIN, "iat");
		// 设置识别语言为中文
		myDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
		// 设置方言为普通话
		myDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
		// 设置录音采样率为
		myDialog.setParameter(SpeechConstant.SAMPLE_RATE, "16000");
		// 设置监听对象
		myDialog.setListener(recognizerDialogListener);
		// 开始识别
		myDialog.show();
	}

	private RecognizerDialogListener recognizerDialogListener = new RecognizerDialogListener() {

		@Override
		public void onError(SpeechError error) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onResult(RecognizerResult result, boolean isLast) {
			// TODO Auto-generated method stub
			// ((TextView) findViewById(R.id.et_content)).setText("");
			// ((TextView) findViewById(R.id.tv_content)).setText("");
			((TextView) findViewById(R.id.tv_content)).append(result.getResultString());
		}

	};
}
