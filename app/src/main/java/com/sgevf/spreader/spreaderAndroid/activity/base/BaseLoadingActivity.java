package com.sgevf.spreader.spreaderAndroid.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sgevf.spreader.http.base.BasicLoadingActivity;

import utils.StatusBarUtils;

public abstract class BaseLoadingActivity<T> extends BasicLoadingActivity<T> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.fullScreen(this);
    }
}
