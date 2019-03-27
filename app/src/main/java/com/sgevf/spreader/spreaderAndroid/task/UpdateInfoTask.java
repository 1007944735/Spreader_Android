package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.UpdateUserActivity;
import com.sgevf.spreader.spreaderAndroid.task.impl.UserService;

import java.util.Map;

import io.reactivex.Observable;

public class UpdateInfoTask extends BaseService<UserService,String> {
    public UpdateInfoTask(Activity mActivity) {
        super(mActivity);
    }

    public UpdateInfoTask setClass(String type,String nickname,String phone){
        params.put("type",type);
        if("1".equals(type)){
            params.put("nickname",nickname);
            params.put("phone","");
        }else if("2".equals(type)){
            params.put("nickname","");
            params.put("phone",phone);
        }
        return this;
    }

    @Override
    protected Observable setObservable(Map data) {
        return service.update(data);
    }

    @Override
    public void onNext(String s) {
        if(mActivity instanceof UpdateUserActivity){
            ((UpdateUserActivity) mActivity).onLoadFinish(s);
        }
    }
}
