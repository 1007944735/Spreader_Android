package com.sgevf.spreader.spreaderAndroid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sgevf.spreader.spreaderAndroid.R;

public class SemiCircleView extends View {
    private Paint paint;
    private int color;

    public SemiCircleView(Context context) {
        this(context, null);
    }

    public SemiCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SemiCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SemiCircleView);
        color=array.getColor(R.styleable.SemiCircleView_color,Color.WHITE);
        array.recycle();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getMode(heightMeasureSpec);
        setMeasuredDimension(widthSize, widthSize/2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawCircle(getMeasuredWidth() / 2, 0, getMeasuredWidth() / 2, paint);
    }
}
