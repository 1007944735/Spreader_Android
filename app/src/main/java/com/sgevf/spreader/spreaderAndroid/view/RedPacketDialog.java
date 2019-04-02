package com.sgevf.spreader.spreaderAndroid.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.animaiton.Rotate3dAnimation;
import com.sgevf.spreader.spreaderAndroid.glide.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import utils.WindowHelper;

public class RedPacketDialog extends Dialog implements View.OnClickListener {
    @BindView(R.id.head)
    public SemiCircleView head;
    @BindView(R.id.open)
    public TextView open;
    @BindView(R.id.front)
    public View front;
    @BindView(R.id.reverse)
    public View reverse;
    @BindView(R.id.banner)
    public Banner banner;
    @BindView(R.id.details)
    public CustomRelativeLayout details;
    @BindView(R.id.money)
    public TextView money;

    private Context context;
    private LayoutInflater inflater;
    private int centerX;
    private int centerY;
    private View view;
    private Rotate3dAnimation openAnimation;
    private Rotate3dAnimation closeAnimation;
    private float depthZ = 700;
    private int duration = 300;
    private OnOpenListener listener;
    private boolean mHeightNeedInit = true;
    private int mDetailHeight;
    private ObjectAnimator animator;


    public RedPacketDialog(@NonNull Context context) {
        super(context, R.style.DialogTheme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (banner != null) {
            banner.startAutoPlay();
        }
    }

    private void init() {
        inflater = LayoutInflater.from(context);
        view = LayoutInflater.from(context).inflate(R.layout.dialog_red_packet, null);
        setContentView(view);
        ButterKnife.bind(this);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = (int) (WindowHelper.getScreenWidth(context) * 0.9f);
        params.height = (int) (WindowHelper.getScreenHeight(context) * 0.7f);
        getWindow().setAttributes(params);
        getWindow().setWindowAnimations(R.style.DialogRedAnimation);
        setCanceledOnTouchOutside(false);
        open.setOnClickListener(this);
        initReverse();
    }

    private void initReverse() {
        initBanner();
        details.setmDrawListener(new CustomRelativeLayout.OnDrawListener() {
            @Override
            public void onDrawStart() {
                if (mHeightNeedInit) {
                    mDetailHeight = details.getHeight();
                    details.setTranslationY(-mDetailHeight);
                    if (mDetailHeight > 0) {
                        mHeightNeedInit = false;
                    }
                }

            }
        });
    }

    private void initBanner() {
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Accordion);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(4000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
    }

    public void setDataBeforeStart(List<String> images, List<String> titles) {
        //设置图片集合
        if (images != null)
            banner.setImages(images);
        //设置标题集合（当banner样式有显示title时）
        if (titles != null)
            banner.setBannerTitles(titles);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public void onClick(View v) {
//        startAnimation();
        listener.onClick(this);
    }

    public void startAnimation() {
        centerX = view.getWidth() / 2;
        centerY = view.getHeight() / 2;
        if (openAnimation == null) {
            initOpenAnimation();
//            initCloseAnimation();
        }
        if (openAnimation.hasStarted() && !openAnimation.hasEnded()) {
            return;
        }
        view.startAnimation(openAnimation);
    }

    private void initOpenAnimation() {
        openAnimation = new Rotate3dAnimation(0, 90, centerX, centerY, depthZ, true);
        openAnimation.setDuration(duration);
        openAnimation.setFillAfter(true);
        openAnimation.setInterpolator(new AccelerateInterpolator());
        openAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                front.setVisibility(View.GONE);
                reverse.setVisibility(View.VISIBLE);

                Rotate3dAnimation rotateAnimation = new Rotate3dAnimation(270, 360, centerX, centerY, depthZ, false);
                rotateAnimation.setDuration(duration);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                view.startAnimation(rotateAnimation);
                rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        animator = ObjectAnimator.ofFloat(details, "translationY", 0);
                        animator.setDuration(duration);
                        animator.start();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (banner != null) {
            banner.stopAutoPlay();
        }
        if (animator != null && animator.isRunning()) {
            animator.cancel();
            animator = null;
        }
    }

    private void initCloseAnimation() {
    }


    public interface OnOpenListener {
        void onClick(RedPacketDialog dialog);
    }

    public void setOnOpenListener(OnOpenListener listener) {
        this.listener = listener;
    }

//    @OnClick(R.id.money)
//    public void money(View view){
//        Log.d("TAG", "money: "+details.getWidth()+":"+details.getHeight());
//    }
}
