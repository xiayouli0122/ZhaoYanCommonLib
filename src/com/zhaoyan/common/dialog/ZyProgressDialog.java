package com.zhaoyan.common.dialog;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhaoyan.common.lib.R;

public class ZyProgressDialog extends ZyDialogBuilder {
	private String mMessage;

	public ZyProgressDialog(Context context) {
		super(context, R.style.dialog_untran);
		setCancelable(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		View customeView = getLayoutInflater().inflate(R.layout.progress_dialog, null);
		if (mMessage != null) {
			TextView textView = (TextView) customeView.findViewById(R.id.tv_pd_message);
			textView.setText(mMessage);
		}
		setCustomView(customeView);
		super.onCreate(savedInstanceState);
	}

	public void setDialogMessage(String message) {
		mMessage = message;
	}

	public void setDialogMessage(int message) {
		mMessage = getContext().getString(message);
	}
}
