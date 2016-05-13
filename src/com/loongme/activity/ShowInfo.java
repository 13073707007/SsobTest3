package com.loongme.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.loongme.activity.R;
import com.loongme.base.BaseActivity;

public class ShowInfo extends BaseActivity {
	private static final String IPHONE_USERAGENT = "Mozilla/5.0 (iPhone; U; "
			+ "CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 "
			+ "(KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16";
	private boolean isError = false;
	private String currentURL;
	private WebSettings settings;
	private WebView wv;
	private String showinfo;
	private Button btn_back;
	private Button btn_setting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_webview);
		findView();
		wv = (WebView) findViewById(R.id.webView1);
		showinfo = getUri();
		System.out.println("这是获取到的链接" + showinfo);
		initWebView();
		showWebView(showinfo);
	}

	/**
	 * 初始化按钮，设置监听。
	 */
	private void findView() {
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_setting = (Button) findViewById(R.id.btn_setting);
		btn_back.setVisibility(View.VISIBLE);
		btn_setting.setVisibility(View.GONE);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowInfo.this.finish();
			}
		});
	}

	/**
	 * 重新加载当前页面，关闭网页中打开的声音
	 */
	protected void onPause() {
		wv.reload();
		super.onPause();
	}

	private String getUri() {
		Bundle bundle = this.getIntent().getExtras();
		String u = bundle.getString("uri");
		if (u != null) {
			System.out.println("this is SHOWn>>" + u);
		} else {
			System.out.println("this is SHOWn>> is null!!!");
		}
		return u;
	}

	private void showWebView(String uri) {
		wv.loadUrl(uri);
	}

	/**
	 * 初始化web视图
	 */
	@SuppressLint("NewApi")
	private void initWebView() {
		wv.clearCache(true);
		wv.clearHistory();
		wv.requestFocus();
		settings = wv.getSettings();
		// settings.setUserAgentString(IPHONE_USERAGENT);
		String userAgen = settings.getUserAgentString();
		Log.e("USERAGRN", userAgen);
		settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		settings.setBlockNetworkImage(true);
		settings.setJavaScriptEnabled(true);// 支持javascript
		settings.setLoadWithOverviewMode(true);
		settings.setUseWideViewPort(true);
		settings.setDomStorageEnabled(true);
		settings.setGeolocationEnabled(true);
		settings.setSavePassword(true);
		settings.setSaveFormData(true);
		settings.setLoadWithOverviewMode(true);
		settings.setUseWideViewPort(true);
		// settings.setPluginsEnabled(true);
		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true);
		settings.setDisplayZoomControls(false);
		// settings.setBuiltInZoomControls(true);
		settings.setAllowFileAccess(true);
		settings.setAppCacheEnabled(true);
		settings.setDomStorageEnabled(true);
		settings.setDefaultTextEncodingName("UTF-8");
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		wv.setScrollBarStyle(0);
		wv.setBackgroundColor(0);
		wv.setWebViewClient(new webViewClient());
		wv.addJavascriptInterface(new JavascriptInterface(this), "imagelistner");
	}

	private void openZoomImage(String url) {
		System.out.println("openZoomImage");
		Intent intent = new Intent();
		intent.putExtra("image", url);
		// intent.setClass(ShowInfo.this, ZoomImageActivity.class);
		ShowInfo.this.startActivity(intent);

	}

	// js通信接口
	public class JavascriptInterface {

		private Context context;

		public JavascriptInterface(Context context) {
			this.context = context;
		}

		public void openImage(String img) {
			System.out.println(img);
			// zoomImage(img);
			openZoomImage(img);
			//
		}

		public void saveImage(String img) {
			Log.e("JavascriptInterface", img);
		}
	}

	class webViewClient extends WebViewClient {
		// 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			System.out.println("shouldOverrideUrlLoading：   " + url);
			currentURL = url;
			view.loadUrl(url);
			if (url.endsWith(".mp4") || url.endsWith(".3gp")) {
				// ShowInfo.this.startActivity(Methods.getVideoFileIntent(url));
			}
			return super.shouldOverrideUrlLoading(view, url);
		}

		// 开始加载网页时要做的工作
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			// WebProcessDialog.show(WebActivity.this);
		}

		// 加载完成时要做的工作
		@Override
		public void onPageFinished(WebView view, String url) {
			System.out.println("onPageFinished:   " + url);
			// WebProcessDialog.close(WebActivity.this);
			currentURL = url;
			if (url.endsWith(".apk")) {
				// downloadDialog(url);
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(i);
				finish();
			}
			// imgWebBg.setVisibility(View.GONE);
			settings.setBlockNetworkImage(false);
			settings.setJavaScriptEnabled(true);
			// addImageClickListner();
			// strAid = getNewsAID(url);
			/*
			 * if (url.contains(CST_url.NEWS_COMMENT_PAGE)) {
			 * shareView.setVisibility(View.GONE); isCommentPage = true; } else
			 * { shareView.setVisibility(View.VISIBLE); isCommentPage = false; }
			 * if ("".equals(strAid)) { isFromOther = true;
			 * 
			 * if (isShop) { replyView.setVisibility(View.VISIBLE); } else {
			 * replyView.setVisibility(View.INVISIBLE); } } else { isFromOther =
			 * false; replyView.setVisibility(View.VISIBLE); }
			 */
			// setGreyIcon(url);
			super.onPageFinished(view, url);
		}

		// 重写此方法可以让webview处理https请求
		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler,
				SslError error) {
			handler.proceed();
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			wv.loadData("", "text/html", "UTF-8");
			isError = true;
			// Validate.Toast(WebActivity.this,
			// getResources().getString(R.string.checkNetwork));
		}
	}

}
