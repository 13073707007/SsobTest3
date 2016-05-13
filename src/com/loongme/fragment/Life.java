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

public class Life extends Fragment {
	LifeListener mbtnListener;

	private Button btn_tianq, btn_xiaohua, btn_zixun, btn_jiudian, btn_jipiao, btn_huocepiao, btn_dache,
			btn_waimai, btn_xianguo, btn_zufang;

	protected String URLlateNews="http://toutiao.com/sem/keyword/%E6%96%B0%E9%97%BB%E5%92%A8%E8%AF%A2/?c=e7bb8fe9aa8ce8af8d2de58897e8a1a82de5ae89e58d933231&a=e696b0e997bbe8b584e8aeaf3c303131313e&k=e696b0e997bbe592a8e8afa2&u=3138373438303830&sc=6261696475";

	protected String URLweather="https://m.baidu.com/s?tn=bmbadr&pu=sz%401320_480%2Cosname%40baidubrowser%2Ccua%40_a-qi4uq-igBNE6lI5me6NNy2I_UCv8NzuDp8eLHA%2Ccut%40pfX8OYaS2i41aXiDyuvLCf41mjQm5BqAC%2Cctv%402%2Ccfrom%401000575h%2Ccen%40cuid_cua_cut%2Ccsrc%40app_box_txt&from=1000575h&word=%E5%A4%A9%E6%B0%94";

	protected String URLjokes="http://www.haha365.com/joke";

	protected String URLTakeOut="http://i.meituan.com/s/beijing-%E5%A4%96%E5%8D%96";

//	protected String URLhotal="http://www.hotels.cn/?dateless=true&PSRC=SEMU&rffrid=sem.hcom.CN.baidu.003.00.03.1.kwrd=ZzZz.1YUzR8HxF.0.5361722446.10203p79948";
	protected String URLhotal="http://www.hotels.cn/";

	protected String URLticket="http://flight.qunar.com/";

	protected String URLtakeTaix="https://partners.uber.com.cn/ob/signup?utm_source=baidu-nonbrand&utm_campaign=search-baidu_37_91_cn-beijing_d_mob_acq_cpc_cn-cn_%B4%F2%B3%B5_nat23142_{creative_id}_{adgroup_id}_{match_type}";

	protected String URLtrainTicket="http://www.ly.com/huochepiao/";

	protected String URLfruitPass="http://www.cuixianyuan.com/wap";

	protected String URLreting="http://m.58.com/bj/zufang";

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			mbtnListener = (LifeListener) activity;
		} catch (Exception e) {
			// TODO: handle exception
			throw new ClassCastException(activity.toString() + "must implement mbtnListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.life_fragment, null, false);
		btn_zixun = (Button) view.findViewById(R.id.btn_zixun);
		btn_tianq = (Button) view.findViewById(R.id.btn_tianq);
		btn_xiaohua = (Button) view.findViewById(R.id.btn_xiaohua);
		btn_waimai = (Button) view.findViewById(R.id.btn_waimai);
		btn_jiudian = (Button) view.findViewById(R.id.btn_jiudian);
		btn_jipiao = (Button) view.findViewById(R.id.btn_jipiao);
		btn_dache = (Button) view.findViewById(R.id.btn_dache);
		btn_huocepiao = (Button) view.findViewById(R.id.btn_huocepiao);
		btn_xianguo = (Button) view.findViewById(R.id.btn_xianguo);
		btn_zufang = (Button) view.findViewById(R.id.btn_zufang);
		btn_zixun.setOnClickListener(Intents);
		btn_tianq.setOnClickListener(Intents);
		btn_xiaohua.setOnClickListener(Intents);
		btn_waimai.setOnClickListener(Intents);
		btn_jiudian.setOnClickListener(Intents);
		btn_jipiao.setOnClickListener(Intents);
		btn_dache.setOnClickListener(Intents);
		btn_huocepiao.setOnClickListener(Intents);
		btn_xianguo.setOnClickListener(Intents);
		btn_zufang.setOnClickListener(Intents);
		return view;
	}

	public interface LifeListener {

		public void transfermsg4(String str);
		
//	            展示webview	
		public void displayWebView(String str);
		
//		添加字段至集合
		public void Putcontent(String str);
			
		
	}

	OnClickListener Intents = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_zixun:
				//mbtnListener.transfermsg4("最新资讯");
				mbtnListener.Putcontent("最新资讯");
				mbtnListener.displayWebView(URLlateNews);
				break;
			case R.id.btn_tianq:
//				mbtnListener.transfermsg4("天气");
				mbtnListener.Putcontent("天气");
				mbtnListener.displayWebView(URLweather);
				break;
			case R.id.btn_xiaohua:
//				mbtnListener.transfermsg4("笑话");
				mbtnListener.Putcontent("笑话");
				mbtnListener.displayWebView(URLjokes);
				break;
			case R.id.btn_waimai:
//				mbtnListener.transfermsg4("定外卖");
				mbtnListener.Putcontent("订外卖");
				mbtnListener.displayWebView(URLTakeOut);
				break;
			case R.id.btn_jiudian:
//				mbtnListener.transfermsg4("酒店");
				mbtnListener.Putcontent("酒店");
				mbtnListener.displayWebView(URLhotal);
				break;
			case R.id.btn_jipiao:
//				mbtnListener.transfermsg4("机票");
				mbtnListener.Putcontent("机票");
				mbtnListener.displayWebView(URLticket);
				break;
			case R.id.btn_dache:
//				mbtnListener.transfermsg4("打车");
				mbtnListener.Putcontent("打车");
				mbtnListener.displayWebView(URLtakeTaix);
				break;
			case R.id.btn_huocepiao:
//				mbtnListener.transfermsg4("火车票");
				mbtnListener.Putcontent("火车票");
				mbtnListener.displayWebView(URLtrainTicket);
				break;
			case R.id.btn_xianguo:
//				mbtnListener.transfermsg4("鲜果速递");
				mbtnListener.Putcontent("鲜果速递");
				mbtnListener.displayWebView(URLfruitPass);
				break;
			case R.id.btn_zufang:
//				mbtnListener.transfermsg4("租房子");
				mbtnListener.Putcontent("租房子");
				mbtnListener.displayWebView(URLreting);
				break;
			default:
				break;
			}
		}
	};
}
