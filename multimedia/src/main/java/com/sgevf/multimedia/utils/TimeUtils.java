package com.sgevf.multimedia.utils;

public class TimeUtils {
    /**
     * 转化时间格式
     * @param curTime 毫秒
     * @return
     */
    public static String formatTime(int curTime){
        String m="";
        String s="";
        curTime=curTime/1000;
        int minute= (int) (curTime*1.0f/60);
        if(minute<10){
            m="0"+minute;
        }else {
            m=minute+"";
        }
        int second=curTime%60;
        if(second<10){
            s="0"+second;
        }else {
            s=second+"";
        }
        return m+":"+s;
    }
}
