package com.loongme.activity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.loongme.activity.R;
import com.loongme.base.BaseActivity;
import com.loongme.util.DeviceUtil;
import com.loongme.util.Spilt;
import com.loongme.util.SsbService;

public class BuyKeywordActivity extends BaseActivity {

	private EditText keywordEdit;
	private Button chooseBt;
	private Button keywordBt;
	private GridView gridView;
	private TextView priceView;
	private String keyword;// 输入的关键字
	private String sendMsg;// 往服务器发送的查询关键字的字符串
	private String receiveMsg;// 调用查询接口后返回的字符串
	private String[] spiltMsg;// 解析返回的字符串
	private String sendKeywordMsg;// 发送获取关键字
	private String getKeywordMsg;// 返回的关键字

	private Map<String, String> keywordMap;
	private List<HashMap<String, String>> keywordList;
	SsbService service = new SsbService();
	PopupWindow popselector;
	View popupWindow_view;
	private final static String TAG = "BuyKeywordActivity";
	private final static int AFTER_GET_PRICE = 0x11;
	String IMEI;
	private int positionp;
	private String toastMsg;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buy_keyword);
		initLayout();
		getIMEINum();
		getKeyord k = new getKeyord();
		k.start();

	}

	/**
	 * 初始化布局
	 */
	private void initLayout() {
		findViewById(R.id.btn_register).setVisibility(View.GONE);
		findViewById(R.id.btn_setting).setVisibility(View.GONE);
		findViewById(R.id.btn_back).setVisibility(View.VISIBLE);
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				finish();
			}
		});
		((TextView) findViewById(R.id.tv_title)).setText("购买关键字");
		keywordEdit = (EditText) findViewById(R.id.buyKeyword_input);
		keywordBt = (Button) findViewById(R.id.buyKeyword_button);
		chooseBt = (Button) findViewById(R.id.buyKeyword_choose);
		priceView = (TextView) findViewById(R.id.buyKeyword_price);
		keywordBt.setOnClickListener(onclick);
		chooseBt.setOnClickListener(onclick);
	}

	/**
	 * 设置按键监听
	 */
	OnClickListener onclick = new OnClickListener() {
		public void onClick(View a) {
			switch (a.getId()) {
			case R.id.buyKeyword_button:
				if (chooseBt.getText().toString().equals("点击选择关键字")) {
					Toast.makeText(BuyKeywordActivity.this, "请选择关键字", Toast.LENGTH_LONG).show();
					break;
				}

				if (null != spiltMsg && null != spiltMsg[0] && null != spiltMsg[1] && null != spiltMsg[2]) {
					Intent i = new Intent();
					Bundle bundle = new Bundle();
					bundle.putStringArray("order", spiltMsg);
					i.putExtra("order", spiltMsg);
					i.putExtras(bundle);
					i.setClass(BuyKeywordActivity.this, PayAlipayActivity.class);
					startActivity(i);
					finish();
				} else {
					Toast.makeText(BuyKeywordActivity.this, "网络繁忙", Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.buyKeyword_choose:
				initPopWindows(a);
				break;
			}
		}
	};

	/**
	 * 
	 */
	OnItemClickListener itemClick = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
			for (int i = 0; i < parent.getCount(); i++) {
				View v = parent.getChildAt(i);
				if (position == i) {// 当前选中的Item改变背景颜色
					// arg1.setBackgroundResource(R.drawable.bottombg_h);
				} else {
					// v.setBackgroundResource(R.drawable.portal_navigation_1bottom);
				}
			}

		}
	};

	private void getIMEINum() {
		IMEI = DeviceUtil.getDeviceId(BuyKeywordActivity.this);
		if (IMEI != null) {
			if ("".equals(IMEI)) {
				IMEI = "358733050263717";
			}
		} else {
			IMEI = "358733050263717";
		}
	}

	/**
	 * 获取关键字列表的线程
	 * 
	 * @author LI
	 * 
	 */
	class getKeyord extends Thread {
		public void run() {
			try {
				service.connecttoserver();
				sendKeywordMsg = "Pis_Career_Type {COMM_INFO {BUSI_CODE 10011} {REGION_ID A} {COUNTY_ID A00} {OFFICE_ID 22342} {OPERATOR_ID 43643} {CHANNEL A2} {OP_MODE SUBMIT} } { {WXOPEN_ID "
						+ IMEI + " } }";
				System.out.println("getKeyord run thread");
				// 发送信息到服务器
				if (service.getSocket().isConnected()) {
					service.SendMsg(service.getSocket(), sendKeywordMsg);
					// Log.i(TAG, sendKeywordMsg);
				}
				// 接收服务器返回的信息
				getKeywordMsg = service.ReceiveMsg(service.getSocket());
				// Log.i(TAG, getKeywordMsg);
				Spilt sp = new Spilt();
				keywordList = sp.spiltKeywordList(getKeywordMsg, TAG);
				// for (int i2 = 0; i2 < keywordList.size(); i2++) {
				// Map<String, String> mapString = keywordList.get(i2);
				// System.out.println(mapString.get("key") + " ==");
				// System.out.println(mapString.get("value"));
				// }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e(TAG, e.toString());
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(TAG, e.toString());
			}
		}
	}

	/**
	 * 获取关键字价格的线程
	 * 
	 * @author LI
	 * 
	 */
	class getKeywordPrice extends Thread {
		public void run() {
			try {
				service.connecttoserver();
				System.out.println("getKeyordPrice run thread");
				// 发送信息到服务器
				if (service.getSocket().isConnected()) {
					service.SendMsg(service.getSocket(), sendMsg);
					Log.i(TAG, sendMsg);
				}
				// 接收服务器返回的信息
				receiveMsg = service.ReceiveMsg(service.getSocket());
				if (receiveMsg != null && receiveMsg.contains("{DETAIL")) {
					Spilt sp = new Spilt();
					System.out.println("关键字价格返回信息" + receiveMsg);
					spiltMsg = sp.spiltKeywordOrder(receiveMsg, TAG);
					Message afterPrice = new Message();
					afterPrice.what = AFTER_GET_PRICE;
					myHandeler.sendMessage(afterPrice);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				Log.e(TAG, e1.toString());
			} catch (Exception e1) {
				e1.printStackTrace();
				Log.e(TAG, e1.toString());
			}
		}
	}

	Handler myHandeler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case AFTER_GET_PRICE:
				priceView.setText("价格：" + spiltMsg[2]);
				break;
			}
		};
	};

	/**
	 * 初始化关键字列表弹窗
	 * 
	 * @param parent
	 */
	@SuppressLint("InflateParams")
	@SuppressWarnings("deprecation")
	private void initPopWindows(View parent) {
		if (popselector == null) {
			int temp;
			temp = this.getWindowManager().getDefaultDisplay().getWidth();
			int screenWidth = temp * 7 / 8;

			int screenHeight = this.getWindowManager().getDefaultDisplay().getHeight() * 3 / 4;
			LayoutInflater mLayoutInflater = LayoutInflater.from(this);
			popupWindow_view = mLayoutInflater.inflate(R.layout.popwindow_keyword, null);
			popselector = new PopupWindow(popupWindow_view, screenWidth, screenHeight, true);
		}
		ColorDrawable cd = new ColorDrawable(0x000000);
		popselector.setBackgroundDrawable(cd);
		// 产生背景变暗效果
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.4f;
		getWindow().setAttributes(lp);
		popselector.setOutsideTouchable(true);
		popselector.setFocusable(true);
		popselector.showAtLocation(this.findViewById(R.id.linearlayout_keyword), Gravity.CENTER, 0, 0);
		popselector.update();
		popselector.setOnDismissListener(new OnDismissListener() {
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
			}
		});
		gridView = (GridView) popupWindow_view.findViewById(R.id.keyword_gridView);
		GridViewAdapter adapter = new GridViewAdapter(BuyKeywordActivity.this, keywordList);
		gridView.setAdapter(adapter);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));// 去除选中效果
		gridView.setOnItemClickListener(itemClick);
	}

	class GridViewAdapter extends BaseAdapter {

		private List<HashMap<String, String>> list;
		// private GridViewItem tempGridViewItem;
		private LayoutInflater layoutInflater;

		public GridViewAdapter(Context context, List<HashMap<String, String>> list) {
			this.list = (List<HashMap<String, String>>) list;
			layoutInflater = LayoutInflater.from(context);
		}

		/**
		 * 数据总数
		 */
		public int getCount() {
			if (null == list) {
				return 0;
			} else {
				return list.size();
			}
		}

		/**
		 * 获取当前数据
		 */
		public Object getItem(int position) {
			// return list.get(position);
			return null;
		}

		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			positionp = position;
			ViewHolder holder;
			if (convertView == null) {
				convertView = layoutInflater.inflate(R.layout.item_keyword, null);
				holder = new ViewHolder();
				// /** 得到各个控件的对象 */
				holder.textView = (TextView) convertView.findViewById(R.id.keyword_item_textView);
				holder.button = (Button) convertView.findViewById(R.id.keyword_item_button);
				convertView.setTag(holder);// 绑定ViewHolder对象
			} else {
				holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
			}
			if (null != list) {
				toastMsg = list.get(position).get("value");
				holder.textView.setText(list.get(position).get("value"));
				holder.button.setText(list.get(position).get("value"));
				holder.button.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {
						Toast.makeText(BuyKeywordActivity.this, "" + position, Toast.LENGTH_SHORT).show();
					}
				});
				holder.textView.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {
						popselector.dismiss();
						chooseBt.setText(list.get(position).get("value"));
						keyword = list.get(position).get("value");
						sendMsg = "Pis_Search_Price {COMM_INFO {BUSI_CODE 10011} {REGION_ID A} {COUNTY_ID A00} {OFFICE_ID 22342} {OPERATOR_ID 43643} {CHANNEL A2} {OP_MODE SUBMIT} } { {WXOPEN_ID "
								+ IMEI + " } {PRODUCT " + keyword + "} }";
						getKeywordPrice k = new getKeywordPrice();
						k.start();
					}
				});
			}
			return convertView;
		}
	}

	public final class ViewHolder {
		TextView textView;
		Button button;
	}
}
