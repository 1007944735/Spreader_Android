package com.sgevf.spreader.spreaderAndroid.glide;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.request.RequestOptions;

/**
 * 全局设定，只需配置一次
 */
public class GlideConfig extends RequestOptions {
    public static int errorId;
    public static int placeHolder;
    public static int fallBack;

    public GlideConfig init() {
        if (errorId != 0) {
            error(errorId);
        }
        if (placeHolder != 0) {
            placeholder(placeHolder);
        }
        if (fallBack != 0) {
            fallback(fallBack);
        }
        return this;
    }

    public GlideConfig transition(Transformation transformation) {
        if (transformation != null) {
            transform(transformation);
        }
        return this;
    }
}
