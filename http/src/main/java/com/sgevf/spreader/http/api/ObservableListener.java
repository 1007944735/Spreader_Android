package com.sgevf.spreader.http.api;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public interface ObservableListener {
    Observable setObservable(Map<String, RequestBody> data);
}
