package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.WelcomeActivity;
import com.sgevf.spreader.spreaderAndroid.model.InitModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.Service;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class InitTask extends BaseService<Service, InitModel> {

    public InitTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.init();
    }

    @Override
    public void onNext(InitModel initModel) {
        if(mTarget instanceof WelcomeActivity){
            ((WelcomeActivity) mTarget).onLoadFinish(initModel);
        }
    }

    @Override
    protected boolean setShowLoading() {
        return false;
    }


}
