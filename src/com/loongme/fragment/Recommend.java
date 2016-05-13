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

public class Recommend extends Fragment {
	protected  String URLlateNews = "http://toutiao.com/sem/keyword/%E6%96%B0%E9%97%BB%E5%92%A8%E8%AF%A2/?c=e7bb8fe9aa8ce8af8d2de58897e8a1a82de5ae89e58d933231&a=e696b0e997bbe8b584e8aeaf3c303131313e&k=e696b0e997bbe592a8e8afa2&u=3138373438303830&sc=6261696475";

	protected  String URLdaylaught = "http://www.haha365.com/joke";

	protected  String URLKeepHealth = "http://bj.58.com/mianbumeir/?refrom=m58";

	protected  String URLCallExpress = "http://bj.58.com/kuaidi";

	protected  String URLTakeOut = "http://i.meituan.com/s/beijing-%E5%A4%96%E5%8D%96";

	protected  String URLFindJobs = "http://bj.58.com/job.shtml";

	protected  String URLWeather = "https://m.baidu.com/s?tn=bmbadr&pu=sz%401320_480%2Cosname%40baidubrowser%2Ccua%40_a-qi4uq-igBNE6lI5me6NNy2I_UCv8NzuDp8eLHA%2Ccut%40pfX8OYaS2i41aXiDyuvLCf41mjQm5BqAC%2Cctv%402%2Ccfrom%401000575h%2Ccen%40cuid_cua_cut%2Ccsrc%40app_box_txt&from=1000575h&word=%E5%A4%A9%E6%B0%94";

	protected  String URLTakingTaxi = "https://partners.uber.com.cn/ob/signup";

	protected  String URLDeliousFoods = "http://i.meituan.com/beijing?cid=1&stid_b=1&cateType=poi";

	protected  String URLKeepBalance = "http://3g.xywy.com/jianfei/";
	IBtnCallListener mbtnListener;

	private Button btn_zixun, btn_meiri, btn_kuaidi, btn_waimai, btn_gongzuo, btn_tianqi, btn_dache,
			btn_meishi, btn_jiansheng, btn_yangsheng;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			mbtnListener = (IBtnCallListener) activity;
		} catch (Exception e) {
			// TODO: handle exception
			throw new ClassCastException(activity.toString() + "must implement mbtnListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = inflater.inflate(R.layout.recommend_fragment, null, false);
		btn_zixun = (Button) view.findViewById(R.id.btn_zixun);
		btn_meiri = (Button) view.findViewById(R.id.btn_meiri);
		btn_kuaidi = (Button) view.findViewById(R.id.btn_kuaidi);
		btn_waimai = (Button) view.findViewById(R.id.btn_waimai);
		btn_gongzuo = (Button) view.findViewById(R.id.btn_gongzuo);
		btn_tianqi = (Button) view.findViewById(R.id.btn_tianqi);
		btn_dache = (Button) view.findViewById(R.id.btn_dache);
		btn_meishi = (Button) view.findViewById(R.id.btn_meishi);
		btn_jiansheng = (Button) view.findViewById(R.id.btn_jiansheng);
		btn_yangsheng = (Button) view.findViewById(R.id.btn_yangsheng);
		btn_zixun.setOnClickListener(Intents);
		btn_meiri.setOnClickListener(Intents);
		btn_kuaidi.setOnClickListener(Intents);
		btn_waimai.setOnClickListener(Intents);
		btn_gongzuo.setOnClickListener(Intents);
		btn_tianqi.setOnClickListener(Intents);
		btn_dache.setOnClickListener(Intents);
		btn_meishi.setOnClickListener(Intents);
		btn_jiansheng.setOnClickListener(Intents);
		btn_yangsheng.setOnClickListener(Intents);
		return view;
	}

	public interface IBtnCallListener {

		public void transfermsg(String str);

		public void displayWebView(String str);
		
//		添加字段至集合
		public void Putcontent(String str);
		
	}

	OnClickListener Intents = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_zixun:
				//mbtnListener.transfermsg("最新资讯");
				mbtnListener.Putcontent("最新资讯");
				mbtnListener.displayWebView(URLlateNews);
				break;
			case R.id.btn_meiri:
				//mbtnListener.transfermsg("每日一乐");
				mbtnListener.Putcontent("每日一乐");
				mbtnListener.displayWebView(URLdaylaught);
				break;
			case R.id.btn_kuaidi:
				//mbtnListener.transfermsg("叫快递");
				mbtnListener.Putcontent("叫快递");
 				mbtnListener.displayWebView(URLCallExpress);
				break;
			case R.id.btn_waimai:
				//mbtnListener.transfermsg("订餐");
				mbtnListener.Putcontent("订餐");
				mbtnListener.displayWebView(URLTakeOut);
				break;
			case R.id.btn_gongzuo:
				//mbtnListener.transfermsg("找工作");
				mbtnListener.Putcontent("找工作");
				mbtnListener.displayWebView(URLFindJobs);
				break;
			case R.id.btn_tianqi:
				//mbtnListener.transfermsg("天气");
				mbtnListener.Putcontent("天气");
				mbtnListener.displayWebView(URLWeather);
				break;
			case R.id.btn_dache:
				//mbtnListener.transfermsg("打车");
				mbtnListener.Putcontent("打车");
				mbtnListener.displayWebView(URLTakingTaxi);
				break;
			case R.id.btn_meishi:
				//mbtnListener.transfermsg("美食");
				mbtnListener.Putcontent("美食");
				mbtnListener.displayWebView(URLDeliousFoods);
				break;
			case R.id.btn_jiansheng:
				//mbtnListener.transfermsg("健身");
				mbtnListener.Putcontent("健身");
				mbtnListener.displayWebView(URLKeepBalance);
				break;
			case R.id.btn_yangsheng:
				//mbtnListener.transfermsg("养生");
				mbtnListener.Putcontent("养生");
				mbtnListener.displayWebView(URLKeepHealth);
				break;
			default:
				break;
			}
		}
	};
}
