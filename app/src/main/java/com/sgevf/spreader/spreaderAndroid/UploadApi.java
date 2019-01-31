package com.sgevf.spreader.spreaderAndroid;

import android.app.Activity;
import android.os.Environment;

import com.sgevf.spreader.http.api.BaseApi;
import com.sgevf.spreader.http.base.ProgressRequestBody;
import com.sgevf.spreader.http.base.impl.UploadProgressListener;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadApi extends BaseApi<UploadImage,Movie> {

    public UploadApi(Activity mActivity, UploadProgressListener listener) {
        super(mActivity, listener);
    }

    @Override
    protected Class getCls() {
        return UploadImage.class;
    }


    @Override
    protected Observable setObservable(MultipartBody.Part part) {
        return service.uploadImage(part);
    }


    @Override
    public String filePath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath()+"/Camera/IMG_20181129_144117.jpg";
    }



    @Override
    public void onNext(Movie movie) {
        if( mActivity instanceof MainActivity){
            ((MainActivity) mActivity).finish(movie);
        }
    }

}
