package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.http.api.BasicApi;
import com.sgevf.spreader.spreaderAndroid.activity.WelcomeActivity;
import com.sgevf.spreader.spreaderAndroid.model.InitModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.Service;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;

public class InitTask extends BasicApi<Service, InitModel> {
    public InitTask(Activity mActivity) {
        super(mActivity);
    }

    @Override
    protected Observable setObservable(Map data) {
        return service.init(data);
    }

    @Override
    public void onNext(InitModel initModel) {
        if(mActivity instanceof WelcomeActivity){
            ((WelcomeActivity) mActivity).onLoadFinish(initModel);
        }
    }

    @Override
    protected boolean setShowLoading() {
        return false;
    }
}
