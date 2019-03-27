package com.sgevf.spreader.spreaderAndroid.task.impl;


import com.sgevf.spreader.http.entity.BasicResult;
import com.sgevf.spreader.spreaderAndroid.glide.MyAppGlideModule;
import com.sgevf.spreader.spreaderAndroid.model.UserModel;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {
    /**
     * 登录
     *
     * @param data
     * @return
     */
    @POST("S0001")
    @FormUrlEncoded
    Observable<BasicResult<UserModel>> login(@FieldMap Map<String, Object> data);

    /**
     * 注册
     *
     * @param data
     * @return
     */
    @POST("S0002")
    @FormUrlEncoded
    Observable<BasicResult<String>> register(@FieldMap Map<String, Object> data);

    /**
     * 修改个人信息（昵称，手机号）
     * @param data
     * @return
     */
    @POST("S0004")
    @FormUrlEncoded
    Observable<BasicResult<String>> update(@FieldMap Map<String, Object> data);
}
