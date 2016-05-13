package com.loongme.view;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loongme.activity.R;
import com.loongme.util.DeviceUtil;
import com.loongme.util.Spilt;
import com.loongme.util.SsbService;

public class SubsFragment extends Fragment {
	private final int FAIL = -1;
	private final int SHOW_SUBS = 1;
	private final int NO_SUBS = 2;
	private final String TAG = "SubsFragment";
	// 网络服务
	SsbService ssbservice = new SsbService();
	private String IMEI;
	// 点击按钮发送到服务器的信息
	private String PisOperTradeOrder;
	// 向服务器发送信息返回的数组
	ArrayList<HashMap<String, Object>> subsListItem;
	View view;
	ListView lv;
	// 判断是否需要刷新
	boolean isFresh = false;

	public boolean getIsFresh() {
		return isFresh;
	}

	public void setIsFresh(boolean isFresh) {
		this.isFresh = isFresh;
	}

	public void onDestroy() {
		Log.i(TAG, "onDestroy");
		super.onDestroy();
	}

	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
	}

	public void onLowMemory() {
		Log.i(TAG, "onLowMemory");
		super.onLowMemory();
	}

	public void onStop() {
		Log.i(TAG, "onStop");
		super.onStop();
	}

	public void onPause() {
		Log.i(TAG, "onPause");
		super.onPause();
	}

	public void onResume() {
		Log.i(TAG, "onResume");
		super.onResume();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "onCreateView");
		view = inflater.inflate(R.layout.fragment_subs, container, false);
		getSubsOrderThread get = new getSubsOrderThread();
		get.start();
		return view;
	}

	class getSubsOrderThread extends Thread {
		public void run() {
			getIMEINum();
			String getOrderMsg = "Pis_Query_Trade_Order  {COMM_INFO {BUSI_CODE 10011} {REGION_ID A} {COUNTY_ID A00} "
					+ "{OFFICE_ID 22342} {OPERATOR_ID 43643} {CHANNEL A2} {OP_MODE SUBMIT}} {{WXOPEN_ID "
					+ IMEI + "} }";
			try {
				ssbservice.connecttoserver();
				System.out.println("run getSubsOrderThread");
				if (ssbservice.getSocket().isConnected()) {
					// 发送信息到服务器
					ssbservice.SendMsg(ssbservice.getSocket(), getOrderMsg);
				} else {
					Message message = new Message();
					message.what = FAIL;
					mhandler.sendMessage(message);
				}
				// 获取服务器返回信息
				String getFromSerciceMsg = ssbservice.ReceiveMsg(ssbservice
						.getSocket());
				Log.i(TAG, "服务器返回信息" + getFromSerciceMsg);
				System.out.println("服务器返回信息" + getFromSerciceMsg);
				if (getFromSerciceMsg != null) {
					// 解析字符串
					Spilt sp = new Spilt();
					subsListItem = sp.getSubsList(getFromSerciceMsg);
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
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	Handler mhandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case FAIL:
				Toast.makeText(getActivity(), "服务器连接失败", Toast.LENGTH_LONG)
						.show();
				break;
			case SHOW_SUBS:
				showAcceptList();
				break;
			case NO_SUBS:
				view.findViewById(R.id.orderTextview2).setVisibility(
						View.VISIBLE);
				break;
			}
		}
	};

	/**
	 * 新建一个类继承BaseAdapter，实现视图与数据的绑定 发布者-用户
	 */
	class MyAdapter extends BaseAdapter {
		private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局

		/** 构造函数 */
		public MyAdapter(Context context) {
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
			if (null != subsListItem) {
				view.findViewById(R.id.orderTextview2).setVisibility(View.GONE);
				String status = subsListItem.get(position).get("Status")
						.toString().trim();
				// 先设置按钮监听再设置setClikable
				/** 为Button添加点击事件 */
				holder.bt.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Log.v("MyListViewBase", "你点击了按钮" + position);
						sendPisOper(subsListItem.get(position).get("Title")
								.toString().trim(), subsListItem.get(position)
								.get("Status").toString().trim());
						freshInterface();
						isFresh = true;
					}
				});
				// 取消按钮设置监听
				holder.cancelBt.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {
						new AlertDialog.Builder(getActivity())
								.setTitle("确认要作废吗？")
								.setPositiveButton("是",
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface arg0,
													int arg1) {
												sendPisOper(
														subsListItem
																.get(position)
																.get("Title")
																.toString()
																.trim(), "0");
												freshInterface();
												isFresh = true;
											}
										}).setNegativeButton("否", null).show();

					}
				});

				/** 设置TextView显示的内容，即我们存放在动态数组中的数据 */
				holder.title.setText(subsListItem.get(position).get("Title")
						.toString());
				if (status.equals("1")) {
					holder.status.setText("已接单");
					holder.bt.setClickable(true);
				} else if (status.equals("2")) {
					// holder.text.setText("已接单");
					// holder.bt.setText("呼叫商家服务");
					holder.status.setText("已推送");
					holder.bt.setVisibility(View.VISIBLE);
					holder.bt.setBackground(getResources().getDrawable(
							R.drawable.btn_white_press));
					holder.bt.setClickable(false);
				} else if (status.equals("3")) {
					// holder.text.setText("已发布服务");
					// holder.bt.setText("等待商家服务");
					holder.status.setText("已发布服务");
					holder.bt.setClickable(true);
				} else if (status.equals("4")) {// 用户等待商家服务
					// holder.text.setText("已建立联系");
					// holder.bt.setText("确认服务");
					holder.status.setText("已建立联系");
					holder.bt.setVisibility(View.VISIBLE);
					holder.bt.setBackground(getResources().getDrawable(
							R.drawable.btn_white_press));
					holder.bt.setClickable(false);
				} else if (status.equals("5")) {// 用户评价
					// holder.text.setText("已建立联系");
					holder.status.setText("已确认服务");
					holder.bt.setClickable(true);
				} else if (status.equals("6")) {// 已经评价。流程结束
					// holder.text.setText("已建立联系");
					// holder.status.setText("已建立联系");
					holder.status.setText("订单已完成");
					holder.bt.setVisibility(View.GONE);
				} else if (status.equals("7")) {
					// holder.text.setText("已建立联系");
					holder.status.setText("订单已完成");
					holder.bt.setVisibility(View.GONE);
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
	 * 点击按钮发送信息到服务器
	 * 
	 * @param id
	 *            订单编号
	 * 
	 * @param type
	 *            发送类型，当前状态
	 */
	private void sendPisOper(String id, String type) {
		// 向服务器发送下一状态的信息，如果是0则返回0
		String s = changAddOne(type);
		PisOperTradeOrder = "Pis_Oper_Trade_Order {COMM_INFO {BUSI_CODE 10011} {REGION_ID A} {COUNTY_ID A00} {OFFICE_ID 22342} {OPERATOR_ID 43643} {CHANNEL A2} {OP_MODE SUBMIT}}"
				+ " {{MESSAGE_ID " + id + "} {OPER_TYPE " + s + "}}";
		boolean is = false;
		// Toast.makeText(getActivity(), "正在发送，请稍候", Toast.LENGTH_SHORT).show();
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
			Toast.makeText(getActivity(), "发送成功", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(getActivity(), "服务器繁忙", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 显示全部发布者订单
	 */
	private void showAcceptList() {
		// 显示全部订单的数据
		lv = (ListView) view.findViewById(R.id.orderManagerListView2);
		MyAdapter mAdapter = new MyAdapter(getActivity());// 得到一个MyAdapter对象
		lv.setAdapter(mAdapter);// 为ListView绑定Adapter
		/** 为ListView添加点击事件 */
		// lv.setOnItemClickListener(onItemClick);
	}

	/**
	 * 获取手机唯一识别imei码
	 */
	private void getIMEINum() {
		// TelephonyManager tm = (TelephonyManager) getActivity()
		// .getSystemService(Context.TELEPHONY_SERVICE);
		// IMEI = tm.getDeviceId();
		IMEI = DeviceUtil.getDeviceId(getActivity());
		if (IMEI != null) {
			if ("".equals(IMEI)) {
				IMEI = "358733050263717";
			}
		} else {
			IMEI = "358733050263717";
		}
	}

	/**
	 * 把当前状态改到下一状态
	 * 
	 * @param msg
	 *            当前状态
	 * @return 下一状态
	 */
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
		} else if (msg.equals("0")) {
			return "0";
		} else {
			return null;
		}
	}

	/**
	 * 点击按钮之后更新一下界面
	 */
	public void freshInterface() {
		com.loongme.view.SubsFragment.getSubsOrderThread get1 = new com.loongme.view.SubsFragment.getSubsOrderThread();
		get1.start();

	}

	public final class ViewHolder {
		public TextView title;
		public TextView status;
		public TextView text;
		public Button bt;
		public Button cancelBt;
	}
}
