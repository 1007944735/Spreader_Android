package com.sgevf.spreader.spreaderAndroid.task.impl;

import com.sgevf.spreader.http.entity.BasicResult;
import com.sgevf.spreader.spreaderAndroid.glide.MyAppGlideModule;
import com.sgevf.spreader.spreaderAndroid.model.HomeAdvertisingListModel;
import com.sgevf.spreader.spreaderAndroid.model.InitModel;
import com.sgevf.spreader.spreaderAndroid.model.SlideShowModel;
import com.sgevf.spreader.spreaderAndroid.model.ValidateCodeModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface Service {
    /**
     * 获取RSA公钥
     *
     * @return
     */
    @POST("S0000")
    Observable<BasicResult<InitModel>> init();

    /**
     * 获取图形验证码
     *
     * @return
     */
    @POST("S0003")
    Observable<BasicResult<ValidateCodeModel>> getValid();

    @POST("S0031")
    Observable<BasicResult<SlideShowModel>> getSlideShow();

    @POST("S0032")
    Observable<BasicResult<HomeAdvertisingListModel>> getHomeList();


}
