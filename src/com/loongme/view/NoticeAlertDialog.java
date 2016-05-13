
package com.loongme.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

public class NoticeAlertDialog extends AlertDialog {

	protected NoticeAlertDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	// 先调用构造方法在调用oncreate方法
	private static boolean isShow = true;
	private Context context;
	private String mYtitle;
	private String mYmsg;

	// private MyDialog myDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

//	public NoticeAlertDialog() {
//		super(ClientEngine.getInstance().getBaseActivity());
//		this.setContext(ClientEngine.getInstance().getBaseActivity());
//	}

	public NoticeAlertDialog(Context context, int theme) {
		super(context, theme);
		this.setContext(context);
	}
	@Override
	public void show() {
		super.show();
	}
//
//	public Context getContext() {
//		return context;
//	}

	public void setContext(Context context) {
		this.context = context;
	}

	public static class Builder {
		private Context context;
		private String title;
		private String message;
		private String positiveButtonText;
		private String negativeButtonText;
		private View contentView;
		private DialogInterface.OnClickListener positiveButtonClickListener,
				negativeButtonClickListener;

		// private TextView msg=(TextView)findViewById(R.id.message);
		public Builder(Context context) {
			this.context = context;
		}

		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}

		public Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;
		}

		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}

		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}

		public Builder setPositiveButton(int positiveButtonText,
				DialogInterface.OnClickListener listener) {
			this.positiveButtonText = (String) context
					.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}

		public Builder setPositiveButton(String positiveButtonText,
				DialogInterface.OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}

		public boolean setCancelable(boolean cancelable) {

			isShow = cancelable;
			return isShow;
		}

		public Builder setNegativeButton(int negativeButtonText,
				DialogInterface.OnClickListener listener) {
			this.negativeButtonText = (String) context
					.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}

//		public Builder setClickFunction(final Command[] commands) {
//			if (commands != null) {
//				if (commands[0] == null) {
//					this.setPositiveButton("知道了", new OnClickListener() {
//
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//						}
//					});
//				} else {
//					this.setPositiveButton(commands[0].getLabel(),
//							new OnClickListener() {
//
//								@Override
//								public void onClick(DialogInterface dialog,
//										int which) {
//									commands[0].onClick();
//								}
//							});
//				}
//				if (commands[1] != null) {
//					this.setNegativeButton(commands[1].getLabel(),
//							new OnClickListener() {
//
//								@Override
//								public void onClick(DialogInterface dialog,
//										int which) {
//									commands[1].onClick();
//								}
//							});
//				}
//			}
//			return this;
//		}

		public Builder setNegativeButton(String negativeButtonText,
				DialogInterface.OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}

//		public NoticeAlertDialog show() {
//			NoticeAlertDialog dialog = create();
//			dialog.show();
//			return dialog;
//		}

//		public NoticeAlertDialog create() {
//			LayoutInflater inflater = (LayoutInflater) context
//					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//			// instantiate the dialog with the custom Theme
//			final NoticeAlertDialog dialog = new NoticeAlertDialog(context, R.style.NoticeAlertDialog);
//			dialog.setCanceledOnTouchOutside(false);// android
//													// 4.0以上dialog点击其他地方也会消失false以后就只能点击按钮消失
//
//			View layout = inflater.inflate(R.layout.dialog, null);
//
//			ScrollView sv = (ScrollView) layout
//					.findViewById(R.id.NoticeAlertDialogScrollView);
//
//			int width = context.getResources().getDrawable(R.drawable.note_top)
//					.getIntrinsicWidth();
//
//			sv.getLayoutParams().width = width - 30;
//
//			if (message != null && message.length() > 160) {
//
//				sv.getLayoutParams().height = MainMenu.screenHeight * 2 / 5;
//
//			}
//
//			dialog.addContentView(layout, new LayoutParams(
//					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
//			// set the dialog title
//			((TextView) layout.findViewById(R.id.title)).setText(title);
//
//			// set the confirm button
//			if (positiveButtonText != null) {
//
//				((Button) layout.findViewById(R.id.dialog_button_ok))
//						.setText(positiveButtonText);
//
//				if (positiveButtonClickListener != null) {
//
//					((Button) layout.findViewById(R.id.dialog_button_ok))
//							.setOnClickListener(new View.OnClickListener() {
//
//								public void onClick(View v) {
//
//									positiveButtonClickListener.onClick(dialog,
//											DialogInterface.BUTTON_POSITIVE);
//									dialog.dismiss();
//								}
//							});
//				}
//			} else {
//
//				// if no confirm button just set the visibility to GONE
//				layout.findViewById(R.id.dialog_button_ok).setVisibility(
//						View.GONE);
//			}
//			if (negativeButtonText != null) {
//
//				((Button) layout.findViewById(R.id.dialog_button_cancel))
//						.setText(negativeButtonText);
//				if (negativeButtonClickListener != null) {
//
//					// ((Button) layout.findViewById(R.id.dialog_button_ok)).
//
//					((Button) layout.findViewById(R.id.dialog_button_cancel))
//							.setOnClickListener(new View.OnClickListener() {
//
//								public void onClick(View v) {
//
//									negativeButtonClickListener.onClick(dialog,
//											DialogInterface.BUTTON_NEGATIVE);
//
//									dialog.dismiss();
//								}
//							});
//				}
//			} else {
//
//				LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ((Button) layout
//						.findViewById(R.id.dialog_button_ok)).getLayoutParams();
//
//				params.setMargins(30, 0, 30, 0);// 这个只是根据我自己的需要调节按钮的位置。大家可以根据自己的需要来调节
//				params.width = width - 60;
//				((Button) layout.findViewById(R.id.dialog_button_ok))
//						.setLayoutParams(params);
//
//				// if no confirm button just set the visibility to GONE
//				layout.findViewById(R.id.dialog_button_cancel).setVisibility(
//						View.GONE);
//			}
//
//			// set the cancel button
//			// set the content message
//
//			if (message != null) {
//
//				((TextView) layout.findViewById(R.id.msg)).setText(message);
//
//			} else if (contentView != null) {
//
//				// if no message set
//				// add the contentView to the dialog body
//
//				((LinearLayout) layout.findViewById(R.id.body))
//						.removeAllViews();
//
//				((LinearLayout) layout.findViewById(R.id.body)).addView(
//						contentView, new LayoutParams(
//								LayoutParams.WRAP_CONTENT,
//								LayoutParams.WRAP_CONTENT));
//			}
//			dialog.setContentView(layout);
//			return dialog;
//		}
	}
}
