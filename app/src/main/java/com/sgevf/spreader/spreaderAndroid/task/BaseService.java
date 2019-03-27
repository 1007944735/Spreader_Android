package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;
import android.text.TextUtils;

import com.sgevf.spreader.http.api.BasicApi;
import com.sgevf.spreader.spreaderAndroid.config.UserConfig;

public abstract class BaseService<T, S> extends BasicApi<T, S> {

    public BaseService(Activity mActivity) {
        super(mActivity);
        String token = UserConfig.getToken(mActivity);
        if (params != null && !TextUtils.isEmpty(token)) {
            params.put("token", token);
        }
    }
}
