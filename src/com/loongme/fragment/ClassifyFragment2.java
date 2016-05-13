package com.loongme.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.loongme.activity.R;
import com.loongme.details.Server2Activity;

public class ClassifyFragment2 extends Fragment implements OnClickListener {
	public static final String TAG2 = "ClassifyFragment";
	private String str;
	RelativeLayout rl_mtmr2, rl_wdyj2, rl_kqk2, rl_mj2, rl_qdqb2, rl_fxmt2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.classifyfragment_item2, null);
		rl_mtmr2 = (RelativeLayout) view.findViewById(R.id.rl_mtmr2);
		rl_wdyj2 = (RelativeLayout) view.findViewById(R.id.rl_wdyj2);
		rl_kqk2 = (RelativeLayout) view.findViewById(R.id.rl_kqk2);
		rl_mj2 = (RelativeLayout) view.findViewById(R.id.rl_mj2);
		rl_qdqb2 = (RelativeLayout) view.findViewById(R.id.rl_qdqb2);
		rl_fxmt2 = (RelativeLayout) view.findViewById(R.id.rl_fxmt2);
		rl_mtmr2.setOnClickListener(this);
		rl_wdyj2.setOnClickListener(this);
		rl_kqk2.setOnClickListener(this);
		rl_mj2.setOnClickListener(this);
		rl_qdqb2.setOnClickListener(this);
		rl_fxmt2.setOnClickListener(this);

		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_mtmr2:
			startActivity(new Intent(getActivity(), Server2Activity.class));
			break;
		case R.id.rl_wdyj2:
			startActivity(new Intent(getActivity(), Server2Activity.class));
			break;
		case R.id.rl_kqk2:
			startActivity(new Intent(getActivity(), Server2Activity.class));
			break;
		case R.id.rl_mj2:
			startActivity(new Intent(getActivity(), Server2Activity.class));
			break;
		case R.id.rl_qdqb2:
			startActivity(new Intent(getActivity(), Server2Activity.class));
			break;
		case R.id.rl_fxmt2:
			startActivity(new Intent(getActivity(), Server2Activity.class));
			break;

		default:
			break;
		}
	}
}
