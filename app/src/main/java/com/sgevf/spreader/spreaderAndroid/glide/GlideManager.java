package com.sgevf.spreader.spreaderAndroid.glide;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.Transformation;
import com.sgevf.spreader.spreaderAndroid.glide.transform.CircleTransform;
import com.sgevf.spreader.spreaderAndroid.glide.transform.RoundTransform;

public class GlideManager {

    public static void showImage(Context context, String url,ImageView view){
        showImage(context,url,view,null);
    }

    public static void showImage(Context context, String url, ImageView view, Transformation transition){
        GlideApp.with(context)
                .load(url)
                .apply(new GlideConfig().init().transition(transition))
                .into(view);
    }

    public static void circleImage(Context context, String url,ImageView view){
        showImage(context,url,view,new CircleTransform());
    }

    public static void circleImage(Context context, int resId,ImageView view){
        GlideApp.with(context)
                .load(resId)
                .apply(new GlideConfig().init().transition(new CircleTransform()))
                .into(view);
    }

    public static void roundImage(Context context, String url,ImageView view,float raduis){
        roundImage(context,url,view,raduis,raduis);
    }

    public static void roundImage(Context context, String url,ImageView view,float rx,float ry){
        showImage(context,url,view,new RoundTransform(rx,ry));
    }

    public static void showImage(Activity activity, String url,ImageView view){
        showImage(activity,url,view,null);
    }

    public static void showImage(Activity activity, String url, ImageView view, Transformation transition){
        GlideApp.with(activity)
                .load(url)
                .apply(new GlideConfig().init().transition(transition))
                .into(view);
    }

    public static void circleImage(Activity activity, String url,ImageView view){
        showImage(activity,url,view,new CircleTransform());
    }

    public static void roundImage(Activity activity, String url,ImageView view,float raduis){
        roundImage(activity,url,view,raduis,raduis);
    }

    public static void roundImage(Activity activity, String url,ImageView view,float rx,float ry){
        showImage(activity,url,view,new RoundTransform(rx,ry));
    }

    public static void showImage(Fragment fragment, String url,ImageView view){
        showImage(fragment,url,view,null);
    }

    public static void showImage(Fragment fragment, String url, ImageView view, Transformation transition){
        GlideApp.with(fragment)
                .load(url)
                .apply(new GlideConfig().init().transition(transition))
                .into(view);
    }

    public static void circleImage(Fragment fragment, String url,ImageView view){
        showImage(fragment,url,view,new CircleTransform());
    }

    public static void roundImage(Fragment fragment, String url,ImageView view,float raduis){
        roundImage(fragment,url,view,raduis,raduis);
    }

    public static void roundImage(Fragment fragment, String url,ImageView view,float rx,float ry){
        showImage(fragment,url,view,new RoundTransform(rx,ry));
    }

    public static void showImage(View view, String url,ImageView target){
        showImage(view,url,target,null);
    }

    public static void showImage(View view, String url, ImageView target, Transformation transition){
        GlideApp.with(view)
                .load(url)
                .apply(new GlideConfig().init().transition(transition))
                .into(target);
    }

    public static void circleImage(View view, String url,ImageView target){
        showImage(view,url,target,new CircleTransform());
    }

    public static void roundImage(View view, String url,ImageView target,float raduis){
        roundImage(view,url,target,raduis,raduis);
    }

    public static void roundImage(View view, String url,ImageView target,float rx,float ry){
        showImage(view,url,target,new RoundTransform(rx,ry));
    }
}
