package com.sgevf.spreader.spreaderAndroid.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseActivity;
import com.sgevf.spreader.spreaderAndroid.config.HttpConfig;
import com.sgevf.spreader.spreaderAndroid.view.DatePickerDialog;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;
import utils.DialogUtils;


@RuntimePermissions
public class WelcomeActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);
        new HeaderView(this).setTitle("欢迎");
        HttpConfig.init(getApplicationContext());
        WelcomeActivityPermissionsDispatcher.getMultiPermissionWithCheck(this);
    }

    public void skip(final View view) {
//        startActivity(new Intent(this,HomeActivity.class));
//        DialogUtils.showConfirm(this,"测试dialog","测试测试","确定","取消",null,null);
        DialogUtils.showSelectTime(this, new DatePickerDialog.OnConfirmListener() {
            @Override
            public void select(String time) {
                ((Button) view).setText(time);
            }
        });
    }


    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void getMultiPermission() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        WelcomeActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
