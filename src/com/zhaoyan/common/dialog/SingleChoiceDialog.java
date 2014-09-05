package com.zhaoyan.common.dialog;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zhaoyan.common.lib.R;

public class SingleChoiceDialog extends ZyDialogBuilder implements OnItemClickListener{
	
	private ListView mListView;
	
	private SingleChoiceAdapter mAdapter;
	
	private List<String> mItemList = null;
	private List<Drawable> mIconList = null;
	
	
	public SingleChoiceDialog(Context context, int defaultChoiceItem){
		super(context, R.style.dialog_untran);
		View customView = LayoutInflater.from(context).inflate(R.layout.dialog_single_choice, null);
		mListView = (ListView) customView.findViewById(R.id.dlg_listview);
		
		mAdapter = new SingleChoiceAdapter(context);
		mAdapter.setSelect(defaultChoiceItem);
		mListView.setAdapter(mAdapter);
		
		mListView.setOnItemClickListener(this);
		
		setDuration(0);
		setMessage(null);
		setCustomView(customView, context);
	}
	
	public void setItemList(List<String> list){
		mItemList = list;
		mAdapter.notifyDataSetChanged();
	}
	
	public void setIconList(List<Drawable> list){
		mIconList = list;
		mAdapter.notifyDataSetChanged();
	}
	
	public int getSelectItemPosition(){
		return mAdapter.getSelectItem();
	}
	
	private class SingleChoiceAdapter extends BaseAdapter{
		LayoutInflater inflater = null;
		int currentSelectPos = -1;
		int defaultPos = -1;
		
		public SingleChoiceAdapter(Context context){
			inflater = LayoutInflater.from(context);
		}
		
		public void setSelect(int position){
			System.out.println("setSelect");
			currentSelectPos = position;
			notifyDataSetChanged();
		}
		
		public int getSelectItem(){
			return currentSelectPos;
		}

		@Override
		public int getCount() {
			return mItemList == null ? 0 : mItemList.size();
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

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			ViewHodler hodler = null;
			if (convertView == null) {
				view = inflater.inflate(R.layout.dialog_single_choice_item, null);
				hodler = new ViewHodler();
				hodler.textView = (TextView) view.findViewById(R.id.dialog_tv_text);
				hodler.iconView = (ImageView) view.findViewById(R.id.dialog_iv_icon);
				hodler.radioButton = (RadioButton) view.findViewById(R.id.dialog_radiobutton);
				view.setTag(hodler);
			} else {
				hodler = (ViewHodler) convertView.getTag();
				view = convertView;
			}
			
			if (currentSelectPos == position) {
				hodler.radioButton.setChecked(true);
			} else {
				hodler.radioButton.setChecked(false);
			}
			
			hodler.textView.setText(mItemList.get(position));
			hodler.iconView.setImageDrawable(mIconList.get(position));
			
			return view;
		}
		
		class ViewHodler{
			TextView textView;
			ImageView iconView;
			RadioButton radioButton;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		mAdapter.setSelect(position);
	}
}
