package com.sgevf.spreader.spreaderAndroid.task.impl;


import com.sgevf.spreader.http.entity.BasicResult;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface TestService {
    @POST("T0000")
    @Multipart
    Observable<BasicResult<String>> upload(@PartMap Map<String, RequestBody> data);
}
