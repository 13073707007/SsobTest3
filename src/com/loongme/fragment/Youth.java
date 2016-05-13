package com.loongme.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.loongme.activity.R;

public class Youth extends Fragment {
	YouthListener mbtnListener;
	private Button btn_youxikong, btn_xuanwu, btn_biaoge, btn_yanchanghui, btn_erciyuan, btn_zhaogongzuo,
			btn_dushu, btn_jiaoyou, btn_aimaoxian, btn_aihuwai;
	protected String outdoors="http://m.8264.com/bbs";
	protected String adventure="http://www.zgfun.com/forum.php";
	protected String makeFriends="http://jiaoyou.58.com/bj";
	protected String read="https://m.douban.com/book";
	protected String findJobs="http://bj.58.com/job.shtml";
	protected String twoDimention="http://comic.qq.com/zt2012/goodgirl";
	protected String concert="http://www.228.com.cn/?type=1&cityjx=bj&typeajx=yanchanghui&source=baidu";
	protected String song="http://wap.juzishu.com.cn/chengnian/Adultvocalmusic";
	protected String dance="http://wap.juzishu.com.cn/chengnian/Adultdance";
	protected String GameController="http://weibo.com/chinaplayers?c=spr_qdhz_bd_baidusmt_weibo_s&nick=%E6%B8%B8%E6%88%8F%E6%8E%A7&is_hot=1";

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			mbtnListener = (YouthListener) activity;
		} catch (Exception e) {
			// TODO: handle exception
			throw new ClassCastException(activity.toString() + "must implement mbtnListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.youth_fragment, null, false);
		btn_youxikong = (Button) view.findViewById(R.id.btn_youxikong);
		btn_xuanwu = (Button) view.findViewById(R.id.btn_xuanwu);
		btn_biaoge = (Button) view.findViewById(R.id.btn_biaoge);
		btn_yanchanghui = (Button) view.findViewById(R.id.btn_yanchanghui);
		btn_erciyuan = (Button) view.findViewById(R.id.btn_erciyuan);
		btn_zhaogongzuo = (Button) view.findViewById(R.id.btn_zhaogongzuo);
		btn_dushu = (Button) view.findViewById(R.id.btn_dushu);
		btn_jiaoyou = (Button) view.findViewById(R.id.btn_jiaoyou);
		btn_aimaoxian = (Button) view.findViewById(R.id.btn_aimaoxian);
		btn_aihuwai = (Button) view.findViewById(R.id.btn_aihuwai);
		btn_youxikong.setOnClickListener(Intents);
		btn_xuanwu.setOnClickListener(Intents);
		btn_biaoge.setOnClickListener(Intents);
		btn_yanchanghui.setOnClickListener(Intents);
		btn_erciyuan.setOnClickListener(Intents);
		btn_zhaogongzuo.setOnClickListener(Intents);
		btn_dushu.setOnClickListener(Intents);
		btn_jiaoyou.setOnClickListener(Intents);
		btn_aimaoxian.setOnClickListener(Intents);
		btn_aihuwai.setOnClickListener(Intents);
		return view;
	}

	public interface YouthListener {

		public void transfermsg6(String str);
		
		public void displayWebView(String str);
		
//		添加字段至集合
		public void Putcontent(String str);
	}

	OnClickListener Intents = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_youxikong:
//				mbtnListener.transfermsg6("游戏控");
				mbtnListener.Putcontent("游戏控");
				mbtnListener.displayWebView(GameController);
				break;
			case R.id.btn_xuanwu:
//				mbtnListener.transfermsg6("炫舞");
				mbtnListener.Putcontent("炫舞");
				mbtnListener.displayWebView(dance);
				break;
			case R.id.btn_biaoge:
//				mbtnListener.transfermsg6("飙歌");
				mbtnListener.Putcontent("飙歌");
				mbtnListener.displayWebView(song);
				break;
			case R.id.btn_yanchanghui:
//				mbtnListener.transfermsg6("演唱会");
				mbtnListener.Putcontent("演唱会");
				mbtnListener.displayWebView(concert);
				break;
			case R.id.btn_erciyuan:
//				mbtnListener.transfermsg6("二次元");
				mbtnListener.Putcontent("二次元");
				mbtnListener.displayWebView(twoDimention);
				break;
			case R.id.btn_zhaogongzuo:
//				mbtnListener.transfermsg6("找工作");
				mbtnListener.Putcontent("找工作");
				mbtnListener.displayWebView(findJobs);
				break;
			case R.id.btn_dushu:
//				mbtnListener.transfermsg6("读书");
				mbtnListener.Putcontent("读书");
				mbtnListener.displayWebView(read);
				break;
			case R.id.btn_jiaoyou:
//				mbtnListener.transfermsg6("交友");
				mbtnListener.Putcontent("交友");
				mbtnListener.displayWebView(makeFriends);
				break;
			case R.id.btn_aimaoxian:
//				mbtnListener.transfermsg6("爱冒险");
				mbtnListener.Putcontent("爱冒险");
				mbtnListener.displayWebView(adventure);
				break;
			case R.id.btn_aihuwai:
//				mbtnListener.transfermsg6("爱户外");
				mbtnListener.Putcontent("爱户外");
				mbtnListener.displayWebView(outdoors);
				break;
			default:
				break;
			}
		}
	};
}
