package com.loongme.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * @author 作者 :zhaojianyu
 * @version 创建时间：2015-7-27 上午9:20:09 类说明 :
 */
public class UIUtils {
	// /*
	// * toast方法
	// */
	// public static void showToastCry(Context context, String info) {
	// Toast toast = Toast.makeText(BaseApplication.mContext, info,
	// Toast.LENGTH_LONG);
	// toast.setGravity(Gravity.CENTER, 0, 0);
	// LinearLayout toastView = (LinearLayout) toast.getView();
	// ImageView imageCodeProject = new ImageView(BaseApplication.mContext);
	// LinearLayout.LayoutParams lp = new
	// LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
	// LayoutParams.WRAP_CONTENT);
	// lp.bottomMargin = DensityPixel.dip2px(context, 5);
	// lp.gravity = Gravity.CENTER_HORIZONTAL;
	// imageCodeProject.setLayoutParams(lp);
	// imageCodeProject.setImageResource(R.drawable.toast_cry);
	// toastView.addView(imageCodeProject, 0);
	// toast.show();
	// // NoRepeatToast.showToast(BaseApplication.mContext, info, 500);
	// // Toast.makeText(BaseApplication.mContext, info,
	// // Toast.LENGTH_SHORT).show();
	// }
	//
	// public static void showToastLongCry(Context context, String info) {
	// // NoRepeatToast.showToast(BaseApplication.mContext, info, 800);
	// // Toast.makeText(BaseApplication.mContext, info,
	// // Toast.LENGTH_LONG).show();
	// Toast toast = Toast.makeText(BaseApplication.mContext, info,
	// Toast.LENGTH_LONG);
	// toast.setGravity(Gravity.CENTER, 0, 0);
	// LinearLayout toastView = (LinearLayout) toast.getView();
	// ImageView imageCodeProject = new ImageView(BaseApplication.mContext);
	// LinearLayout.LayoutParams lp = new
	// LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
	// LayoutParams.WRAP_CONTENT);
	// lp.bottomMargin = DensityPixel.dip2px(context, 5);
	// lp.gravity = Gravity.CENTER_HORIZONTAL;
	// imageCodeProject.setLayoutParams(lp);
	// imageCodeProject.setImageResource(R.drawable.toast_cry);
	// toastView.addView(imageCodeProject, 0);
	// toast.show();
	// }
	//
	// public static void showToastCry(Context context, int resId) {
	// // NoRepeatToast.showToast(BaseApplication.mContext, resId,
	// // Toast.LENGTH_LONG);
	// // Toast.makeText(BaseApplication.mContext, resId,
	// // Toast.LENGTH_SHORT).show();
	// Toast toast = Toast.makeText(BaseApplication.mContext, resId,
	// Toast.LENGTH_LONG);
	// toast.setGravity(Gravity.CENTER, 0, 0);
	// LinearLayout toastView = (LinearLayout) toast.getView();
	// ImageView imageCodeProject = new ImageView(BaseApplication.mContext);
	// LinearLayout.LayoutParams lp = new
	// LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
	// LayoutParams.WRAP_CONTENT);
	// lp.bottomMargin = DensityPixel.dip2px(context, 5);
	// lp.gravity = Gravity.CENTER_HORIZONTAL;
	// imageCodeProject.setLayoutParams(lp);
	// imageCodeProject.setImageResource(R.drawable.toast_cry);
	// toastView.addView(imageCodeProject, 0);
	// toast.show();
	// }
	//
	// public static void showToastLongCry(Context context, int resId) {
	// // NoRepeatToast.showToast(BaseApplication.mContext, resId,
	// // Toast.LENGTH_LONG);
	// // Toast.makeText(BaseApplication.mContext, resId,
	// // Toast.LENGTH_LONG).show();
	// Toast toast = Toast.makeText(BaseApplication.mContext, resId,
	// Toast.LENGTH_LONG);
	// toast.setGravity(Gravity.CENTER, 0, 0);
	// LinearLayout toastView = (LinearLayout) toast.getView();
	// ImageView imageCodeProject = new ImageView(BaseApplication.mContext);
	// LinearLayout.LayoutParams lp = new
	// LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
	// LayoutParams.WRAP_CONTENT);
	// lp.bottomMargin = DensityPixel.dip2px(context, 5);
	// lp.gravity = Gravity.CENTER_HORIZONTAL;
	// imageCodeProject.setLayoutParams(lp);
	// imageCodeProject.setImageResource(R.drawable.toast_cry);
	// toastView.addView(imageCodeProject, 0);
	// toast.show();
	// }
	//
	// /*
	// * toast方法
	// */
	// public static void showToastSmile(Context context, String info) {
	// Toast toast = Toast.makeText(BaseApplication.mContext, info,
	// Toast.LENGTH_LONG);
	// toast.setGravity(Gravity.CENTER, 0, 0);
	// LinearLayout toastView = (LinearLayout) toast.getView();
	// ImageView imageCodeProject = new ImageView(BaseApplication.mContext);
	// LinearLayout.LayoutParams lp = new
	// LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
	// LayoutParams.WRAP_CONTENT);
	// lp.bottomMargin = DensityPixel.dip2px(context, 5);
	// lp.gravity = Gravity.CENTER_HORIZONTAL;
	// imageCodeProject.setLayoutParams(lp);
	// imageCodeProject.setImageResource(R.drawable.toast_smile);
	// toastView.addView(imageCodeProject, 0);
	// toast.show();
	// // NoRepeatToast.showToast(BaseApplication.mContext, info, 500);
	// // Toast.makeText(BaseApplication.mContext, info,
	// // Toast.LENGTH_SHORT).show();
	// }
	//
	// public static void showToastLongSmile(Context context, String info) {
	// // NoRepeatToast.showToast(BaseApplication.mContext, info, 800);
	// // Toast.makeText(BaseApplication.mContext, info,
	// // Toast.LENGTH_LONG).show();
	// Toast toast = Toast.makeText(BaseApplication.mContext, info,
	// Toast.LENGTH_LONG);
	// toast.setGravity(Gravity.CENTER, 0, 0);
	// LinearLayout toastView = (LinearLayout) toast.getView();
	// ImageView imageCodeProject = new ImageView(BaseApplication.mContext);
	// LinearLayout.LayoutParams lp = new
	// LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
	// LayoutParams.WRAP_CONTENT);
	// lp.bottomMargin = DensityPixel.dip2px(context, 5);
	// lp.gravity = Gravity.CENTER_HORIZONTAL;
	// imageCodeProject.setLayoutParams(lp);
	// imageCodeProject.setImageResource(R.drawable.toast_smile);
	// toastView.addView(imageCodeProject, 0);
	// toast.show();
	// }
	//
	// public static void showToastSmile(Context context, int resId) {
	// // NoRepeatToast.showToast(BaseApplication.mContext, resId,
	// // Toast.LENGTH_LONG);
	// // Toast.makeText(BaseApplication.mContext, resId,
	// // Toast.LENGTH_SHORT).show();
	// Toast toast = Toast.makeText(BaseApplication.mContext, resId,
	// Toast.LENGTH_LONG);
	// toast.setGravity(Gravity.CENTER, 0, 0);
	// LinearLayout toastView = (LinearLayout) toast.getView();
	// ImageView imageCodeProject = new ImageView(BaseApplication.mContext);
	// LinearLayout.LayoutParams lp = new
	// LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
	// LayoutParams.WRAP_CONTENT);
	// lp.bottomMargin = DensityPixel.dip2px(context, 5);
	// lp.gravity = Gravity.CENTER_HORIZONTAL;
	// imageCodeProject.setLayoutParams(lp);
	// imageCodeProject.setImageResource(R.drawable.toast_smile);
	// toastView.addView(imageCodeProject, 0);
	// toast.show();
	// }
	//
	// public static void showToastLongSmile(Context context, int resId) {
	// // NoRepeatToast.showToast(BaseApplication.mContext, resId,
	// // Toast.LENGTH_LONG);
	// // Toast.makeText(BaseApplication.mContext, resId,
	// // Toast.LENGTH_LONG).show();
	// Toast toast = Toast.makeText(BaseApplication.mContext, resId,
	// Toast.LENGTH_LONG);
	// toast.setGravity(Gravity.CENTER, 0, 0);
	// LinearLayout toastView = (LinearLayout) toast.getView();
	// ImageView imageCodeProject = new ImageView(BaseApplication.mContext);
	// LinearLayout.LayoutParams lp = new
	// LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
	// LayoutParams.WRAP_CONTENT);
	// lp.bottomMargin = DensityPixel.dip2px(context, 5);
	// lp.gravity = Gravity.CENTER_HORIZONTAL;
	// imageCodeProject.setLayoutParams(lp);
	// imageCodeProject.setImageResource(R.drawable.toast_smile);
	// toastView.addView(imageCodeProject, 0);
	// toast.show();
	// }

	/**
	 * 吐司
	 * 
	 * @param context
	 * @param content
	 */
	public static void showToast(Context context, String content) {
		Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 强制隐藏软键盘
	 * 
	 * @param context
	 * @param view
	 */
	public static void hideSoftKeyBoard(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

	}

}
