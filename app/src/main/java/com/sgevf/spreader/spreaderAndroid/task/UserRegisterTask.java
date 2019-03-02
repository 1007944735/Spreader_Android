package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.http.api.BasicApi;
import com.sgevf.spreader.spreaderAndroid.activity.RegisterActivity;
import com.sgevf.spreader.spreaderAndroid.task.impl.UserService;

import org.json.JSONObject;

import io.reactivex.Observable;

public class UserRegisterTask extends BasicApi<UserService,String> {
    public UserRegisterTask(Activity mActivity) {
        super(mActivity);
    }

    public UserRegisterTask setClass(String username,String password,String uuid,String code){
        map.put("userName",username);
        map.put("password",password);
        map.put("uuid",uuid);
        map.put("code",code);
        return this;
    }

    @Override
    protected Observable setObservable(JSONObject json) {
        return service.register(json);
    }

    @Override
    public void onNext(String s) {
        if(mActivity instanceof RegisterActivity){
            ((RegisterActivity) mActivity).onLoadFinish(s);
        }
    }
}
