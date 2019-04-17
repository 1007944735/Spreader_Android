package com.sgevf.spreader.http.entity;

import com.sgevf.spreader.http.base.ProgressRequestBody;
import com.sgevf.spreader.http.base.impl.UploadProgressListener;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;

public class RequestBody {
    private Map<String, okhttp3.RequestBody> map;
    private UploadProgressListener listener;

    public RequestBody() {
        map = new HashMap<>();
    }

    public void put(String key, String values) {
        okhttp3.RequestBody body = okhttp3.RequestBody.create(MediaType.parse("text/plain"), values);
        map.put(key, body);
    }

    public void put(String key, File file,String name) {
        okhttp3.RequestBody body = okhttp3.RequestBody.create(MediaType.parse("multipart/form-data"), file);
        ProgressRequestBody progressBody = new ProgressRequestBody(body, listener,name);
        map.put(key + "\";filename=\"" + file.getName(), progressBody);

    }

    public Map<String, okhttp3.RequestBody> getParam() {
        return map;
    }

    public void setUploadListener(UploadProgressListener listener){
        this.listener=listener;
    }
}
