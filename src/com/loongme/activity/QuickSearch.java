package com.loongme.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loongme.activity.R;
import com.loongme.base.BaseActivity;

public class QuickSearch extends BaseActivity {
	public final Context QuickCont = QuickSearch.this;
	private TextView tv_fun_aroundRestaurant;
	private TextView tv_fun_aroundktv;
	private TextView tv_fun_aroundboll;
	private TextView tv_road_tomingzhu;
	private TextView tv_road_tobeach;
	private TextView tv_road_youyitojing;
	private TextView tv_life_weather;
	private TextView tv_life_cook;
	private TextView tv_life_topnews;
	private TextView tv_soft_game;
	private TextView tv_soft_weixin;
	private TextView tv_soft_puzzle;
	private TextView tv_music_hotmusic;
	private TextView tv_music_movie;
	private TextView tv_music_wumeiniang;
	private TextView tv_travel_tickettobeijing;
	private TextView tv_travel_checkticket;
	private TextView tv_travel_hotel;
	private TextView tv_note_meeting;
	private TextView tv_note_getup;
	private TextView tv_note_medicine;
	private TextView tv_command_mingchao;
	private TextView tv_command_fruit;
	private TextView tv_command_ip;
	private List<ImageView> list = new ArrayList<ImageView>();
	private ImageView img_travel;
	private ImageView img_note;
	private ImageView img_road;
	private ImageView img_command;
	private ImageView img_life;
	private ImageView img_soft;
	private ImageView img_music;
	private ImageView img_fun;
	private boolean isFun = true;
	private boolean isTravel = true;
	private boolean isRoad = true;
	private boolean isLife = true;
	private boolean isCommand = true;
	private boolean isMusic = true;
	private boolean isSoft = true;
	private boolean isNote = true;
	private LinearLayout lt_quickBg;
	private LinearLayout lt_quickCommand;
	private LinearLayout lt_quickMusic;
	private LinearLayout lt_quickRoad;
	private LinearLayout lt_quickSoft;
	private LinearLayout lt_quicktravel;
	private LinearLayout lt_quickNote;
	private LinearLayout lt_quickLife;

