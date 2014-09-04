package com.zhaoyan.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	public static String getCurrentTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
		Date date = new Date(System.currentTimeMillis());
		return format.format(date);
	}
	
	public static String getDate(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(System.currentTimeMillis());
		return format.format(date);
	}
	
}
