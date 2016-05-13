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

public class Call extends Fragment {

	protected String furnitureStorage = "http://m.58.com/bj/kaisuo";
	protected String cleanKeeping = "http://bj.58.com/baojie";
	protected String babySitter = "http://bj.58.com/baomu";
	protected String callExpress = "http://bj.58.com/kuaidi";
	protected String pipelineClean = "http://bj.58.com/shutong";
	protected String openLock = "http://bj.58.com/shutong";
	protected String homeDecorationRepair = "http://bj.58.com/shutong";
	protected String homeMoving = "http://bj.58.com/shutong";
	protected String airConditioningRepair = "http://bj.58.com/kongtiao";
	protected String marketToHome = "http://bj.58.com/shop.shtml";

	private Button btn_unblank, btn_home, btn_move, btn_air, btn_supermarket,
			btn_homemaking, btn_clean, btn_nurse, btn_snack, btn_pipeline;

	CallListener mbtnListener3;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
			mbtnListener3 = (CallListener) activity;
		} catch (Exception e) {
			// TODO: handle exception
			throw new ClassCastException(activity.toString() + "must implement mbtnListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.call_fragment, null, false);
		btn_unblank = (Button) view.findViewById(R.id.btn_unblank);
		btn_home = (Button) view.findViewById(R.id.btn_home);
		btn_move = (Button) view.findViewById(R.id.btn_move);
		btn_air = (Button) view.findViewById(R.id.btn_air);
		btn_supermarket = (Button) view.findViewById(R.id.btn_supermarket);
		btn_homemaking = (Button) view.findViewById(R.id.btn_homemaking);
		btn_clean = (Button) view.findViewById(R.id.btn_clean);
		btn_nurse = (Button) view.findViewById(R.id.btn_nurse);
		btn_snack = (Button) view.findViewById(R.id.btn_snack);
		btn_pipeline = (Button) view.findViewById(R.id.btn_pipeline);
		btn_unblank.setOnClickListener(Intents);
		btn_home.setOnClickListener(Intents);
		btn_move.setOnClickListener(Intents);
		btn_air.setOnClickListener(Intents);
		btn_supermarket.setOnClickListener(Intents);
		btn_homemaking.setOnClickListener(Intents);
		btn_clean.setOnClickListener(Intents);
		btn_nurse.setOnClickListener(Intents);
		btn_snack.setOnClickListener(Intents);
		btn_pipeline.setOnClickListener(Intents);
		return view;
	}

	public interface CallListener {

		public void transfermsg3(String str);

		public void displayWebView(String str);
	}

	OnClickListener Intents = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_unblank:
//				mbtnListener3.transfermsg3("开锁");
				mbtnListener3.displayWebView(openLock);
				break;
			case R.id.btn_home:
//				mbtnListener3.transfermsg3("家装维修");
				mbtnListener3.displayWebView(homeDecorationRepair);
				break;
			case R.id.btn_move:
//				mbtnListener3.transfermsg3("搬家");
				mbtnListener3.displayWebView(homeMoving);
				break;
			case R.id.btn_air:
//				mbtnListener3.transfermsg3("空调维修");
				mbtnListener3.displayWebView(airConditioningRepair);
				break;
			case R.id.btn_supermarket:
//				mbtnListener3.transfermsg3("超市到家");
				mbtnListener3.displayWebView(marketToHome);
				break;
			case R.id.btn_homemaking:
//				mbtnListener3.transfermsg3("家居收纳");
				mbtnListener3.displayWebView(furnitureStorage);
				break;
			case R.id.btn_clean:
//				mbtnListener3.transfermsg3("保洁清洗");
				mbtnListener3.displayWebView(cleanKeeping);
				break;
			case R.id.btn_nurse:
//				mbtnListener3.transfermsg3("保姆月嫂");
				mbtnListener3.displayWebView(babySitter);
				break;
			case R.id.btn_snack:
//				mbtnListener3.transfermsg3("叫快递");
				mbtnListener3.displayWebView(callExpress);
				break;
			case R.id.btn_pipeline:
//				mbtnListener3.transfermsg3("管道疏通");
				mbtnListener3.displayWebView(pipelineClean);
				break;
			default:
				break;
			}
		}
	};
}
