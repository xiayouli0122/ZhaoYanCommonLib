package com.zhaoyan.common.views;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhaoyan.common.lib.R;
import com.zhaoyan.common.utils.ViewUtil;

public class TableTitleView extends LinearLayout implements OnClickListener {

	private Context mContext;
	private ArrayList<TextView> mTableTitles = new ArrayList<TextView>();
	private LinearLayout mTabHost;
	private ImageView mCursorImageView;
	private int mLastSelectPostion;

	private OnTableSelectChangeListener mListener;

	public TableTitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		setOrientation(VERTICAL);
	}

	public TableTitleView(Context context) {
		super(context);
		mContext = context;
		setOrientation(VERTICAL);
	}

	public void initTitles(String[] tableTitles) {
		LayoutParams tableHostParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mTabHost = new LinearLayout(mContext);
		mTabHost.setOrientation(LinearLayout.HORIZONTAL);
		addView(mTabHost, tableHostParams);

		LayoutParams tableTitleParams = new LayoutParams(0,
				LayoutParams.MATCH_PARENT);
		tableTitleParams.weight = 1;
		tableTitleParams.gravity = Gravity.CENTER;

		TextView tableTitleTextView;
		final int paddingTop = ViewUtil.dp2px(mContext, 10);
		final int paddingBottom = paddingTop;
		for (String title : tableTitles) {
			tableTitleTextView = new TextView(mContext);
			tableTitleTextView.setText(title);
			tableTitleTextView.setTextSize(12);
			tableTitleTextView.setTextColor(mContext.getResources().getColor(
					R.color.table_title_unselected));
			tableTitleTextView.setGravity(Gravity.CENTER);
			tableTitleTextView.setPadding(0, paddingTop, 0, paddingBottom);
			tableTitleTextView.setClickable(true);
			tableTitleTextView.setOnClickListener(this);
			mTableTitles.add(tableTitleTextView);
			mTabHost.addView(tableTitleTextView, tableTitleParams);
		}
		if (mTableTitles.size() > 0) {
			mTableTitles.get(0).setTextColor(
					mContext.getResources().getColor(
							R.color.table_title_selected));
			mTableTitles.get(0).setTextSize(14);
		}

		LinearLayout cursorLinearLayout = new LinearLayout(mContext);
		cursorLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		LayoutParams cursorParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		addView(cursorLinearLayout, cursorParams);

		mCursorImageView = new ImageView(mContext);
		mCursorImageView.setImageResource(R.drawable.table_title_cursor);
		mCursorImageView.setScaleType(ScaleType.FIT_XY);
		View cursorBlankView = new View(mContext);
		cursorParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
		cursorParams.weight = 1;
		cursorLinearLayout.addView(mCursorImageView, cursorParams);
		cursorParams = new LayoutParams(0, 0);
		cursorParams.weight = tableTitles.length - 1;
		cursorLinearLayout.addView(cursorBlankView, cursorParams);

		View bottomLineView = new View(mContext);
		LayoutParams bottomLineParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, 2);
		bottomLineView.setBackgroundColor(mContext.getResources().getColor(
				R.color.table_title_selected));
		addView(bottomLineView, bottomLineParams);
	}

	public void setSelectedPostion(int position) {
		if (position != mLastSelectPostion) {
			if (position >= mTableTitles.size()) {
				position = mTableTitles.size() - 1;
			}
			setCurrentItem(position);
		}
	}

	public String getTableTitle(int position) {
		return mTableTitles.get(position).getText().toString();
	}

	public void setTableTitle(int position, String title) {
		mTableTitles.get(position).setText(title);
	}

	@Override
	public void onClick(View v) {
		for (int i = 0; i < mTableTitles.size(); i++) {
			if (v == mTableTitles.get(i)) {
				if (mLastSelectPostion != i) {
					setCurrentItem(i);
					if (mListener != null) {
						mListener.onTableSelect(i);
					}
				}
			}
		}
	}

	private void setCurrentItem(int position) {
		float animStart = (float) mLastSelectPostion / mTabHost.getChildCount();
		float animEnd = (float) position / mTabHost.getChildCount();
		TranslateAnimation translateAnimation = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, animStart,
				Animation.RELATIVE_TO_PARENT, animEnd,
				Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT,
				0);
		translateAnimation.setFillAfter(true);
		translateAnimation.setDuration(300);
		mCursorImageView.startAnimation(translateAnimation);
		mTableTitles.get(position).setTextColor(
				mContext.getResources().getColor(R.color.table_title_selected));
		mTableTitles.get(position).setTextSize(14);
		mTableTitles.get(mLastSelectPostion).setTextColor(
				mContext.getResources()
						.getColor(R.color.table_title_unselected));
		mTableTitles.get(mLastSelectPostion).setTextSize(12);
		mLastSelectPostion = position;
	}

	public void setOnTableSelectChangeListener(
			OnTableSelectChangeListener listener) {
		mListener = listener;
	}

	public interface OnTableSelectChangeListener {
		void onTableSelect(int position);
	}

}
