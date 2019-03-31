package com.sgevf.spreader.spreaderAndroid.task;

import android.app.Activity;

import com.sgevf.spreader.spreaderAndroid.map.MapDiscoverActivity;
import com.sgevf.spreader.spreaderAndroid.model.MapRedResultModels;
import com.sgevf.spreader.spreaderAndroid.task.impl.PubService;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class MapSearchFilterTask extends BaseService<PubService, MapRedResultModels> {
    public MapSearchFilterTask(Activity mActivity, Object mTarget) {
        super(mActivity, mTarget);
    }

    public MapSearchFilterTask setClass(String longitude, String latitude, String type,String number,String amount) {
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("type",type);//1 固定红包，2 随机红包
        params.put("number",number);//1 固定红包，2 随机红包
        params.put("amount",amount);//1 固定红包，2 随机红包
        return this;
    }

    @Override
    public Observable setObservable(Map<String, RequestBody> data) {
        return service.mapSearchByFilter(data);
    }


    @Override
    public void onSuccess(MapRedResultModels mapRedResultModels) {
        if(mTarget instanceof MapDiscoverActivity){
            ((MapDiscoverActivity) mTarget).onLoadFinish(mapRedResultModels);
        }
    }
}
