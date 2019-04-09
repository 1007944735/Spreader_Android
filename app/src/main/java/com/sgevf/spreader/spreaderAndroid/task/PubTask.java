package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.PubActivity;
import com.sgevf.spreader.spreaderAndroid.task.impl.PubService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class PubTask extends BaseService<PubService, String> {


    public PubTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.pub(data);
    }

    @Override
    public void onSuccess(String s) {
        if (mTarget instanceof PubActivity) {
            ((PubActivity) mTarget).onLoadFinish(s);
        }
    }
}
