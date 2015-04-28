package com.zhaoyan.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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

import com.zhaoyan.common.lib.R;

public class ZyDialogBuilder extends Dialog {

    private final String defTextColor="#FFFFFFFF";
    private final String defDividerColor="#11000000";
    private final String defMsgColor="#FFFFFFFF";
    private final String defDialogColor="#FFE74C3C";
    
    private int mTitleColor;
    private int mTitleTextColor;

    private Context mContext;

    private Effectstype mEffectstype = null;

    private LinearLayout mLinearLayoutView;
    private RelativeLayout mRlinearLayoutView;
    private LinearLayout mLinearLayoutMsgView;
    private LinearLayout mLinearLayoutTopView;
    private FrameLayout mFrameLayoutCustomView;
    
    private View mCustomView;

    private View mDialogView;
    private View mDivider;
    private View mButtonView;

    private TextView mTitleView;
    private ImageView mIconView;
    private TextView mMessageView;

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    
    private View mDivideOne;
    private View mDivideTwo;

    private int mDuration = -1;
    
    private String mTitle;
	private String mMessage;
	private int mIconResId = -1;
	
	private boolean mHasMessage = false;
	private boolean mShowTitle = false;
	private boolean mShowNegativeBtn = false;
	private boolean mShowPositiveBtn = false;
	private boolean mShowNeutralBtn = false;
	
	private String mNegativeMessage,mPositiveMessage,mNeutralMessage;
	
	private onZyDialogClickListener mNegativeListener;
	private onZyDialogClickListener mPositiveListener;
	private onZyDialogClickListener mNeutralListener;

    private volatile static ZyDialogBuilder instance;
    
    public interface onZyDialogClickListener{
		void onClick(Dialog dialog);
	}

    public ZyDialogBuilder(Context context) {
        super(context, R.style.dialog_untran);
        mContext = context;
    }
    public ZyDialogBuilder(Context context,int theme) {
        super(context, theme);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width  = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        
        init(mContext);
    }

