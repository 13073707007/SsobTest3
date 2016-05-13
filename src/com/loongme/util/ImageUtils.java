package com.loongme.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

/**
 * @author: 刘磊
 * @类 说明:
 * @version 1.3
 * @创建时间：2015-5-5 下午5:09:20
 * 
 */
public class ImageUtils {

	/**
	 * 根据文件路径删除文件
	 * 
	 * @param filePath
	 */
	public static void delFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * @param context
	 * @param uri
	 * @param imageView
	 *            defaultImg 默认图片
	 */
	// public static void setImageView(Context context, String uri, ImageView
	// imageView, int defaultImg) {
	// DisplayImageOptions defaultImageOptions =
	// EntPlusApplication.getDefaultImageOptions(defaultImg);
	// ImageLoader.getInstance().displayImage(uri, imageView,
	// defaultImageOptions);
	// }

	/**
	 * 图片上面绘字
	 * 
	 * @param origBitmap
	 * @param text
	 * @return
	 */
	public static Bitmap drawNewBitmap(Context mContext, Bitmap origBitmap,
			String text) {
		int width = origBitmap.getWidth();
		int hight = origBitmap.getHeight();
		Bitmap newBitmap = Bitmap.createBitmap(width, hight,
				Bitmap.Config.ARGB_8888); // 建立一个空的BItMap
		Canvas canvas = new Canvas(newBitmap);// 初始化画布绘制的图像到icon上
		Paint photoPaint = new Paint(); // 建立画笔
		photoPaint.setDither(true); // 获取跟清晰的图像采样
		photoPaint.setFilterBitmap(true);// 过滤一些
		Rect src = new Rect(0, 0, newBitmap.getWidth(), newBitmap.getHeight());// 创建一个指定的新矩形的坐标
		Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标
		canvas.drawBitmap(origBitmap, src, dst, photoPaint);// 将photo 缩放或则扩大到
		// dst使用的填充区photoPaint
		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG
				| Paint.DEV_KERN_TEXT_FLAG);// 设置画笔
		textPaint.setTextSize(DensityPixel.dip2px(mContext, 30));// 字体大小
		textPaint.setTypeface(Typeface.DEFAULT_BOLD);// 采用默认的宽度
		textPaint.setColor(Color.parseColor("#CFD2E2"));// 采用的颜色
		if (text.matches("[0-9A-Za-z]")) {
			canvas.drawText(text, DensityPixel.dip2px(mContext, width / 6 - 7),
					DensityPixel.dip2px(mContext, hight / 6 + 11), textPaint);// 绘制上去字，开始未知x,y采用那只笔绘制
		} else {
			canvas.drawText(text,
					DensityPixel.dip2px(mContext, width / 6 - 16f),
					DensityPixel.dip2px(mContext, hight / 6 + 10.5f), textPaint);// 绘制上去字，开始未知x,y采用那只笔绘制
		}
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		return newBitmap;
	}

	/**
	 * 个人头像
	 * 
	 * @param origBitmap
	 * @param text
	 * @return
	 */
	public static Bitmap drawPersonBitmap(Context mContext, Bitmap origBitmap,
			String text) {
		int width = origBitmap.getWidth();
		int hight = origBitmap.getHeight();
		Bitmap newBitmap = Bitmap.createBitmap(width, hight,
				Bitmap.Config.ARGB_8888); // 建立一个空的BItMap
		Canvas canvas = new Canvas(newBitmap);// 初始化画布绘制的图像到icon上
		Paint photoPaint = new Paint(); // 建立画笔
		photoPaint.setDither(true); // 获取跟清晰的图像采样
		photoPaint.setFilterBitmap(true);// 过滤一些
		Rect src = new Rect(0, 0, newBitmap.getWidth(), newBitmap.getHeight());// 创建一个指定的新矩形的坐标
		Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标
		canvas.drawBitmap(origBitmap, src, dst, photoPaint);// 将photo 缩放或则扩大到
		// dst使用的填充区photoPaint
		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG
				| Paint.DEV_KERN_TEXT_FLAG);// 设置画笔
		textPaint.setTextSize(DensityPixel.dip2px(mContext, 30));// 字体大小
		textPaint.setTypeface(Typeface.DEFAULT_BOLD);// 采用默认的宽度
		textPaint.setColor(Color.parseColor("#CFD2E2"));// 采用的颜色

		canvas.drawText(text, DensityPixel.dip2px(mContext, width / 6 - 13),
				DensityPixel.dip2px(mContext, hight / 6 + 15), textPaint);// 绘制上去字，开始未知x,y采用那只笔绘制
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		return newBitmap;
	}

	/**
	 * 保存图片到app指定路径
	 * 
	 * @param bm头像图片资源
	 * @param fileName保存名称
	 */
	public static void saveFile(Bitmap bm, String filePath) {
		try {
			String Path = filePath.substring(0, filePath.lastIndexOf("/"));
			File dirFile = new File(Path);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			File myCaptureFile = new File(filePath);
			BufferedOutputStream bo = null;
			bo = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
			bm.compress(Bitmap.CompressFormat.PNG, 100, bo);

			bo.flush();
			bo.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Bitmap getBitmapByPath(String filePath,
			BitmapFactory.Options opts) {
		FileInputStream fis = null;
		Bitmap bitmap = null;
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			bitmap = BitmapFactory.decodeStream(fis, null, opts);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return bitmap;
	}

	/**
	 * 文件--->二进制数组
	 * 
	 * @param filePath
	 * @return
	 */
	public static byte[] File2byte(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

}
