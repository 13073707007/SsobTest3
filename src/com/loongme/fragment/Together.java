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

public class Together extends Fragment {
	TogetherListener mbtnListener5;
	private Button btn_meishi, btn_dianying, btn_gouwu, btn_seying, btn_meirong, btn_meijia, btn_guonei,
			btn_chuguo, btn_zijia, btn_mengcong;
	protected String deliousFoods="http://i.meituan.com/beijing?cid=1&stid_b=1&cateType=poi";
	protected String movie="http://i.meituan.com/beijing?cid=99&stid_b=1&cateType=poi";
	protected String shopping="http://i.meituan.com/beijing?cid=4&stid_b=1&cateType=deal";
	protected String takePhoto="http://m.fengniao.com";
	protected String beautify="http://m.58.com/bj/mianbumeir/?PGTID=0d2070f9-0000-1dd8-57d4-1247d745ecfd&ClickID=2";
	protected String Nail="http://bj.58.com/mrmeijia/?refrom=m58";
	protected String inboundTourism="http://m.tuniu.com/bj/domestic";
	protected String travelAbroad="http://m.tuniu.com/bj/abroad";
	protected String roadTrip="http://m.tuniu.com/bj/drive";
	protected String Pets="http://bj.58.com/pets.shtml";


	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			mbtnListener5 = (TogetherListener) activity;
		} catch (Exception e) {
			// TODO: handle exception
			throw new ClassCastException(activity.toString() + "must implement mbtnListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.together_fragment, null, false);
		btn_meishi = (Button) view.findViewById(R.id.btn_meishi);
		btn_dianying = (Button) view.findViewById(R.id.btn_dianying);
		btn_gouwu = (Button) view.findViewById(R.id.btn_gouwu);
		btn_seying = (Button) view.findViewById(R.id.btn_seying);
		btn_meirong = (Button) view.findViewById(R.id.btn_meirong);
		btn_meijia = (Button) view.findViewById(R.id.btn_meijia);
		btn_guonei = (Button) view.findViewById(R.id.btn_guonei);
		btn_chuguo = (Button) view.findViewById(R.id.btn_chuguo);
		btn_zijia = (Button) view.findViewById(R.id.btn_zijia);
		btn_mengcong = (Button) view.findViewById(R.id.btn_mengcong);
		btn_meishi.setOnClickListener(Intents);
		btn_dianying.setOnClickListener(Intents);
		btn_gouwu.setOnClickListener(Intents);
		btn_seying.setOnClickListener(Intents);
		btn_meirong.setOnClickListener(Intents);
		btn_meijia.setOnClickListener(Intents);
		btn_guonei.setOnClickListener(Intents);
		btn_chuguo.setOnClickListener(Intents);
		btn_mengcong.setOnClickListener(Intents);
		btn_zijia.setOnClickListener(Intents);
		return view;
	}

	public interface TogetherListener {

		public void transfermsg5(String str);
		
		public void displayWebView(String str);
		
//		添加字段至集合
		public void Putcontent(String str);
	}

	OnClickListener Intents = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_meishi:
//				mbtnListener5.transfermsg5("美食");
				mbtnListener5.Putcontent("美食");
				mbtnListener5.displayWebView(deliousFoods);
				break;
			case R.id.btn_dianying:
//				mbtnListener5.transfermsg5("电影");
				mbtnListener5.Putcontent("电影");
				mbtnListener5.displayWebView(movie);
				break;
			case R.id.btn_gouwu:
//				mbtnListener5.transfermsg5("购物");
				mbtnListener5.Putcontent("购物");
				mbtnListener5.displayWebView(shopping);
				break;
			case R.id.btn_seying:
//				mbtnListener5.transfermsg5("摄影");
				mbtnListener5.Putcontent("摄影");
				mbtnListener5.displayWebView(takePhoto);
				break;
			case R.id.btn_meirong:
//				mbtnListener5.transfermsg5("美容");
				mbtnListener5.Putcontent("美容");
				mbtnListener5.displayWebView(beautify);
				break;
			case R.id.btn_meijia:
//				mbtnListener5.transfermsg5("美甲");
				mbtnListener5.Putcontent("美甲");
				mbtnListener5.displayWebView(Nail);
				break;
			case R.id.btn_guonei:
//				mbtnListener5.transfermsg5("国内游");
				mbtnListener5.Putcontent("国内游");
				mbtnListener5.displayWebView(inboundTourism);
				break;
			case R.id.btn_chuguo:
//				mbtnListener5.transfermsg5("出国游");
				mbtnListener5.Putcontent("出国游");
				mbtnListener5.displayWebView(travelAbroad);
				break;
			case R.id.btn_zijia:
//				mbtnListener5.transfermsg5("自驾游");
				mbtnListener5.Putcontent("自驾游");
				mbtnListener5.displayWebView(roadTrip);
				break;
			case R.id.btn_mengcong:
//				mbtnListener5.transfermsg5("萌宠");
				mbtnListener5.Putcontent("萌宠");
				mbtnListener5.displayWebView(Pets);
				break;
			default:
				break;
			}
		}
	};
}
