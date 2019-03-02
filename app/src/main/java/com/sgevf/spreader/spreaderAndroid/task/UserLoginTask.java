package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.http.api.BasicApi;
import com.sgevf.spreader.spreaderAndroid.activity.LoginActivity;
import com.sgevf.spreader.spreaderAndroid.model.UserModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.UserService;

import org.json.JSONObject;

import io.reactivex.Observable;

public class UserLoginTask extends BasicApi<UserService, UserModel> {

    public UserLoginTask(Activity mActivity) {
        super(mActivity);
    }


    public UserLoginTask setClass(String userName, String password){
        map.put("userName",userName);
        map.put("password",password);
        return this;
    }

    @Override
    protected Observable setObservable(JSONObject json) {
        return service.login(json);
    }

    @Override
    public void onNext(UserModel userModel) {
        if(mActivity instanceof LoginActivity){
            ((LoginActivity) mActivity).onLoadFinish(userModel);
        }
    }
}
