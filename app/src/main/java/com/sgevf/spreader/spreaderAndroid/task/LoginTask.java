package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.LoginActivity;
import com.sgevf.spreader.spreaderAndroid.config.UserConfig;
import com.sgevf.spreader.spreaderAndroid.model.UserModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.UserService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import utils.RSAUtils;

public class LoginTask extends BaseService<UserService, UserModel> {


    public LoginTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    public LoginTask setClass(String userName, String password) {
        try {
            String publicKey = UserConfig.getPublicKey(mActivity);
            byte[] pk = RSAUtils.base64Decode(publicKey);
            String encodePass=RSAUtils.base64Encode(RSAUtils.encryptByPublicKey(password.getBytes("utf-8"),pk));
            params.put("username", userName);
            params.put("password", encodePass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }



    @Override
    public void onNext(UserModel userModel) {
        if (mTarget instanceof LoginActivity) {
            ((LoginActivity) mTarget).onLoadFinish(userModel);
        }
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.login(data);
    }
}
