package com.sgevf.spreader.http.okhttp;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.nanoTime();
        Log.d("Http", "发送请求Url：" + request.url());
        if (!(request.body() instanceof MultipartBody)) {
            Log.d("Http", "发送请求：" + bodyToString(request.body()));
        }
        Response response = chain.proceed(request);
        long endTime = System.nanoTime();
        ResponseBody body = response.peekBody(1024 * 1024);
        Log.d("Http", "接收响应Url: " + response.request().url());
        Log.d("Http", "接收响应: " + body.string());
        Log.d("Http", "响应时间: " + (endTime - startTime) / 1e6d);
        return response;
    }

    private static String bodyToString(RequestBody request) {
        try {
            RequestBody copy = request;
            Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return URLDecoder.decode(buffer.readUtf8(), "utf-8");
        } catch (final IOException e) {
            return "did not work";
        }
    }


}
