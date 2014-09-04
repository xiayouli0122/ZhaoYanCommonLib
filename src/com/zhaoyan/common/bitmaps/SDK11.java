package com.zhaoyan.common.bitmaps;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SDK11 {

    public static <P> void executeOnThreadPool(AsyncTask<P, ?, ?> task, P... params) {
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
    }
    
    public static void addInBitmapOption(BitmapFactory.Options opts, Bitmap inBitmap) {
        opts.inBitmap = inBitmap;
    }

}
