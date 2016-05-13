package com.loongme.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

public class VpAdapter extends FragmentPagerAdapter {
	List<Fragment> frags=new ArrayList<Fragment>();

	public VpAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return frags.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return frags.size();
	}

}
