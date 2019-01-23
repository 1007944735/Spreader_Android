package com.sgevf.spreader.spreader_android;

import com.sgevf.spreader.http.entity.BaseResult;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService{
    @POST("user/top")
    @FormUrlEncoded
    Observable<BaseResult<Movie>> getTopMovie(@Field(value = "count") String key);
}
