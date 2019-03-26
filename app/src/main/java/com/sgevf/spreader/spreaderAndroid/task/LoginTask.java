package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.http.api.BasicApi;
import com.sgevf.spreader.spreaderAndroid.activity.LoginActivity;
import com.sgevf.spreader.spreaderAndroid.config.UserConfig;
import com.sgevf.spreader.spreaderAndroid.model.UserModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.UserService;

import java.util.Map;

import io.reactivex.Observable;
import utils.RSAUtils;

public class LoginTask extends BasicApi<UserService, UserModel> {

    public LoginTask(Activity mActivity) {
        super(mActivity);
    }


    public LoginTask setClass(String userName, String password) {
        try {
            String publicKey = UserConfig.getPublicKey(mActivity);
            byte[] pk = RSAUtils.base64Decode(publicKey);
            String encodePass=RSAUtils.base64Encode(RSAUtils.encryptByPublicKey(password.getBytes("utf-8"),pk));
            map.put("username", userName);
            map.put("password", encodePass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    protected Observable setObservable(Map data) {
        return service.login(data);
    }


    @Override
    public void onNext(UserModel userModel) {
        if (mActivity instanceof LoginActivity) {
            ((LoginActivity) mActivity).onLoadFinish(userModel);
        }
    }
}
