package com.sgevf.spreader.spreaderAndroid.test;

import android.app.Activity;
import android.util.Log;

import com.sgevf.spreader.http.api.BasicApi;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import utils.JsonUtils;

public class Api extends BasicApi<ApiService,Movie> {

    public Api(Activity mActivity) {
        super(mActivity);
    }

    @Override
    protected Class getCls() {
        return ApiService.class;
    }


    @Override
    protected Observable setObservable() {
        Movie movie=new Movie();
        movie.count=10;
        movie.title="妈卖批";
        Map<String,Object> map=new HashMap<>();
        map.put("name","王嘉杰");
        return service.getTopMovie(JsonUtils.createJson(map));
    }



    @Override
    public void onNext(Movie movie) {
        Log.d("TAG", "onNext: "+movie.title);
        if(mActivity instanceof MActivity){
            ((MActivity) mActivity).onLoadFinish(movie);
        }
    }

}
