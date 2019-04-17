package com.sgevf.spreader.spreaderAndroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sgevf.spreader.spreaderAndroid.R;

public class SuperProgressBar extends View {
    private Paint upload;
    private Paint circle;
    private Paint check;
    private Paint text;
    private int radius;
    private float progress = 0;
    private int strokeWidth = 20;
    private int checkWidth = 15;
    private int line1_x = 0;
    private int line1_y = 0;
    private int line2_x = 0;
    private int line2_y = 0;
    private Path path;
    private String num="";

    public SuperProgressBar(Context context) {
        this(context, null);
    }

    public SuperProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        upload = new Paint(Paint.ANTI_ALIAS_FLAG);
        circle = new Paint(Paint.ANTI_ALIAS_FLAG);
        check = new Paint(Paint.ANTI_ALIAS_FLAG);
        text = new Paint(Paint.ANTI_ALIAS_FLAG);
        upload.setColor(getResources().getColor(R.color.colorTheme));
        upload.setStrokeWidth(strokeWidth);
        upload.setStyle(Paint.Style.STROKE);
        upload.setStrokeCap(Paint.Cap.ROUND);
        check.setColor(getResources().getColor(R.color.colorTheme));
        check.setStrokeWidth(checkWidth);
        check.setStyle(Paint.Style.STROKE);
        check.setStrokeCap(Paint.Cap.ROUND);
        circle.setColor(Color.WHITE);
        text.setColor(getResources().getColor(R.color.colorTheme));
        text.setTextAlign(Paint.Align.CENTER);
        text.setTextSize(50);
        path = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, 200);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, 200);
        } else {
            setMeasuredDimension(widthSize, heightSize);
        }
        radius = Math.min(getMeasuredWidth(), getMeasuredHeight()) / 2;
        path.moveTo(radius / 2-10, radius);
    }

    public void setProgress(float progress,String num) {
        this.progress = progress;
        invalidate();
        this.num=num;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(radius, radius, radius, circle);
        canvas.drawArc(new RectF(strokeWidth/2, strokeWidth/2, radius * 2 - strokeWidth/2, radius * 2 - strokeWidth/2), -90, 360 * progress, false, upload);
        canvas.drawCircle(radius, radius, radius - strokeWidth, circle);
        if (progress >= 1) {
            drawCheck(canvas);
        }else {
            drawNum(canvas);
        }
        postInvalidateDelayed(10);
    }

    private void drawNum(Canvas canvas) {
        if(!num.isEmpty()){
            Paint.FontMetrics fontMetrics=text.getFontMetrics();
            float top=fontMetrics.top;
            float bottom=fontMetrics.bottom;
            int baseline= (int) (radius-top/2-bottom/2);
            canvas.drawText(num,radius,baseline,text);
        }
    }

    private void drawCheck(Canvas canvas) {
        if (line1_x < radius / 2) {
            line1_x += 2;
            line1_y += 2;
        }
//        canvas.drawLine(radius / 2, radius, radius / 2 + line1_x, radius + line1_y, check);
        if (line1_x >= radius / 2 && line2_x < 3 * radius / 4) {
            line2_x += 2;
            line2_y += 2;
        }
        path.lineTo(radius / 2 + line1_x + line2_x-10, radius + line1_y - line2_y);
        canvas.drawPath(path, check);
    }
}
