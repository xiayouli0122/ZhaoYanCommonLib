package com.zhaoyan.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {
	public static final String DEFAULT_NAME = "zhaoyan";

	public static SharedPreferences getSharedPreference(Context context) {
		return getSharedPreference(context, DEFAULT_NAME);
	}
	
	public static SharedPreferences getSharedPreference(Context context, String name) {
		return context.getSharedPreferences(name, Context.MODE_PRIVATE);
	}

}
