package com.sgevf.spreader.spreaderAndroid.task.impl;

import com.sgevf.spreader.http.entity.BasicResult;
import com.sgevf.spreader.spreaderAndroid.model.UserModel;
import com.sgevf.spreader.spreaderAndroid.model.ValidateCodeModel;

import org.json.JSONObject;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {
    @POST("/SV0001")
    @FormUrlEncoded
    Observable<BasicResult<UserModel>> login(@Field("params") JSONObject json);

    @POST("/SV0002")
    @FormUrlEncoded
    Observable<BasicResult<ValidateCodeModel>> code(@Field("params") JSONObject json);

    @POST("/SV0003")
    @FormUrlEncoded
    Observable<BasicResult<String>> register(@Field("params") JSONObject json);


}
