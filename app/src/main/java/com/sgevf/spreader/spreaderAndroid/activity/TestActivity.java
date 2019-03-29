package com.sgevf.spreader.spreaderAndroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.task.TestUploadTask;

public class TestActivity extends BaseLoadingActivity<String> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    @Override
    public void onLoadFinish(String s) {

    }

    public void upload(View view) {
        new TestUploadTask(this,this).setClass("/storage/emulated/0/DCIM/browser-photo/1540170514499.jpg","/storage/emulated/0/DCIM/Screenshots/Screenshot_2018-08-08-09-52-22-071_com.miui.home.png").request();
    }
}
