package com.sgevf.spreader.http.api;

import android.util.Log;

import com.sgevf.spreader.http.entity.BaseResult;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SimpleObserver<T> implements Observer<BaseResult<T>> {
    private static final String TAG="SimpleObserver";
    public ObserverOnNextListener<T> listener;
    @Override
    public void onSubscribe(Disposable d) {
        Log.d(TAG, "onSubscribe: ");
    }

    @Override
    public void onNext(BaseResult<T> tBaseResult) {
        listener.onNext(tBaseResult.data);
    }


    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError: " + e.getMessage());
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete: Over!");
    }

    public ObserverOnNextListener<T> getListener() {
        return listener;
    }

    public void setListener(ObserverOnNextListener<T> listener) {
        this.listener = listener;
    }
}
