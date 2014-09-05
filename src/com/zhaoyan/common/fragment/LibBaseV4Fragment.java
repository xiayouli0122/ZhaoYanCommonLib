package com.zhaoyan.common.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.zhaoyan.common.actionmenu.ActionMenu;
import com.zhaoyan.common.actionmenu.ActionMenu.ActionMenuItem;
import com.zhaoyan.common.actionmenu.ActionMenuInflater;
import com.zhaoyan.common.actionmenu.ActionMenuInterface.OnMenuItemClickListener;
import com.zhaoyan.common.actionmenu.MenuBarManager;
import com.zhaoyan.common.lib.R;
import com.zhaoyan.common.utils.Notice;

public class LibBaseV4Fragment extends Fragment implements OnMenuItemClickListener{
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
		mMenuBarView = view.findViewById(R.id.ll_menubar_bottom);
		mMenuBarView.setVisibility(View.GONE);
		mMenuHolder = (LinearLayout) view.findViewById(R.id.ll_menutabs_holder);
		
		mMenuBarManager = new MenuBarManager(getActivity().getApplicationContext(), mMenuHolder);
		mMenuBarManager.setOnMenuItemClickListener(this);
	}
	
	public void startMenuBar(){
		mMenuBarView.setVisibility(View.VISIBLE);
		mMenuBarView.clearAnimation();
		mMenuBarView.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.slide_up_in));
		mMenuBarManager.refreshMenus(mActionMenu);
	}
	
	public void destroyMenuBar(){
		mMenuBarView.setVisibility(View.GONE);
		mMenuBarView.clearAnimation();
		mMenuBarView.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.slide_down_out));
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
