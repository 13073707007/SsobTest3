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
import com.loongme.details.ServerActivity;

public class ClassifyFragment extends Fragment implements OnClickListener {
	public static final String TAG = "ClassifyFragment";
	private String str;
	RelativeLayout rl_mtmr1, rl_wdyj1, rl_kqk1, rl_mj1, rl_qdqb1, rl_fxmt1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.classifyfragment_item, null);
		rl_mtmr1 = (RelativeLayout) view.findViewById(R.id.rl_mtmr1);
		rl_wdyj1 = (RelativeLayout) view.findViewById(R.id.rl_wdyj1);
		rl_kqk1 = (RelativeLayout) view.findViewById(R.id.rl_kqk1);
		rl_mj1 = (RelativeLayout) view.findViewById(R.id.rl_mj1);
		rl_qdqb1 = (RelativeLayout) view.findViewById(R.id.rl_qdqb1);
		rl_fxmt1 = (RelativeLayout) view.findViewById(R.id.rl_fxmt1);
		rl_mtmr1.setOnClickListener(this);
		rl_wdyj1.setOnClickListener(this);
		rl_kqk1.setOnClickListener(this);
		rl_mj1.setOnClickListener(this);
		rl_qdqb1.setOnClickListener(this);
		rl_fxmt1.setOnClickListener(this);

		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_mtmr1:
			startActivity(new Intent(getActivity(), ServerActivity.class));
			break;
		case R.id.rl_wdyj1:
			startActivity(new Intent(getActivity(), ServerActivity.class));
			break;
		case R.id.rl_kqk1:
			startActivity(new Intent(getActivity(), ServerActivity.class));
			break;
		case R.id.rl_mj1:
			startActivity(new Intent(getActivity(), ServerActivity.class));
			break;
		case R.id.rl_qdqb1:
			startActivity(new Intent(getActivity(), ServerActivity.class));
			break;
		case R.id.rl_fxmt1:
			startActivity(new Intent(getActivity(), ServerActivity.class));
			break;

		default:
			break;
		}
	}
}
