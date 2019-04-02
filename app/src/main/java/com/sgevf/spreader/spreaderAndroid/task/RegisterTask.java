package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.RegisterActivity;
import com.sgevf.spreader.spreaderAndroid.config.UserConfig;
import com.sgevf.spreader.spreaderAndroid.task.impl.UserService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import utils.RSAUtils;

public class RegisterTask extends BaseService<UserService, String> {


    public RegisterTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    public RegisterTask setClass(String username, String password, String uuid, String code) {
        try {
            String publicKey = UserConfig.getPublicKey(mActivity);
            byte[] enData = RSAUtils.encryptByPublicKey(password.getBytes("utf-8"), RSAUtils.base64Decode(publicKey));
            String base64Data = RSAUtils.base64Encode(enData);
            params.put("username", username);
            params.put("password", base64Data);
            params.put("uuuid", uuid);
            params.put("valid", code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }


    @Override
    public void onSuccess(String s) {
        if (mTarget instanceof RegisterActivity) {
            ((RegisterActivity) mTarget).onLoadFinish(s);
        }
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.register(data);
    }
}
