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
import butterknife.OnClick;
import utils.WindowHelper;

public class RedPacketDialog extends Dialog {
    @BindView(R.id.from_name)
    public TextView fromName;
    @BindView(R.id.money)
    public TextView money;
    private Context context;
    private View view;

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

    private void init() {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_red_packet, null);
        setContentView(view);
        ButterKnife.bind(this);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = (int) (WindowHelper.getScreenWidth(context) * 0.8f);
        params.height = (int) (WindowHelper.getScreenHeight(context) * 0.6f);
        getWindow().setAttributes(params);
        getWindow().setWindowAnimations(R.style.DialogRedAnimation);
        setCanceledOnTouchOutside(false);
    }

    public void setFromName(String name) {
        fromName.setText("来自" + name);
    }

    public void setMoney(String account) {
        money.setText(account);
    }

    @OnClick(R.id.exit)
    public void exit() {
        dismiss();
    }

    @OnClick(R.id.receive_details)
    public void receiveDetails() {

    }
}
