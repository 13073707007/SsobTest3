package com.loongme.personage;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import com.loongme.activity.R;
import com.loongme.base.BaseActivity;

/*
 * 
 * 推荐事事帮
 */
public class Recommend2Activity extends BaseActivity implements OnClickListener {
	private ImageView iv_fanhuire2;
	private Button btn_weixin_per, btn_QQ_per, btn_pengyou_per, btn_QQKJ_per,
			btn_XLWB_per, btn_TXWB_per;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_recommend2);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		iv_fanhuire2 = (ImageView) findViewById(R.id.iv_fanhuire2);
		findViewById(R.id.btn_weixin_per).setOnClickListener(this);
		findViewById(R.id.btn_QQ_per).setOnClickListener(this);
		findViewById(R.id.btn_pengyou_per).setOnClickListener(this);
		findViewById(R.id.btn_QQKJ_per).setOnClickListener(this);
		findViewById(R.id.btn_XLWB_per).setOnClickListener(this);
		findViewById(R.id.btn_TXWB_per).setOnClickListener(this);
		iv_fanhuire2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_fanhuire2:
//			startActivity(new Intent(getApplicationContext(), MeGuset2Activity.class));
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			finish();
			break;
		case R.id.btn_weixin_per:
			showShare(true, Wechat.NAME);
			break;
		case R.id.btn_QQ_per:
			showShare(true, QQ.NAME);
			break;
		case R.id.btn_pengyou_per:
			showShare(true, WechatMoments.NAME);
			break;
		case R.id.btn_QQKJ_per:
			showShare(true, QZone.NAME);
			break;
		case R.id.btn_XLWB_per:
			showShare(true, SinaWeibo.NAME);
			break;
		case R.id.btn_TXWB_per:
			showShare(true, TencentWeibo.NAME);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			startActivity(new Intent(getApplicationContext(), MeGuset2Activity.class));
			overridePendingTransition(R.anim.push_right_in,
					R.anim.push_right_out);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void showShare(boolean silent, String platform) {
		final OnekeyShare oks = new OnekeyShare();
		oks.setTitle("事事帮");
		// oks.setSilent(true);
		String text = this.getString(R.string.share_title);
		oks.setText(text);
		oks.setTitleUrl("http://43.242.181.12/dd.myapp.com/16891/199AD6B5FC7E74B17FAE62F652CAC311.apk?mkey=570f370c7373d892&f=10b&fsname=com.loongme.activity_2.0.4_20151028.apk&crazycache=1&p=.apk");// QQ链接
		oks.setUrl("http://43.242.181.12/dd.myapp.com/16891/199AD6B5FC7E74B17FAE62F652CAC311.apk?mkey=570f370c7373d892&f=10b&fsname=com.loongme.activity_2.0.4_20151028.apk&crazycache=1&p=.apk");// 微信的链接
		oks.setDialogMode();
		oks.disableSSOWhenAuthorize();
		if (platform != null) {
			oks.setPlatform(platform);
		}
		// 去自定义不同平台的字段内容
		oks.setShareContentCustomizeCallback(new ShareContentCustomizeDemo());
		oks.show(this);
	}
}
