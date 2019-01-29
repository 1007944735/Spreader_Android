package com.sgevf.spreader.spreaderAndroid.glide.transform;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.util.Util;

import java.nio.ByteBuffer;
import java.security.MessageDigest;

public class RoundTransform extends BitmapTransformation {
    private static final String ID="com.sgevf.glidedemo.glide.RoundTransform";
    private static final byte[] ID_BYTES = ID.getBytes();

    private float rx;
    private float ry;

    public RoundTransform(float raduis){
        this(raduis,raduis);
    }

    public RoundTransform(float rx, float ry){
        this.rx=rx;
        this.ry=ry;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return createRoundImage(toTransform);
    }

    private Bitmap createRoundImage(Bitmap bitmap) {
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();

        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        Bitmap newBitmap=Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(newBitmap);
        BitmapShader shader=new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawRoundRect(new RectF(0,0,width,height),rx,ry,paint);
        return newBitmap;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
        byte[] x= ByteBuffer.allocate(4).putFloat(rx).array();
        messageDigest.update(x);
        byte[] y= ByteBuffer.allocate(4).putFloat(ry).array();
        messageDigest.update(y);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof RoundTransform){
            RoundTransform round= (RoundTransform) obj;
            return rx==round.rx
                    &&ry==round.ry;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Util.hashCode(ID_BYTES,loopHash(rx,ry));
    }

    private int loopHash(float... f){
        int code=0;
        for (float fl:f) {
            code=Util.hashCode(code,Util.hashCode(fl));
        }
        return code;
    }


}
