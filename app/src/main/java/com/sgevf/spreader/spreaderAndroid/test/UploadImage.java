package com.sgevf.spreader.spreaderAndroid.test;

import com.sgevf.spreader.http.entity.BasicResult;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadImage {
    @Multipart
    @POST("fileUpload")
    Observable<BasicResult<Movie>> uploadImage(@Part MultipartBody.Part file);
}
