
package com.loongme.util;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 * 
 * @description
 * 			此类禁止修改，资源文件中引用
 * @version 1.0
 */
@SuppressLint("NewApi")
public class CustomLinearLayout extends LinearLayout {
	private List<Object> list;
	private String uri;
	
	int count;
	private BaseAdapter mAdapter;
	public void setCustomAdapter(BaseAdapter adapter){
		this.mAdapter = adapter;
		if(mAdapter == null)
			return;
		fillLinearLayout();
		//uri = url;
	}
	/**
	 * 根据适配器来画出布局
	 */
	public void fillLinearLayout() {
		remove();
		this.count = mAdapter.getCount();
		for (int i = 0; i < count; i++) {
			View v = mAdapter.getView(i, null, null);
			addView(v, i);
			v.setOnClickListener(onclickListener);
		}
	}
	
	/**
	 * 添加监听器
	 */
	OnClickListener onclickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			//System.out.println("this is uri"+uri.toString());
			/*String infService = Context.LAYOUT_INFLATER_SERVICE;  
	        LayoutInflater li;  
	        li = (LayoutInflater) getContext().getSystemService(infService); 
			WebView view = (WebView) li.inflate(R.layout.activity_webview, null);
			webv = (WebView) view.findViewById(R.id.wv_showUrl);
			webv.loadUrl(uri);*/
			
		}
	};
	/**
	 * 添加一个item
	 * */
	public void add(){
		View v = mAdapter.getView(0, null, null);
		addView(v, count);
		mAdapter.notifyDataSetChanged();
	}
	
	public void remove(){
		removeAllViews();
	}

	
	public CustomLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CustomLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomLinearLayout(Context context) {
		super(context);
	}
}
