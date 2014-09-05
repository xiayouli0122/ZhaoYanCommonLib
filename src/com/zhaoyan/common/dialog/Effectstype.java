package com.zhaoyan.common.dialog;


public enum  Effectstype {

	FadeIn(FadeIn.class),
    SlideBottom(SlideBottom.class);

    private Class<BaseEffects> effectsClass;
//
    private Effectstype(Class mclass) {
    	effectsClass = mclass;
    }

    public BaseEffects getAnimator() {
        try {
            return (BaseEffects) effectsClass.newInstance();
        } catch (Exception e) {
            throw new Error("Can not init animatorClazz instance");
        }
    }
}
