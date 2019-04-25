package com.sgevf.spreader.spreaderAndroid.task.impl;

import com.sgevf.spreader.http.entity.BasicResult;
import com.sgevf.spreader.spreaderAndroid.model.BusinessModel;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface BusinessService {
    @POST("S0035")
    @Multipart
    Observable<BasicResult<BusinessModel>> uploadInfo(@PartMap Map<String, RequestBody> data);

    @POST("S0036")
    @Multipart
    Observable<BasicResult<BusinessModel>> searchBusinessInfo(@PartMap Map<String, RequestBody> data);

}
