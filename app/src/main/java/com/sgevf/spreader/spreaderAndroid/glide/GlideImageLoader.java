package com.sgevf.spreader.spreaderAndroid.glide;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        GlideManager.showImage(context, (String) path, imageView);
    }
}
