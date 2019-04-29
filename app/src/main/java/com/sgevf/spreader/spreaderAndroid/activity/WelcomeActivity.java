package com.sgevf.spreader.spreaderAndroid.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.amap.api.location.AMapLocation;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.config.HttpConfig;
import com.sgevf.spreader.spreaderAndroid.config.UserConfig;
import com.sgevf.spreader.spreaderAndroid.glide.GlideConfig;
import com.sgevf.spreader.spreaderAndroid.map.MapLocationHelper;
import com.sgevf.spreader.spreaderAndroid.model.InitModel;
import com.sgevf.spreader.spreaderAndroid.task.InitTask;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;


@RuntimePermissions
public class WelcomeActivity extends BaseLoadingActivity<InitModel> {
    private MapLocationHelper helper;
    @BindView(R.id.welcome_img)
    ImageView welcomeImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);
        ButterKnife.bind(this);
        HttpConfig.init(getApplicationContext());
        init();
        initMap();
        WelcomeActivityPermissionsDispatcher.getMultiPermissionWithCheck(this);

        AlphaAnimation aa = new AlphaAnimation(0.1f, 1.0f);
        aa.setDuration(2000);
        welcomeImg.startAnimation(aa);

        aa.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                skip();
            }
        });
    }

    private void init() {
        //配置glide
        GlideConfig.errorId = R.mipmap.icon_error;
        GlideConfig.placeHolder = R.mipmap.icon_head_image_default;
        GlideConfig.fallBack = R.mipmap.icon_head_image_default;
        new InitTask(this, this).request();
    }

    private void initMap() {
        helper = new MapLocationHelper(this);
        helper.setLocationListener(new MapLocationHelper.LocationListener() {
            @Override
            public void onLocationChange(AMapLocation location) {
                UserConfig.setAdCode(WelcomeActivity.this, location.getAdCode());
            }
        });
        helper.startOnceLocation();
    }

    public void skip() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    void getMultiPermission() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        WelcomeActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    protected void onStop() {
        super.onStop();
        helper.stopLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.destroyLocation();
    }

    @Override
    public void onLoadFinish(InitModel initModel) {
        UserConfig.setPublicKey(this, initModel.publicKey);
    }
}
