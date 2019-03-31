package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.map.MapDiscoverActivity;
import com.sgevf.spreader.spreaderAndroid.model.MapRedResultModels;
import com.sgevf.spreader.spreaderAndroid.task.impl.PubService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class MapSearchOrderTask extends BaseService<PubService, MapRedResultModels> {
    public MapSearchOrderTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    public MapSearchOrderTask setClass(String longitude, String latitude,String type) {
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("type",type);//1 人数最多，2 金额最大，3 距离最近
        return this;
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.mapSearchByOrder(data);
    }


    @Override
    public void onSuccess(MapRedResultModels mapRedResultModels) {
        if(mTarget instanceof MapDiscoverActivity){
            ((MapDiscoverActivity) mTarget).onLoadFinish(mapRedResultModels);
        }
    }
}
