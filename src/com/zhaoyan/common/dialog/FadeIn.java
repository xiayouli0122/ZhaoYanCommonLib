package com.zhaoyan.common.dialog;

import com.nineoldandroids.animation.ObjectAnimator;

import android.view.View;

public class FadeIn extends BaseEffects{

    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view,"alpha",0,1).setDuration(mDuration)
        );
    }
}
