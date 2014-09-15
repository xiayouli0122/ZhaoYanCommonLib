package com.zhaoyan.common.media;

import java.util.List;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.util.Log;

public class MultiMediaScanner {
	private static final String TAG = MultiMediaScanner.class.getSimpleName();
	
	/**
	 * Convenience for constructing a MediaScannerConnection
	 * @param context  The caller's Context, required for establishing a connection to the media scanner service
	 * @param pathsList list of paths to be scanned
	 * @param mimeTypesList Optional array of MIME types for each path. If mimeType is null, then the mimeType will be inferred from the file extension
	 */
	public static void scanFiles(Context context, List<String> pathsList,List<String> mimeTypesList){
		Log.d(TAG, "scanFiles.size=" + pathsList.size());
		String[] paths = new String[pathsList.size()];
		pathsList.toArray(paths);
		
		String[] mimeTypes = null;
		if (null != mimeTypesList) {
			mimeTypes = new String[mimeTypesList.size()];
			mimeTypesList.toArray(mimeTypes);
		}
		
		MediaScannerConnection.scanFile(context, paths, mimeTypes, new OnScanCompletedListener() {
			@Override
			public void onScanCompleted(String path, Uri uri) {
				// TODO Auto-generated method stub
				Log.d(TAG, "onScanCompleted.path=" + path);
			}
		});
	}
}
