package com.sgevf.spreader.spreaderAndroid.task.impl;


import com.sgevf.spreader.http.entity.BasicResult;
import com.sgevf.spreader.spreaderAndroid.glide.MyAppGlideModule;
import com.sgevf.spreader.spreaderAndroid.model.UserModel;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface UserService {
    /**
     * 登录
     *
     * @param data
     * @return
     */
    @POST("S0001")
    @Multipart
    Observable<BasicResult<UserModel>> login(@PartMap Map<String, RequestBody> data);

    /**
     * 注册
     *
     * @param data
     * @return
     */
    @POST("S0002")
    @Multipart
    Observable<BasicResult<String>> register(@PartMap Map<String, RequestBody> data);

    /**
     * 修改个人信息（昵称，手机号）
     * @param data
     * @return
     */
    @POST("S0004")
    @Multipart
    Observable<BasicResult<String>> update(@PartMap Map<String, RequestBody> data);
}
