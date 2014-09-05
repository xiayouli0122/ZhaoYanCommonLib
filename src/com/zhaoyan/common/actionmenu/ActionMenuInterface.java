package com.zhaoyan.common.actionmenu;

import com.zhaoyan.common.actionmenu.ActionMenu.ActionMenuItem;


public interface ActionMenuInterface {
	
	public interface OnMenuItemClickListener{
		public void onMenuItemClick(ActionMenuItem item);
	}
}
