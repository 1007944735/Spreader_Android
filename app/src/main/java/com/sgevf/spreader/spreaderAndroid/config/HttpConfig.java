package com.sgevf.spreader.spreaderAndroid.config;

import android.content.Context;

import com.sgevf.spreader.http.utils.NetConfig;

import utils.PropertiesUtils;

public class HttpConfig {
    public static void init(Context c){
        NetConfig.URL = PropertiesUtils.getUrl(c);
    }
}
