package com.zhaoyan.common.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.zhaoyan.common.dialog.ZyProgressDialog;
import com.zhaoyan.common.lib.R;
import com.zhaoyan.common.media.MultiMediaScanner;
import com.zhaoyan.common.utils.FileManager;

public class FileDeleteHelper {
	private static final String TAG = "MediaDeleteHelper";
	
	private List<String> mCurDelList = new ArrayList<String>();
	
	//for mediascanner
	List<String> mPathList = new ArrayList<String>();
	
	private ZyProgressDialog progressDialog = null;
	
	private OnDeleteListener mOnDeleteListener = null;
	private Context mContext;
	
	public interface OnDeleteListener{
		public void onDeleteFinished();
	}
	
	public void setOnDeleteListener(OnDeleteListener listener){
		mOnDeleteListener = listener;
	}
	
	public void cancelDeleteListener(){
		if (null != mOnDeleteListener) {
			mOnDeleteListener = null;
		}
	}
	
	public FileDeleteHelper(Context context){
		mContext = context;
	}
	
	/**
	 * record current operation fileinfos
	 * @param files
	 */
	public void setDeletePathList(List<String> paths) {
        copyFileList(paths);
    }
	
	private void copyFileList(List<String> paths) {
        synchronized(mCurDelList) {
        	mCurDelList.clear();
            for (String path : paths) {
            	mCurDelList.add(path);
            }
        }
    }
	
	public void doDelete(){
		asyncExecute(new Runnable() {
			@Override
			public void run() {
				for(String path: mCurDelList){
					if (mStopDelete) {
						break;
					}
					
					doDeleteFiles(new File(path));
					
//					for (int i = 0; i < mPathList.size(); i++) {
//						Log.d(TAG, "pathList[" + i + "]:" + mPathList.get(i));
//					}
				}
				
				MultiMediaScanner.scanFiles(mContext, mPathList, null);
				clear();
			}
		});
	}
	
	private void doDeleteFiles(File file){
		if (file.isFile()) {
			FileManager.deleteFile(file);
			mPathList.add(file.getAbsolutePath());
			return;
		}else {
			//dir alse need to update db
			mPathList.add(file.getAbsolutePath());
			File[] files = file.listFiles();
			if (null != files) {
				//delete child files
				for(File f : files){
					if (mStopDelete) {
						return;
					}
					
					if (f.isDirectory()) {
						doDeleteFiles(f);
					}else {
						FileManager.deleteFile(f);
						mPathList.add(f.getAbsolutePath());
					}
				}
			}
			//delete dir
			FileManager.deleteFile(file);
		}
	}
	
	private void asyncExecute(Runnable r) {
        final Runnable _r = r;
        new AsyncTask<Void, Void, Void>() {
        	protected void onPreExecute() {
        		progressDialog = new ZyProgressDialog(mContext);
        		progressDialog.setMessage(R.string.deleting);
        		progressDialog.show();
        	};
        	
        	
			@Override
			protected Void doInBackground(Void... params) {
				synchronized (mCurDelList) {
					_r.run();
				}
				
				if (mOnDeleteListener != null) {
					mOnDeleteListener.onDeleteFinished();
				}
				return null;
			}
			
			protected void onPostExecute(Void result) {
				if (null != progressDialog) {
					progressDialog.cancel();
					progressDialog = null;
				}
			};
		}.execute();
    }
	
	public void clear() {
		Log.d(TAG, "clear");
		synchronized (mCurDelList) {
			mCurDelList.clear();
			mPathList.clear();
		}
		mStopDelete = false;
	}
	
	private boolean mStopDelete = false;
	public void stopCopy(){
		mStopDelete = true;
		clear();
	}
}
