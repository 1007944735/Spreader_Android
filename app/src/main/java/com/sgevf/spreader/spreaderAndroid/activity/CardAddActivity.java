package com.sgevf.spreader.spreaderAndroid.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.sgevf.spreader.http.utils.ToastUtils;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.task.CardAddTask;
import com.sgevf.spreader.spreaderAndroid.view.CardUseRuleView;
import com.sgevf.spreader.spreaderAndroid.view.CountDownDialog;
import com.sgevf.spreader.spreaderAndroid.view.DatePickerDialog;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.http.FieldMap;
import utils.DialogUtils;

public class CardAddActivity extends BaseLoadingActivity<String> {
    @BindView(R.id.discountRule)
    public EditText discountRule;
    @BindView(R.id.useRule)
    public CardUseRuleView useRule;
    @BindView(R.id.start)
    public TextView start;
    @BindView(R.id.end)
    public TextView end;
    private String et="0000-00-00 00:00:00";//有效时间

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_card_add);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle("添加");

    }

    @Override
    public void onLoadFinish(String s) {
        ToastUtils.Toast(this, "添加成功");
        setResult(1002);
        finish();
    }

    @OnClick(R.id.submit)
    public void submit() {
        String st = start.getText().toString();
        String ur = useRule.getUseRule();
        String dr=discountRule.getText().toString();
        new CardAddTask(this, this).setClass(dr, ur, st, et).request();
    }

    @OnClick(R.id.start_time)
    public void startTime() {
        DialogUtils.showSelectTime(this, new DatePickerDialog.OnConfirmListener() {
            @Override
            public void select(String time) {
                start.setText(time);
            }
        });
    }

    @OnClick(R.id.end_time)
    public void endTime() {
        DialogUtils.showCountDown(this, new CountDownDialog.OnConfirmListener() {
            @Override
            public void select(String day, String hour, String minites) {
                end.setText(day + "天" + hour + "时" + minites + "分");
                et="0000-00-"+day+" "+hour+":"+minites+":00";
            }
        });
    }
}