    public static ZyDialogBuilder getInstance(Context context) {
        if (instance == null) {
            synchronized (ZyDialogBuilder.class) {
                if (instance == null) {
                    instance = new ZyDialogBuilder(context,R.style.dialog_untran);
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
        
        if (mCustomView != null) {
        	 if (mFrameLayoutCustomView.getChildCount()>0){
                 mFrameLayoutCustomView.removeAllViews();
             }
             mFrameLayoutCustomView.addView(mCustomView);
		}
        
        if (mShowTitle) {
        	mLinearLayoutTopView.setVisibility(View.VISIBLE);
        	 mTitleView = (TextView) mDialogView.findViewById(R.id.alertTitle);
             mTitleView.setTextColor(Color.WHITE);
             mTitleView.setText(mTitle);
             
             mIconView = (ImageView) mDialogView.findViewById(R.id.icon);
             if (mIconResId != -1) {
            	 mIconView.setImageResource(mIconResId);
			}
		} else {
			mLinearLayoutTopView.setVisibility(View.GONE);
		}
        
        if (mHasMessage) {
        	mLinearLayoutMsgView.setVisibility(View.VISIBLE);
        	mMessageView = (TextView) mDialogView.findViewById(R.id.message);
        	mMessageView.setText(mMessage);
		} else {
			mLinearLayoutMsgView.setVisibility(View.GONE);
		}

        mButtonView = mDialogView.findViewById(R.id.button_layout);
        
        if (!mShowNegativeBtn && !mShowPositiveBtn && !mShowNeutralBtn) {
			mButtonView.setVisibility(View.GONE);
		}else {
			if (mShowNegativeBtn) {
				mButton1=(Button)mDialogView.findViewById(R.id.button1);
				mButton1.setText(mNegativeMessage);
				mButton1.setVisibility(View.VISIBLE);
				mButton1.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (null == mNegativeListener) {
							dismiss();
						} else {
							mNegativeListener.onClick(ZyDialogBuilder.this);
						}
					}
				});
			}
			
			if (mShowNeutralBtn) {
				mButton2=(Button)mDialogView.findViewById(R.id.button2);
				mButton2.setText(mNeutralMessage);
				mButton2.setVisibility(View.VISIBLE);
				mButton2.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (null == mNeutralListener) {
							dismiss();
						} else {
							mNeutralListener.onClick(ZyDialogBuilder.this);
						}
					}
				});
			}
			
			if (mShowPositiveBtn) {
				 mButton3 = (Button) mDialogView.findViewById(R.id.button3);
				 mButton3.setText(mPositiveMessage);
				 mButton3.setVisibility(View.VISIBLE);
				 mButton3.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							if (null == mPositiveListener) {
								dismiss();
							} else {
								mPositiveListener.onClick(ZyDialogBuilder.this);
							}
						}
					});
			}
			
			mDivideOne = mDialogView.findViewById(R.id.divider_one);
			mDivideTwo = mDialogView.findViewById(R.id.divider_two);
			if (mShowNegativeBtn && mShowNeutralBtn) {
				mDivideOne.setVisibility(View.VISIBLE);
			}
			
			if (mShowNeutralBtn && mShowPositiveBtn) {
				mDivideTwo.setVisibility(View.VISIBLE);
			}
			
			if (mShowNegativeBtn && mShowPositiveBtn) {
				mDivideOne.setVisibility(View.VISIBLE);
			}
		}
        
        setContentView(mDialogView);
        this.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                mLinearLayoutView.setVisibility(View.VISIBLE);
                if(mEffectstype==null){
//                    mEffectstype=Effectstype.SlideBottom;
                	return;
                }
                start(mEffectstype);
            }
        });
    }
    

    public void toDefault(){
        mTitleView.setTextColor(Color.parseColor(defTextColor));
        mDivider.setBackgroundColor(Color.parseColor(defDividerColor));
        mMessageView.setTextColor(Color.parseColor(defMsgColor));
        mLinearLayoutView.setBackgroundColor(Color.parseColor(defDialogColor));
    }

    public ZyDialogBuilder setDividerColor(String colorString) {
        mDivider.setBackgroundColor(Color.parseColor(colorString));
        return this;
    }


    public ZyDialogBuilder setDialogTitle(CharSequence title) {
    	mShowTitle= true;
    	mTitle = (String) title;
        return this;
    }
    
    public ZyDialogBuilder setDialogTitle(int resId) {
    	String title = mContext.getString(resId);
        return setDialogTitle(title);
    }
    
    public ZyDialogBuilder setTitleColor(int colorResId){
    	
    	return this;
    }

    public ZyDialogBuilder setMessage(int textResId) {
    	String msg = mContext.getString(textResId);
        return setMessage(msg);
    }

    public ZyDialogBuilder setMessage(CharSequence msg) {
    	mHasMessage = true;
    	mMessage = (String) msg;
        return this;
    }

    public ZyDialogBuilder setIcon(int drawableResId) {
        mIconResId = drawableResId;
        return this;
    }

    public ZyDialogBuilder setDuration(int duration) {
        this.mDuration=duration;
        return this;
    }

    public ZyDialogBuilder setEffect(Effectstype type) {
        this.mEffectstype=type;
        return this;
    }
    
    public void setNegativeButton(String text, onZyDialogClickListener listener){
		mNegativeMessage = text;
		mShowNegativeBtn = true;
		mNegativeListener = listener;
	}
	
	public void setNegativeButton(int textId, onZyDialogClickListener listener){
		String text = mContext.getString(textId);
		setNegativeButton(text, listener);
	}
	
	public void setPositiveButton(String text, onZyDialogClickListener listener){
		mPositiveMessage = text;
		mShowPositiveBtn = true;
		mPositiveListener = listener;
	}
	
	public void setPositiveButton(int textId, onZyDialogClickListener listener){
		String text = mContext.getString(textId);
		setPositiveButton(text, listener);
	}
	
	public void setNeutralButton(String text, onZyDialogClickListener listener){
		mNeutralMessage = text;
		mShowNeutralBtn = true;
		mNeutralListener = listener;
	}
	
	public void setNeutralButton(int textId, onZyDialogClickListener listener){
		String text = mContext.getString(textId);
		setPositiveButton(text, listener);
	}

    public ZyDialogBuilder setCustomView(int resId, Context context) {
        View customView = View.inflate(context, resId, null);
       mCustomView = customView;
        return this;
    }

    public ZyDialogBuilder setCustomView(View view) {
        mCustomView = view;
        return this;
    }

    private void toggleView(View view,Object obj){
        if (obj==null){
            view.setVisibility(View.GONE);
        }else {
            view.setVisibility(View.VISIBLE);
        }
    }
    
    @Override
    public void show() {
//        if (mTitleView.getText().equals("")) mDialogView.findViewById(R.id.topPanel).setVisibility(View.GONE);
        super.show();
    }

    private void start(Effectstype type){
        BaseEffects animator = type.getAnimator();
        if(mDuration != -1){
            animator.setDuration(Math.abs(mDuration));
        }
        animator.start(mRlinearLayoutView);
    }
	
}
