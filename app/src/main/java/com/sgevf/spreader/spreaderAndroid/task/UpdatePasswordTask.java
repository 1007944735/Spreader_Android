package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.UpdateUserActivity;
import com.sgevf.spreader.spreaderAndroid.config.UserConfig;
import com.sgevf.spreader.spreaderAndroid.task.impl.UserService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import utils.RSAUtils;

public class UpdatePasswordTask extends BaseService<UserService, String> {
    public UpdatePasswordTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    public UpdatePasswordTask setClass(String oldPassword, String newPassword) {
        try {
            String publicKey = UserConfig.getPublicKey(mActivity);
            byte[] op = RSAUtils.encryptByPublicKey(oldPassword.getBytes("utf-8"), RSAUtils.base64Decode(publicKey));
            byte[] np = RSAUtils.encryptByPublicKey(newPassword.getBytes("utf-8"), RSAUtils.base64Decode(publicKey));
            String encodeOp = RSAUtils.base64Encode(op);
            String encodeNp = RSAUtils.base64Encode(np);
            params.put("oldPassword", encodeOp);
            params.put("newPassword", encodeNp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.updatePassword(data);
    }

    @Override
    public void onSuccess(String s) {
        if (mTarget instanceof UpdateUserActivity) {
            ((UpdateUserActivity) mTarget).onLoadFinish(s);
        }
    }
}
