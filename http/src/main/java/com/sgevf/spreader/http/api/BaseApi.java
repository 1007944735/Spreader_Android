package com.sgevf.spreader.http.api;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseApi<T,S> implements ObserverOnNextListener<S>{
    public T service;
    public BaseApi(String url){
        Retrofit retrofit=new Retrofit.Builder()
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

    public abstract Observable setObservable();

    protected Observer setObserver(){
        SimpleObserver<S> observer=new SimpleObserver<>();
        observer.setListener(this);
        return observer;
    }

}
