package com.zhaoyan.common.file;

import android.content.Context;

import com.zhaoyan.common.lib.R;

public class FileUtils {

	public static final String[] ERROR_NAME_STRS = {":","<",">","\\","|","?","/"};
	
	/**
	 * verify the file name's format is correct
	 * @param context  for getResource
	 * @param name the name need to verify
	 * @return null:the filename's format is correct,else return the error message
	 */
	public static String FileNameFormatVerify(Context context, String name){
		if (name.equals(".")) {
			return context.getString(R.string.file_rename_error_2, ".");
		}else if (name.equals("..")) {
			return context.getString(R.string.file_rename_error_2, "..");
		}else {
			for (int i = 0; i < ERROR_NAME_STRS.length; i++) {
				if (name.indexOf(ERROR_NAME_STRS[i]) >= 0) {
					return context.getString(R.string.file_rename_error_1, ERROR_NAME_STRS[i]);
				}
			}
		}
		return null;
	}
}
