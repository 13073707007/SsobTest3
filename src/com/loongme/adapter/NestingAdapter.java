package com.loongme.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loongme.activity.R;
import com.loongme.activity.ShowInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class NestingAdapter extends BaseAdapter {
	public static DisplayImageOptions options;
	private int mRowLayout;
	private List<String[]> list;
	private Context mcontext;
	private LayoutInflater minflater;

	public NestingAdapter(Context context, int rowLayout) {
		this.mcontext = context;
		this.mRowLayout = rowLayout;
		this.list = new ArrayList<String[]>();
	}

	public void addItem(List<String[]> item) {
		this.list.addAll(item);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public String[] getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void clear() {
		list.clear();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		minflater = LayoutInflater.from(mcontext);
		if (convertView == null) {
			convertView = minflater.inflate(this.mRowLayout, null);
			holder = new ViewHolder();
			holder.lt_main = (LinearLayout) convertView
					.findViewById(R.id.lt_nesting_main);
			holder.img = (ImageView) convertView.findViewById(R.id.img_nesting);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
			holder.tvContent = (TextView) convertView
					.findViewById(R.id.tv_detailContent);
			holder.tvAddress = (TextView) convertView
					.findViewById(R.id.tv_address);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}
		/*
		 * List<String[]> testList = new ArrayList<String[]>(); for (int i =
		 * 0;i<list.size();i++) { String[] temp = list.get(i);
		 * testList.add(temp); }
		 */
		final String[] temp = this.list.get(position);
		holder.tvTitle.setText(temp[1]);
		holder.tvContent.setText(temp[0]);
		holder.tvAddress.setText(temp[2]);
		holder.lt_main.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.lt_nesting_main:
					System.out.println("this is in the nesting :url>>"
							+ temp[4] + temp[1]);
					
					Intent intent = new Intent();
					intent.setClass(mcontext, ShowInfo.class);
					Bundle bun = new Bundle();
					bun.putString("uri", temp[4]);
					intent.putExtras(bun);
					mcontext.startActivity(intent);
					break;
				}
			}
		});

		ImageLoader.getInstance().displayImage(temp[3], holder.img,setImageOptions());

		// String[] temp = this.list.get(position);
		// 测试数据

		// holder.tvTitle.setText(temp[0]);
		// holder.tvContent.setText(temp[1]);
		// holder.tvAddress.setText(temp[2]);

		return convertView;
	}
	
	private DisplayImageOptions setImageOptions() {
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher) // 设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.ic_launcher)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ic_launcher) // 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
				// .decodingOptions(BitmapFactory.Options.)//设置图片的解码配置
				// .delayBeforeLoading(int delayInMillis)//int
				// delayInMillis为你设置的下载前的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// .preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				.displayer(new RoundedBitmapDisplayer(20))// 是否设置为圆角，弧度为多少
				.displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
				.build();// 构建完成
		return options;
	}

	class ViewHolder {
		LinearLayout lt_main;
		ImageView img;
		TextView tvTitle;
		TextView tvContent;
		TextView tvAddress;
	}

}
