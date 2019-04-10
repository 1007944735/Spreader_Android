package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.PoiItem;
import com.sgevf.spreader.http.utils.ToastUtils;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.map.MapActivity;
import com.sgevf.spreader.spreaderAndroid.model.ExpandInfoModel;
import com.sgevf.spreader.spreaderAndroid.model.ExpandPhotoModel;
import com.sgevf.spreader.spreaderAndroid.task.PubTask;
import com.sgevf.spreader.spreaderAndroid.view.DatePickerDialog;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import java.io.File;
import java.lang.annotation.ElementType;
import java.security.interfaces.ECKey;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.DialogUtils;

public class PubActivity extends BaseLoadingActivity<String> {
    @BindView(R.id.count)
    public EditText count;
    @BindView(R.id.price)
    public EditText price;
    @BindView(R.id.start)
    public TextView start;
    @BindView(R.id.end)
    public TextView end;
    @BindView(R.id.address)
    public TextView address;
    @BindView(R.id.change)
    public TextView change;
    @BindView(R.id.text)
    public TextView text;

    private ExpandInfoModel infos;
    private PoiItem poi;
    private int type = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pub);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle(R.string.history_release);
        infos = getIntent().getParcelableExtra("infos");
        changeTextColor(1);
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
        DialogUtils.showSelectTime(this, new DatePickerDialog.OnConfirmListener() {
            @Override
            public void select(String time) {
                end.setText(time);
            }
        });
    }

    @OnClick(R.id.release_address)
    public void releaseAddress() {
        startActivityForResult(new Intent(this, MapActivity.class), 1000);
    }

    @OnClick(R.id.change)
    public void change() {
        if (type == 1) {
            changeTextColor(2);
        } else {
            changeTextColor(1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1000 && resultCode == 1001) {
            poi = data.getParcelableExtra("poi");
            address.setText(poi.getTitle());
        } else if (requestCode == 2000 && resultCode == 2001) {
            PubTask task = new PubTask(this, this);
            if (type == 1) {
                task.params.put("amount", "" + Integer.valueOf(count.getText().toString()) * Double.valueOf(price.getText().toString()));
            } else if (type == 0) {
                task.params.put("amount", "" + Double.valueOf(price.getText().toString()));
            }
            task.params.put("type", type + "");
            task.params.put("pubLongitude", poi.getLatLonPoint().getLongitude() + "");
            task.params.put("pubLatitude", poi.getLatLonPoint().getLatitude() + "");
            task.params.put("startTime", start.getText().toString());
            task.params.put("endTime", end.getText().toString());
            task.params.put("maxNumber", count.getText().toString());
            task.params.put("pubAddress", address.getText().toString());
            task.params.put("title", infos.title);
            task.params.put("info", infos.info);
            task.params.put("video", new File(infos.video.path));
            task.params.put("orderId", data.getIntExtra("orderId", 0) + "");
            for (ExpandPhotoModel picture : infos.pictures) {
                task.params.put("pictures", new File(picture.path));
            }
            task.request();
        }
    }

    @OnClick(R.id.submit)
    public void submit() {
        String m = null;
        if (type == 1) {
            m = "" + Integer.valueOf(count.getText().toString()) * Double.valueOf(price.getText().toString());
        } else if (type == 0) {
            m = "" + Double.valueOf(price.getText().toString());
        }
        startActivityForResult(new Intent(this, PayActivity.class).putExtra("amount", m), 2000);
    }

    @Override
    public void onLoadFinish(String s) {
        ToastUtils.Toast(this, s);
        startActivity(new Intent(this, HomeActivity.class));
    }

    private void changeTextColor(int t) {
        if (t == 1) {
            SpannableString spannableString = SpannableString.valueOf("当前为普通红包，点击转换成随机红包");
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#0099EE")), 8, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            change.setText(spannableString);
            type = 1;
            text.setText("单个金额");
        } else if (t == 2) {
            SpannableString spannableString = SpannableString.valueOf("当前为随机红包，点击转换成普通红包");
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#0099EE")), 8, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            change.setText(spannableString);
            type = 0;
            text.setText("总金额");
        }
        price.setText("");
        count.setText("");
        start.setText("");
        end.setText("");
        address.setText("");
    }
}