	private LinearLayout lt_fun;
	private LinearLayout lt_comand;
	private LinearLayout lt_road;
	private LinearLayout lt_music;
	private LinearLayout lt_soft;
	private LinearLayout lt_travel;
	private LinearLayout lt_note;
	private LinearLayout lt_life;
	private TextView tv_title;
	private Button btn_setting;
	private Button btn_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_quicksearch);
		findView();
	}

	private void findView() {
		// 气泡
		tv_fun_aroundRestaurant = (TextView) findViewById(R.id.tv_fun_aroundRestaurant);
		tv_fun_aroundktv = (TextView) findViewById(R.id.tv_fun_aroundktv);
		tv_fun_aroundboll = (TextView) findViewById(R.id.tv_fun_aroundboll);

		tv_road_tomingzhu = (TextView) findViewById(R.id.tv_road_tomingzhu);
		tv_road_tobeach = (TextView) findViewById(R.id.tv_road_tobeach);
		tv_road_youyitojing = (TextView) findViewById(R.id.tv_road_youyitojing);
		tv_life_weather = (TextView) findViewById(R.id.tv_life_weather);
		tv_life_cook = (TextView) findViewById(R.id.tv_life_cook);
		tv_life_topnews = (TextView) findViewById(R.id.tv_life_topnews);
		tv_soft_game = (TextView) findViewById(R.id.tv_soft_game);
		tv_soft_weixin = (TextView) findViewById(R.id.tv_soft_weixin);
		tv_soft_puzzle = (TextView) findViewById(R.id.tv_soft_puzzle);
		tv_music_hotmusic = (TextView) findViewById(R.id.tv_music_hotmusic);
		tv_music_movie = (TextView) findViewById(R.id.tv_music_movie);
		tv_music_wumeiniang = (TextView) findViewById(R.id.tv_music_wumeiniang);
		tv_travel_tickettobeijing = (TextView) findViewById(R.id.tv_travel_tickettobeijing);
		tv_travel_checkticket = (TextView) findViewById(R.id.tv_travel_checkticket);
		tv_travel_hotel = (TextView) findViewById(R.id.tv_travel_hotel);
		tv_note_meeting = (TextView) findViewById(R.id.tv_note_meeting);
		tv_note_getup = (TextView) findViewById(R.id.tv_note_getup);
		tv_note_medicine = (TextView) findViewById(R.id.tv_note_medicine);
		tv_command_mingchao = (TextView) findViewById(R.id.tv_command_mingchao);
		tv_command_fruit = (TextView) findViewById(R.id.tv_command_fruit);
		tv_command_ip = (TextView) findViewById(R.id.tv_command_ip);

		tv_fun_aroundRestaurant.setOnClickListener(onclickForBubble);
		tv_fun_aroundktv.setOnClickListener(onclickForBubble);
		tv_fun_aroundboll.setOnClickListener(onclickForBubble);
		tv_road_tomingzhu.setOnClickListener(onclickForBubble);
		tv_road_tobeach.setOnClickListener(onclickForBubble);
		tv_road_youyitojing.setOnClickListener(onclickForBubble);
		tv_life_weather.setOnClickListener(onclickForBubble);
		tv_life_cook.setOnClickListener(onclickForBubble);
		tv_life_topnews.setOnClickListener(onclickForBubble);
		tv_soft_game.setOnClickListener(onclickForBubble);
		tv_soft_weixin.setOnClickListener(onclickForBubble);
		tv_soft_puzzle.setOnClickListener(onclickForBubble);
		tv_music_hotmusic.setOnClickListener(onclickForBubble);
		tv_music_movie.setOnClickListener(onclickForBubble);
		tv_music_wumeiniang.setOnClickListener(onclickForBubble);
		tv_travel_tickettobeijing.setOnClickListener(onclickForBubble);
		tv_travel_checkticket.setOnClickListener(onclickForBubble);
		tv_travel_hotel.setOnClickListener(onclickForBubble);
		tv_note_meeting.setOnClickListener(onclickForBubble);
		tv_note_getup.setOnClickListener(onclickForBubble);
		tv_note_medicine.setOnClickListener(onclickForBubble);
		tv_command_mingchao.setOnClickListener(onclickForBubble);
		tv_command_fruit.setOnClickListener(onclickForBubble);
		tv_command_ip.setOnClickListener(onclickForBubble);

		// 隐藏布局
		lt_quickBg = (LinearLayout) findViewById(R.id.lt_quickbg);
		lt_quickCommand = (LinearLayout) findViewById(R.id.lt_quicktcommand);
		lt_quickMusic = (LinearLayout) findViewById(R.id.lt_quicktmusic);
		lt_quickRoad = (LinearLayout) findViewById(R.id.lt_quicktroad);
		lt_quickSoft = (LinearLayout) findViewById(R.id.lt_quicktsoft);
		lt_quicktravel = (LinearLayout) findViewById(R.id.lt_quickttravel);
		lt_quickNote = (LinearLayout) findViewById(R.id.lt_quicktnote);
		lt_quickLife = (LinearLayout) findViewById(R.id.lt_quicklife);
		// 主布局
		lt_fun = (LinearLayout) findViewById(R.id.lt_fun);
		lt_comand = (LinearLayout) findViewById(R.id.lt_command);
		lt_road = (LinearLayout) findViewById(R.id.lt_road);
		lt_music = (LinearLayout) findViewById(R.id.lt_music);
		lt_soft = (LinearLayout) findViewById(R.id.lt_soft);
		lt_travel = (LinearLayout) findViewById(R.id.lt_travel);
		lt_note = (LinearLayout) findViewById(R.id.lt_note);
		lt_life = (LinearLayout) findViewById(R.id.lt_life);
		// 图片
		img_command = (ImageView) findViewById(R.id.image_command);
		img_fun = (ImageView) findViewById(R.id.img_fun);
		img_travel = (ImageView) findViewById(R.id.image_travel);
		img_note = (ImageView) findViewById(R.id.image_note);
		img_road = (ImageView) findViewById(R.id.imgge_load);
		img_soft = (ImageView) findViewById(R.id.image_soft);
		img_life = (ImageView) findViewById(R.id.image_life);
		img_music = (ImageView) findViewById(R.id.image_music);
		list.add(img_command);
		list.add(img_fun);
		list.add(img_travel);
		list.add(img_note);
		list.add(img_road);
		list.add(img_soft);
		list.add(img_life);
		list.add(img_music);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setBackgroundResource(R.drawable.icon_left);
		}
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_setting = (Button) findViewById(R.id.btn_setting);
		tv_title = (TextView) findViewById(R.id.tv_title);
		btn_setting.setVisibility(View.GONE);
		tv_title.setText("快捷搜索");
		btn_back.setVisibility(View.VISIBLE);
		lt_fun.setOnClickListener(onclickForQuick);
		lt_road.setOnClickListener(onclickForQuick);
		btn_back.setVisibility(View.VISIBLE);
		btn_back.setOnClickListener(onclickForQuick);
		lt_life.setOnClickListener(onclickForQuick);
		lt_soft.setOnClickListener(onclickForQuick);
		lt_music.setOnClickListener(onclickForQuick);
		lt_travel.setOnClickListener(onclickForQuick);
		lt_note.setOnClickListener(onclickForQuick);
		lt_comand.setOnClickListener(onclickForQuick);
	}

	private void startIntent(String message, Context context, Class<?> cls,
			String name) {
		Bundle bundle = new Bundle();
		bundle.putString(name, message);
		Intent intent = new Intent();
		intent.putExtras(bundle);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setClass(context, cls);
		startActivity(intent);
		finish();
	}

	OnClickListener onclickForBubble = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_fun_aroundRestaurant:
				startIntent("附近餐馆", QuickSearch.this, UnderstanderDemo.class,
						"temp");
				break;
			case R.id.tv_fun_aroundktv:
				startIntent("附近的ktv", QuickSearch.this, UnderstanderDemo.class,
						"temp");
				break;
			case R.id.tv_fun_aroundboll:
				startIntent("最火爆最近距离的台球馆", QuickSearch.this,
						UnderstanderDemo.class, "temp");
				break;
			case R.id.tv_road_tomingzhu:
				// Toast.makeText(QuickSearch.this, "等待接口改善",
				// Toast.LENGTH_SHORT).show();
				startIntent("坐公交到明珠广场", QuickSearch.this,
						UnderstanderDemo.class, "temp");
				break;
			case R.id.tv_road_tobeach:
				// Toast.makeText(QuickSearch.this, "等待接口改善",
				// Toast.LENGTH_SHORT).show();
				startIntent("开车到假日海滩", QuickSearch.this,
						UnderstanderDemo.class, "temp");
				break;
			case R.id.tv_road_youyitojing:
				// Toast.makeText(QuickSearch.this, "等待接口改善",
				// Toast.LENGTH_SHORT).show();
				startIntent("从友谊商场到京华城怎么走", QuickSearch.this,
						UnderstanderDemo.class, "temp");
				break;
			case R.id.tv_life_weather:
				startIntent("今天的天气怎么样", QuickSearch.this,
						UnderstanderDemo.class, "temp");
				break;
			case R.id.tv_life_cook:
				startIntent("回锅肉怎么做", QuickSearch.this, UnderstanderDemo.class,
						"temp");
				break;
			case R.id.tv_life_topnews:
				startIntent("今天的头条新闻", QuickSearch.this,
						UnderstanderDemo.class, "temp");
				break;
			case R.id.tv_soft_game:
				startIntent("最好玩的游戏", QuickSearch.this, UnderstanderDemo.class,
						"temp");
				break;
			case R.id.tv_soft_weixin:
				startIntent("下载微信", QuickSearch.this, UnderstanderDemo.class,
						"temp");
				break;
			case R.id.tv_soft_puzzle:
				startIntent("益智类游戏", QuickSearch.this, UnderstanderDemo.class,
						"temp");
				break;
			case R.id.tv_music_hotmusic:
				startIntent("最近比较火的歌曲", QuickSearch.this,
						UnderstanderDemo.class, "temp");
				break;
			case R.id.tv_music_movie:
				startIntent("我要看电影", QuickSearch.this, UnderstanderDemo.class,
						"temp");
				break;
			case R.id.tv_music_wumeiniang:
				startIntent("我要看武媚娘传奇", QuickSearch.this,
						UnderstanderDemo.class, "temp");
				break;
			case R.id.tv_travel_tickettobeijing:
				startIntent("预订30号到北京的机票", QuickSearch.this,
						UnderstanderDemo.class, "temp");
				break;
			case R.id.tv_travel_checkticket:
				startIntent("查询海口到北京的火车票", QuickSearch.this,
						UnderstanderDemo.class, "temp");
				break;
			case R.id.tv_travel_hotel:
				startIntent("预订明天北京的酒店", QuickSearch.this,
						UnderstanderDemo.class, "temp");
				break;
			case R.id.tv_note_meeting:
				startIntent("明天11点钟提醒我开会", QuickSearch.this,
						UnderstanderDemo.class, "temp");
				break;
			case R.id.tv_note_getup:
				startIntent("明天早上八点叫我起床", QuickSearch.this,
						UnderstanderDemo.class, "temp");
				break;
			case R.id.tv_note_medicine:
				startIntent("下午两点提醒我买药", QuickSearch.this,
						UnderstanderDemo.class, "temp");
				break;
			case R.id.tv_command_mingchao:
				startIntent("明朝从哪一年开始", QuickSearch.this,
						UnderstanderDemo.class, "temp");
				break;
			case R.id.tv_command_fruit:
				startIntent("冬天吃什么水果好", QuickSearch.this,
						UnderstanderDemo.class, "temp");
				break;
			case R.id.tv_command_ip:
				startIntent("怎么查看电脑的ip地址", QuickSearch.this,
						UnderstanderDemo.class, "temp");
				break;
			}
		}
	};

	OnClickListener onclickForQuick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.lt_fun:
				img_fun = (ImageView) findViewById(R.id.img_fun);
				if (isFun) {
					lt_quickBg.setVisibility(View.VISIBLE);
					img_fun.setBackgroundResource(R.drawable.icon_down);
					isFun = false;
				} else {
					lt_quickBg.setVisibility(View.GONE);
					img_fun.setBackgroundResource(R.drawable.icon_left);
					isFun = true;
				}
				break;
			case R.id.btn_back:
				finish();
				break;
			case R.id.lt_road:
				if (isRoad) {
					lt_quickRoad.setVisibility(View.VISIBLE);
					img_road.setBackgroundResource(R.drawable.icon_down);
					isRoad = false;
				} else {
					lt_quickRoad.setVisibility(View.GONE);
					img_road.setBackgroundResource(R.drawable.icon_left);
					isRoad = true;
				}
				break;
			case R.id.lt_life:
				if (isLife) {
					lt_quickLife.setVisibility(View.VISIBLE);
					img_life.setBackgroundResource(R.drawable.icon_down);
					isLife = false;
				} else {
					lt_quickLife.setVisibility(View.GONE);
					img_life.setBackgroundResource(R.drawable.icon_left);
					isLife = true;
				}
				break;
			case R.id.lt_soft:
				if (isSoft) {
					lt_quickSoft.setVisibility(View.VISIBLE);
					img_soft.setBackgroundResource(R.drawable.icon_down);
					isSoft = false;
				} else {
					lt_quickSoft.setVisibility(View.GONE);
					img_soft.setBackgroundResource(R.drawable.icon_left);
					isSoft = true;
				}
				break;
			case R.id.lt_music:
				if (isMusic) {
					lt_quickMusic.setVisibility(View.VISIBLE);
					img_music.setBackgroundResource(R.drawable.icon_down);
					isMusic = false;
				} else {
					lt_quickMusic.setVisibility(View.GONE);
					img_music.setBackgroundResource(R.drawable.icon_left);
					isMusic = true;
				}
				break;
			case R.id.lt_travel:
				if (isTravel) {
					lt_quicktravel.setVisibility(View.VISIBLE);
					img_travel.setBackgroundResource(R.drawable.icon_down);
					isTravel = false;
				} else {
					lt_quicktravel.setVisibility(View.GONE);
					img_travel.setBackgroundResource(R.drawable.icon_left);
					isTravel = true;
				}
				break;
			case R.id.lt_note:
				if (isNote) {
					lt_quickNote.setVisibility(View.VISIBLE);
					img_note.setBackgroundResource(R.drawable.icon_down);
					isNote = false;
				} else {
					lt_quickNote.setVisibility(View.GONE);
					img_note.setBackgroundResource(R.drawable.icon_left);
					isNote = true;
				}
				break;
			case R.id.lt_command:
				if (isCommand) {
					lt_quickCommand.setVisibility(View.VISIBLE);
					img_command.setBackgroundResource(R.drawable.icon_down);
					isCommand = false;
				} else {
					lt_quickCommand.setVisibility(View.GONE);
					img_command.setBackgroundResource(R.drawable.icon_left);
					isCommand = true;
				}
				break;
			}
		}
	};

}
