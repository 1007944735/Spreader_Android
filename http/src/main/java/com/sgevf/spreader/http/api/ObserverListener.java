package com.sgevf.spreader.http.api;

public interface ObserverListener<T> {
    void onNext(T t);
}
