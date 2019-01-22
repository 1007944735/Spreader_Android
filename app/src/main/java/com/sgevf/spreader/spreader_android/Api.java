package com.sgevf.spreader.spreader_android;

import android.util.Log;

import com.sgevf.spreader.http.api.BaseApi;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class Api extends BaseApi<ApiService,Movie>{
    public Api(String url) {
        super(url);
    }

    @Override
    public Class getCls() {
        return ApiService.class;
    }

    @Override
    public Observable setObservable() {
        return service.getTopMovie();
    }

    @Override
    public void onNext(Movie movie) {
        Log.d("TAG", "onNext: "+movie.title);
    }

}
