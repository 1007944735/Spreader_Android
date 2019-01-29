package com.sgevf.spreader.spreaderAndroid.glide.transform;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

public class CircleTransform extends BitmapTransformation {
    private static final String ID = "com.sgevf.glidedemo.CircleTransform";
    private static final byte[] ID_BYTES = ID.getBytes();

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return createCircleBitmapByShader(toTransform);
    }

    private Bitmap createCircleBitmapByClip(Bitmap bitmap) {
        int r = Math.min(bitmap.getWidth(), bitmap.getHeight());
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap newBitmap = Bitmap.createBitmap(r, r, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        Path path = new Path();
        path.addCircle(r / 2, r / 2, r / 2, Path.Direction.CW);
        canvas.clipPath(path);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return newBitmap;
    }

    private Bitmap createCircleBitmapByXfermode(Bitmap bitmap) {
        int r = Math.min(bitmap.getWidth(), bitmap.getHeight());
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap newBitmap = Bitmap.createBitmap(r, r, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawCircle(r / 2, r / 2, r / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return newBitmap;
    }

    private Bitmap createCircleBitmapByShader(Bitmap bitmap) {
        int r = Math.min(bitmap.getWidth(), bitmap.getHeight());
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap newBitmap = Bitmap.createBitmap(r, r, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(bitmapShader);
        canvas.drawCircle(r / 2, r / 2, r / 2, paint);
        return newBitmap;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof CircleTransform;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }
}
