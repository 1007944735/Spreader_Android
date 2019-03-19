package com.sgevf.spreader.spreaderAndroid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.sgevf.spreader.spreaderAndroid.R;

public class BubbleTextView extends android.support.v7.widget.AppCompatTextView {
    private int storkColor;
    private int radius;
    private int storkWidth;
    private int backColor;
    private Paint storkPaint;
    private Paint backPaint;
    private int width;
    private int height;
    private Path path;
    public BubbleTextView(Context context) {
        this(context,null);
    }

    public BubbleTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BubbleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.BubbleTextView);
        storkColor=array.getColor(R.styleable.BubbleTextView_storkColor, Color.parseColor("#1296db"));
        radius=array.getDimensionPixelOffset(R.styleable.BubbleTextView_radius,0);
        storkWidth=array.getDimensionPixelOffset(R.styleable.BubbleTextView_storkWidth,0);
        backColor=array.getColor(R.styleable.BubbleTextView_backColor,Color.WHITE);
        array.recycle();
        init();
    }

    private void init() {
        storkPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        storkPaint.setColor(storkColor);
        storkPaint.setStyle(Paint.Style.STROKE);
        storkPaint.setStrokeWidth(storkWidth);
        backPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        backPaint.setColor(backColor);
        backPaint.setStyle(Paint.Style.FILL);
        setBackgroundColor(Color.TRANSPARENT);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width=getMeasuredWidth();
        height=getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        path=new Path();
        path.moveTo(radius,0);
        path.lineTo(width-radius,0);
        path.addArc(new RectF(-radius,0,radius,radius*2),-90,0);
//        path.lineTo(width,height);
//        path.lineTo(0,height);
//        path.close();
        canvas.drawPath(path,backPaint);
        canvas.drawPath(path,storkPaint);
        super.onDraw(canvas);

    }
}
