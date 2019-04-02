package com.sgevf.spreader.http.entity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;

public class RequestBody {
    private Map<String, okhttp3.RequestBody> map;

    public RequestBody() {
        map = new HashMap<>();
    }

    public void put(String key, String values) {
        okhttp3.RequestBody body = okhttp3.RequestBody.create(MediaType.parse("text/plain"), values);
        map.put(key, body);
    }

    public void put(String key, File file) {
        okhttp3.RequestBody body = okhttp3.RequestBody.create(MediaType.parse("multipart/form-data"), file);
        map.put(key + "\";filename=\"" + file.getName(), body);
    }

    public Map<String, okhttp3.RequestBody> getParam() {
        return map;
    }
}
