package com.sgevf.spreader.spreaderAndroid.task.impl;

import com.sgevf.spreader.http.entity.BasicResult;
import com.sgevf.spreader.spreaderAndroid.model.UserCardModel;
import com.sgevf.spreader.spreaderAndroid.model.UserCouponCheckModel;
import com.sgevf.spreader.spreaderAndroid.model.UserCouponListModel;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface UserCouponService {
    @POST("S0027")
    @Multipart
    Observable<BasicResult<UserCouponListModel>> getUserCoupon(@PartMap Map<String, RequestBody> data);

    @POST("S0028")
    @Multipart
    Observable<BasicResult<UserCouponCheckModel>> checkUserCoupon(@PartMap Map<String, RequestBody> data);

    @POST("S0029")
    @Multipart
    Observable<BasicResult<String>> useCoupon(@PartMap Map<String, RequestBody> data);

    @POST("S0030")
    @Multipart
    Observable<BasicResult<UserCardModel>> queryUserCard(@PartMap Map<String, RequestBody> data);
}
