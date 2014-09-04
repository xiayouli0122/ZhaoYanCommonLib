package com.zhaoyan.common.utils;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.zhaoyan.common.lib.R;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;

public class APKUtil {
	
	/*
     * 采用了新的办法获取APK图标，之前的失败是因为android中存在的一个BUG,通过
     * appInfo.publicSourceDir = apkPath;来修正这个问题，详情参见:
     * http://code.google.com/p/android/issues/detail?id=9151
     */
	/**
	 * Get apk name from an apk file which is not installed.
	 * 
	 * @param context
	 * @param apkPath
	 * @return
	 */
	public static Drawable getApkIcon2(Context context, String apkPath) {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;
            try {
                return appInfo.loadIcon(pm);
            } catch (OutOfMemoryError e) {
            	e.printStackTrace();
            }
        }
        return context.getResources().getDrawable(
				R.drawable.icon_apk);
    }
	
	/**
	 * Get apk name from an apk file which is not installed.
	 * 
	 * @param context
	 * @param apkPath
	 * @return
	 */
	public static String getApkLabel(Context context, String apkPath) {
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(apkPath,
				PackageManager.GET_ACTIVITIES);
		if (info != null) {
			ApplicationInfo appInfo = info.applicationInfo;
			appInfo.sourceDir = apkPath;
			appInfo.publicSourceDir = apkPath;
			try {
				return (String) appInfo.loadLabel(pm);
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Get apk icon from an apk file which is not installed.
	 *  @deprecated use {@link getApkIcon2()} instead of
	 * @param context
	 * @param apkPath
	 * @return
	 */
	public static Drawable getApkIcon(Context context, String apkPath) {
		Drawable icon = null;
		String PATH_PackageParser = "android.content.pm.PackageParser";
		String PATH_AssetManager = "android.content.res.AssetManager";
		try {
			Class<?> pkgParserCls = Class.forName(PATH_PackageParser);
			Class<?>[] typeArgs = { String.class };
			Constructor<?> pkgParserCt = pkgParserCls.getConstructor(typeArgs);
			Object[] valueArgs = { apkPath };
			Object pkgParser = pkgParserCt.newInstance(valueArgs);

			DisplayMetrics metrics = new DisplayMetrics();
			metrics.setToDefaults();

			typeArgs = new Class<?>[] { File.class, String.class,
					DisplayMetrics.class, int.class };

			Method pkgParser_parsePackageMtd = pkgParserCls.getDeclaredMethod(
					"parsePackage", typeArgs);

			valueArgs = new Object[] { new File(apkPath), apkPath, metrics, 0 };
			Object pkgParserPkg = pkgParser_parsePackageMtd.invoke(pkgParser,
					valueArgs);

			Field appInfoFld = pkgParserPkg.getClass().getDeclaredField(
					"applicationInfo");

			ApplicationInfo info = (ApplicationInfo) appInfoFld
					.get(pkgParserPkg);

			Class<?> assetMagCls = Class.forName(PATH_AssetManager);
			Object assetMag = assetMagCls.newInstance();
			typeArgs = new Class[1];
			typeArgs[0] = String.class;

			Method assetMag_addAssetPathMtd = assetMagCls.getDeclaredMethod(
					"addAssetPath", typeArgs);
			valueArgs = new Object[1];
			valueArgs[0] = apkPath;

			assetMag_addAssetPathMtd.invoke(assetMag, valueArgs);

			Resources res = context.getResources();

			typeArgs = new Class[3];
			typeArgs[0] = assetMag.getClass();
			typeArgs[1] = res.getDisplayMetrics().getClass();
			typeArgs[2] = res.getConfiguration().getClass();

			Constructor<Resources> resCt = Resources.class
					.getConstructor(typeArgs);

			valueArgs = new Object[3];

			valueArgs[0] = assetMag;
			valueArgs[1] = res.getDisplayMetrics();
			valueArgs[2] = res.getConfiguration();
			res = (Resources) resCt.newInstance(valueArgs);

			if (info != null) {
				if (info.icon != 0) {
					// Get the apk icon.
					icon = res.getDrawable(info.icon);
					res.getString(info.labelRes);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (icon == null) {
			icon = context.getResources().getDrawable(
					R.drawable.icon_apk);
		}
		return icon;
	}

	/**
	 * Get apk name from an apk file which is not installed.
	 * @deprecated use {@link getApkLabel()} instead of
	 * @param context
	 * @param apkPath
	 * @return
	 */
	public static String getApkName(Context context, String apkPath) {
		String name = null;
		String PATH_PackageParser = "android.content.pm.PackageParser";
		String PATH_AssetManager = "android.content.res.AssetManager";
		try {
			Class<?> pkgParserCls = Class.forName(PATH_PackageParser);
			Class<?>[] typeArgs = { String.class };
			Constructor<?> pkgParserCt = pkgParserCls.getConstructor(typeArgs);
			Object[] valueArgs = { apkPath };
			Object pkgParser = pkgParserCt.newInstance(valueArgs);

			DisplayMetrics metrics = new DisplayMetrics();
			metrics.setToDefaults();

			typeArgs = new Class<?>[] { File.class, String.class,
					DisplayMetrics.class, int.class };

			Method pkgParser_parsePackageMtd = pkgParserCls.getDeclaredMethod(
					"parsePackage", typeArgs);

			valueArgs = new Object[] { new File(apkPath), apkPath, metrics, 0 };
			Object pkgParserPkg = pkgParser_parsePackageMtd.invoke(pkgParser,
					valueArgs);

			Field appInfoFld = pkgParserPkg.getClass().getDeclaredField(
					"applicationInfo");

			ApplicationInfo info = (ApplicationInfo) appInfoFld
					.get(pkgParserPkg);

			Class<?> assetMagCls = Class.forName(PATH_AssetManager);
			Object assetMag = assetMagCls.newInstance();
			typeArgs = new Class[1];
			typeArgs[0] = String.class;

			Method assetMag_addAssetPathMtd = assetMagCls.getDeclaredMethod(
					"addAssetPath", typeArgs);
			valueArgs = new Object[1];
			valueArgs[0] = apkPath;

			assetMag_addAssetPathMtd.invoke(assetMag, valueArgs);

			Resources res = context.getResources();

			typeArgs = new Class[3];
			typeArgs[0] = assetMag.getClass();
			typeArgs[1] = res.getDisplayMetrics().getClass();
			typeArgs[2] = res.getConfiguration().getClass();

			Constructor<Resources> resCt = Resources.class
					.getConstructor(typeArgs);

			valueArgs = new Object[3];

			valueArgs[0] = assetMag;
			valueArgs[1] = res.getDisplayMetrics();
			valueArgs[2] = res.getConfiguration();
			res = (Resources) resCt.newInstance(valueArgs);

			if (info != null) {
				if (info.icon != 0) {
					// Get the apk label.
					name = res.getString(info.labelRes);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}
	
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	public static void installApp(Context context, String apkPath){
		File apkFile = new File(apkPath);
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			intent.setAction(Intent.ACTION_INSTALL_PACKAGE);
			intent.setData(Uri.fromFile(apkFile));
		} else {
			intent.setAction(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive"); 
		}
		context.startActivity(intent);
	}
	
	public static boolean isAppInstalled(Context context,String packageName){
		PackageInfo packageInfo = null;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return packageInfo == null ? false : true;
	}
	
	public static String getInstalledAppVersion(Context context,String packageName){
		PackageInfo packageInfo = null;
		try {
			packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return packageInfo != null ? packageInfo.versionName : null;
	}
}
