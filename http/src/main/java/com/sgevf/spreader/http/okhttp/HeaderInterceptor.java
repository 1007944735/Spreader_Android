package com.sgevf.spreader.http.okhttp;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    private Context context;

    public HeaderInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request o = chain.request();
        Request.Builder builder = o.newBuilder()
                .header("uuid", mix(getAndroidId(),getSerialNumber()));//uuid;
        Request request = builder.build();
        return chain.proceed(request);
    }

    /**
     * 获取androidId
     *
     * @return
     */
    private String getAndroidId() {
        return Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
    }

    /**
     * 获取Serial Number
     *
     * @return
     */
    private String getSerialNumber() {
        return Build.SERIAL;
    }

    private String mix(String androidId, String serialNumber) {
        try {
            //获取摘要器 MessageDigest
            String text = androidId + serialNumber;
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            //通过摘要器对字符串的二进制字节数组进行hash计算
            byte[] digest = messageDigest.digest(text.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digest.length; i++) {
                //循环每个字符 将计算结果转化为正整数;
                int digestInt = digest[i] & 0xff;
                //将10进制转化为较短的16进制
                String hexString = Integer.toHexString(digestInt);
                //转化结果如果是个位数会省略0,因此判断并补0
                if (hexString.length() < 2) {
                    sb.append(0);
                }
                //将循环结果添加到缓冲区
                sb.append(hexString);
            }
            //返回整个结果
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
