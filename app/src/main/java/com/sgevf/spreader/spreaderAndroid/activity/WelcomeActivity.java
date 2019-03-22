package com.sgevf.spreader.spreaderAndroid.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.sgevf.multimedia.TestActivity;
import com.sgevf.multimedia.camera.CameraActivity;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseActivity;
import com.sgevf.spreader.spreaderAndroid.config.HttpConfig;
import com.sgevf.spreader.spreaderAndroid.config.UserConfig;
import com.sgevf.spreader.spreaderAndroid.map.MapLocationHelper;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;


@RuntimePermissions
public class WelcomeActivity extends BaseActivity {
    private MapLocationHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);
        new HeaderView(this).setTitle("欢迎");
        HttpConfig.init(getApplicationContext());
        initMap();
        WelcomeActivityPermissionsDispatcher.getMultiPermissionWithCheck(this);
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

    public void camera(View view) {
        startActivity(new Intent(this, CameraActivity.class));
    }


    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
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

    public void systemPhoto(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 1000);
    }

    public void jump(View view) {
        Intent intent=new Intent(this, TestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
