package com.zhaoyan.common.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zhaoyan.common.actionmenu.ActionMenu;
import com.zhaoyan.common.lib.R;

public class CheckableBaseAdapter extends BaseAdapter{
	private static final String TAG = "BaseCursorAdapter";
	protected int mMenuMode = ActionMenu.MODE_NORMAL;
	protected SparseBooleanArray mCheckArray = null;
	

	public CheckableBaseAdapter(Context context) {
		mCheckArray = new SparseBooleanArray();
		// TODO Auto-generated constructor stub
	}
	
	
	public void updateViewBackground(int position, View view){
		if (isChecked(position)) {
			view.setBackgroundResource(R.color.kk_theme_color);
		}else {
			view.setBackgroundResource(Color.TRANSPARENT);
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void changeMode(int mode){
		mMenuMode = mode;
	}

	public boolean isMode(int mode) {
		return mMenuMode == mode;
	}

	public void checkedAll(boolean isChecked) {
		// TODO Auto-generated method stub
		int count = getCount();
		for (int i = 0; i < count; i++) {
			setChecked(i, isChecked);
		}
	}

	public void setChecked(int position, boolean isChecked) {
		mCheckArray.put(position, isChecked);
	}

	public void setChecked(int position) {
		mCheckArray.put(position, !isChecked(position));
	}

	public boolean isChecked(int position) {
		return mCheckArray.get(position);
	}

	public int getCheckedCount() {
//		return 0;
		int count = 0;
		for (int i = 0; i < mCheckArray.size(); i++) {
			if (mCheckArray.valueAt(i)) {
				count ++;
			}
		}
		return count;
	}

	public List<Integer> getCheckedPosList() {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < mCheckArray.size(); i++) {
			if (mCheckArray.valueAt(i)) {
				list.add(i);
			}
		}
		return list;
	}

	public List<String> getCheckedNameList() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getCheckedPathList() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

}
