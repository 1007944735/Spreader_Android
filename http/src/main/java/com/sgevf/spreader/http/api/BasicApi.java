package com.sgevf.spreader.http.api;

import android.app.Activity;
import android.util.Log;

import com.sgevf.spreader.http.base.ProgressRequestBody;
import com.sgevf.spreader.http.base.impl.UploadProgressListener;
import com.sgevf.spreader.http.okhttp.OKHttpManager;
import com.sgevf.spreader.http.utils.NetConfig;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BasicApi<T, S> implements ObserverOnNextListener<S> {
    public T service;
    public Activity mActivity;

    private UploadProgressListener listener;
    private String uploadFileName="fileName";
    private String mediaType="image/jpeg";
    public BasicApi(Activity mActivity) {
        this(mActivity, NetConfig.URL);
    }

    public BasicApi(Activity mActivity, String url) {
        this(mActivity, url, null);

    }

    public BasicApi(Activity mActivity, UploadProgressListener listener) {
        this(mActivity, NetConfig.URL, listener);

    }

    public BasicApi(Activity mActivity, String url, UploadProgressListener listener) {
        this.mActivity = mActivity;
        this.listener = listener;
        Retrofit retrofit = new Retrofit.Builder()
                .client(OKHttpManager.getClient())
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        if (getCls().isInterface()) {
            service = (T) retrofit.create(getCls());
        } else {
            throw new RuntimeException("serviceImpl 不是接口");
        }
    }

    //上传图片的路径 默认为""
    public String filePath() {
        return "";
    }

    public void request() {
        if (!"".equals(filePath())) {
            String url = filePath();
            File file = new File(url);
            if (file.exists()) {
                RequestBody requestBody = RequestBody.create(MediaType.parse(mediaType()), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData(uploadFileName(), file.getName(), new ProgressRequestBody(requestBody, listener));
                Observable o = setObservable(part);
                if (o != null) {
                    subscribe(o, setObserver());
                } else {
                    Log.d("BasicApi", "setObservable(MultipartBody.Part part)");
                }
            } else {
                Log.d("BasicApi", "url 不存在");
            }
        } else {
            Observable o = setObservable();
            if (o != null) {
                subscribe(o, setObserver());
            } else {
                Log.d("BasicApi", "没有重写setObservable()");
            }
        }
    }

    private void subscribe(Observable observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    protected abstract Class getCls();

    protected Observable setObservable() {
        return null;
    }

    protected Observable setObservable(MultipartBody.Part part) {
        return null;
    }

    private Observer setObserver() {
        SimpleObserver<S> observer = new SimpleObserver<>(mActivity);
        observer.setListener(this);
        return observer;
    }

    public String uploadFileName(){
        return uploadFileName;
    }

    public String mediaType(){
        return mediaType;
    }

}
