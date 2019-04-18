package com.sgevf.spreader.spreaderAndroid.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;

import utils.CalendarUtils;
import utils.ParseUtils;

public class CountDownDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private TextView confirm;
    private TextView cancel;
    private PickerView day;
    private PickerView hour;
    private PickerView minute;
    private OnConfirmListener confirmListener;

    public CountDownDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_count_down_style);
        init();
        initData();
    }

    private void init() {
        confirm = findViewById(R.id.confirm);
        cancel = findViewById(R.id.cancel);
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
        day.setData(ParseUtils.arrayToList(context.getResources().getStringArray(R.array.day)));
        hour.setData(ParseUtils.arrayToList(context.getResources().getStringArray(R.array.hour_a)));
        minute.setData(ParseUtils.arrayToList(context.getResources().getStringArray(R.array.minute_a)));
        day.setSelectItem(0);
        hour.setSelectItem(0);
        minute.setSelectItem(0);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.confirm) {
            String d = day.getSelect();
            String h = hour.getSelect();
            String mm = minute.getSelect();
            if (confirmListener != null) {
                confirmListener.select(d ,  h, mm );
            }
            dismiss();
        }
        if (v.getId() == R.id.cancel) {
            dismiss();
        }
    }

    public interface OnConfirmListener {
        void select(String day,String hour,String minites);
    }

    public void setConfirmListener(OnConfirmListener confirmListener) {
        this.confirmListener = confirmListener;
    }
}
