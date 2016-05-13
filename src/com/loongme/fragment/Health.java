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

public class Health extends Fragment {
	HealthListener mHealthListener;
	private Button btn_xinli, btn_yisheng, btn_zhuanjia, btn_dianyi, btn_jiayi, btn_peizhen, btn_yaopin,
			btn_yuer, btn_jianfei, btn_zhangyi;

	protected String doctorOnline="http://m.xywy.com/home/self_help/zz.html?fromurl=wk_1";
	protected String heartConsulting="http://3g.club.xywy.com/familyDoctor/list/subject?id=321";
	protected String phoneDoctor="http://3g.zhuanjia.xywy.com/dhyslist.htm?fromurl=wk_2";
	protected String professorAppointment="http://3g.zhuanjia.xywy.com";
	protected String familyDoctor="http://3g.club.xywy.com/familyDoctor/list/subject?id=766&fromurl=3gbanner";
	protected String accompanyingService="http://m.yishengshi.xywy.com";
	protected String medicine="http://3g.yao.xywy.com/?fromurl=wk_1";
	protected String parentingRole="http://3g.xywy.com/baby?fromurl=Mindex-2";
	protected String diet="http://3g.xywy.com/jianfei";
	protected String APPDoctor="http://m.xywy.com/home/hospital/index.html";
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			mHealthListener = (HealthListener) activity;
		} catch (Exception e) {
			// TODO: handle exception
			throw new ClassCastException(activity.toString() + "must implement mbtnListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.health_fragment, null, false);
		btn_xinli = (Button) view.findViewById(R.id.btn_xinli);
		btn_yisheng = (Button) view.findViewById(R.id.btn_yisheng);
		btn_zhuanjia = (Button) view.findViewById(R.id.btn_zhuanjia);
		btn_dianyi = (Button) view.findViewById(R.id.btn_dianyi);
		btn_jiayi = (Button) view.findViewById(R.id.btn_jiayi);
		btn_peizhen = (Button) view.findViewById(R.id.btn_peizhen);
		btn_yaopin = (Button) view.findViewById(R.id.btn_yaopin);
		btn_yuer = (Button) view.findViewById(R.id.btn_yuer);
		btn_jianfei = (Button) view.findViewById(R.id.btn_jianfei);
		btn_zhangyi = (Button) view.findViewById(R.id.btn_zhangyi);
		btn_xinli.setOnClickListener(Intent);
		btn_yisheng.setOnClickListener(Intent);
		btn_zhuanjia.setOnClickListener(Intent);
		btn_dianyi.setOnClickListener(Intent);
		btn_peizhen.setOnClickListener(Intent);
		btn_yaopin.setOnClickListener(Intent);
		btn_yuer.setOnClickListener(Intent);
		btn_jianfei.setOnClickListener(Intent);
		btn_zhangyi.setOnClickListener(Intent);
		btn_jiayi.setOnClickListener(Intent);
		return view;
	}

	public interface HealthListener {

		public void healthfermsg(String str);
		
		public void displayWebView(String str);
		
//		添加字段至集合
		public void Putcontent(String str);

	}

	OnClickListener Intent = new OnClickListener() {

		

		

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_xinli:
//				mHealthListener.healthfermsg("心理咨询");
				mHealthListener.Putcontent("心理咨询");
				mHealthListener.displayWebView(heartConsulting);
				break;
			case R.id.btn_yisheng:
//				mHealthListener.healthfermsg("医生在线");
				mHealthListener.Putcontent("医生在线");
				mHealthListener.displayWebView(doctorOnline);
				break;
			case R.id.btn_zhuanjia:
//				mHealthListener.healthfermsg("专家预约");
				mHealthListener.Putcontent("专家预约");
				mHealthListener.displayWebView(professorAppointment);
				break;
			case R.id.btn_dianyi:
//				mHealthListener.healthfermsg("电话医生");
				mHealthListener.Putcontent("电话医生");
				mHealthListener.displayWebView(phoneDoctor);
				break;
			case R.id.btn_jiayi:
//				mHealthListener.healthfermsg("家庭医生");
				mHealthListener.Putcontent("家庭医生");
				mHealthListener.displayWebView(familyDoctor);
				break;
			case R.id.btn_peizhen:
//				mHealthListener.healthfermsg("陪诊服务");
				mHealthListener.Putcontent("陪诊服务");
				mHealthListener.displayWebView(accompanyingService);
				break;
			case R.id.btn_yaopin:
//				mHealthListener.healthfermsg("药品");
				mHealthListener.Putcontent("药品");
				mHealthListener.displayWebView(medicine);
				break;
			case R.id.btn_yuer:
//				mHealthListener.healthfermsg("育儿经");
				mHealthListener.Putcontent("育儿经");
				mHealthListener.displayWebView(parentingRole);
				break;
			case R.id.btn_jianfei:
//				mHealthListener.healthfermsg("减肥");
				mHealthListener.Putcontent("减肥");
				mHealthListener.displayWebView(diet);
				break;
			case R.id.btn_zhangyi:
//				mHealthListener.healthfermsg("掌上医生");
				mHealthListener.Putcontent("掌上医生");
				mHealthListener.displayWebView(APPDoctor);
				break;
			default:
				break;
			}
		}
	};
}
