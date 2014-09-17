package com.zhaoyan.common.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.zhaoyan.common.actionmenu.ActionMenu;
import com.zhaoyan.common.actionmenu.ActionMenu.ActionMenuItem;
import com.zhaoyan.common.actionmenu.ActionMenuInflater;
import com.zhaoyan.common.actionmenu.ActionMenuInterface.OnMenuItemClickListener;
import com.zhaoyan.common.actionmenu.MenuBarManager;
import com.zhaoyan.common.lib.R;

public class LibBaseFragmentActivity extends FragmentActivity implements OnMenuItemClickListener {
	private static final String TAG = LibBaseFragmentActivity.class.getSimpleName();

	// menubar
	protected View mMenuBarView;
	
	protected LinearLayout mMenuHolder;
	protected MenuBarManager mMenuBarManager;
	protected ActionMenu mActionMenu;

	private ActionMenuInflater mActionMenuInflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}


	protected void initMenuBar(View view) {
		mMenuHolder = (LinearLayout) view.findViewById(R.id.ll_menutabs_holder);

		mMenuBarManager = new MenuBarManager(getApplicationContext(),
				mMenuHolder);
		mMenuBarManager.setOnMenuItemClickListener(this);
	}

	public void startMenuBar(View view) {
		view.setVisibility(View.VISIBLE);
		view.clearAnimation();
		view.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_up_in));
		mMenuBarManager.refreshMenus(mActionMenu);
	}

	public void destroyMenuBar(View view) {
		view.setVisibility(View.GONE);
		view.clearAnimation();
		view.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_down_out));
	}

	protected ActionMenuInflater getActionMenuInflater() {
		if (null == mActionMenuInflater) {
			mActionMenuInflater = new ActionMenuInflater(
					getApplicationContext());
		}
		return mActionMenuInflater;
	}

	@Override
	public void onMenuItemClick(ActionMenuItem item) {
		// TODO Auto-generated method stub
	}
	
}
