package com.sgevf.spreader.spreaderAndroid;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

import com.sgevf.spreader.http.api.BaseApi;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadApi extends BaseApi<UploadImage,Movie> {

    public UploadApi(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public Class getCls() {
        return UploadImage.class;
    }

    @Override
    protected Observable setObservable() {
        String url= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath()+"/Camera/IMG_20181129_144117.jpg";

        File file=new File(url);
        RequestBody requestBody=RequestBody.create(MediaType.parse("image/jpeg"),file);
        MultipartBody.Part part=MultipartBody.Part.createFormData("fileName",file.getName(),new ProgressRequestBody(requestBody, new UploadProgressListener() {
            @Override
            public void progress(long currentBytesCount, long totalBytesCount) {
                Log.d("TAG", "progress: "+currentBytesCount);
            }
        }));
        return service.uploadImage(part);
    }

    @Override
    public void onNext(Movie movie) {
        if( mActivity instanceof MainActivity){
            ((MainActivity) mActivity).finish(movie);
        }
    }
}
