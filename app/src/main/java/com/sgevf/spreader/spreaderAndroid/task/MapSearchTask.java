package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.map.MapDiscoverActivity;
import com.sgevf.spreader.spreaderAndroid.model.MapRedResultModels;
import com.sgevf.spreader.spreaderAndroid.task.impl.PubService;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class MapSearchTask extends BaseService<PubService, MapRedResultModels> {
    public MapSearchTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    public MapSearchTask setClass(String longitude, String latitude, String orderType, String redPacketType, String number, String amount) {
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("orderType", orderType);//1 人数最多，2 金额最大，3 距离最近
        params.put("redPacketType", redPacketType);//0 随机红包,1 固定红包
        params.put("number", number);
        params.put("amount", amount);
        return this;
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.mapSearch(data);
    }


    @Override
    public void onSuccess(MapRedResultModels mapRedResultModels) {
        if (mTarget instanceof MapDiscoverActivity) {
            ((MapDiscoverActivity) mTarget).onLoadFinish(mapRedResultModels);
        }
    }
}
