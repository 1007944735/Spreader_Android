package com.sgevf.spreader.spreaderAndroid.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sgevf.spreader.spreaderAndroid.R;

import utils.CalendarUtils;
import utils.ParseUtils;

public class DatePickerDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private TextView confirm;
    private TextView cancel;
    private PickerView year;
    private PickerView month;
    private PickerView day;
    private PickerView hour;
    private PickerView minute;
    private OnConfirmListener confirmListener;

    private boolean leapYear = false;
    private int twoMon = 0;// 1、大月 31天  2、小月 30天 3、二月

    public DatePickerDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_date_picker_style);
        init();
        initData();
        rule();
    }

    private void init() {
        confirm = findViewById(R.id.confirm);
        cancel = findViewById(R.id.cancel);
        year = findViewById(R.id.year);
        month = findViewById(R.id.month);
        day = findViewById(R.id.day);
        hour = findViewById(R.id.hour);
        minute = findViewById(R.id.minute);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    private void initData() {
        ColorStateList csl = context.getResources().getColorStateList(R.color.bg_btn_text_selector);
        confirm.setTextColor(csl);
        cancel.setTextColor(csl);
        year.setData(ParseUtils.arrayToList(context.getResources().getStringArray(R.array.year)));
        month.setData(ParseUtils.arrayToList(context.getResources().getStringArray(R.array.month)));
        day.setData(ParseUtils.arrayToList(context.getResources().getStringArray(R.array.day_31)));
        hour.setData(ParseUtils.arrayToList(context.getResources().getStringArray(R.array.hour)));
        minute.setData(ParseUtils.arrayToList(context.getResources().getStringArray(R.array.minute)));
        initDefaultTime();
    }

    private void rule() {

        year.setOnSelectListener(new PickerView.OnSelectListener() {
            @Override
            public void onSelect(String text) {
                leapYear = isLeapYear(text);
                change(leapYear, twoMon);
            }
        });

        month.setOnSelectListener(new PickerView.OnSelectListener() {
            @Override
            public void onSelect(String text) {
                if ("2".equals(text)) {
                    twoMon = 3;
                } else if ("7".equals(text) || "8".equals(text)) {
                    twoMon = 1;
                } else if (Integer.valueOf(text) % 2 == 1) {
                    twoMon = 1;
                } else {
                    twoMon = 2;
                }
                change(leapYear, twoMon);
            }
        });
    }

    /**
     * 设置当前的时间
     */
    private void initDefaultTime() {
        year.setSelectItem(CalendarUtils.getYear() + "");
        month.setSelectItem(CalendarUtils.getMonth() + "");
        day.setSelectItem(CalendarUtils.getDay() + "");
        hour.setSelectItem(CalendarUtils.getHour() + "");
        minute.setSelectItem(CalendarUtils.getMinute() + "");
    }

    private boolean isLeapYear(int year) {
        if (year % 4 == 0 && year % 100 != 0) {
            return true;
        }
        if (year % 400 == 0) {
            return true;
        }
        return false;
    }

    private boolean isLeapYear(String year) {
        return isLeapYear(Integer.valueOf(year));
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.confirm) {
            String y = year.getSelect();
            String m = month.getSelect();
            String d = day.getSelect();
            String h = hour.getSelect();
            String mm = minute.getSelect();
            if (confirmListener != null) {
                confirmListener.select(y + "-" + m + "-" + d + " " + h + ":" + mm);
            }
            dismiss();
        }
        if (v.getId() == R.id.cancel) {
            dismiss();
        }
    }

    private void change(boolean bool, int var) {
        if (bool && var == 3) {
            //闰年 2月 29天
            day.setData(ParseUtils.arrayToList(context.getResources().getStringArray(R.array.day_29)));
        } else if (!bool && var == 3) {
            //平年 2月 28天
            day.setData(ParseUtils.arrayToList(context.getResources().getStringArray(R.array.day_28)));
        } else if (var == 1) {
            //大月 31天
            day.setData(ParseUtils.arrayToList(context.getResources().getStringArray(R.array.day_31)));
        } else if (var == 2) {
            //小月 30天
            day.setData(ParseUtils.arrayToList(context.getResources().getStringArray(R.array.day_30)));
        }
    }

    public interface OnConfirmListener {
        void select(String time);
    }

    public void setConfirmListener(OnConfirmListener confirmListener) {
        this.confirmListener = confirmListener;
    }
}
