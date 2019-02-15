package utils;

import android.content.Context;

public class WindowHelper {

    public static int getScreentWidth(Context c){
        return c.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreentHeight(Context c){
        return c.getResources().getDisplayMetrics().heightPixels;
    }
}
