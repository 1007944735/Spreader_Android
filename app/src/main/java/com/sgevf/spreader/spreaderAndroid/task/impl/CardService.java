package com.sgevf.spreader.spreaderAndroid.task.impl;

import com.sgevf.spreader.http.entity.BasicResult;
import com.sgevf.spreader.spreaderAndroid.model.CardListModel;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface CardService {
    @POST("S0024")
    @Multipart
    Observable<BasicResult<CardListModel>> cardList(@PartMap Map<String, RequestBody> data);

    @POST("S0025")
    @Multipart
    Observable<BasicResult<String>> deleteCard(@PartMap Map<String, RequestBody> data);

    @POST("S0026")
    @Multipart
    Observable<BasicResult<String>> addCard(@PartMap Map<String, RequestBody> data);
}
