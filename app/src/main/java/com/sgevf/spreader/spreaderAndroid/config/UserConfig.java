package com.sgevf.spreader.spreaderAndroid.config;

import android.content.Context;
import android.content.SharedPreferences;

public class UserConfig {
    public static final String NAME = "userConfig";
    public static final String ADCODE = "adcode";//当前城市信息
    public static final String USERID = "userid";//userId
    public static final String NICKNAME = "nickname";//用户昵称
    public static final String USERNAME = "username";//用户帐号
    public static final String PASSWORD = "password";//用户密码
    public static final String USERHEAD = "userhead";//用户头像
    public static final String USERPHONE = "userphone";//用户手机号

    public static final String LOGINSTATUS = "loginstatus";//登录状态
    public static final String AUTOLOGIN = "autologin";//自动登录
    public static final String REMEMBERPASS = "rememberpass";//记住密码


    public static void setAdCode(Context context, String adCode) {
        SharedPreferences.Editor editor = context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
        editor.putString(ADCODE, adCode);
        editor.apply();
    }

    public static String getAdCode(Context context) {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE).getString(ADCODE, "");
    }

    public static void setUserId(Context context, Integer userId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(USERID, userId);
        editor.apply();
    }

    public static Integer getUserId(Context context) {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE).getInt(USERID, -1);
    }

    public static void setNickName(Context context, String nickName) {
        SharedPreferences.Editor editor = context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
        editor.putString(NICKNAME, nickName);
        editor.apply();
    }

    public static String getNickName(Context context) {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE).getString(NICKNAME, "");
    }

    public static void setUserName(Context context, String userName) {
        SharedPreferences.Editor editor = context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
        editor.putString(USERNAME, userName);
        editor.apply();
    }

    public static String getUserName(Context context) {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE).getString(USERNAME, "");
    }

    public static void setPassword(Context context, String password) {
        SharedPreferences.Editor editor = context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
        editor.putString(PASSWORD, password);
        editor.apply();
    }

    public static String getPassword(Context context) {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE).getString(PASSWORD, "");
    }

    public static void setUserHead(Context context, String userHead) {
        SharedPreferences.Editor editor = context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
        editor.putString(USERHEAD, userHead);
        editor.apply();
    }

    public static String getUserHead(Context context) {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE).getString(USERHEAD, "");
    }

    public static void setUserPhone(Context context, String userPhone) {
        SharedPreferences.Editor editor = context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
        editor.putString(USERPHONE, userPhone);
        editor.apply();
    }

    public static String getUserPhone(Context context) {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE).getString(USERPHONE, "");
    }

    public static void setLoginStatus(Context context, boolean loginStatus) {
        SharedPreferences.Editor editor = context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(LOGINSTATUS, loginStatus);
        editor.apply();
    }

    public static boolean isLogin(Context context) {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE).getBoolean(LOGINSTATUS, false);
    }

    public static void setAutoLogin(Context context, boolean autoLogin) {
        SharedPreferences.Editor editor = context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(AUTOLOGIN, autoLogin);
        editor.apply();
    }

    public static boolean isAutoLogin(Context context) {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE).getBoolean(AUTOLOGIN, false);
    }

    public static void setRememberPass(Context context, boolean rememberPass) {
        SharedPreferences.Editor editor = context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(REMEMBERPASS, rememberPass);
        editor.apply();
    }

    public static boolean isRememberPass(Context context) {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE).getBoolean(REMEMBERPASS, false);
    }

}
