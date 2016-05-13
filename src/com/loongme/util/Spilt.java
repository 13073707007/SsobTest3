package com.loongme.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public class Spilt {
	public String[] spiltString(String msg, String TAG) {

		String finalResult = "";
		String[] result = { "", "" };
		String temp;
		if (msg.contains("textpic")) {
			String lastt = "";
			String[] a = msg.split("ROBOT_OUTPUT");
			String s = a[1];
			int index = s.indexOf("}");
			// System.out.println(s);
			// System.out.println("" + index);
			String last = s.substring(0, index);
			System.out.println(">>>>>>" + last);

			if (last.contains("{")) {
				int inde = last.indexOf("{");
				lastt = last.substring(inde + 1, last.length());
				System.out.println(lastt);
				result[1] = lastt;
			} else {
				result[1] = last;
			}

			result[0] = "textpic";
			// result[1] = lastt;
			return result;
		} else if (msg.contains("text") && !msg.contains("textpic")) {
			String lastt = "";
			String[] a = msg.split("ROBOT_OUTPUT");
			String s = a[1];
			int index = s.indexOf("}");
			// System.out.println(s);
			// System.out.println("" + index);
			String last = s.substring(0, index);
			System.out.println(">>>>>>" + last);

			if (last.contains("{")) {
				int inde = last.indexOf("{");
				lastt = last.substring(inde + 1, last.length());
				System.out.println(lastt);
				result[1] = lastt;
			} else {
				result[1] = last;
			}
			result[0] = "text";
			return result;
		} else if (msg.contains("avi")) {

		}
		return null;
	}

	public String[] spiltFuckText(String msg, String TAG) {
		String[] Fuckresult = { "", "" };
		if (msg.contains("textpic")) {
			String lastt = "";
			String[] a = msg.split("ROBOT_OUTPUT");
			String s = a[1];
			int index = s.indexOf("}");
			// System.out.println(s);
			// System.out.println("" + index);
			String last = s.substring(0, index);
			System.out.println(">>>>>>" + last);

			if (last.contains("{")) {
				int inde = last.indexOf("{");
				lastt = last.substring(inde + 1, last.length());
				System.out.println(lastt);
				Fuckresult[1] = lastt;
			} else {
				Fuckresult[1] = last;
			}
			Fuckresult[0] = "textpic";
			// result[1] = lastt;
			return Fuckresult;
		} else if (msg.contains("text") && !msg.contains("textpic")) {
			String last = "";
			int index;
			String lastt = "";
			String[] a = msg.split("ROBOT_OUTPUT");
			String s = a[1];
			if (s.contains("}")) {
				index = s.indexOf("}");
				// System.out.println(s);
				last = s.substring(0, index);
				System.out.println(">>>>>>" + last);
				if (last.contains("{")) {
					int inde = last.indexOf("{");
					lastt = last.substring(inde + 1, last.length());
					System.out.println(lastt);
					Fuckresult[1] = lastt;
				} else {
					Fuckresult[1] = last;
				}
			} else if (s.contains("{")) {
				int indexfuck = s.indexOf("{");
				lastt = s.substring(indexfuck + 1, s.length());
				Fuckresult[1] = lastt;
			} else {
				Fuckresult[1] = s;
			}
			Fuckresult[0] = "text";
			return Fuckresult;
		} else if (msg.contains("avi")) {

		}
		return null;
	}

	public String spiltGeneralString(String msg, String context, String tag) {
		String NoticeResult = "";
		if (msg.contains(context)) {
			String lastt = "";
			String[] a = msg.split(context);
			String s = a[1];
			int index = s.indexOf("}");
			// System.out.println(s);
			// System.out.println("" + index);
			String last = s.substring(0, index);
			System.out.println(">>>>>>" + last);

			if (last.contains("{")) {
				int inde = last.indexOf("{");
				lastt = last.substring(inde + 1, last.length());
				System.out.println(lastt);
				NoticeResult = lastt;
			} else {
				NoticeResult = last;
			}
			// result[1] = lastt;
		}
		return NoticeResult;
	}

	public String[] spiltNoticeString(String msg, String tag) {
		String[] NoticeResult = { "", "", "" };
		if (msg.contains("MESSAGE_ID")) {
			String lastt = "";
			String[] a = msg.split("MESSAGE_ID");
			String s = a[1];
			int index = s.indexOf("}");
			// System.out.println(s);
			// System.out.println("" + index);
			String last = s.substring(0, index);
			System.out.println("MESSAGE_ID = " + last);

			if (last.contains("{")) {
				int inde = last.indexOf("{");
				lastt = last.substring(inde + 1, last.length());
				System.out.println(lastt);
				NoticeResult[0] = lastt;
			} else {
				NoticeResult[0] = last;
			}
			// result[1] = lastt;
		}
		if (msg.contains("CUST_OPEN_ID")) {
			String lastt = "";
			String[] a = msg.split("CUST_OPEN_ID");
			String s = a[1];
			int index = s.indexOf("}");
			// System.out.println(s);
			// System.out.println("" + index);
			String last = s.substring(0, index);
			System.out.println("CUST_OPEN_ID = " + last);

			if (last.contains("{")) {
				int inde = last.indexOf("{");
				lastt = last.substring(inde + 1, last.length());
				System.out.println(lastt);
				NoticeResult[1] = lastt;
			} else {
				NoticeResult[1] = last;
			}
			// result[1] = lastt;
		}
		if (msg.contains("ROBOTOUTPUT")) {
			String lastt = "";
			String[] a = msg.split("ROBOTOUTPUT");
			String s = a[1];
			int index = s.indexOf("}");
			// System.out.println(s);
			// System.out.println("" + index);
			String last = s.substring(0, index);
			System.out.println("ROBOTOUTPUT = " + last);

			if (last.contains("{")) {
				int inde = last.indexOf("{");
				lastt = last.substring(inde + 1, last.length());
				System.out.println(lastt);
				NoticeResult[2] = lastt;
			} else {
				NoticeResult[2] = last;
			}
			// result[1] = lastt;
		}
		return NoticeResult;
	}

	public String[] spiltAcceptString(String msg, String tag) {
		String[] accept;
		int acceptCount = 0;
		if (msg.contains("ACCEPT_COUNT")) {
			String[] a = msg.split("ACCEPT_COUNT");
			String s = a[1];
			int index = s.indexOf("}");
			// System.out.println(s);
			String last = s.substring(0, index);
			if (last.contains("{")) {
				int inde = last.indexOf("{");
				last = last.substring(inde + 1, last.length());
			}
			acceptCount = Integer.parseInt(last.trim(), 10);
			System.out.println("acceptCount = " + acceptCount);
		}
		if (acceptCount == 0) {
			return null;
		}
		if (msg.contains("ACCEPT_LIST")) {
			accept = new String[acceptCount - 1];
			String[] a = msg.split("ACCEPT_LIST");
			String s = a[1];
			int index = s.indexOf("}");
			// System.out.println(s);
			String last = s.substring(0, index);
			if (last.contains("{")) {
				int inde = last.indexOf("{");
				last = last.substring(inde + 1, last.length());
			}
			System.out.println("ACCEPT_LIST = " + last.trim());
			accept = last.trim().split(" ");
			return accept;
		}
		return null;
	}

	/**
	 * 解析服务订单详情
	 * 
	 * @param msg
	 *            需要解析的字符串
	 * @return 返回解析好的动态数组
	 */
	public ArrayList<HashMap<String, Object>> getAcceptList(String msg) {
		if (null == spiltAcceptString(msg, msg)) {
			return null;
		}
		String[] acceptList = spiltAcceptString(msg, msg);
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < acceptList.length; i++) {
			String[] accept = acceptList[i].split("\\^");
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("Title", accept[3]);
			map.put("Status", accept[1]);
			map.put("WXOPEN_ID", accept[2]);
			map.put("Time", accept[0]);
			// System.out.print("accept 1 2 3" + accept[0]);
			listItem.add(map);
		}
		return listItem;
	}

	public String[] spiltSubsString(String msg, String tag) {
		String[] accept;
		int acceptCount = 0;
		if (msg.contains("SUBS_COUNT")) {
			String[] a = msg.split("SUBS_COUNT");
			String s = a[1];
			int index = s.indexOf("}");
			// System.out.println(s);
			String last = s.substring(0, index);
			if (last.contains("{")) {
				int inde = last.indexOf("{");
				last = last.substring(inde + 1, last.length());
			}
			acceptCount = Integer.parseInt(last.trim(), 10);
			System.out.println("acceptCount = " + acceptCount);
		}
		if (acceptCount == 0) {
			return null;
		}
		if (msg.contains("SUBS_LIST")) {
			accept = new String[acceptCount - 1];
			String[] a = msg.split("SUBS_LIST");
			String s = a[1];
			int index = s.indexOf("}");
			// System.out.println(s);
			String last = s.substring(0, index);
			if (last.contains("{")) {
				int inde = last.indexOf("{");
				last = last.substring(inde + 1, last.length());
			}
			System.out.println("ACCEPT_LIST = " + last.trim());
			accept = last.trim().split(" ");
			return accept;
		}
		return null;
	}

	/**
	 * 解析发布订单详情
	 * 
	 * @param msg
	 *            需要解析的字符串
	 * @return 返回解析好的动态数组
	 */
	public ArrayList<HashMap<String, Object>> getSubsList(String msg) {
		if (null == spiltSubsString(msg, msg)) {
			return null;
		}
		String[] acceptList = spiltSubsString(msg, msg);
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < acceptList.length; i++) {
			String[] accept = acceptList[i].split("\\^");
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("Title", accept[3]);
			map.put("Status", accept[1]);
			map.put("WXOPEN_ID", accept[2]);
			map.put("Time", accept[0]);
			// System.out.print("accept 1 2 3" + accept[0]);
			listItem.add(map);
		}
		return listItem;
	}

	/**
	 * 解析服务器返回的订单信息
	 * 
	 * @param msg
	 *            服务器返回的订单信息
	 * @param tag
	 * @return
	 * 
	 */
	public String[] spiltOrderString(String msg, String tag) {
		String[] accept;
		String[] subs;
		int ACCEPT_COUNT = 0;
		int SUBS_COUNT = 0;
		// 获取作为服务者订单的总数
		if (msg.contains("ACCEPT_COUNT")) {
			String[] a = msg.split("ACCEPT_COUNT");
			String s = a[1];
			int index = s.indexOf("}");
			// System.out.println(s);
			String last = s.substring(0, index);
			if (last.contains("{")) {
				int inde = last.indexOf("{");
				last = last.substring(inde + 1, last.length());
			}
			ACCEPT_COUNT = Integer.parseInt(last.trim(), 10);
			System.out.println("ACCEPT_COUNT = " + ACCEPT_COUNT);
		}
		// 获取作为发布者的订单的总数
		if (msg.contains("SUBS_COUNT")) {
			String[] a = msg.split("SUBS_COUNT");
			String s = a[1];
			int index = s.indexOf("}");
			String last = s.substring(0, index);
			if (last.contains("{")) {
				int inde = last.indexOf("{");
				last = last.substring(inde + 1, last.length());
			}
			SUBS_COUNT = Integer.parseInt(last.trim(), 10);
			System.out.println("SUBS_COUNT = " + SUBS_COUNT);
		}
		// 获取作为服务者的订单详情
		if (msg.contains("ACCEPT_LIST")) {
			accept = new String[ACCEPT_COUNT - 1];
			String lastt = "";
			String[] a = msg.split("ACCEPT_LIST");
			String s = a[1];
			int index = s.indexOf("}");
			// System.out.println(s);
			String last = s.substring(0, index);
			if (last.contains("{")) {
				int inde = last.indexOf("{");
				lastt = last.substring(inde + 1, last.length());
			}
			System.out.println("ACCEPT_LIST = " + lastt);
			accept = lastt.split(" ");
		}
		// 获取作为发布者的订单详情
		if (msg.contains("SUBS_LIST")) {
			accept = new String[ACCEPT_COUNT - 1];
			String lastt = "";
			String[] a = msg.split("SUBS_LIST");
			String s = a[1];
			int index = s.indexOf("}");
			// System.out.println(s);
			String last = s.substring(0, index);
			if (last.contains("{")) {
				int inde = last.indexOf("{");
				lastt = last.substring(inde + 1, last.length());
			}
			System.out.println("SUBS_LIST = " + lastt);
			subs = lastt.split(" ");
		}
		ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		for (int ii = 0; ii < ACCEPT_COUNT; ii++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("", "");

			listItem.add(map);
		}
		for (int ii = 0; ii < SUBS_COUNT; ii++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("", "");

			listItem.add(map);
		}
		if (msg.contains("ROBOTOUTPUT")) {
			String lastt = "";
			String[] a = msg.split("ROBOTOUTPUT");
			String s = a[1];
			int index = s.indexOf("}");
			// System.out.println(s);
			String last = s.substring(0, index);
			System.out.println("ROBOTOUTPUT = " + last);

			if (last.contains("{")) {
				int inde = last.indexOf("{");
				lastt = last.substring(inde + 1, last.length());
				System.out.println(lastt);
			}
		}
		return null;
	}

	/**
	 * 解析购买关键字的回复
	 * 
	 * @param message
	 *            解析字段
	 * @param TAG
	 * @return keyword detail price
	 */
	public String[] spiltKeywordOrder(String message, String TAG) {
		String[] s000 = { "", "", "" };// PROD_NAME
		if (message.contains("DETAIL")) {
			String lastt = "";
			String[] a = message.split("DETAIL");
			String s = a[1];
			int index = s.indexOf("}");
			// System.out.println(s);
			String last = s.substring(0, index).trim();
			System.out.println("DETAIL = " + last);
			if (last.contains("{")) {
				int inde = last.indexOf("{");
				lastt = last.substring(inde + 1, last.length()).trim();
				// System.out.println(lastt);
				s000[1] = lastt;
			} else {
				s000[1] = last;
			}
		}
		if (message.contains("PRICE")) {
			String lastt = "";
			String[] a = message.split("PRICE");
			String s = a[1];
			int index = s.indexOf(".");
			String last = s.substring(0, index + 3);// 取小数点后两位
			System.out.println("PRICE = " + last);
			if (last.contains("{")) {
				int inde = last.indexOf("{");
				lastt = last.substring(inde + 1, last.length());
				// System.out.println(lastt);
				s000[2] = lastt.trim();
			} else {
				s000[2] = last.trim();
			}
		}
		if (message.contains("PROD_NAME")) {
			String lastt = "";
			String[] a = message.split("PROD_NAME");
			String s = a[1];
			int index = s.indexOf("}");
			// System.out.println(s);
			String last = s.substring(0, index);
			System.out.println("PROD_NAME = " + last);
			if (last.contains("{")) {
				int inde = last.indexOf("{");
				lastt = last.substring(inde + 1, last.length());
				// System.out.println(lastt);
				s000[0] = lastt.trim();
			} else {
				s000[0] = last.trim();
			}
		}
		return s000;
	}

	/**
	 * 解析服务器返回的关键字列表
	 * 
	 * @param message
	 * @param TAG
	 * @return Map<String, String>
	 */
	public Map<String, String> spiltKeywordMap(String message, String TAG) {
		Map<String, String> map1 = new HashMap<String, String>();
		String[] keywordList;
		String keywordlist = null;
		if (message.contains("CAREER_LIST")) {
			String lastt = "";
			String[] a = message.split("CAREER_LIST");
			String s = a[1];
			int index = s.indexOf("}");
			System.out.println(s);
			String last = s.substring(0, index);
			System.out.println("CAREER_LIST = " + last);
			if (last.contains("{")) {
				int inde = last.indexOf("{");
				lastt = last.substring(inde + 1, last.length());
				System.out.println(lastt);
				keywordlist = lastt.trim();
			} else {
				keywordlist = last.trim();
			}
		}
		keywordList = keywordlist.split(" ");
		for (int i = 0; i < keywordList.length; i++) {
			String[] array = keywordList[i].split("=");
			map1.put(array[0], array[1]);
		}
		return map1;
	}

	/**
	 * 解析服务器返回的关键字列表
	 * 
	 * @param message
	 * @param TAG
	 * @return List<Map<String, String>> key value accout
	 */
	public List<HashMap<String, String>> spiltKeywordList(String message,
			String TAG) {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		String[] keywordList;
		String keywordlist = null;
		if (message.contains("CAREER_LIST")) {
			String lastt = "";
			String[] a = message.split("CAREER_LIST");
			String s = a[1];
			int index = s.indexOf("}");
			// System.out.println(s);
			String last = s.substring(0, index);
			// System.out.println("CAREER_LIST = " + last);
			if (last.contains("{")) {
				int inde = last.indexOf("{");
				lastt = last.substring(inde + 1, last.length());
				// System.out.println(lastt);
				keywordlist = lastt.trim();
			} else {
				keywordlist = last.trim();
			}
		}
		keywordList = keywordlist.split(" ");
		for (int i = 0; i < keywordList.length; i++) {
			String[] array = keywordList[i].split("=");
			HashMap<String, String> map1 = new HashMap<String, String>();
			map1.put(array[0], array[1]);
			map1.put("key", array[0]);
			map1.put("value", array[1]);
			map1.put("accout", array[2]);
			list.add(map1);
		}
		return list;
	}

	public String spiltWelcomeString(String message, String TAG) {
		if (message.contains("http")) {
			String[] spiltFirst = message.split("ROBOT_OUTPUT");
			String test = spiltFirst[1];
			String[] test2 = test.split("\\{");
			String msg = test2[1];
			String[] spiltSecond = msg.split("\\}");
			String finalMessage = spiltSecond[0].trim();
			return finalMessage;
		} else {
			String[] spiltFirst = message.split("ROBOT_OUTPUT");
			String test = spiltFirst[1];
			String[] test2 = test.split("\\}");
			String msg = test2[0];
			if (msg.contains("{")) {
				int inde = msg.indexOf("{");
				String lastt = msg.substring(inde + 1, msg.length());
				System.out.println("lastt : " + lastt);
				msg = lastt;
			}
			return msg;
		}

	}

	public String spiltWelcomeString1(String message, String TAG) {
		if (message.contains("http")) {
			System.out.println("message:" + message);
			String[] spiltFirst = message.split("ROBOT_OUTPUT");
			System.out.println("spiltFirst[0]:" + spiltFirst[0]);
			String test = spiltFirst[1];
			System.out.println("spiltFirst[1]:" + spiltFirst[1]);
			String[] test2 = test.split("\\{");
			String msg = test2[1];
			System.out.println("test2[0]:" + test2[0]);
			System.out.println("test2[1]:" + test2[1]);
			String[] spiltSecond = msg.split("\\}");
			System.out.println("spiltSecond[0]:" + spiltSecond[0]);
			String finalMessage = spiltSecond[0].trim();
			System.out.println("spiltSecond[1]:" + spiltSecond[1]);
			return finalMessage;
		} else {
			String[] spiltFirst = message.split("ROBOT_OUTPUT");
			String test = spiltFirst[1];
			String[] test2 = test.split("\\}");
			String msg = test2[0];
			if (msg.contains("{")) {
				int inde = msg.indexOf("{");
				String lastt = msg.substring(inde + 1, msg.length());
				System.out.println(lastt);
				msg = lastt;
			}
			return msg;
		}
	}

	public List spilt(String result, String TAG) {
		if (result == null) {
			return null;
		} else {
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			Map<String, String> map = new HashMap<String, String>();

			if (result.contains("^")) {
				String[] b = result.split("\\^");
				for (int i = 0; i < b.length; i++) {
					String test = b[i];
					String[] theLastString = test.split("\\|\\|");
					Map<String, String> mapCount = new HashMap<String, String>();
					for (int j = 0; j < theLastString.length; j++) {

						mapCount.put("content" + j, theLastString[j]);
						System.out.println("content" + j + "等于  "
								+ theLastString[j]);
						/*
						 * if (theLastString[j].contains("ROBOT_OUTPUT")) {
						 * Log.e(TAG, "before:" + theLastString[j].trim());
						 * String first = theLastString[j].substring(11);
						 * Log.e(TAG, "after:" + first.trim()); Log.e(TAG,
						 * "this is lastString :!>>" + j + first.trim()); } else
						 * { if (theLastString[j].trim().contains("")) {
						 * Log.e(TAG, "/////is true!!!!"); } Log.e(TAG,
						 * "this is lastString :!>>" + j +
						 * theLastString[j].trim()); }
						 */

					}
					list.add(mapCount);

				}
			} else {
				String[] theLastStringtb = result.split("\\|\\|");
				Map<String, String> mapCount = new HashMap<String, String>();
				for (int j = 0; j < theLastStringtb.length; j++) {

					mapCount.put("content" + j, theLastStringtb[j]);
					System.out.println("content" + j + "等于  "
							+ theLastStringtb[j]);

				}
				list.add(mapCount);
			}

			return list;
		}

	}

	public static String getUrl(String textInfo) {
		String url = "";
		if (textInfo.contains("http")) {
			String regex;
			final List<String> list = new ArrayList<String>();
			regex = "<a[^>]*href=(\"([^\"]*)\"|\'([^\']*)\'|([^\\s>]*))[^>]*>(.*?)</a>";
			final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
			final Matcher ma = pa.matcher(textInfo);
			if (ma.find()) {
				url = ma.group();
			}
		}
		return url;
	}

	public static String splitTextUrl(String text) {
		if (text.contains("href=\"")) {
			String[] first = text.split("href=\"");
			String[] sec = first[1].split("\"");
			return sec[0];
		} else {
			return text;
		}

	}

	public static String spiltTextUrl2(String message) {
		String[] temp = { "", "" };
		if (message.contains("<a href")) {
			temp = message.split("<a href");
		}
		return temp[0];
	}

	public static String spiltTextUrl3(String message) {
		String temp[] = { "", "", "" };
		if (message.contains("<a href=")) {
			temp = message.split(">");
		}
		return temp[1];
	}

	public static String spiltNum(String message) {
		message = message.trim();
		String str = "";
		if (message != null && !(message.equals(""))) {
			for (int i = 0; i < message.length(); i++) {
				if (message.charAt(i) >= 48 && message.charAt(i) <= 57) {
					str += message.charAt(i);
				}
			}
		}
		return str;
	}

	public static String spiltBaidu(String message) {
		String[] temp = { "", "", "" };
		if (message.contains("zhidao")) {
			temp = message.split("zhidao");
		}
		return temp[0];
	}
}
