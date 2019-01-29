package com.sgevf.spreader.spreaderAndroid;

import com.sgevf.spreader.http.entity.BaseResult;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService{
    @POST("user/top")
    @FormUrlEncoded
    Observable<BaseResult<Movie>> getTopMovie(@Field(value = "count") String key);
}
