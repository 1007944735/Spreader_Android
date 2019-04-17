package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.http.base.impl.UploadProgressListener;
import com.sgevf.spreader.spreaderAndroid.activity.PubActivity;
import com.sgevf.spreader.spreaderAndroid.model.PubResultModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.PubService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class PubTask extends BaseService<PubService, PubResultModel> {


    public PubTask(Activity mActivity, Object mTarget, UploadProgressListener listener) {
        super(mActivity, mTarget, listener);
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.pub(data);
    }

    @Override
    public void onSuccess(PubResultModel s) {
        if (mTarget instanceof PubActivity) {
            ((PubActivity) mTarget).onLoadFinish(s);
        }
    }
}
