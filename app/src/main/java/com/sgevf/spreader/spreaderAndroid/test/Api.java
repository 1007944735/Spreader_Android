package com.sgevf.spreader.spreaderAndroid.test;

import android.app.Activity;
import android.util.Log;

import com.sgevf.spreader.http.api.BasicApi;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;

public class Api extends BasicApi<ApiService, Movie> {

    public Api(Activity mActivity) {
        super(mActivity);
    }


    public Api setClass() {
        map.put("name", "王嘉杰");
        return this;
    }

    @Override
    protected Observable setObservable(Map data) {
        return super.setObservable(data);
    }

    @Override
    public void onNext(Movie movie) {
        Log.d("TAG", "onNext: " + movie.title);
        if (mActivity instanceof MActivity) {
            ((MActivity) mActivity).onLoadFinish(movie);
        }
    }

}
