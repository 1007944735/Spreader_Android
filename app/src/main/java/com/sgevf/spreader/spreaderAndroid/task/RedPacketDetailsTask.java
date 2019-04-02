package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.map.MapDiscoverActivity;
import com.sgevf.spreader.spreaderAndroid.model.RedPacketDetailsModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.PubService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class RedPacketDetailsTask extends BaseService<PubService, RedPacketDetailsModel> {
    public RedPacketDetailsTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    public RedPacketDetailsTask setClass(int redPacketId, String longitude, String latitude) {
        params.put("redPacketId", redPacketId + "");
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        return this;
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.redPacketDetails(data);
    }

    @Override
    public void onSuccess(RedPacketDetailsModel redPacketDetailsModel) {
        if (mTarget instanceof MapDiscoverActivity) {
            ((MapDiscoverActivity) mTarget).initDetailsLayout(redPacketDetailsModel);
        }
    }

    @Override
    protected boolean setShowLoading() {
        return false;
    }
}
