package com.loongme.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.loongme.activity.R;
import com.loongme.activity.ShowInfo;
import com.loongme.activity.UnderstanderDemo;
import com.loongme.com.model.chatBean;
import com.loongme.util.CustomLinearLayout;
import com.loongme.util.Spilt;

public class OnlyRightShowadapter extends BaseAdapter {
	//private  static chatBean bean;
	public static AnimationDrawable anim;
	private String text;
	private View view;
	private ImageView img;
	private View conver;
	// 默认发音人
	private String voicer = "xiaoyan";

	@Override
	public int getItemViewType(int position) {
		int type = super.getItemViewType(position);
		chatBean bean = list.get(position);
		if (bean != null) {
			type = bean.getType();
		}
		return type;
	}

	@Override
	public int getViewTypeCount() {
		return 3;
	}

	public void setDataSource(List<chatBean> data) {
		this.list = data;
	}

	public void clearAll() {
		this.list.clear();
	}

	public SpeechSynthesizer mTts;
	private final int TYPE_LIST = 2;
	private final int TYPE_RECEIVER_TXT = 0;
	private final int TYPE_SEND_TXT = 1;
	private List<chatBean> list;
	private static Context mcontext;
	private LayoutInflater minflater;
	private NestingAdapter nestAdapter;
	private String result;
	private ImageView iv_touxiang;

