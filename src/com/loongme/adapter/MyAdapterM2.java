package com.loongme.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.loongme.activity.R;
import com.loongme.personage.Release2Activity;

public class MyAdapterM2 extends BaseAdapter {
	private Context context;
	private String[] strings;
	public static int mPosition;

	public MyAdapterM2(Context context, String[] strings) {
		this.context = context;
		this.strings = strings;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return strings.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return strings[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = LayoutInflater.from(context).inflate(
				R.layout.classify_items, null);
		TextView classify_text = (TextView) convertView
				.findViewById(R.id.classify_text);
		mPosition = position;
		classify_text.setText(strings[position]);
		if (position == Release2Activity.mPosition) {
			convertView.setBackgroundColor(Color.parseColor("#EBEBEC"));
		} else {
			convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
		}
		return convertView;
	}

}
