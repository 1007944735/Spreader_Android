package com.sgevf.spreader.spreaderAndroid.task.impl;

import com.sgevf.spreader.http.entity.BasicResult;
import com.sgevf.spreader.spreaderAndroid.model.HistoryReleaseListModel;
import com.sgevf.spreader.spreaderAndroid.model.HistorySurplusModel;
import com.sgevf.spreader.spreaderAndroid.model.MapRedResultModels;
import com.sgevf.spreader.spreaderAndroid.model.PubResultModel;
import com.sgevf.spreader.spreaderAndroid.model.RedPacketDetailsModel;
import com.sgevf.spreader.spreaderAndroid.model.UploadFileModel;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface PubService {
    @POST("S0005")
    @Multipart
    Observable<BasicResult<PubResultModel>> pub(@PartMap Map<String, RequestBody> data);


    @POST("S0006")
    @Multipart
    Observable<BasicResult<MapRedResultModels>> mapSearch(@PartMap Map<String, RequestBody> data);

    @POST("S0008")
    @Multipart
    Observable<BasicResult<RedPacketDetailsModel>> redPacketDetails(@PartMap Map<String, RequestBody> data);

    @POST("S0021")
    @Multipart
    Observable<BasicResult<HistoryReleaseListModel>> historyRelease(@PartMap Map<String, RequestBody> data);

    @POST("S0022")
    @Multipart
    Observable<BasicResult<HistorySurplusModel>> surplusRefresh(@PartMap Map<String, RequestBody> data);

    @POST("S0023")
    @Multipart
    Observable<BasicResult<String>> cancelPub(@PartMap Map<String, RequestBody> data);

    @POST("S0034")
    @Multipart
    Observable<BasicResult<UploadFileModel>> uploadFile(@PartMap Map<String, RequestBody> data);
}
