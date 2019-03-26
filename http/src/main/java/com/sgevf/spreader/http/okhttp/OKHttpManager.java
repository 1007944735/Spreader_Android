package com.sgevf.spreader.http.okhttp;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class OKHttpManager {
    private static final long DEFAULT_CONNECT_TIMEOUT = 6;
    private static final long DEFAULT_READ_TIMEOUT = 10;
    private static final long DEFAULT_WRITE_TIMEOUT = 10;

    public static OkHttpClient getClient(Context context){
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT,TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT,TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT,TimeUnit.SECONDS)
                .addInterceptor(new HeaderInterceptor(context))
                .addInterceptor(new LoggingInterceptor())
                .build();
        return client;
    }
}
