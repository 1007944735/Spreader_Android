package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.sgevf.spreader.http.api.BasicApi;
import com.sgevf.spreader.http.base.impl.UploadProgressListener;
import com.sgevf.spreader.spreaderAndroid.activity.LoginActivity;
import com.sgevf.spreader.spreaderAndroid.config.UserConfig;

import io.reactivex.disposables.Disposable;

public abstract class BaseService<T, S> extends BasicApi<T, S> {

    public BaseService(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
        String token = UserConfig.getToken(mActivity);
        if (params != null && !TextUtils.isEmpty(token)) {
            params.put("token", token);
        }
    }

    public BaseService(Activity mActivity, String url) {
        super(mActivity, url);
        String token = UserConfig.getToken(mActivity);
        if (params != null && !TextUtils.isEmpty(token)) {
            params.put("token", token);
        }
    }

    public BaseService(Activity mActivity, UploadProgressListener listener) {
        super(mActivity, listener);
        String token = UserConfig.getToken(mActivity);
        if (params != null && !TextUtils.isEmpty(token)) {
            params.put("token", token);
        }
    }

    public BaseService(Activity mActivity, String url, UploadProgressListener listener) {
        super(mActivity, url, listener);
        String token = UserConfig.getToken(mActivity);
        if (params != null && !TextUtils.isEmpty(token)) {
            params.put("token", token);
        }
    }

    @Override
    public void beforeRequest(Disposable d) {

    }

    @Override
    public void onFail(String reCode) {
        if ("400".equals(reCode)) {
            mActivity.startActivity(new Intent(mActivity, LoginActivity.class).putExtra("isBacktoHome", false));
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void finishRequest() {

    }
}
