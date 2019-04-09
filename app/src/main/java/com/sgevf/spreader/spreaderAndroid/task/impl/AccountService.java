package com.sgevf.spreader.spreaderAndroid.task.impl;

import com.sgevf.spreader.http.entity.BasicResult;
import com.sgevf.spreader.spreaderAndroid.model.HistoryDetailsModel;
import com.sgevf.spreader.spreaderAndroid.model.UserAccountModel;
import com.sgevf.spreader.spreaderAndroid.model.WalletHistoryRedPacketDetailsModel;
import com.sgevf.spreader.spreaderAndroid.model.WalletHistoryWithdrawDetailsModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface AccountService {
    @POST("S0010")
    @Multipart
    Observable<BasicResult<UserAccountModel>> selectAccount(@PartMap Map<String, RequestBody> data);

    @POST("S0011")
    @Multipart
    Observable<BasicResult<HistoryDetailsModel>> queryHistory(@PartMap Map<String,RequestBody> data);

    @POST("S0012")
    @Multipart
    Observable<BasicResult<WalletHistoryWithdrawDetailsModel>> withdrawDetails(@PartMap Map<String,RequestBody> data);

    @POST("S0013")
    @Multipart
    Observable<BasicResult<WalletHistoryRedPacketDetailsModel>> redPacketDetails(@PartMap Map<String,RequestBody> data);
}
