package com.loongme.personage;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.loongme.activity.R;
import com.loongme.base.AppManager;
import com.loongme.fragment.AchieveFragment;
import com.loongme.fragment.IndentFragment;

/**
 * 
 * @author 我的帮客订单
 * 
 */
public class GuestIndentActivity extends FragmentActivity {
	private ViewPager vPager;
	private ImageView img_bottom_line;
	private TextView text_one, text_two;
	private int currIndex = 0;
	private int bmpW;// 图片宽度
	private int offset;// 动画图片偏移量

	List<Fragment> mList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guest_indent);
		AppManager.getAppManager().addActivity(this);
		initView();
		initImageView();
		initViewPager();
	}

	private void initViewPager() {
		// TODO Auto-generated method stub
		vPager = (ViewPager) findViewById(R.id.vPager);
		mList = new ArrayList<Fragment>();
		mList.add(new IndentFragment());
		mList.add(new AchieveFragment());
		MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), mList);
		vPager.setAdapter(adapter);
		vPager.setCurrentItem(0);
		vPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	private void initImageView() {
		// TODO Auto-generated method stub
		img_bottom_line = (ImageView) findViewById(R.id.img_bottom_line);
		bmpW = img_bottom_line.getLayoutParams().width;
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		offset = (screenW / 2 - bmpW) / 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		img_bottom_line.setImageMatrix(matrix);
	}

	private void initView() {
		// TODO Auto-generated method stub
		text_one = (TextView) findViewById(R.id.text_one);
		text_two = (TextView) findViewById(R.id.text_two);
		text_one.setOnClickListener(new MyOnClick(0));
		text_two.setOnClickListener(new MyOnClick(1));
	}

	class MyOnPageChangeListener implements OnPageChangeListener {
		int one = offset * 2 + bmpW;

		// int two=offset*2;

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			Animation animation = new TranslateAnimation(one * currIndex, one
					* arg0, 0, 0);
			currIndex = arg0;
			animation.setFillAfter(true);
			animation.setDuration(300);
			img_bottom_line.startAnimation(animation);
		}
	}

	class MyAdapter extends FragmentPagerAdapter {
		List<Fragment> list;

		public MyAdapter(FragmentManager fm, List list) {
			super(fm);
			// TODO Auto-generated constructor stub
			this.list = list;
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return list.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

	}

	class MyOnClick implements OnClickListener {
		int index = 0;

		public MyOnClick(int i) {
			// TODO Auto-generated constructor stub
			this.index = i;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			vPager.setCurrentItem(index);
		}

	}

	@Override
	protected void onDestroy() {
		AppManager.getAppManager().finishActivity(this);
		super.onDestroy();
	}

}
