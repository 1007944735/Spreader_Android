package com.sgevf.spreader.spreaderAndroid.task.impl;

import com.sgevf.spreader.http.entity.BasicResult;
import com.sgevf.spreader.spreaderAndroid.model.InitModel;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Service {
    /**
     * 获取RSA公钥
     * @param data
     * @return
     */
    @POST("/S0000")
    @FormUrlEncoded
    Observable<BasicResult<InitModel>> init(@FieldMap Map<String,Object> data);

    /**
     * 获取图形验证码
     * @param data
     * @return
     */
    @POST("/SV0003")
    @FormUrlEncoded
    Observable<BasicResult<String>> getValid(@FieldMap Map<String,Object> data);


}
