package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.map.MapDiscoverActivity;
import com.sgevf.spreader.spreaderAndroid.model.GrabRedPacketModel;
import com.sgevf.spreader.spreaderAndroid.task.impl.GrabService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class GrabRedPacketTask extends BaseService<GrabService, GrabRedPacketModel> {
    public GrabRedPacketTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    public GrabRedPacketTask setClass(Integer redPacketId,String longitude,String latitude){
        params.put("redPacketId",redPacketId+"");
        params.put("longitude",longitude);
        params.put("latitude",latitude);
        return this;
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.grab(data);
    }

    @Override
    public void onSuccess(GrabRedPacketModel grabRedPacketModel) {
        if(mTarget instanceof MapDiscoverActivity){
            ((MapDiscoverActivity) mTarget).grabResult(grabRedPacketModel);
        }
    }
}
