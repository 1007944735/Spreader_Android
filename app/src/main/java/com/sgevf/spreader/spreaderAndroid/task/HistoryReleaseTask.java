package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.HistoryReleaseActivity;
import com.sgevf.spreader.spreaderAndroid.model.HistoryReleaseListModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.PubService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class HistoryReleaseTask extends BaseService<PubService, HistoryReleaseListModel> {
    public HistoryReleaseTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    public HistoryReleaseTask setClass(String type){
        params.put("type",type);
        return this;
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.historyRelease(data);
    }

    @Override
    public void onSuccess(HistoryReleaseListModel historyReleaseListModel) {
        if(mTarget instanceof HistoryReleaseActivity){
            ((HistoryReleaseActivity) mTarget).onLoadFinish(historyReleaseListModel.list);
        }
    }
}
