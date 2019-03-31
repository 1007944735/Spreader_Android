package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.task.impl.TestService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class TestUploadTask extends BaseService<TestService, String> {


    public TestUploadTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    public TestUploadTask setClass(String url1, String url2){
        params.put("url","123123");
        return this;
    }

    @Override
    public void onSuccess(String s) {

    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.upload(data);
    }
}
