package com.sgevf.spreader.http.okhttp;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * 暂时弃用
 */
@Deprecated
public class HeaderInterceptor implements Interceptor {
    private static final String GET = "GET";
    private static final String POST = "POST";

    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取请求
        Request request = chain.request();
        //获取请求方式
        String method = request.method();
        //公共参数
        HashMap<String, Object> commonParamsMap = new HashMap<>();

        commonParamsMap.put("T", "debug");

        //get请求
        if (GET.equals(method)) {
            HttpUrl url = request.url();
            HttpUrl newUrl = url.newBuilder()
                    .addEncodedQueryParameter("T", "debug")
                    .build();
            request = request.newBuilder().url(newUrl).build();
        } else if (POST.equals(method)) {
            RequestBody body = request.body();
            if (body instanceof FormBody) {
                FormBody.Builder builder = new FormBody.Builder();
                builder.add("T", "debug");
                for (int i=0;i<((FormBody) body).size();i++){
                    builder.addEncoded(((FormBody) body).encodedName(i),((FormBody) body).encodedValue(i));
                }
                request = request.newBuilder().method(request.method(),builder.build()).build();
            }
        }

//        request.newBuilder().addHeader("Content-Type","application/json;charset=UTF-8");
        return chain.proceed(request);
    }


}
