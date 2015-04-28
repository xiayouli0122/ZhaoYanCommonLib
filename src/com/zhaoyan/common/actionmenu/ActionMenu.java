package com.zhaoyan.common.actionmenu;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.util.Log;

public class ActionMenu{
	private static final String TAG = "ActionMenu";
	
	public static final int ACTION_MENU_OPEN = 0x01;
	public static final int ACTION_MENU_SEND = 0x02;
	public static final int ACTION_MENU_DELETE = 0x03;
	public static final int ACTION_MENU_INFO = 0x04;
	public static final int ACTION_MENU_MORE = 0x05;
	public static final int ACTION_MENU_PLAY = 0x06;
	public static final int ACTION_MENU_RENAME = 0x07;
	public static final int ACTION_MENU_SELECT = 0x08;
	public static final int ACTION_MENU_UNINSTALL = 0x09;
	public static final int ACTION_MENU_MOVE_TO_GAME = 0x10;
	public static final int ACTION_MENU_MOVE_TO_APP = 0x11;
	public static final int ACTION_MENU_BACKUP = 0x12;
	public static final int ACTION_MENU_CANCEL = 0x13;
	public static final int ACTION_MENU_CLEAR_HISTORY = 0x14;
	public static final int ACTION_MENU_CLEAR_HISTORY_AND_FILE = 0x15;
	public static final int ACTION_MENU_COPY = 0x16;
	public static final int ACTION_MENU_PASTE = 0x17;
	public static final int ACTION_MENU_CUT = 0x18;
	public static final int ACTION_MENU_CREATE_FOLDER = 0x19;
	public static final int ACTION_MENU_CANCEL_TRANSFER = 0x20;
	public static final int ACTION_MENU_CANCEL_SEND = 0x21;
	public static final int ACTION_MENU_CANCEL_RECEIVE = 0x22;
	public static final int ACTION_MENU_CAPTURE = 0x23;
	public static final int ACTION_MENU_PICK_PICTURE = 0x24;
	public static final int ACTION_MENU_SHARE = 0x25;
	
	//action menu mode,edit is menu mode
	public static final int MODE_NORMAL = 0;
	public static final int MODE_EDIT = 1;
	public static final int MODE_COPY = 2;
	public static final int MODE_CUT = 3;
	
	private List<ActionMenuItem> items = new ArrayList<ActionMenu.ActionMenuItem>();
	
	private Context mContext;
	
	public ActionMenu(Context context){
		mContext = context;
	}
	
	public ActionMenuItem addItem(int itemId){
		ActionMenuItem item = new ActionMenuItem(itemId);
		items.add(item);
		return item;
	}
	
	public void addItem(int id, int iconId, String title){
		ActionMenuItem item = new ActionMenuItem(id, iconId, title);
		items.add(item);
	}
	
	public void addItem(int id, int iconId, int title_redId){
		String title = mContext.getResources().getString(title_redId);
		addItem(id, iconId, title);
	}
	
	public int size(){
		return items.size();
	}
	
	public ActionMenuItem getItem(int index){
		try {
			return items.get(index);
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "Out of bound,the size is " + size() + ",index is " + index);
			return null;
		}
	}
	
	public ActionMenuItem findItem(int id){
		for(ActionMenuItem item : items){
			if (id == item.getItemId()) {
				return item;
			}
		}
		
		return null;
	}
	
	public class ActionMenuItem{
		int id;
		int iconId;
		int enableIconId;
		int disableIconId;
		String title;
		boolean enable = true;
		int text_color;
		
		ActionMenuItem(int itemid){
			id = itemid;
			text_color = mContext.getResources().getColor(android.R.color.black);
		}
		
		ActionMenuItem(int id, int iconid, String title){
			this.id = id;
			this.iconId = iconid;
			this.title = title;
			text_color = mContext.getResources().getColor(android.R.color.black);
		}
		
		public int getItemId(){
			return id;
		}
		
		public void setTitle(String title){
			this.title = title;
		}
		
		public void setTitle(int resId){
			String title = mContext.getResources().getString(resId);
			setTitle(title);
		}
		
		public String getTitle(){
			return title;
		}
		
		public void setTextColor(int color_id){
			this.text_color = color_id;
		}
		
		public int getTextColor(){
			return text_color;
		}
		
		public void setIcon(int iconId){
			this.iconId = iconId;
		}
		
		public int getIcon(){
			return iconId;
		}
		
		public void setEnableIcon(int iconId){
			this.enableIconId = iconId;
		}
		
		public int getEnableIcon(){
			return enableIconId == 0 ? iconId : enableIconId;
		}
		
		public void setDisableIcon(int iconId){
			this.disableIconId = iconId;
		}
		
		public int getDisableIcon(){
			return disableIconId == 0 ? iconId : disableIconId;
		}
		
		public void setEnable(boolean enable){
			this.enable = enable;
		}
		
		public boolean isEnable(){
			return enable;
		}
	}
	
}
