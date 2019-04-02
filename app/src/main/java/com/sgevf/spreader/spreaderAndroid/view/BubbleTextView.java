package com.sgevf.spreader.spreaderAndroid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.ButtonBarLayout;
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
    private int bubbleGravity;
    private int offset;
    private int bubbleHeight;
    private int bubbleWidth = 10;

    public BubbleTextView(Context context) {
        this(context, null);
    }

    public BubbleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BubbleTextView);
        storkColor = array.getColor(R.styleable.BubbleTextView_storkColor, Color.parseColor("#1296db"));
        radius = array.getDimensionPixelOffset(R.styleable.BubbleTextView_radius, 0);
        storkWidth = array.getDimensionPixelOffset(R.styleable.BubbleTextView_storkWidth, 0);
        backColor = array.getColor(R.styleable.BubbleTextView_backColor, Color.WHITE);
        bubbleGravity = array.getInt(R.styleable.BubbleTextView_bubbleGravity, 0);
        offset = array.getDimensionPixelOffset(R.styleable.BubbleTextView_offset, 0);
        bubbleHeight = array.getDimensionPixelOffset(R.styleable.BubbleTextView_bubbleHeight, 10);
        array.recycle();
        init();
    }

    private void init() {
        storkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        storkPaint.setColor(storkColor);
        storkPaint.setStyle(Paint.Style.STROKE);
        storkPaint.setStrokeWidth(storkWidth);
        backPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backPaint.setColor(backColor);
        backPaint.setStyle(Paint.Style.FILL);
        setBackgroundColor(Color.TRANSPARENT);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth() - 2 * bubbleHeight;
        height = getMeasuredHeight() - 2 * bubbleHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (bubbleGravity == 0) {
            path = new Path();
            path.moveTo(radius, 0);
            path.lineTo(width / 2 - bubbleWidth + offset, 0);
            path.lineTo(width / 2, -bubbleHeight);
            path.lineTo(width / 2 + bubbleWidth + offset, 0);
            path.lineTo(width - radius, 0);
            path.arcTo(new RectF(width - radius * 2, 0, width, radius * 2), -90, 90, false);
            path.lineTo(width, height - radius);
            path.arcTo(new RectF(width - radius * 2, height - 2 * radius, width, height), 0, 90, false);
            path.lineTo(radius, height);
            path.arcTo(new RectF(0, height - 2 * radius, radius * 2, height), 90, 90, false);
            path.lineTo(0, radius);
            path.arcTo(new RectF(0, 0, radius * 2, radius * 2), 180, 90, false);
            path.close();
        } else if (bubbleGravity == 1) {
            path = new Path();
            path.moveTo(radius, 0);
            path.lineTo(width - radius, 0);
            path.arcTo(new RectF(width - radius * 2, 0, width, radius * 2), -90, 90, false);
            path.lineTo(width, height / 2 - bubbleWidth + offset);
            path.lineTo(width + bubbleHeight, height / 2);
            path.lineTo(width, height / 2 + bubbleWidth + offset);
            path.lineTo(width, height - radius);
            path.arcTo(new RectF(width - radius * 2, height - 2 * radius, width, height), 0, 90, false);
            path.lineTo(radius, height);
            path.arcTo(new RectF(0, height - 2 * radius, radius * 2, height), 90, 90, false);
            path.lineTo(0, radius);
            path.arcTo(new RectF(0, 0, radius * 2, radius * 2), 180, 90, false);
            path.close();
        } else if (bubbleGravity == 2) {
            path = new Path();
            path.moveTo(radius, 0);
            path.lineTo(width - radius, 0);
            path.arcTo(new RectF(width - radius * 2, 0, width, radius * 2), -90, 90, false);
            path.lineTo(width, height - radius);
            path.arcTo(new RectF(width - radius * 2, height - 2 * radius, width, height), 0, 90, false);
            path.lineTo(width / 2 + bubbleWidth + offset, height);
            path.lineTo(width / 2, height + bubbleHeight);
            path.lineTo(width / 2 - bubbleWidth + offset, height);
            path.lineTo(radius, height);
            path.arcTo(new RectF(0, height - 2 * radius, radius * 2, height), 90, 90, false);
            path.lineTo(0, radius);
            path.arcTo(new RectF(0, 0, radius * 2, radius * 2), 180, 90, false);
            path.close();
        } else if (bubbleGravity == 3) {
            path = new Path();
            path.moveTo(radius, 0);
            path.lineTo(width - radius, 0);
            path.arcTo(new RectF(width - radius * 2, 0, width, radius * 2), -90, 90, false);
            path.lineTo(width, height - radius);
            path.arcTo(new RectF(width - radius * 2, height - 2 * radius, width, height), 0, 90, false);
            path.lineTo(radius, height);
            path.arcTo(new RectF(0, height - 2 * radius, radius * 2, height), 90, 90, false);
            path.lineTo(0, height / 2 + bubbleWidth + offset);
            path.lineTo(-bubbleHeight, height / 2);
            path.lineTo(0, height / 2 - bubbleWidth + offset);
            path.lineTo(0, radius);
            path.arcTo(new RectF(0, 0, radius * 2, radius * 2), 180, 90, false);
            path.close();
        }
        canvas.drawPath(path, backPaint);
        canvas.drawPath(path, storkPaint);
        super.onDraw(canvas);

    }
}
