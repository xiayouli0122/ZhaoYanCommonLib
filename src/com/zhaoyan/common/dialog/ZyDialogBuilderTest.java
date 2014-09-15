package com.zhaoyan.common.dialog;

import com.zhaoyan.common.lib.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ZyDialogBuilderTest extends Dialog implements DialogInterface {

    private final String defTextColor="#FFFFFFFF";
    private final String defDividerColor="#11000000";
    private final String defMsgColor="#FFFFFFFF";
    private final String defDialogColor="#FFE74C3C";



    private Effectstype type=null;

    private LinearLayout mLinearLayoutView;
    private RelativeLayout mRlinearLayoutView;
    private LinearLayout mLinearLayoutMsgView;
    private LinearLayout mLinearLayoutTopView;
    private FrameLayout mFrameLayoutCustomView;

    private View mDialogView;
    private View mDivider;
    private View mButtonView;

    private TextView mTitle;
    private TextView mMessage;

    private ImageView mIcon;
    private Button mButton1;
    private Button mButton2;
    
    private View mDivideOne;

    private int mDuration = -1;

//    private boolean isCancelable=true;

    private volatile static ZyDialogBuilderTest instance;

    public ZyDialogBuilderTest(Context context) {
        super(context);
        init(context);

    }
    public ZyDialogBuilderTest(Context context,int theme) {
        super(context, theme);
        init(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width  = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        
    }

    public static ZyDialogBuilderTest getInstance(Context context) {
        if (instance == null) {
            synchronized (ZyDialogBuilderTest.class) {
                if (instance == null) {
                    instance = new ZyDialogBuilderTest(context,R.style.dialog_untran);
                }
            }
        }
        return instance;
    }

    private void init(Context context) {
        mDialogView = View.inflate(context, R.layout.nifty_dialog_layout, null);
        
        mLinearLayoutView=(LinearLayout)mDialogView.findViewById(R.id.parentPanel);
        mRlinearLayoutView=(RelativeLayout)mDialogView.findViewById(R.id.main);
        mLinearLayoutTopView=(LinearLayout)mDialogView.findViewById(R.id.topPanel);
        mLinearLayoutMsgView=(LinearLayout)mDialogView.findViewById(R.id.contentPanel);
        mFrameLayoutCustomView=(FrameLayout)mDialogView.findViewById(R.id.customPanel);

        mTitle = (TextView) mDialogView.findViewById(R.id.alertTitle);
        mTitle.setTextColor(Color.WHITE);
        mMessage = (TextView) mDialogView.findViewById(R.id.message);
        mIcon = (ImageView) mDialogView.findViewById(R.id.icon);
        mDivider = mDialogView.findViewById(R.id.titleDivider);
        mButtonView = mDialogView.findViewById(R.id.button_layout);
        mButtonView.setVisibility(View.GONE);
        mButton1=(Button)mDialogView.findViewById(R.id.button1);
        mButton2=(Button)mDialogView.findViewById(R.id.button2);
        
        mDivideOne = mDialogView.findViewById(R.id.divider_one);
        
        setContentView(mDialogView);
        this.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                mLinearLayoutView.setVisibility(View.VISIBLE);
                if(type==null){
                    type=Effectstype.SlideBottom;
                }
                start(type);
            }
        });
