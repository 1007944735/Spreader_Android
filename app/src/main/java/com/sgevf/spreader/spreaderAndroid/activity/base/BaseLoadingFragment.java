package com.sgevf.spreader.spreaderAndroid.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sgevf.spreader.http.base.BasicLoadingFragment;

import utils.WindowHelper;

public abstract class BaseLoadingFragment<T> extends BasicLoadingFragment<T> {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowHelper.fullScreen(getActivity());
    }
}
