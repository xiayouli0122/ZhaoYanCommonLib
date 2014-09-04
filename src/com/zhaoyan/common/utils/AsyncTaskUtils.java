package com.zhaoyan.common.utils;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;

public class AsyncTaskUtils {

	/**
	 * AsyncTask的版本判断执行
	 * 
	 * @param task
	 *            AsyncTask
	 * @param args
	 *            参数
	 */
	@SuppressLint("NewApi")
	public static <T> void execute(AsyncTask<T, ?, ?> task, T... args) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.DONUT) {
			throw new UnsupportedOperationException(
					"This class can only be used on API 4 and newer.");
		}
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			task.execute(args);
		} else {
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, args);
		}
	}
}
