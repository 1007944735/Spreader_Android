package com.sgevf.spreader.spreader_android;

import com.sgevf.spreader.http.entity.BaseResult;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService{
    @GET("user/top")
    Observable<BaseResult<Movie>> getTopMovie();
}
