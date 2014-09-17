package com.zhaoyan.common.views;

import com.zhaoyan.common.utils.Log;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager {
	private static final String TAG = CustomViewPager.class.getSimpleName();
	private boolean slideEnable;
	public CustomViewPager(Context context) {
		super(context);
	}
	
	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		if (slideEnable) {
			return super.dispatchKeyEvent(event);
		}
		return false;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		if (slideEnable) {
			return super.onInterceptTouchEvent(arg0);
		}
		return false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d(TAG, "onTouchEvent");
		if (slideEnable) {
			return super.onTouchEvent(event);
		}
		return false;
	}
	
	public void setPagerSlideEnable(boolean enable){
		this.slideEnable = enable;
	}

}
