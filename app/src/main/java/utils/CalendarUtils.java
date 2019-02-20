package utils;

import java.util.Calendar;

public class CalendarUtils {
    /**
     * 获取时间 yyyy/MM/dd hh/mm
     *
     * @return
     */
    public static String getTime() {
        Calendar calendar = Calendar.getInstance();
        return getYear() + "/" + getMonth() + "/" + getDay() + " " + getHour() + ":" + getMinute();
    }

    /**
     * 获取Calendar
     *
     * @return
     */
    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    /**
     * 获取年份
     *
     * @return
     */
    public static int getYear() {
        return getCalendar().get(Calendar.YEAR);
    }

    /**
     * 获取月份
     *
     * @return
     */
    public static int getMonth() {
        return getCalendar().get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日
     *
     * @return
     */
    public static int getDay() {
        return getCalendar().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取小时
     *
     * @return
     */
    public static int getHour() {
        return getCalendar().get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取分钟
     *
     * @return
     */
    public static int getMinute() {
        return getCalendar().get(Calendar.MINUTE);
    }

    /**
     * 获取星期
     *
     * @return
     */
    public static int getWeekDay() {
        return getCalendar().get(Calendar.DAY_OF_WEEK);
    }
}
