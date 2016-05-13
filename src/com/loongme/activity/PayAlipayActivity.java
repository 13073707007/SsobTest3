package com.loongme.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.loongme.activity.R;
import com.loongme.base.BaseActivity;
import com.loongme.pay.Base64;
import com.loongme.pay.PayResult;
import com.loongme.pay.SignUtils;
import com.loongme.util.DeviceUtil;
import com.loongme.util.SsbService;

public class PayAlipayActivity extends BaseActivity {
	// 连接服务器
	SsbService service = new SsbService();
	// 信息标签
	private final String TAG = "PAY";
	// 商户PID
	public static final String PARTNER = "2088011728520190";
	// 商户收款账号
	public static final String SELLER = "zmzbs123@163.com";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALoal7W9MZY1pFCoUV1ziE7mW7cCirLfu/nDw7JGDYAzWePM4PLroOleUz1IRmUoYWgEiHXQlScDHqMgQ7gpV8Xx1jwZ5udP0TWraOxoRjIgml8YeUsSQjGcxM5WFVR2qRQ5mLb99KadczKTzjM9aV3xgfoFhf7spCjc3N7FcGyRAgMBAAECgYAj0dZ9bmrK5Iw50gvHFuOXfBMPCHg1sYZ2rk1OVNloYXndtKNfXibchAE1fGHAOqN059p0Aq+SkLEm6KDgI2T3KgbG8z5MpMd6syPThW4btnPsKvJQoM4mjdpLY+P8A1XtniujkY50P9lWJ7mo4xplDjIfr5AFQwDV+ksSp5YXOQJBAPCcG3i67HswgiLZiSDQ2A5TR/RDm9igweQbIEeaOJT0icCs1QTU1jiHLoUB2XslpgJMpho9Yxirl435iBlSSsMCQQDGAfjPgADGf/8ZX7jRJYdwfnufy8DwrMYjEnUlWHzdUp5wxyd6r7/eNnUel2i4lAB/PNbeQqARtMNAVeeGJq4bAkEAxD+vsINWCwx/uIkR7fApqVWIJNVYMhK80p7Fb2F8sRHboYnWnYAz0huAeAAQMP9+LfXyrQhQ4Xhjvus+dNjIHQJAAKidBUOtvPtMA++Xk8qGKs+g5aUt3R8WAaPcE+W5dJsDv+hJr1TKVJjlxtZGGOcVIVc4d8DM+4IYbCxc+stCJQJASRVffnskvvytFo+SezvD56eq7GqiFKDDrVQbA7NtCOK50XEap97NFxMloMzg+l2fvz6oNwJ3JIrOnquH4BPnmw==";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";

	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;
	// 商品价格
	String PRICE = "0.01";
	String IMEI;
	String ORDER_ID;
	/**
	 * subject detail price
	 */
	private String[] recieveMsg;// subject detail price

	String name;
	Button finish;

