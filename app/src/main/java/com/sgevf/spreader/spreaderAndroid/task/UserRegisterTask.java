package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.http.api.BasicApi;
import com.sgevf.spreader.spreaderAndroid.activity.RegisterActivity;
import com.sgevf.spreader.spreaderAndroid.task.impl.Service;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;

public class UserRegisterTask extends BasicApi<Service, String> {

    public UserRegisterTask(Activity mActivity) {
        super(mActivity);
    }

    public UserRegisterTask setClass(String username, String password, String uuid, String code) {
        map.put("userName", username);
        map.put("password", password);
        map.put("uuid", uuid);
        map.put("code", code);
        return this;
    }

    @Override
    protected Observable setObservable(Map data) {
        return super.setObservable(data);
    }

    @Override
    public void onNext(String s) {
        if (mActivity instanceof RegisterActivity) {
            ((RegisterActivity) mActivity).onLoadFinish(s);
        }
    }
}
