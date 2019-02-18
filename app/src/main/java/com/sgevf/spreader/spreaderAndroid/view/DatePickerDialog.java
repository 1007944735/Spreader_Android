package com.sgevf.spreader.spreaderAndroid.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;

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
        day.setData(ParseUtils.arrayToList(context.getResources().getStringArray(R.array.day_30)));
        hour.setData(ParseUtils.arrayToList(context.getResources().getStringArray(R.array.hour)));
        minute.setData(ParseUtils.arrayToList(context.getResources().getStringArray(R.array.minute)));
        month.setSelectItem(11);
        day.setSelectItem(10);
        month.setOnSelectListener(new PickerView.OnSelectListener() {
            @Override
            public void onSelect(String text) {
                if("3".equals(text)){
                    day.selectNext();
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.confirm) {
            String y = year.getSelect();
            String m = month.getSelect();
            String d = day.getSelect();
            String h = hour.getSelect();
            String mm=minute.getSelect();
            if(confirmListener!=null){
                confirmListener.select(y+"年"+m+"月"+d+"日"+h+":"+mm);
            }
            dismiss();
        }
        if (v.getId() == R.id.cancel) {
            dismiss();
        }
    }

    public interface OnConfirmListener{
        void select(String time);
    }

    public void setConfirmListener(OnConfirmListener confirmListener){
        this.confirmListener=confirmListener;
    }
}
