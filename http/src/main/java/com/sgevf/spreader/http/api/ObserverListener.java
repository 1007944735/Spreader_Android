package com.sgevf.spreader.http.api;

import io.reactivex.disposables.Disposable;

public interface ObserverListener<T> {
    void beforeRequest(Disposable d);

    void onSuccess(T t);

    void onFail(String reCode);

    void onError(Throwable e);

    void finishRequest();

}