	public OnlyRightShowadapter(Context context, List<chatBean> chatList, String result) {
		this.list = chatList;
		this.mcontext = context;
		this.result = result;
		/*mTts = SpeechSynthesizer.createSynthesizer(context, null);
		mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);// 设置发音人
		mTts.setParameter(SpeechConstant.SPEED, "70");// 设置语速
		mTts.setParameter(SpeechConstant.VOLUME, "80");*/
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return super.isEnabled(position);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		view = convertView;
		final chatBean bean = list.get(position);

		String title = bean.getTextTitle();
		int type = bean.getType();

		minflater = LayoutInflater.from(mcontext);
		final ViewHolder holder = new ViewHolder();
		TextView textContent;
		final ImageView img_voice;

		if (type == TYPE_SEND_TXT) {

			convertView = minflater.inflate(
					R.layout.chatting_item_msg_text_right, null);
			iv_touxiang=(ImageView) convertView.findViewById(R.id.iv_touxiang);
		/*} else if (type == TYPE_RECEIVER_TXT) {
			convertView = minflater.inflate(R.layout.chatting_item_left, null);*/

		} else if (type == TYPE_LIST) {

			convertView = minflater.inflate(R.layout.activity_nesting_listview,
					null);
			convertView.setClickable(false);
			holder.lv_nesting = (CustomLinearLayout) convertView
					.findViewById(R.id.lv_nesting);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_count = (TextView) convertView
					.findViewById(R.id.tv_count);
			convertView.setTag(holder);
		}
		if (type != TYPE_LIST) {
			textContent = (TextView) convertView
					.findViewById(R.id.tv_chatcontent);
			img_voice = (ImageView) convertView.findViewById(R.id.img_voice);
			if (UnderstanderDemo.isSpeak) {
				img_voice
						.setBackgroundResource(R.drawable.icon_voice_fubenleft);
			} else {
				img_voice
						.setBackgroundResource(R.drawable.icon_voice_fubenleft_stop);
			}
			holder.tvContent = textContent;
			holder.iv_voice = img_voice;
			convertView.setTag(holder);
			if (bean.getText() != null) {
				if (bean.getText().contains("http")
						|| bean.getText().contains("zhidao")||bean.getText().contains("tel:")) {
					System.out.println(bean.getText());
					if (bean.getText().contains("zhidao")) {
						textContent.setText(Spilt.spiltBaidu(bean.getText()));
					} else {
						textContent.setText(Html
								.fromHtml(bean.getText().trim()
						                ));
						System.out.println(Html
								.fromHtml(bean.getText().trim()
						                ));
//						textContent.setText(bean.getText().trim());
						textContent.setMovementMethod(LinkMovementMethod.getInstance());
						addLink(textContent);
					}
					Spanned t = Html.fromHtml(bean.getText());
					text = String.valueOf(t);
					
					System.out.println(Spilt.splitTextUrl(bean.getText()));
					img_voice.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							OnlyRightShowadapter.this.notifyDataSetChanged();
							if (UnderstanderDemo.mTts.isSpeaking()) {
								UnderstanderDemo.isSpeak = false;
								img_voice
										.setBackgroundResource(R.drawable.icon_voice_fubenleft_stop);
								UnderstanderDemo.mTts.stopSpeaking();
							} else {
								UnderstanderDemo.isSpeak = true;
								img_voice
										.setBackgroundResource(R.drawable.icon_voice_fubenleft);
								if (bean.getText().contains("zhidao")) {
									UnderstanderDemo.mTts.startSpeaking(
											Spilt.spiltBaidu(bean.getText()),
											mSynListener);
								} else {
									UnderstanderDemo.mTts.startSpeaking(text, mSynListener);
								}
							}

						}
					});
//					textContent.setOnClickListener(new OnClickListener() {
//
//						@Override
//						public void onClick(View arg0) {
//							// TODO Auto-generated method stub
//							Intent intent = new Intent();
//							intent.setClass(mcontext, ShowInfo.class);
//							Bundle bundle = new Bundle();
//							bundle.putString("uri",
//									Spilt.splitTextUrl(bean.getText()));
//							intent.putExtras(bundle);
//							mcontext.startActivity(intent);
//						}
//					});
				} else {

					img_voice.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							OnlyRightShowadapter.this.notifyDataSetChanged();
							if (UnderstanderDemo.mTts.isSpeaking()) {
								UnderstanderDemo.isSpeak = false;
								img_voice
										.setBackgroundResource(R.drawable.icon_voice_fubenleft);
								UnderstanderDemo.mTts.stopSpeaking();
							} else {
								UnderstanderDemo.isSpeak = true;
								img_voice
										.setBackgroundResource(R.drawable.icon_voice_fubenleft_stop);
								UnderstanderDemo.mTts.startSpeaking(bean.getText(), mSynListener);
							}
						}
					});
					textContent.setText(bean.getText().trim());
				}

			} else {
				textContent.setText("对不起，您问的问题太难了，换个问题试试");
				img_voice.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						OnlyRightShowadapter.this.notifyDataSetChanged();
						if (UnderstanderDemo.mTts.isSpeaking()) {
							UnderstanderDemo.isSpeak = false;
							img_voice
									.setBackgroundResource(R.drawable.icon_voice_fubenleft);
							UnderstanderDemo.mTts.stopSpeaking();
						} else {
							UnderstanderDemo.isSpeak = true;
							img_voice
									.setBackgroundResource(R.drawable.icon_voice_fubenleft_stop);
							UnderstanderDemo.mTts.startSpeaking("对不起，您问的问题太难了，换个问题试试",
									mSynListener);
						}
					}
				});
			}

		} else {
			nestAdapter = new NestingAdapter(mcontext,
					R.layout.activity_nesting_item);
			nestAdapter.addItem(bean.getData());
			List<String[]> li = bean.getData();
			if (bean.getText() != null) {
				System.out.println("this is bean.getText()" + bean.getText());
			} else {
				System.out.println("bean.getText() is null");
			}
			holder.tv_name.setText(bean.getText());
			int sum = li.size();
			// holder.tv_count.setText("( " + sum + "个" + " )");
			holder.lv_nesting.setCustomAdapter(nestAdapter);
			holder.lv_nesting.setOrientation(LinearLayout.VERTICAL);
			holder.lv_nesting.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

				}
			});

		}

		return convertView;
	}

	private SynthesizerListener mSynListener = new SynthesizerListener() {

		@Override
		public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onCompleted(SpeechError arg0) {
			// UnderstanderDemo.isSpeak = false;
			// chatAdapter.anim.stop();
			// UnderstanderDemo.isSpeak = false;
		}

		@Override
		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSpeakBegin() {
			// chatAdapter.anim.start();
			// UnderstanderDemo.isSpeak = true;
		}

		@Override
		public void onSpeakPaused() {
			// chatAdapter.anim.stop();
			// UnderstanderDemo.isSpeak = false;
		}

		@Override
		public void onSpeakProgress(int arg0, int arg1, int arg2) {

		}

		@Override
		public void onSpeakResumed() {
		}

	};

	class ViewHolder {
		public ImageView img_voice;
		public TextView tv_count;
		public TextView tv_name;
		public CustomLinearLayout lv_nesting;
		public ImageView iv_userhead;
		public TextView tvSendTime;
		public TextView tvUserName;
		public TextView tvContent;
		public ImageView iv_voice;
		public int type;
	}
	
	private void addLink(TextView tv) {
		
        CharSequence text = tv.getText();
        if (text instanceof Spannable) {
                int end = text.length();
                Spannable sp = (Spannable) tv.getText();
                
                URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);
                SpannableStringBuilder style = new SpannableStringBuilder(text);
                style.clearSpans();// should clear old spans
                for (URLSpan url : urls) {
                        MyURLSpan myURLSpan = new MyURLSpan(url.getURL());
                        style.setSpan(myURLSpan, sp.getSpanStart(url),
                                        sp.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                tv.setText(style);
        }
}
	
	private static class MyURLSpan extends ClickableSpan {

        private String mUrl;

        MyURLSpan(String url) {
                mUrl = url;
        }

        @Override
        public void onClick(View widget) {
                // TODO Auto-generated method stub
               // Toast.makeText(mcontext, "hello!" + mUrl, Toast.LENGTH_LONG).show();
                Log.e("URL", mUrl);
                if (mUrl.contains("tel:")) {
                	String[] first = mUrl.split("tel:");
                	String fis = first[1];
                	//Toast.makeText(mcontext, "hello!" + fis, Toast.LENGTH_LONG).show();
                	// 用intent启动拨打电话
					Intent intent = new Intent(
							Intent.ACTION_DIAL,
							Uri.parse("tel:" + fis.trim()));
					mcontext.startActivity(intent);
                }else {
                	Intent intent = new Intent();
					intent.setClass(mcontext, ShowInfo.class);
					Bundle bundle = new Bundle();
					//bundle.putString("uri",
							//Spilt.splitTextUrl(bean.getText()));
					bundle.putString("uri", mUrl);
					intent.putExtras(bundle);
					mcontext.startActivity(intent);
                }
        }
}

}
