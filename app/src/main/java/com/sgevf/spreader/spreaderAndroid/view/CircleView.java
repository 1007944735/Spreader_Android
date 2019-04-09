package com.sgevf.spreader.spreaderAndroid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.sgevf.spreader.spreaderAndroid.R;

public class CircleView extends android.support.v7.widget.AppCompatImageView {
    private Paint paint;
    private Paint mPaint;
    private float width;
    private float height;
    private float radius;
    private Matrix matrix;


    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        matrix=new Matrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width=getMeasuredWidth();
        height=getMeasuredHeight();
        radius=Math.min(width,height)/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(width/2,height/2,radius,mPaint);
        Drawable drawable=getDrawable();
        if(drawable==null){
            super.onDraw(canvas);
            return;
        }
        if(drawable instanceof BitmapDrawable){
            paint.setShader(initBitmapDrawable((BitmapDrawable) drawable));
            canvas.drawCircle(width/2,height/2,radius,paint);
        }
    }

    private Shader initBitmapDrawable(BitmapDrawable drawable) {
        Bitmap bitmap=drawable.getBitmap();
        BitmapShader shader=new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale=Math.max(width/bitmap.getWidth(),height/bitmap.getHeight());
        matrix.setScale(scale,scale);
        shader.setLocalMatrix(matrix);
        return shader;
    }
}
