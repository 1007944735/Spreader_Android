package com.sgevf.spreader.spreaderAndroid.task.impl;

import com.sgevf.spreader.http.entity.BasicResult;
import com.sgevf.spreader.spreaderAndroid.model.AliAuthInfoModel;
import com.sgevf.spreader.spreaderAndroid.model.CashWithdrawModel;
import com.sgevf.spreader.spreaderAndroid.model.PubOrderModel;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.internal.operators.observable.ObservableAll;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface PayService {
    @POST("S0014")
    @Multipart
    Observable<BasicResult<PubOrderModel>> pubOrder(@PartMap Map<String, RequestBody> data);

    @POST("S0017")
    @Multipart
    Observable<BasicResult<AliAuthInfoModel>> getAuthInfo(@PartMap Map<String,RequestBody> data);

    @POST("S0019")
    @Multipart
    Observable<BasicResult<CashWithdrawModel>> withdraw(@PartMap Map<String,RequestBody> data);

    @POST("S0020")
    @Multipart
    Observable<BasicResult<CashWithdrawModel>> queryStatus(@PartMap Map<String,RequestBody> data);
}
