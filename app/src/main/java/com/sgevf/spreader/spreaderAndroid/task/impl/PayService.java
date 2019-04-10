package com.sgevf.spreader.spreaderAndroid.task.impl;

import android.support.design.button.MaterialButton;

import com.sgevf.spreader.http.entity.BasicResult;
import com.sgevf.spreader.spreaderAndroid.model.PubOrderModel;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface PayService {
    @POST("S0014")
    @Multipart
    Observable<BasicResult<PubOrderModel>> pubOrder(@PartMap Map<String, RequestBody> data);
}
