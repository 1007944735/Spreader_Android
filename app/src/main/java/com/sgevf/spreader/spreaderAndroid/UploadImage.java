package com.sgevf.spreader.spreaderAndroid;

import android.text.method.MovementMethod;

import com.sgevf.spreader.http.entity.BaseResult;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadImage {
    @Multipart
    @POST("fileUpload")
    Observable<BaseResult<Movie>> uploadImage(@Part MultipartBody.Part file);
}
