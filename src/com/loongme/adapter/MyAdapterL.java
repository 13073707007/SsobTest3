package com.loongme.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.loongme.activity.R;
import com.loongme.com.model.Data;

public class MyAdapterL extends BaseAdapter{
	private List<Data> list;
	private Context context;
	
	public MyAdapterL(Context context) {
		// TODO Auto-generated constructor stub
//		this.list=list;
		this.context=context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View view=LayoutInflater.from(context).inflate(R.layout.item, null);
//		TextView tv1=(TextView) view.findViewById(R.id.tv1);
//		TextView tv2=(TextView) view.findViewById(R.id.tv2);
//		TextView tv3=(TextView) view.findViewById(R.id.tv3);
//		ImageView iv=(ImageView) view.findViewById(R.id.iv1);
//		Log.e("aaa", list.size()+"");
//		Data data=list.get(arg0);
//		iv.setImageResource(data.getIv1());
//		tv1.setText(data.getTitle());
//		tv2.setText(data.getData());
//		tv3.setText(data.getMessage());
		return view;
	}

}
