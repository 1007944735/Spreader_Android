package com.sgevf.spreader.spreaderAndroid.test;

import android.app.Activity;
import android.os.Environment;

import com.sgevf.spreader.http.api.BasicApi;
import com.sgevf.spreader.http.base.impl.UploadProgressListener;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

public class UploadApi extends BasicApi<UploadImage,Movie> {

    public UploadApi(Activity mActivity, UploadProgressListener listener) {
        super(mActivity, listener);
    }

//    @Override
//    protected Class getCls() {
//        return UploadImage.class;
//    }


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
        if( mActivity instanceof MActivity){
            ((MActivity) mActivity).finish(movie);
        }
    }

}
