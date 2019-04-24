package com.sgevf.spreader.spreaderAndroid.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.sgevf.spreader.spreaderAndroid.glide.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.List;

public class VideoBannerViewAdapter extends PagerAdapter {
    private Context context;
    private String videoUrl;
    private List<String> images;

    public VideoBannerViewAdapter(Context context, String videoUrl, List<String> images) {
        this.context = context;
        this.videoUrl = videoUrl;
        this.images = images;
    }

    @Override
    public int getCount() {
//        if (videoUrl != null) {
//            return 2;
//        } else {
//            return 1;
//        }
        return 1;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Banner banner = new Banner(context);
        banner.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        banner.setImages(images);
        banner.setVideoUrl(videoUrl);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(new GlideImageLoader());
        banner.setBannerAnimation(Transformer.Default);
        banner.isAutoPlay(true);
        banner.setDelayTime(8000);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.start();
        container.addView(banner);
        return banner;
}

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}