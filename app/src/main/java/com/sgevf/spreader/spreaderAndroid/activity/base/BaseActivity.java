package com.sgevf.spreader.spreaderAndroid.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sgevf.spreader.http.base.BasicActivity;

import utils.StatusBarUtils;

public abstract class BaseActivity extends BasicActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.fullScreen(this);
    }

}
