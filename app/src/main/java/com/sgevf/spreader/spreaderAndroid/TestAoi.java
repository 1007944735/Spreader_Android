package com.sgevf.spreader.spreaderAndroid;

import android.app.Activity;

import com.sgevf.spreader.http.api.BaseApi;

import io.reactivex.Observable;

public class TestAoi extends BaseApi<Api,Movie> {
    public TestAoi(Activity mActivity) {
        super(mActivity);
    }

    @Override
    protected Class getCls() {
        return null;
    }

    @Override
    public void onNext(Movie movie) {

    }

    @Override
    protected Observable setObservable() {
        return super.setObservable();
    }
}
