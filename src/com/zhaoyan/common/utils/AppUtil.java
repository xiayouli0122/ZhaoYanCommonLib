package com.zhaoyan.common.utils;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.util.Log;

public class AppUtil {
	private static final String TAG = "AppUtil";
	
	public static void installApk(Context context, String apkFilePath) {
		if (apkFilePath.endsWith(".apk")) {
			installApk(context, new File(apkFilePath));
		}
	}

	public static void installApk(Context context, File file) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		context.startActivity(intent);
	}
	
	public static void uninstallApp(Context context, String packageName){
		Uri packageUri = Uri.parse("package:" + packageName);
		Intent deleteIntent = new Intent();
		deleteIntent.setAction(Intent.ACTION_DELETE);
		deleteIntent.setData(packageUri);
		context.startActivity(deleteIntent);
	}
	
	public static String getAppLabel(String packageName, PackageManager pm){
		ApplicationInfo applicationInfo = null;
		try {
			applicationInfo = pm.getApplicationInfo(packageName, 0);
		} catch (NameNotFoundException e) {
			Log.e(TAG, "getAppLabel.name not found:" + packageName);
			Log.e(TAG, e.toString());
			return null;
		}
		return applicationInfo.loadLabel(pm).toString();
	}
	
	public static String getAppVersion(String packageName, PackageManager pm){
		String version = "";
		try {
			version = pm.getPackageInfo(packageName, 0).versionName;
		} catch (NameNotFoundException e) {
			Log.e(TAG, "getAppVersion.name not found:" + packageName);
			e.printStackTrace();
		}
		return version;
	}
	
	public static String getAppSourceDir(String packageName, PackageManager pm){
		ApplicationInfo applicationInfo = null;
		try {
			applicationInfo = pm.getApplicationInfo(packageName, 0);
		} catch (NameNotFoundException e) {
			Log.e(TAG, "getAppSourceDir:" + packageName + " name not found.");
			e.printStackTrace();
		}
		return applicationInfo.sourceDir;
	}
}
