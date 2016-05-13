package com.loongme.activity;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.loongme.activity.R;
import com.loongme.base.BaseActivity;
import com.loongme.util.DeviceUtil;
import com.loongme.util.Spilt;
import com.loongme.util.SsbService;

public class OrderManagerActivity extends BaseActivity implements
		TabContentFactory {
	// 显示服务者订单的hanlder编号
	private final int SHOW_ACCEPT = 10;
	// 显示发布者订单的handler编号
	private final int SHOW_SUBS = 11;
	// 显示listview的handler编号
	private final int SHOW = 12;
	// 显示服务者无订单的编号
	private final int NO_ACCEPT = 13;
	// 显示发布者无订单的编号
	private final int NO_SUBS = 14;
	// 服务器连接失败
	private final int FAIL = 20;
	// tabhost
	private static TabHost tbProductHost;
	// 滑动手势
	private GestureDetector detector;
	// tab widget水平滑动条
	private HorizontalScrollView hScroller;
	private int screenWidth;// 屏幕宽度 单位：dp

	private TabHost tabhost;
	private ListView lv;
	private ListView lv2;
	// 定义一个动态数组
	ArrayList<HashMap<String, Object>> listItem;
	// 显示服务者订单的动态数组
	ArrayList<HashMap<String, Object>> acceptListItem;
	// 显示发布者订单的动态数组
	ArrayList<HashMap<String, Object>> subsListItem;
	// 网络服务
	SsbService ssbservice = new SsbService();
	// 获取服务返回的字符串
	private String getFromSerciceMsg;

	private String PisOperTradeOrder;
	private String IMEI;
	private final String TAG = "OrderManager";

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_order_manager);
		getOrderThread get = new getOrderThread();
		get.start();
		//
		initLayout();

		// SimpleAdapter adapter = new SimpleAdapter(this, getData(),
		// R.layout.item_order, new String[] { "title", "info", "img" },
		// new int[] { R.id.order_title, R.id.order_content,
		// R.id.order_style });
		// lv.setListAdapter(adapter);
	}

	/**
	 * 自定义
	 */
	private void initLayout1() {
		setContentView(R.layout.tabhost_order);
		// 初始化tabhost
		tbProductHost = (TabHost) findViewById(R.id.tabhost_order);
		tbProductHost.setup();

		// tbProductHost
		// .setup(OrderManagerActivity.this.getLocalActivityManager());
		// 获取手机屏幕的宽高
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int screenWidth = px2dip(OrderManagerActivity.this,
				displayMetrics.widthPixels);// dp
		// 初始化TabHost，根据arr
		for (int i = 0; i < 10; i++) {
			View view = LayoutInflater.from(OrderManagerActivity.this).inflate(
					R.layout.tab_button_order, null);
			TextView tView = (TextView) view
					.findViewById(R.id.order_tabs_tabHost);
			tView.setText("tab" + i);
			tbProductHost.addTab(tbProductHost.newTabSpec("tab" + i)
					.setIndicator(view).setContent(OrderManagerActivity.this));
			updateTab(tabhost);// 调用方法设置tabWidget选项卡的颜色
			tbProductHost.setOnTabChangedListener(new OnTabChangedListener());
		}
		// hScroller = (HorizontalScrollView) findViewById(R.id.hScroller_scan);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 更新Tab标签的颜色，和字体的颜色
	 * 
	 * @param tabHost
	 */
	private void updateTab(final TabHost tabHost) {
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			View view = tabHost.getTabWidget().getChildAt(i);
			TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i)
					.findViewById(R.id.order_tabs_tabHost);
			tv.setTextSize(16);
			tv.setTypeface(Typeface.SERIF, 2); // 设置字体和风格
			if (tabHost.getCurrentTab() == i) {// 选中
				view.setBackgroundColor(getResources().getColor(R.color.red));// 选中后的背景
																				// #eb037f
				tv.setTextColor(this.getResources().getColorStateList(
						android.R.color.black));
			} else {// 不选中
				view.setBackgroundColor(getResources().getColor(R.color.yellow));// 非选择的背景
																					// #f8c514
				tv.setTextColor(this.getResources().getColorStateList(
						android.R.color.white));
			}
		}
	}

	class OnTabChangedListener implements OnTabChangeListener {
		@Override
		public void onTabChanged(String tabId) {
			tbProductHost.setCurrentTabByTag(tabId);
			System.out.println("tabid " + tabId);
			System.out.println("curreny after: "
					+ tbProductHost.getCurrentTabTag());
			updateTab(tbProductHost);
		}
	}

	public View createTabContent(String arg0) {
		// 初始化tabHost里面某一个选项卡的内容，可以通过Inflater来加载已经定义好的xml布局文件
		// to-do
		return null;
	}

	public void flingLeft() {
		// 切换选项卡
		int currentTab = tbProductHost.getCurrentTab();
		if (currentTab != 0) {
			currentTab--;
			switchTab(currentTab);
		}
		// 水平滑动
		hScrollManagment(true, currentTab);
	}

	public void flingRight() {
		// 切换选项卡
		int currentTab = tbProductHost.getCurrentTab();
		if (currentTab != tbProductHost.getTabWidget().getChildCount()) {
			currentTab++;
			switchTab(currentTab);
		}
		// 水平滑动
		hScrollManagment(false, currentTab);
	}

	// 用于在切换选项卡时自动居中所选中的选项卡的位置
	private void hScrollManagment(boolean isToLeft, int currentTab) {
		int count = tbProductHost.getTabWidget().getChildCount();
		System.out.println("000111:hScrollManagment count=" + count);
		if (179 * count > screenWidth) {
			int nextPosX = (int) (currentTab + 0.5) * 179 - screenWidth / 2;// 此处的179可以自行修改
			// hScroller.scrollTo(nextPosX, 0);
			hScroller.smoothScrollTo(nextPosX, 0);
		}
	}

	private static void switchTab(final int toTab) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				tbProductHost.post(new Runnable() {
					@Override
					public void run() {
						tbProductHost.setCurrentTab(toTab);
					}
				});
			}
		}).start();
	}

	// public boolean onTouchEvent(MotionEvent event) {
	// return this.detector.onTouchEvent(event);
	// }
	//
	// public boolean onDown(MotionEvent arg0) {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
	// float velocityY) {
	// if (e1.getX() - e2.getX() < -120) {
	// flingLeft();
	// return true;
	// } else if (e1.getX() - e2.getX() > 120) {
	// flingRight();
	// return true;
	// }
	//
	// return false;
	// }
	//
	// public void onLongPress(MotionEvent e) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
	// float distanceY) {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// public void onShowPress(MotionEvent e) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// public boolean onSingleTapUp(MotionEvent e) {
	// // TODO Auto-generated method stub
	// return false;
	// }

	/**
	 * 初始化布局
	 */
	private void initLayout() {
		this.findViewById(R.id.btn_back).setVisibility(View.VISIBLE);
		this.findViewById(R.id.btn_setting).setVisibility(View.GONE);
		this.findViewById(R.id.btn_back).setOnClickListener(
				new OnClickListener() {
					public void onClick(View arg0) {
						finish();
					}
				});
		// TextView title = (TextView) this.findViewById(R.id.title);
		// title.setText("订单管理");
		// ((TextView) this.findViewById(R.id.title)).setText("订单管理");
		/* tabhost的设置 */
		tabhost = (TabHost) findViewById(R.id.hostOrder);
		tabhost.setup();
		tabhost.addTab(tabhost.newTabSpec("服务的订单").setContent(R.id.tab_order_1)
				.setIndicator("服务的订单"));
		tabhost.addTab(tabhost.newTabSpec("发布的订单").setContent(R.id.tab_order_2)
				.setIndicator("发布的订单"));
		// tabhost.addTab(tabhost.newTabSpec("未服务").setContent(R.id.tab_order_3)
		// .setIndicator("未服务"));
		// tabhost.addTab(tabhost.newTabSpec("未确认").setContent(R.id.tab_order_4)
		// .setIndicator("未确认"));
		// tabhost.addTab(tabhost.newTabSpec("未收款").setContent(R.id.tab_order_5)
		// .setIndicator("未收款"));
		// 设置tabwidget的高度
		TabWidget tabwidget = tabhost.getTabWidget();
		tabwidget.getLayoutParams().height = 100;
		for (int i = 0; i < tabwidget.getChildCount(); i++) {
			tabwidget.getChildAt(i).getLayoutParams().height = 60;
			final TextView tv1 = (TextView) tabwidget.getChildAt(i)
					.findViewById(android.R.id.title);
			tv1.setGravity(android.view.Gravity.TOP);
		}
	}

	@SuppressLint("HandlerLeak")
	Handler mhandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case FAIL:
				Toast.makeText(OrderManagerActivity.this, "服务器连接失败",
						Toast.LENGTH_LONG).show();
				break;
			case SHOW_ACCEPT:
				showAcceptList();
				break;
			case SHOW_SUBS:
				showSubsList();
				break;
			case NO_ACCEPT:
				findViewById(R.id.orderTextview1).setVisibility(View.VISIBLE);
				break;
			case NO_SUBS:
				findViewById(R.id.orderTextview2).setVisibility(View.VISIBLE);
				break;
			}
		}
	};

	// protected void onListItemClick(ListView l, View v, int position, long id)
	// {
	//
	// Log.v("MyListView4-click", (String)mData.get(position).get("title"));
	// }

	/**
	 * 新建一个类继承BaseAdapter，实现视图与数据的绑定 服务者-商家
	 */
	private class MyAdapter extends BaseAdapter {
		private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局

		/** 构造函数 */
		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			if (null == acceptListItem) {
				return 0;
			} else {
				return acceptListItem.size();// 返回数组的长度
			}
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder;
			// 观察convertView随ListView滚动情况
			Log.v("MyListViewBase", "getView " + position + " " + convertView);
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.item_order, null);
				holder = new ViewHolder();
				/** 得到各个控件的对象 */
				holder.title = (TextView) convertView
						.findViewById(R.id.order_title);
				holder.text = (TextView) convertView
						.findViewById(R.id.order_content);
				holder.bt = (Button) convertView
						.findViewById(R.id.order_button);
				holder.cancelBt = (Button) convertView
						.findViewById(R.id.order_cancel);
				holder.status = (TextView) convertView
						.findViewById(R.id.order_status);
				convertView.setTag(holder);// 绑定ViewHolder对象
			} else {
				holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
			}
			if (null != acceptListItem) {
				findViewById(R.id.orderTextview1).setVisibility(View.GONE);
				String status = acceptListItem.get(position).get("Status")
						.toString().trim();
				// 先设置按钮监听再设置setClikable
				/** 为Button添加点击事件 */
				holder.bt.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Log.v("MyListViewBase", "你点击了按钮" + position);
						sendPisOper(acceptListItem.get(position).get("Title")
								.toString().trim(), changAddOne(acceptListItem
								.get(position).get("Status").toString().trim()));
					}
				});
				// "取消"按钮设置监听
				holder.cancelBt.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {
						sendPisOper(acceptListItem.get(position).get("Title")
								.toString().trim(), "0");
					}
				});

				/** 设置TextView显示的内容，即我们存放在动态数组中的数据 */
				holder.title.setText(acceptListItem.get(position).get("Title")
						.toString());
				if (status.equals("2")) {
					// holder.text.setText("已接单");
					holder.status.setText("已接单");
					holder.bt.setText("服务");
					holder.bt.setVisibility(View.VISIBLE);
				} else if (status.equals("1")) {
					// holder.text.setText("已发布服务");
					holder.status.setText("已发布服务");
					holder.bt.setText("");
					holder.bt.setVisibility(View.GONE);
				} else if (status.equals("0")) {
					// holder.text.setText("已服务");
					holder.status.setText("已服务");
					holder.bt.setVisibility(View.GONE);
				} else {
					// holder.text.setText("未知");
					holder.status.setText("未知");
					holder.bt.setVisibility(View.GONE);
				}
				return convertView;
			} else {
				return convertView;
			}
		}
	}

	/**
	 * 点击按钮发送信息到服务器
	 * 
	 * @param id
	 *            订单编号
	 * 
	 * @param type
	 *            发送类型
	 */
	private void sendPisOper(String id, String type) {
		PisOperTradeOrder = "Pis_Oper_Trade_Order {COMM_INFO {BUSI_CODE 10011} {REGION_ID A} {COUNTY_ID A00} {OFFICE_ID 22342} {OPERATOR_ID 43643} {CHANNEL A2} {OP_MODE SUBMIT}}"
				+ " {{MESSAGE_ID " + id + "} {OPER_TYPE " + type + "}}";
		boolean is = false;
		Toast.makeText(OrderManagerActivity.this, "正在发送，请稍候",
				Toast.LENGTH_SHORT).show();
		try {
			ssbservice.connecttoserver();
			if (ssbservice.getSocket().isConnected()) {
				ssbservice.SendMsg(ssbservice.getSocket(), PisOperTradeOrder);
				is = true;
			}
			String reciever = ssbservice.ReceiveMsg(ssbservice.getSocket());
			Log.i(TAG, reciever);
			System.out.println("发送Pis_Oper_Trade_Order返回信息：" + reciever);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (is) {
			Toast.makeText(OrderManagerActivity.this, "发送成功", Toast.LENGTH_LONG)
					.show();
		} else {
			Toast.makeText(OrderManagerActivity.this, "服务器繁忙",
					Toast.LENGTH_LONG).show();
		}
	}

	private String changAddOne(String msg) {
		if (msg.equals("2")) {
			return "3";
		} else if (msg.equals("3")) {
			return "4";
		} else if (msg.equals("4")) {
			return "5";
		} else if (msg.equals("5")) {
			return "6";
		} else if (msg.equals("6")) {
			return "7";
		} else if (msg.equals("7")) {
			return "8";
		} else {
			return null;
		}
	}

	/**
	 * 新建一个类继承BaseAdapter，实现视图与数据的绑定 发布者-用户
	 */
	private class MyAdapter2 extends BaseAdapter {
		private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局

		/** 构造函数 */
		public MyAdapter2(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			if (null == subsListItem) {
				return 0;
			} else {
				return subsListItem.size();// 返回数组的长度
			}
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		/** 书中详细解释该方法 */
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder;
			// 观察convertView随ListView滚动情况
			Log.v("MyListViewBase", "getView " + position + " " + convertView);
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.item_order, null);
				holder = new ViewHolder();
				/** 得到各个控件的对象 */
				holder.title = (TextView) convertView
						.findViewById(R.id.order_title);
				holder.text = (TextView) convertView
						.findViewById(R.id.order_content);
				holder.bt = (Button) convertView
						.findViewById(R.id.order_button);
				holder.status = (TextView) convertView
						.findViewById(R.id.order_status);
				convertView.setTag(holder);// 绑定ViewHolder对象
			} else {
				holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
			}
			if (null != subsListItem) {
				findViewById(R.id.orderTextview1).setVisibility(View.GONE);
				// 先设置按钮监听再设置setClickale
				/** 为Button添加点击事件 */
				holder.bt.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Log.v("MyListViewBase", "你点击了按钮" + position);// 打印Button的点击信息
						sendPisOper(subsListItem.get(position).get("Title")
								.toString(),
								subsListItem.get(position).get("Status")
										.toString());
					}
				});
				/** 设置TextView显示的内容，即我们存放在动态数组中的数据 */
				holder.title.setText(subsListItem.get(position).get("Title")
						.toString());
				String status = subsListItem.get(position).get("Status")
						.toString();
				if (status.equals("2")) {
					// holder.text.setText("已接单");
					holder.status.setText("已接单");
					holder.bt.setText("呼叫商家服务");
					holder.bt.setVisibility(View.VISIBLE);
					holder.bt.setClickable(true);
				} else if (status.equals("3")) {
					// holder.text.setText("已发布服务");
					holder.status.setText("已发布服务");
					holder.bt.setText("等待商家服务");
					holder.bt.setClickable(false);
				} else if (status.equals("4")) {
					// holder.text.setText("已建立联系");
					holder.status.setText("已建立联系");
					holder.bt.setText("确认服务");
					holder.bt.setVisibility(View.VISIBLE);
					holder.bt.setClickable(true);
				} else if (status.equals("5")) {
					// holder.text.setText("已建立联系");
					holder.status.setText("已建立联系");

				} else if (status.equals("6")) {
					// holder.text.setText("已建立联系");
					holder.status.setText("已建立联系");

				} else if (status.equals("7")) {
					// holder.text.setText("已建立联系");
					holder.status.setText("已建立联系");

				} else {
					holder.text.setText("未知");
					holder.bt.setVisibility(View.GONE);
					holder.bt.setClickable(true);
				}

				return convertView;
			} else {
				return convertView;
			}

		}
	}

	/**
	 * item点击监听
	 * 
	 * @author Administrator
	 * 
	 */
	OnItemClickListener onItemClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Log.v("MyListViewBase", "你点击了ListView条目" + arg2);// 在LogCat中输出信息
		}
	};

	class updateThread extends Thread {
		public void run() {

		}
	}

	public final class ViewHolder {
		public TextView title;
		public TextView status;
		public TextView text;
		public Button bt;
		public Button cancelBt;
	}

	/**
	 * 向服务器调用查询接口
	 * 
	 * @author Administrator
	 * 
	 */
	class getOrderThread extends Thread {

		public void run() {
			getIMEINum();
			String getOrderMsg = "Pis_Query_Trade_Order  {COMM_INFO {BUSI_CODE 10011} {REGION_ID A} {COUNTY_ID A00} "
					+ "{OFFICE_ID 22342} {OPERATOR_ID 43643} {CHANNEL A2} {OP_MODE SUBMIT}} {{WXOPEN_ID "
					+ IMEI + "} }";
			try {
				// 等待，接收来自服务器返回的消息
				Thread.sleep(1000);
				ssbservice.connecttoserver();
				System.out.println("run getOrderThread");
				if (ssbservice.getSocket().isConnected()) {
					// 发送信息到服务器
					ssbservice.SendMsg(ssbservice.getSocket(), getOrderMsg);
				} else {
					Message message = new Message();
					message.what = FAIL;
					mhandler.sendMessage(message);
				}
				// 获取服务器返回信息
				getFromSerciceMsg = ssbservice.ReceiveMsg(ssbservice
						.getSocket());
				Log.i(TAG, "服务器返回信息" + getFromSerciceMsg);
				System.out.println("服务器返回信息" + getFromSerciceMsg);
				if (getFromSerciceMsg != null) {
					// 解析字符串
					Spilt sp = new Spilt();
					acceptListItem = sp.getAcceptList(getFromSerciceMsg);
					subsListItem = sp.getSubsList(getFromSerciceMsg);
					if (null == acceptListItem) {
						Message msgAfterGet = new Message();
						msgAfterGet.what = NO_ACCEPT;
						mhandler.sendMessage(msgAfterGet);
					} else {
						Message msgAfterGet = new Message();
						msgAfterGet.what = SHOW_ACCEPT;
						mhandler.sendMessage(msgAfterGet);
					}
					if (null == subsListItem) {
						Message msgAfterGet = new Message();
						msgAfterGet.what = NO_SUBS;
						mhandler.sendMessage(msgAfterGet);
					} else {
						Message msgAfterGet = new Message();
						msgAfterGet.what = SHOW_SUBS;
						mhandler.sendMessage(msgAfterGet);
					}
				}
				// writer.close();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 点击按钮后往服务器发送信息
	 * 
	 * @author Administrator
	 * 
	 */
	class sendMessage extends Thread {
		public void run() {
			getIMEINum();
			try {
				ssbservice.connecttoserver();
				if (ssbservice.getSocket().isConnected()) {
					// 发送信息到服务器
					ssbservice.SendMsg(ssbservice.getSocket(), "");
				} else {
					Message message = new Message();
					message.what = 12;
					mhandler.sendMessage(message);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 显示全部服务者订单
	 */
	private void showAcceptList() {
		// 显示全部订单的数据
		lv = (ListView) findViewById(R.id.orderManagerListView);
		MyAdapter mAdapter = new MyAdapter(this);// 得到一个MyAdapter对象
		lv.setAdapter(mAdapter);// 为ListView绑定Adapter
		/** 为ListView添加点击事件 */
		lv.setOnItemClickListener(onItemClick);
	}

	/**
	 * 显示全部发布者订单
	 */
	private void showSubsList() {
		lv2 = (ListView) findViewById(R.id.orderManagerListView2);
		MyAdapter2 mAdapter = new MyAdapter2(this);// 得到一个MyAdapter对象
		lv2.setAdapter(mAdapter);// 为ListView绑定Adapter
		/** 为ListView添加点击事件 */
		lv2.setOnItemClickListener(onItemClick);
	}

	/**
	 * 获取手机唯一识别imei码
	 */
	private void getIMEINum() {
		TelephonyManager tm = (TelephonyManager) this
				.getSystemService(TELEPHONY_SERVICE);
		// IMEI = tm.getDeviceId();
		IMEI = DeviceUtil.getDeviceId(OrderManagerActivity.this);
		if (IMEI != null) {
			if ("".equals(IMEI)) {
				IMEI = "358733050263717";
			}
		} else {
			IMEI = "358733050263717";
		}
	}

}
