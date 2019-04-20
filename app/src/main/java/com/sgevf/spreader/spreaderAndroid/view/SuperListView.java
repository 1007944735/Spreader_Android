package com.sgevf.spreader.spreaderAndroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class SuperListView extends ListView {
    public SuperListView(Context context) {
        super(context);
    }

    public SuperListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SuperListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
