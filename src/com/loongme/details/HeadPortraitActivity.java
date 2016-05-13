package com.loongme.details;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.loongme.activity.R;
import com.loongme.base.BaseActivity;
import com.loongme.business.Helpers;
import com.loongme.personage.MeDataActivity;
import com.loongme.util.UIUtils;

public class HeadPortraitActivity extends BaseActivity implements
		OnClickListener {
	private Button btn_camera, btn_photo_album, btn_head_portrait_cancel,
			btn_head_portrait;
	private ImageView iv_head_portrait, _head_portrait;
	// 设置头像
	private static final int IMAGE_REQUEST_CODE = 0; // 请求码 本地图片
	private static final int CAMERA_REQUEST_CODE = 1; // 拍照
	private static final int RESULT_REQUEST_CODE = 2; // 裁剪
	private static final String SAVE_AVATORNAME = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/ssb/headportrait.png";// 保存的图片名
	private static final String SAVE_AVATORNAME2 = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/ssb/as.png";// 保存的图片名
	private Bitmap headporBitmap;
	private InputStream InputStream;
	String url = "http://120.24.37.141:8080/LoginAndResigister/HeadImageServlet";
	private File f;
	private StringBuffer str;
	private String aaaa;
	private String ss;
	private byte[] bitmaps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_head_portrait);
		initView();
	}

	private void initView() {
		btn_camera = (Button) findViewById(R.id.btn_camera);
		btn_photo_album = (Button) findViewById(R.id.btn_photo_album);
		btn_head_portrait_cancel = (Button) findViewById(R.id.btn_head_portrait_cancel);
		btn_head_portrait = (Button) findViewById(R.id.btn_head_portrait);
		iv_head_portrait = (ImageView) findViewById(R.id.iv_head_portrait);
		_head_portrait = (ImageView) findViewById(R.id._head_portrait);
		btn_head_portrait_cancel.setOnClickListener(this);
		btn_photo_album.setOnClickListener(this);
		btn_camera.setOnClickListener(this);
		btn_head_portrait.setOnClickListener(this);
		iv_head_portrait.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_head_portrait:
			startActivity(new Intent(getApplicationContext(),
					MeDataActivity.class));
			deleteLicenseCache();
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
			break;
		case R.id.btn_camera:// 相机
			Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			it.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(SAVE_AVATORNAME)));
			startActivityForResult(it, CAMERA_REQUEST_CODE);
			break;
		case R.id.btn_head_portrait:// 提交
			try {
				UploadBitmap();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.btn_photo_album:// 相册
			Intent it2 = new Intent();
			it2.setType("image/*");
			it2.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(it2, IMAGE_REQUEST_CODE);
			break;
		case R.id.btn_head_portrait_cancel:
			startActivity(new Intent(getApplicationContext(),
					MeDataActivity.class));
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
			break;

		default:
			break;
		}
	}

	private void UploadBitmap() throws FileNotFoundException {

		HttpUtils httpUtils = new HttpUtils(5000);
		RequestParams params = new RequestParams();
		JSONObject object = new JSONObject();
		try {
			object.put("userId", Helpers.getUserInfo(this).getUserId());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		params.addBodyParameter("image0.png", "");
		params.addBodyParameter("image0.png", new FileInputStream(
				SAVE_AVATORNAME2), new File(SAVE_AVATORNAME2).length());
		params.addQueryStringParameter("requestMessage", object.toString());
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onFailure(HttpException arg0, String arg1) {
						UIUtils.showToast(HeadPortraitActivity.this, "头像上传失败");
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						if (arg0.statusCode == 200) {
							System.out.println("---touxiang----" + arg0.result);
							com.alibaba.fastjson.JSONObject json = JSON
									.parseObject(arg0.result);
							boolean status = json.getBoolean("status");
							if (status == true) {
								// deleteLicenseCache();
								startActivity(new Intent(
										getApplicationContext(),
										MeDataActivity.class));
								deleteLicenseCache();
								overridePendingTransition(
										R.anim.base_slide_left_in,
										R.anim.base_slide_left_out);
								finish();
							}
						}
					}
				});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_CANCELED) {
			switch (requestCode) {
			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				startPhotoZoom(Uri.fromFile(new File(SAVE_AVATORNAME)));
				break;
			case RESULT_REQUEST_CODE:
				if (data != null) {
					getImageToView(data);
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 裁剪图片方法实现
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, RESULT_REQUEST_CODE);
	}

	/**
	 * 保存裁剪之后的图片数据
	 */
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			headporBitmap = toRoundBitmap(photo);// 设置圆形头像
			saveMyBitmap(headporBitmap); // 保存裁剪后的图片到SD
			bitmaps = getBitmapByte(headporBitmap);
			for (byte b : bitmaps) {
				ss = Integer.toBinaryString(b);
			}
			_head_portrait.setImageBitmap(headporBitmap);// 显示
		}
	}

	public byte[] getBitmapByte(Bitmap bitmap) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("ssss:::" + out.toByteArray());
		return out.toByteArray();
	}

	/**
	 * 将头像保存在SDcard
	 */
	public void saveMyBitmap(Bitmap bitmap) {
		File f = new File(SAVE_AVATORNAME);
		File parentFile = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/ssb");
		if (!parentFile.exists()) {
			parentFile.mkdir();
		}
		if (!f.exists()) {
			try {
				f.createNewFile();
				FileOutputStream fOut = new FileOutputStream(f);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
				fOut.flush();
				fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			FileInputStream fis;
			try {
				fis = new FileInputStream(f);
				byte[] b;
				b = new byte[fis.available()];
				str = new StringBuffer();
				fis.read(b);
				for (byte bs : b) {
					aaaa = Integer.toBinaryString(bs);
				}
				File apple = new File(SAVE_AVATORNAME2);// 把字节数组的图片写到另一个地方
				FileOutputStream fos = new FileOutputStream(apple);
				fos.write(b);
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startActivity(new Intent(getApplicationContext(),
					MeDataActivity.class));
			deleteLicenseCache();
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 转换图片成圆形
	 * 
	 * @param bitmap
	 *            传入Bitmap对象
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}
		Bitmap output = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		return output;
	}

	/**
	 * 删除缓存
	 */
	private void deleteLicenseCache() {
		File file = new File(SAVE_AVATORNAME2);
		if (file.exists()) {
			file.delete();
		}
		File file2 = new File(SAVE_AVATORNAME);
		if (file2.exists()) {
			file2.delete();
		}
	}

}
