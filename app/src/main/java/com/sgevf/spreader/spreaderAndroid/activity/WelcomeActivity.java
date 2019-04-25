package com.sgevf.spreader.spreaderAndroid.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

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
import com.sgevf.spreader.spreaderAndroid.view.SuperProgressBar;
import com.sgevf.spreader.spreaderAndroid.view.UploadImageView;
import com.sgevf.spreader.spreaderAndroid.view.VideoBannerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;


@RuntimePermissions
public class WelcomeActivity extends BaseLoadingActivity<InitModel> {
    private MapLocationHelper helper;
    private SuperProgressBar progress;
    private ViewPager viewPager;
    private UploadImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);
        new HeaderView(this).setTitle("欢迎");
        HttpConfig.init(getApplicationContext());
        init();
        initMap();
        WelcomeActivityPermissionsDispatcher.getMultiPermissionWithCheck(this);

        progress=findViewById(R.id.progress);

        viewPager=findViewById(R.id.viewPager);
        List<String> images=new ArrayList<>();
        images.add("http://47.103.8.72:8080/spreader/picture/pictureNb0S20190417100425.jpg");
        images.add("http://47.103.8.72:8080/spreader/picture/pictureNb0S20190417100425.jpg");
        images.add("http://47.103.8.72:8080/spreader/picture/pictureNb0S20190417100425.jpg");
        images.add("http://47.103.8.72:8080/spreader/picture/pictureNb0S20190417100425.jpg");
        VideoBannerViewAdapter adapter=new VideoBannerViewAdapter(this,null,images);
        viewPager.setAdapter(adapter);
        image=findViewById(R.id.image);
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

    public void skip(final View view) {
        startActivity(new Intent(this, HomeActivity.class));
    }

    public void video(View view) {
        Thread thread=new Thread(){
            @Override
            public void run() {
                super.run();
                for (int i=0;i<=100;i++){
                    final float k=i/100f;
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress.setProgress(k,"1");
                        }
                    });
                }
            }
        };
        thread.start();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==UploadImageView.REQUEST_CODE){
            image.uploadFile();
        }
    }
}
