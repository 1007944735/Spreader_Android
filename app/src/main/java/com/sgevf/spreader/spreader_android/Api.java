package com.sgevf.spreader.spreader_android;

import android.app.Activity;
import android.util.Log;

import com.sgevf.spreader.http.api.BaseApi;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class Api extends BaseApi<ApiService,Movie>{

    public Api(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public Class getCls() {
        return ApiService.class;
    }

    @Override
    protected Observable setObservable() {
        Movie movie=new Movie();
        movie.count=10;
        movie.title="妈卖批";
        return service.getTopMovie("妈卖批");
    }


    @Override
    public void onNext(Movie movie) {
        Log.d("TAG", "onNext: "+movie.title);
        if(mActivity instanceof MainActivity){
            ((MainActivity) mActivity).onLoadFinish(movie);
        }
    }

}
