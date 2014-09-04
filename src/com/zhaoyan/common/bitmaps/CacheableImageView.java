package com.zhaoyan.common.bitmaps;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CacheableImageView extends ImageView {

    private static void onDrawableSet(Drawable drawable) {
        if (drawable instanceof CacheableBitmapDrawable) {
            ((CacheableBitmapDrawable) drawable).setBeingUsed(true);
        }
    }

    private static void onDrawableUnset(final Drawable drawable) {
        if (drawable instanceof CacheableBitmapDrawable) {
            ((CacheableBitmapDrawable) drawable).setBeingUsed(false);
        }
    }

    public CacheableImageView(Context context) {
        super(context);
    }

    public CacheableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CacheableImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        final Drawable previousDrawable = getDrawable();

        // Set new Drawable
        super.setImageDrawable(drawable);

        if (drawable != previousDrawable) {
            onDrawableSet(drawable);
            onDrawableUnset(previousDrawable);
        }
    }

    @Override
    public void setImageResource(int resId) {
        final Drawable previousDrawable = getDrawable();
        super.setImageResource(resId);
        onDrawableUnset(previousDrawable);
    }

    @Override
    public void setImageURI(Uri uri) {
        final Drawable previousDrawable = getDrawable();
        super.setImageURI(uri);
        onDrawableUnset(previousDrawable);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        // Will cause displayed bitmap wrapper to be 'free-able'
        setImageDrawable(null);
    }

}
