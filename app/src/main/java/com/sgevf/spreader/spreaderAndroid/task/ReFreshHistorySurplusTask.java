package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.activity.HistoryReleaseDetailsActivity;
import com.sgevf.spreader.spreaderAndroid.model.HistorySurplusModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.PubService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class ReFreshHistorySurplusTask extends BaseService<PubService, HistorySurplusModel> {
    public ReFreshHistorySurplusTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    public ReFreshHistorySurplusTask setClass(String redPacketId) {
        params.put("redPacketId", redPacketId);
        return this;
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.surplusRefresh(data);
    }

    @Override
    public void onSuccess(HistorySurplusModel historySurplusModel) {
        if (mTarget instanceof HistoryReleaseDetailsActivity) {
            ((HistoryReleaseDetailsActivity) mTarget).onLoadFinish(historySurplusModel);
        }
    }

    @Override
    protected boolean setShowLoading() {
        return false;
    }
}
