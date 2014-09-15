package com.zhaoyan.common.imageloader;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;

public class AsyncVideoLoader {
	// SoftReference是软引用，是为了更好的为了系统回收变量
	public static HashMap<Long, SoftReference<Bitmap>> bitmapCaches;
	private ContentResolver contentResolver = null;

	public AsyncVideoLoader(Context context) {
		bitmapCaches = new HashMap<Long, SoftReference<Bitmap>>();
		contentResolver = context.getContentResolver();
	}

	public Bitmap loadBitmap(final long videoId,
			final ILoadVideoCallback videoCallback) {
		if (bitmapCaches.containsKey(videoId)) {
			// 从缓存中获取
			SoftReference<Bitmap> softReference = bitmapCaches.get(videoId);
			Bitmap bitmap = softReference.get();
			if (null != bitmap) {
				return bitmap;
			}
		}

		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				videoCallback.onObtainBitmap((Bitmap) message.obj, videoId);
			}
		};

		new Thread() {
			@Override
			public void run() {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inDither = false;
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				// get video thumbail
				Bitmap bitmap = MediaStore.Video.Thumbnails.getThumbnail(
						contentResolver, videoId, Images.Thumbnails.MINI_KIND,
						options);
				bitmapCaches.put(videoId, new SoftReference<Bitmap>(bitmap));
				Message message = handler.obtainMessage(0, bitmap);
				handler.sendMessage(message);
			}
		}.start();

		return null;
	}

	/**
	 * 异步加载的回调接口
	 */
	public interface ILoadVideoCallback {
		public void onObtainBitmap(Bitmap bitmap, long id);
	}
}
