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

    public MapSearchTask setClass(String longitude, String latitude) {
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        return this;
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.mapSearch(data);
    }


    @Override
    public void onNext(MapRedResultModels mapRedResultModels) {
        if(mTarget instanceof MapDiscoverActivity){
            ((MapDiscoverActivity) mTarget).onLoadFinish(mapRedResultModels);
        }
    }
}
