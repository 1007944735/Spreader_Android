package com.sgevf.spreader.http.api;

import android.app.Activity;

import com.sgevf.spreader.http.okhttp.OKHttpManager;
import com.sgevf.spreader.http.utils.NetConfig;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseApi<T,S> implements ObserverOnNextListener<S>{
    public T service;
    public Activity mActivity;
    public BaseApi(Activity mActivity){
        this(mActivity, NetConfig.URL);
    }
    public BaseApi(Activity mActivity,String url){
        this.mActivity=mActivity;
        Retrofit retrofit=new Retrofit.Builder()
                .client(OKHttpManager.getClient())
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        if(getCls().isInterface()) {
            service = (T) retrofit.create(getCls());
        }else {
            throw new RuntimeException("serviceImpl 不是接口");
        }
        subscribe(setObservable(),setObserver());
    }

    private void subscribe(Observable observable,Observer observer){
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public abstract Class getCls();

    protected abstract Observable setObservable();

    private Observer setObserver(){
        SimpleObserver<S> observer=new SimpleObserver<>(mActivity);
        observer.setListener(this);
        return observer;
    }

}
