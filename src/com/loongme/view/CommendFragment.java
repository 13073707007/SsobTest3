package com.loongme.view;
//package com.loongme.view;
//
//import com.loongme.activity.R;
//import android.annotation.SuppressLint;
//import android.app.Fragment;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//@SuppressLint("NewApi")
//public class CommendFragment extends Fragment {
//	private static final int DEFAULT_TAG = -1;
//	private int tag;
//	private TextView tv;
//
//	/**
//	 * 添加标识,返回带标识的对象。
//	 * 
//	 * @param tag
//	 *            标识
//	 * @return 带标识的Fragment
//	 */
//	public static CommendFragment newInstance(int tag) {
//		CommendFragment fragment = new CommendFragment();
//		Bundle b = new Bundle();
//		b.putInt("tag", tag);
//		fragment.setArguments(b);
//		return fragment;
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		Bundle b = getArguments();
//		tag = (null == b ? DEFAULT_TAG : b.getInt("tag"));
//	}
//
//	/**
//	 * 解析布局
//	 */
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		View view = inflater.inflate(R.layout.fragment_com, container, false);
//		tv = (TextView) view.findViewById(R.id.orderTextview1);
//		tv.setText(tv.getText().toString() + tag);
//
//		return view;
//	}
//
//	@Override
//	public void onActivityCreated(Bundle savedInstanceState) {
//		super.onActivityCreated(savedInstanceState);
//
//	}
//}
