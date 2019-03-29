package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.UpdateUserActivity;
import com.sgevf.spreader.spreaderAndroid.task.impl.UserService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class UpdateInfoTask extends BaseService<UserService, String> {


    public UpdateInfoTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    public UpdateInfoTask setClass(String type, String nickname, String phone) {
        params.put("type", type);
        if ("1".equals(type)) {
            params.put("nickname", nickname);
            params.put("phone", "");
        } else if ("2".equals(type)) {
            params.put("nickname", "");
            params.put("phone", phone);
        }
        return this;
    }


    @Override
    public void onNext(String s) {
        if (mTarget instanceof UpdateUserActivity) {
            ((UpdateUserActivity) mTarget).onLoadFinish(s);
        }
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.update(data);
    }
}
