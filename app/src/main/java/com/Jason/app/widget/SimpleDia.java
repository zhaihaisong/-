package com.Jason.app.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Spanned;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.TextView;

import com.Jason.app.R;


/**
 * 带轻微动画效果的对话框
 * @author Jason
 */
public class SimpleDia extends Dialog implements View.OnClickListener {
	private View mDialogView;
	private TextView mTitleTextView;
    private TextView mContentTextView;
    private TextView mConfirmButton;
    private TextView mCancelButton;
    private String mTitleText;
    private Spanned mContentText;
    private String mConfirmText;
    private String mCancelText;
    private OnClickButtonListener mOnClickButtonListener;
    private boolean mShowCancel;
    private boolean mShowContent;
    private boolean mCloseFromCancel;
    private AnimationSet mModalInAnim;
    private AnimationSet mModalOutAnim;
    private Animation mOverlayOutAnim;
    private int mAlertType;
    public static final int Normal_Type = 0;
    public static final int Ok_No_Type = 1;
    
    public interface OnClickButtonListener {
        void onClick(SimpleDia dialog, int flag);
    }
    
    public SimpleDia(Context context) {
        this(context, Normal_Type);
    }

    public SimpleDia(final Context context, int alertType) {
        super(context, R.style.alert_dialog);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        mAlertType = alertType;
        mModalInAnim = (AnimationSet) SimpleAnim.loadAnimation(getContext(), R.anim.modal_in);
        mModalOutAnim = (AnimationSet) SimpleAnim.loadAnimation(getContext(), R.anim.modal_out);
        mModalOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            	//
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                mDialogView.setVisibility(View.GONE);
                mDialogView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mCloseFromCancel) {
                        	SimpleDia.super.cancel();
                        } else {
                        	SimpleDia.super.dismiss();
                        }
                    }
                });
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            	//
            }
        });
        mOverlayOutAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                WindowManager.LayoutParams wlp = getWindow().getAttributes();
                wlp.alpha = 1 - interpolatedTime;
                getWindow().setAttributes(wlp);
            }
        };
        mOverlayOutAnim.setDuration(120);
    }

    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm);
        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        mTitleTextView = (TextView)findViewById(R.id.tv_title);
        mContentTextView = (TextView)findViewById(R.id.tv_content);
        mConfirmButton = (TextView)findViewById(R.id.tv_ok);
        mCancelButton = (TextView)findViewById(R.id.tv_no);
        
        mConfirmButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);
        
        setTitleText(mTitleText);
        setContentText(mContentText);
        setConfirmText(mConfirmText);
        setCancelText(mCancelText);
        changeAlertType(mAlertType, true);
    }
    
    private void restore() {
    	mConfirmButton.setVisibility(View.VISIBLE);
    }
    
    private void changeAlertType(int alertType, boolean fromCreate) {
    	mAlertType = alertType;
    	if (mDialogView != null) {
    		if (!fromCreate) {
    			restore();
    		}
    		switch (mAlertType) {
    		case Ok_No_Type:
    		}
    	}
    }
    
    public int getAlerType () {
        return mAlertType;
    }
    
    public void changeAlertType(int alertType) {
        changeAlertType(alertType, false);
    }
    
    public String getTitleText () {
        return mTitleText;
    }
    
    public SimpleDia setTitleText (String text) {
        mTitleText = text;
        if (mTitleTextView != null && mTitleText != null) {
            mTitleTextView.setText(mTitleText);
        }
        return this;
    }
    public String getContentText() {
        return mContentText.toString();
    }
    
    public SimpleDia setContentText (Spanned text) {
        mContentText = text;
        if (mContentTextView != null && mContentText != null) {
            showContentText(true);
            mContentTextView.setText(mContentText);
        }
        return this;
    }
    
    public boolean isShowCancelButton () {
        return mShowCancel;
    }
    
    public SimpleDia showCancelButton (boolean isShow) {
        mShowCancel = isShow;
        if (mCancelButton != null) {
            mCancelButton.setVisibility(mShowCancel ? View.VISIBLE : View.GONE);
        }
        return this;
    }
    
    public boolean isShowContentText () {
        return mShowContent;
    }
    
    public SimpleDia showContentText (boolean isShow) {
        mShowContent = isShow;
        if (mContentTextView != null) {
            mContentTextView.setVisibility(mShowContent ? View.VISIBLE : View.GONE);
        }
        return this;
    }
    
    public String getCancelText () {
        return mCancelText;
    }
    
    public SimpleDia setCancelText (String text) {
        mCancelText = text;
        if (mCancelButton != null && mCancelText != null) {
            showCancelButton(true);
            mCancelButton.setText(mCancelText);
        }
        return this;
    }
    
    public String getConfirmText () {
        return mConfirmText;
    }
    
    public SimpleDia setConfirmText (String text) {
        mConfirmText = text;
        if (mConfirmButton != null && mConfirmText != null) {
            mConfirmButton.setText(mConfirmText);
        }
        return this;
    }
    
//    public SimpleDia setCancelClickListener (OnSimpleDiaClickListener listener) {
//        mCancelClickListener = listener;
//        return this;
//    }

    public SimpleDia setClickListener(OnClickButtonListener listener) {
    	mOnClickButtonListener = listener;
        return this;
    }
    
    protected void onStart() {
        mDialogView.startAnimation(mModalInAnim);
    }
    
    @Override
    public void cancel() {
        dismissWithAnimation(true);
    }
    
    public void dismissWithAnimation() {
        dismissWithAnimation(false);
    }
    
    private void dismissWithAnimation(boolean fromCancel) {
        mCloseFromCancel = fromCancel;
        mConfirmButton.startAnimation(mOverlayOutAnim);
        mDialogView.startAnimation(mModalOutAnim);
    }
    
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.tv_no) {
            if (mOnClickButtonListener != null) {
            	mOnClickButtonListener.onClick(SimpleDia.this, 0);
            	cancel();
            } else {
                dismissWithAnimation();
            }
        } else if (v.getId() == R.id.tv_ok) {
            if (mOnClickButtonListener != null) {
            	mOnClickButtonListener.onClick(SimpleDia.this, 1);
            	cancel();
            } else {
                dismissWithAnimation();
            }
        }
	}
    
}