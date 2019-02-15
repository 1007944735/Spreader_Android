package com.sgevf.spreader.spreaderAndroid.test;

import com.sgevf.spreader.http.entity.BasicResult;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService{
    @POST("user/top")
    @FormUrlEncoded
    Observable<BasicResult<Movie>> getTopMovie(@Field(value = "count") String key);


}