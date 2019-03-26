package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.http.api.BasicApi;
import com.sgevf.spreader.spreaderAndroid.activity.RegisterActivity;
import com.sgevf.spreader.spreaderAndroid.config.UserConfig;
import com.sgevf.spreader.spreaderAndroid.task.impl.UserService;

import java.util.Map;

import io.reactivex.Observable;
import utils.RSAUtils;

public class RegisterTask extends BasicApi<UserService, String> {

    public RegisterTask(Activity mActivity) {
        super(mActivity);
    }

    public RegisterTask setClass(String username, String password, String uuid, String code) {
        try {
            String publicKey = UserConfig.getPublicKey(mActivity);
            byte[] enData=RSAUtils.encryptByPublicKey(password.getBytes("utf-8"),RSAUtils.base64Decode(publicKey));
            String base64Data=RSAUtils.base64Encode(enData);
            map.put("username", username);
            map.put("password", base64Data);
            map.put("uuuid", uuid);
            map.put("valid", code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    protected Observable setObservable(Map data) {
        return service.register(data);
    }

    @Override
    public void onNext(String s) {
        if (mActivity instanceof RegisterActivity) {
            ((RegisterActivity) mActivity).onLoadFinish(s);
        }
    }
}
