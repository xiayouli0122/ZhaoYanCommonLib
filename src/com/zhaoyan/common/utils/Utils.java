package com.zhaoyan.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.StatFs;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class Utils {
	private static final String TAG = Utils.class.getSimpleName();
	
	
	/**
	 * byte convert
	 * @param size like 3232332
	 * @return like 3.23M
	 */
	public static String getFormatSize(long size){
		DecimalFormat df = new DecimalFormat("###.##");
		float f;
		if (size >= 1024 * 1024 * 1024){
			f = (float) ((float) size / (float) (1024 * 1024 * 1024));
		    return (df.format(Float.valueOf(f).doubleValue())+"GB");
		}else if (size >= 1024 * 1024) {
			f = (float) ((float) size / (float) (1024 * 1024));
		    return (df.format(Float.valueOf(f).doubleValue())+"MB");
		}else if (size >= 1024) {
			f = (float) ((float) size / (float) 1024);
			return (df.format(Float.valueOf(f).doubleValue())+"KB");
		}else {
			return String.valueOf((int)size) + "B";
		}
	}
	
	/**get app install date*/
	public static String getFormatDate(long date){
		return getFormatDate(new Date(date));
	}
	
	public static String getFormatDate(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = format.format(date);
		return dateString;
	}
	
	/**set dialog dismiss or not*/
	public static void setDialogDismiss(DialogInterface dialog, boolean dismiss){
		try {
			Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
			field.setAccessible(true);
			field.set(dialog, dismiss);
			dialog.dismiss();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * force show virtual menu key </br>
	 * must call after setContentView() 
	 * @param window you can use getWindow()
	 */
	public static void forceShowMenuKey(Window window){
		try {
			window.addFlags(WindowManager.LayoutParams.class.getField("FLAG_NEEDS_MENU_KEY").getInt(null));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static long getAvailableBlockSize(String path) {
		StatFs stat = new StatFs(path);
		long blocksize = stat.getBlockSize();
		long availableblocks = stat.getAvailableBlocks();
		return availableblocks * blocksize;
	}
	
	/**
	 * bytes tp chars
	 * 
	 * @param bytes
	 * @return
	 */
	public static char[] getChars(byte[] bytes) {
		Charset cs = Charset.forName("UTF-8");
		ByteBuffer bb = ByteBuffer.allocate(bytes.length);
		bb.put(bytes);
		bb.flip();
		CharBuffer cb = cs.decode(bb);
		return cb.array();
	}
	
	/**
	 * get n int random nums from a number
	 * (从指定范围内生成n个不重复的随机数)
	 * @param n random count
	 * @param totalNum  指定范围的最大数
	 * @return 不重复的HashSet集合
	 */
	public static Set<Integer> getRandomNums(int n, int totalNum){
		Set<Integer> set = new HashSet<Integer>();
		Random random  = new Random();
		for (int i = 0; i < n; i++) {
			int num = random.nextInt(totalNum);
			while (!set.add(num)) {
				num = random.nextInt(totalNum);
			}
		}
		return set;
	}
	
	/**
	 * get percent from two spec num,like: 123123/11321400 = 9%
	 * @param progress  current download bytes
	 * @param max max file size bytes
	 * @return like:20%
	 */
	public static String getPercent(long progress, long max) {
        return new StringBuilder(16).append(getProgress(progress, max)).append("%").toString();
    }
    
	/**
	 * get progress from two spec nums
	 * @param progress bytes,like:213123
	 * @param max  bytes,like:34123131
	 * @return progress,int,like:23
	 */
    public static int getProgress(long progress, long max) {
        int rate = 0;
        if (progress <= 0 || max <= 0) {
            rate = 0;
        } else if (progress > max) {
            rate = 100;
        } else {
            rate = (int)((double)progress / max * 100);
        }
        return rate;
    }
    
    /** 
     * formate time
     * @param time audio/video time like 12323312
     * @return the format time string like 00:12:23
     */  
	public static String mediaTimeFormat(long duration) {
		long hour = duration / (60 * 60 * 1000);
		String min = duration % (60 * 60 * 1000) / (60 * 1000) + "";
		String sec = duration % (60 * 60 * 1000) % (60 * 1000) + "";

		if (min.length() < 2) {
			min = "0" + duration / (1000 * 60) + "";
		}

		if (sec.length() == 4) {
			sec = "0" + sec;
		} else if (sec.length() == 3) {
			sec = "00" + sec;
		} else if (sec.length() == 2) {
			sec = "000" + sec;
		} else if (sec.length() == 1) {
			sec = "0000" + sec;
		}

		if (hour == 0) {
			return min + ":" + sec.trim().substring(0, 2);
		} else {
			String hours = "";
			if (hour < 10) {
				hours = "0" + hour;
			} else {
				hours = hours + "";
			}
			return hours + ":" + min + ":" + sec.trim().substring(0, 2);
		}
	}
	
	/**
     * Collapse status panel
     * 
     * @param context
     *            the context used to fetch status bar manager
     */
     public static void collapseStatusBar(Context context) {
        try {
            Object statusBarManager = context.getSystemService("statusbar");
            Method collapse;

            if (Build.VERSION.SDK_INT <= 16) {
                collapse = statusBarManager.getClass().getMethod("collapse");
            } else {
                collapse = statusBarManager.getClass().getMethod("collapsePanels");
            }
            collapse.invoke(statusBarManager);
        } catch (Exception localException) {
        	Log.e(TAG, "collapseStatusBar:" + localException);
            localException.printStackTrace();
        }
    }
	
}
