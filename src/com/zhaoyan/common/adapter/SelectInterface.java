package com.zhaoyan.common.adapter;

import java.util.List;

/**
 * if you want relize multi select function,you need implements thi interface
 * @author Yuri
 *
 */
public interface SelectInterface {
	/**
	 * change current opertion mode,normal or menu edit
	 * @param mode
	 */
	public void changeMode(int mode);
	
	/**
	 * is current mode equals spec mode
	 * @param mode
	 * @return
	 */
	public boolean isMode(int mode);
	
	/**
	 * check All or not
	 * @param isChecked true or false
	 */
	public void checkedAll(boolean isChecked);
	
	/**
	 * set the item is checked or not
	 * @param position the position that clicked
	 * @param isChecked checked or not
	 */
	public void setChecked(int position, boolean isChecked);
	
	/**
	 * set the item checked or unselected </br>
	 * if checked, unCheck </br>
	 * if unChecked, check
	 * @param position
	 */
	public void setChecked(int position);
	
	/**
	 * get the item is checked or not
	 * @param position
	 * @return
	 */
	public boolean isChecked(int position);
	
	/**
	 * get current checked items count
	 * @return
	 */
	public int getCheckedCount();
	
	/**
	 * get current checked items position list
	 * @return
	 */
	public List<Integer> getCheckedPosList();
	
	/**
	 *  get current checked items name list
	 * @return
	 */
	public List<String> getCheckedNameList();
	
	/**
	 *  get current checked items file path list
	 * @return
	 */
	public List<String> getCheckedPathList();
	
	/**
	 * set listview or gridview is scorll or idle
	 * @param flag
	 */
	public void setIdleFlag(boolean flag);
}
