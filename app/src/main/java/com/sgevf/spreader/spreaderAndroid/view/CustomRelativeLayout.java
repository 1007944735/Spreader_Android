package com.sgevf.spreader.spreaderAndroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class CustomRelativeLayout extends RelativeLayout {
    private OnDrawListener mDrawListener;

    public CustomRelativeLayout(Context context) {
        super(context);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDrawListener != null) {
            mDrawListener.onDrawStart();
        }
    }

    public void setmDrawListener(OnDrawListener mDrawListener) {
        this.mDrawListener = mDrawListener;
    }

    public interface OnDrawListener {
        void onDrawStart();
    }
}
