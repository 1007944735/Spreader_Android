package com.sgevf.spreader.http.api;

import android.app.Activity;
import android.util.Log;

import com.sgevf.spreader.http.base.impl.UploadProgressListener;
import com.sgevf.spreader.http.okhttp.OKHttpManager;
import com.sgevf.spreader.http.utils.NetConfig;

import java.lang.reflect.ParameterizedType;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BasicApi<T, S> implements ObserverListener<S>, ObservableListener {
    public T service;
    public Activity mActivity;
    public com.sgevf.spreader.http.entity.RequestBody params;
    public Object mTarget;

    public BasicApi(Activity mActivity, Object mTarget) {
        this(mActivity, mTarget, NetConfig.URL);
    }

    public BasicApi(Activity mActivity, Object mTarget, String url) {
        this(mActivity, mTarget, url, null);
    }

    public BasicApi(Activity mActivity, Object mTarget, UploadProgressListener listener) {
        this(mActivity, mTarget, NetConfig.URL, listener);
    }

    public BasicApi(Activity mActivity, Object mTarget, String url, UploadProgressListener listener) {
        this.mTarget = mTarget;
        params = new com.sgevf.spreader.http.entity.RequestBody();
        params.setUploadListener(listener);
        this.mActivity = mActivity;
        Retrofit retrofit = new Retrofit.Builder()
                .client(OKHttpManager.getClient(mActivity))
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

    public void request() {
        Observable o = setObservable(params.getParam());
        if (o != null) {
            subscribe(o, setObserver(setShowLoading()));
        } else {
            Log.d("BasicApi", "没有重写setObservable()");
        }
    }

    private void subscribe(Observable observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private Class<T> getCls() {
        Class c = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return c;
    }

    private Observer setObserver(boolean show) {
        SimpleObserver<S> observer = new SimpleObserver<>(mActivity, mTarget, show);
        observer.setListener(this);
        return observer;
    }

    protected boolean setShowLoading() {
        return true;
    }
}
