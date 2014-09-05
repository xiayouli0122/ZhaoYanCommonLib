package com.zhaoyan.common.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.zhaoyan.common.actionmenu.ActionMenu;
import com.zhaoyan.common.lib.R;


public class CheckableCursorAdapter extends CursorAdapter implements SelectInterface {
	private static final String TAG = CheckableCursorAdapter.class.getSimpleName();
	protected int mMenuMode = ActionMenu.MODE_NORMAL;
	protected SparseBooleanArray mCheckArray = null;
	

	public CheckableCursorAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		mCheckArray = new SparseBooleanArray();
		// TODO Auto-generated constructor stub
	}
	
	public void updateViewBackground(boolean selected, int position, View view){
		if (selected) {
			view.setBackgroundResource(R.color.kk_theme_color);
		}else {
			view.setBackgroundResource(Color.TRANSPARENT);
		}
	}

	@Override
	public void bindView(View arg0, Context arg1, Cursor arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void changeMode(int mode){
		mMenuMode = mode;
	}

	@Override
	public boolean isMode(int mode) {
		return mMenuMode == mode;
	}

	@Override
	public void checkedAll(boolean isChecked) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setChecked(int position, boolean isChecked) {
		mCheckArray.put(position, isChecked);
	}

	@Override
	public void setChecked(int position) {
		mCheckArray.put(position, !isChecked(position));
	}

	@Override
	public boolean isChecked(int position) {
		return mCheckArray.get(position);
	}

	@Override
	public int getCheckedCount() {
//		// TODO Auto-generated method stub
//		return 0;
		int count = 0;
		for (int i = 0; i < mCheckArray.size(); i++) {
			if (mCheckArray.valueAt(i)) {
				count ++;
			}
		}
		return count;
	}

	@Override
	public List<Integer> getCheckedPosList() {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < mCheckArray.size(); i++) {
			if (mCheckArray.valueAt(i)) {
				list.add(i);
			}
		}
		return list;
	}

	@Override
	public List<String> getCheckedNameList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getCheckedPathList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIdleFlag(boolean flag) {
		// TODO Auto-generated method stub
	}

}
