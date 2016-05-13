package com.loongme.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.loongme.activity.R;
import com.loongme.base.BaseActivity;

public class CompanyIntroduction extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company_introduction);
		findViewById(R.id.btn_setting).setVisibility(View.GONE);
		findViewById(R.id.btn_back).setVisibility(View.VISIBLE);
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}
