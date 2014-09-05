package com.zhaoyan.common.actionmenu;

/**
 * if you want use MenuBar,you need implements this interface
 * @author Yuri
 *
 */
public interface MenuBarInterface {
	
	/**
	 * update menu bar item icon and text color,enable or disable
	 */
	public void updateMenuBar();
	
	/**
	 * checked all,or  uncheck all function
	 */
	public void doCheckAll();
}
