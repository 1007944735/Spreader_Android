package com.sgevf.spreader.spreaderAndroid.test;

import android.app.Activity;
import android.util.Log;

import com.sgevf.spreader.http.api.BasicApi;

import io.reactivex.Observable;

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
        return service.getTopMovie("妈卖批");
    }



    @Override
    public void onNext(Movie movie) {
        Log.d("TAG", "onNext: "+movie.title);
        if(mActivity instanceof MActivity){
            ((MActivity) mActivity).onLoadFinish(movie);
        }
    }

}
