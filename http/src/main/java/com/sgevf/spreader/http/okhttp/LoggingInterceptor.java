package com.sgevf.spreader.http.okhttp;

import android.util.Log;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        long startTime=System.nanoTime();
        Log.d("Http","发送请求Url："+request.url());
        Log.d("Http","发送请求Json："+request.body());
        Response response=chain.proceed(request);
        long endTime=System.nanoTime();
        ResponseBody body=response.peekBody(1024*1024);
        Log.d("Http", "接收响应Url: "+response.request().url());
        Log.d("Http", "接收响应Json: "+body.string());
        Log.d("Http", "接收响应时间: "+(endTime - startTime) / 1e6d);
        return response;
    }
}
