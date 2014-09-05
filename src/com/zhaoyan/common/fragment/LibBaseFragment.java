package com.zhaoyan.common.fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.zhaoyan.common.actionmenu.ActionMenu;
import com.zhaoyan.common.actionmenu.ActionMenu.ActionMenuItem;
import com.zhaoyan.common.actionmenu.ActionMenuInflater;
import com.zhaoyan.common.actionmenu.ActionMenuInterface.OnMenuItemClickListener;
import com.zhaoyan.common.actionmenu.MenuBarManager;
import com.zhaoyan.common.lib.R;
import com.zhaoyan.common.utils.Log;
import com.zhaoyan.common.utils.Notice;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class LibBaseFragment extends Fragment implements OnMenuItemClickListener{
	private static String TAG = LibBaseFragment.class.getSimpleName();
	protected Notice mNotice = null;
	protected Context mContext = null;
	
	//menubar
	protected View mMenuBarView;
	protected LinearLayout mMenuHolder;
	protected MenuBarManager mMenuBarManager;
	protected ActionMenu mActionMenu;
	
	private ActionMenuInflater mActionMenuInflater;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		mNotice = new Notice(mContext);
				
		setHasOptionsMenu(true);
	}
	
	protected void initMenuBar(View view){
		mMenuHolder = (LinearLayout) view.findViewById(R.id.ll_menutabs_holder);
		
		mMenuBarManager = new MenuBarManager(getActivity().getApplicationContext(), mMenuHolder);
		mMenuBarManager.setOnMenuItemClickListener(this);
	}
	
	public void startMenuBar(View view){
		Log.d(TAG, "startMenuBar:" + view);
		view.setVisibility(View.VISIBLE);
		view.clearAnimation();
		view.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.slide_up_in));
		mMenuBarManager.refreshMenus(mActionMenu);
	}
	
	public void destroyMenuBar(View view){
		Log.d(TAG, "destroyMenuBar:" + view);
		view.setVisibility(View.GONE);
		view.clearAnimation();
		view.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.slide_down_out));
	}
	
	/**
	 * when user pressed back key
	 */
	public boolean onBackPressed(){
		return true;
	}
	
	public void onDestroy() {
		super.onDestroy();
	}

	protected ActionMenuInflater getActionMenuInflater(){
		if (null == mActionMenuInflater) {
			mActionMenuInflater = new ActionMenuInflater(getActivity().getApplicationContext());
		}
		return mActionMenuInflater;
	}

	@Override
	public void onMenuItemClick(ActionMenuItem item) {
		// TODO Auto-generated method stub
	}
}