//        mRlinearLayoutView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (isCancelable)dismiss();
//            }
//        });
    }

    public void toDefault(){
        mTitle.setTextColor(Color.parseColor(defTextColor));
        mDivider.setBackgroundColor(Color.parseColor(defDividerColor));
        mMessage.setTextColor(Color.parseColor(defMsgColor));
        mLinearLayoutView.setBackgroundColor(Color.parseColor(defDialogColor));
    }

    public ZyDialogBuilderTest setDividerColor(String colorString) {
        mDivider.setBackgroundColor(Color.parseColor(colorString));
        return this;
    }


    public ZyDialogBuilderTest setDialogTitle(CharSequence title) {
        toggleView(mLinearLayoutTopView,title);
        mTitle.setText(title);
        return this;
    }

    public ZyDialogBuilderTest setTitleColor(String colorString) {
        mTitle.setTextColor(Color.parseColor(colorString));
        return this;
    }

    public ZyDialogBuilderTest setMessage(int textResId) {
        toggleView(mLinearLayoutMsgView,textResId);
        mMessage.setText(textResId);
        return this;
    }

    public ZyDialogBuilderTest setMessage(CharSequence msg) {
        toggleView(mLinearLayoutMsgView,msg);
        mMessage.setText(msg);
        return this;
    }
    public ZyDialogBuilderTest setMessageColor(String colorString) {
        mMessage.setTextColor(Color.parseColor(colorString));
        return this;
    }

    public ZyDialogBuilderTest setIcon(int drawableResId) {
        mIcon.setImageResource(drawableResId);
        return this;
    }

    public ZyDialogBuilderTest setIcon(Drawable icon) {
        mIcon.setImageDrawable(icon);
        return this;
    }

    public ZyDialogBuilderTest setDuration(int duration) {
        this.mDuration=duration;
        return this;
    }

    public ZyDialogBuilderTest setEffect(Effectstype type) {
        this.type=type;
        return this;
    }
    
    public ZyDialogBuilderTest setButtonDrawable(int resid) {
        mButton1.setBackgroundResource(resid);
        mButton2.setBackgroundResource(resid);
        return this;
    }
    
    public ZyDialogBuilderTest setButtonClick(int which, CharSequence text, View.OnClickListener clickListener){
    	mButtonView.setVisibility(View.VISIBLE);
    	switch (which) {
		case BUTTON1:
			toggleView(mButton1,text);
	        mButton1.setText(text);
	        if (clickListener == null) {
				mButton1.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dismiss();
					}
				});
				break;
			}
	        mButton1.setOnClickListener(clickListener);
			break;
		case BUTTON2:
			toggleView(mButton2,text);
	        mButton2.setText(text);
	        mDivideOne.setVisibility(View.VISIBLE);
	        if (clickListener == null) {
	        	mButton2.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dismiss();
					}
				});
				break;
			}
	        mButton2.setOnClickListener(clickListener);
			break;

		default:
			break;
		}
    	return this;
    }


    public ZyDialogBuilderTest setCustomView(int resId, Context context) {
        View customView = View.inflate(context, resId, null);
        if (mFrameLayoutCustomView.getChildCount()>0){
            mFrameLayoutCustomView.removeAllViews();
        }
        mFrameLayoutCustomView.addView(customView);
        return this;
    }

    public ZyDialogBuilderTest setCustomView(View view, Context context) {
        if (mFrameLayoutCustomView.getChildCount()>0){
            mFrameLayoutCustomView.removeAllViews();
        }
        mFrameLayoutCustomView.addView(view);

        return this;
    }
//    public ZyDialogBuilderTest isCancelableOnTouchOutside(boolean cancelable) {
//        this.isCancelable=cancelable;
//        this.setCanceledOnTouchOutside(cancelable);
//        return this;
//    }
//
//    public ZyDialogBuilderTest isCancelable(boolean cancelable) {
//        this.isCancelable=cancelable;
//        this.setCancelable(cancelable);
//        return this;
//    }

    private void toggleView(View view,Object obj){
        if (obj==null){
            view.setVisibility(View.GONE);
        }else {
            view.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void show() {
        if (mTitle.getText().equals("")) mDialogView.findViewById(R.id.topPanel).setVisibility(View.GONE);
        super.show();
    }

    private void start(Effectstype type){
        BaseEffects animator = type.getAnimator();
        if(mDuration != -1){
            animator.setDuration(Math.abs(mDuration));
        }
        animator.start(mRlinearLayoutView);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mButton1.setVisibility(View.GONE);
        mButton2.setVisibility(View.GONE);
    }
}