	private Handler nHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();
				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(PayAlipayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();

					finish = (Button) findViewById(R.id.pay_finish);
					// finish.setVisibility(View.VISIBLE);
					finish.setText("完成");
					finish.setVisibility(View.VISIBLE);
					finish.setOnClickListener(new OnClickListener() {
						public void onClick(View arg0) {
							finish();
						}
					});
					/* 发送信息到服务器说支付成功 */
					String sendMsg = "Pis_Buy_Keywords {COMM_INFO {BUSI_CODE 10011} {REGION_ID A} {COUNTY_ID A00} {OFFICE_ID 22342} {OPERATOR_ID 43643} {CHANNEL A2} {OP_MODE SUBMIT} } { {WXOPEN_ID "
							+ IMEI
							+ " } {PRODUCT "
							+ recieveMsg[0]
							+ "} {AMOUNT "
							+ recieveMsg[2]
							+ "} {ORDER_ID " + ORDER_ID + "} }";
					try {
						service.connecttoserver();
						if (service.getSocket().isConnected()) {
							service.SendMsg(service.getSocket(), sendMsg);
							Log.i(TAG, sendMsg);
						}
						String receiveMsg = service.ReceiveMsg(service.getSocket());
						Log.i(TAG, receiveMsg);
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(PayAlipayActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
						/* 发送信息到服务器说支付结果确认中 */
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(PayAlipayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
						// 点击返回按钮
						finish = (Button) findViewById(R.id.pay_finish);
						// finish.setVisibility(View.VISIBLE);
						finish.setText("返回");
						finish.setVisibility(View.VISIBLE);
						finish.setOnClickListener(new OnClickListener() {
							public void onClick(View arg0) {
								finish();
							}
						});
						/* 发送信息到服务器说支付失败 */
					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(PayAlipayActivity.this, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		};
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_main);
		findViewById(R.id.pay).setOnClickListener(onclick);
		getMessage();
		initLayout();

	}

	/**
	 * 获取activity传过来的信息
	 * 
	 * @return detail price keyword
	 */
	private String[] getMsg() {
		Intent itent = getIntent();
		String[] spiltMsg = itent.getStringArrayExtra("order");
		if (null != spiltMsg) {
			Log.i("order", spiltMsg.toString());
			return spiltMsg;
		} else {
			Log.i("order", "spiltMsg is null");
			return null;
		}
	}

	private void getMessage() {
		Intent itent = getIntent();
		recieveMsg = itent.getStringArrayExtra("order");
		if (null == recieveMsg) {
			Log.e("order", "spiltMsg is null");
		}
	}

	private void initLayout() {
		((TextView) findViewById(R.id.product_subject)).setText(recieveMsg[0]);
		((TextView) findViewById(R.id.product_detail)).setText(recieveMsg[1]);
		((TextView) findViewById(R.id.product_price)).setText(recieveMsg[2]);
		getIMEINum();
	}

	OnClickListener onclick = new OnClickListener() {

		public void onClick(View arg0) {

			// 订单
			String orderInfo = getOrderInfo(recieveMsg[0], recieveMsg[1], recieveMsg[2]);// PRICE
			Log.i(TAG, orderInfo);
			System.out.println("orderInfo :::::::" + orderInfo);
			// 对订单做RSA 签名
			// String sign1 = sign(orderInfo);
			String sign = sign1(orderInfo, RSA_PRIVATE);
			Log.i(TAG, sign + "");
			System.out.print("sign :::::::" + sign);
			try {
				// 仅需对sign 做URL编码
				sign = URLEncoder.encode(sign, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			// 完整的符合支付宝参数规范的订单信息
			final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
			Log.i(TAG, payInfo);
			Runnable payRunnable = new Runnable() {
				public void run() {
					// 构造PayTask 对象
					PayTask alipay = new PayTask(PayAlipayActivity.this);
					// 调用支付接口，获取支付结果
					String result = alipay.pay(payInfo);

					Message msg = new Message();
					msg.what = SDK_PAY_FLAG;
					msg.obj = result;
					nHandler.sendMessage(msg);
				}
			};

			// 必须异步调用
			Thread payThread = new Thread(payRunnable);
			payThread.start();
		}
	};

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	// public void pay(View v) {
	// // 订单
	// String orderInfo = getOrderInfo(getString(R.string.goods_name),
	// getString(R.string.goods_detail), "0.01");
	// Log.i(TAG, orderInfo);
	// System.out.print("orderInfo :::::::" + orderInfo);
	// // 对订单做RSA 签名
	// String sign = sign(orderInfo);
	// Log.i(TAG, sign);
	// System.out.print("sign :::::::" + sign);
	// try {
	// // 仅需对sign 做URL编码
	// sign = URLEncoder.encode(sign, "UTF-8");
	// } catch (UnsupportedEncodingException e) {
	// e.printStackTrace();
	// }
	// // 完整的符合支付宝参数规范的订单信息
	// final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
	// + getSignType();
	// Log.i(TAG, payInfo);
	// Runnable payRunnable = new Runnable() {
	//
	// public void run() {
	// // 构造PayTask 对象
	// PayTask alipay = new PayTask(PayAlipayActivity.this);
	// // 调用支付接口，获取支付结果
	// String result = alipay.pay(payInfo);
	//
	// Message msg = new Message();
	// msg.what = SDK_PAY_FLAG;
	// msg.obj = result;
	// nHandler.sendMessage(msg);
	// }
	// };
	//
	// // 必须异步调用
	// Thread payThread = new Thread(payRunnable);
	// payThread.start();
	// }

	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 * 
	 */
	public void check(View v) {
		Runnable checkRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask payTask = new PayTask(PayAlipayActivity.this);
				// 调用查询接口，获取查询结果
				boolean isExist = payTask.checkAccountIfExist();

				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
				msg.obj = isExist;
				nHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();

	}

	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(String subject, String body, String price) {
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		ORDER_ID = getOutTradeNo();
		orderInfo += "&out_trade_no=" + "\"" + ORDER_ID + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	public String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		System.out.println(RSA_PRIVATE);
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	public String sign1(String content, String privateKey) {
		try {
			byte[] keyBytes = Base64.decode(RSA_PRIVATE);
			Log.i(TAG, keyBytes.toString());
			System.out.println(keyBytes);
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(keyBytes);
			System.out.print(priPKCS8);
			// KeyFactory keyf = KeyFactory.getInstance("RSA");
			KeyFactory keyf = KeyFactory.getInstance("RSA"); // , "BC"
			System.out.print(keyf);
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
			System.out.print(priKey);

			java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");

			signature.initSign(priKey);
			signature.update(content.getBytes("UTF-8"));

			byte[] signed = signature.sign();

			return Base64.encode(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

	private void getIMEINum() {
		IMEI = DeviceUtil.getDeviceId(PayAlipayActivity.this);
		if (IMEI != null) {
			if ("".equals(IMEI)) {
				IMEI = "358733050263717";
			}
		} else {
			IMEI = "358733050263717";
		}
	}
}
