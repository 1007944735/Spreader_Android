package com.sgevf.spreader.spreaderAndroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.sgevf.spreader.http.utils.ToastUtils;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.glide.GlideManager;
import com.sgevf.spreader.spreaderAndroid.model.ValidateCodeModel;
import com.sgevf.spreader.spreaderAndroid.task.RegisterTask;
import com.sgevf.spreader.spreaderAndroid.task.ValidateCodeTask;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseLoadingActivity<String> {
    @BindView(R.id.username)
    public EditText userName;
    @BindView(R.id.password)
    public EditText password;
    @BindView(R.id.code)
    public EditText code;
    @BindView(R.id.image_code)
    public ImageView imageCode;
    @BindView(R.id.register)
    public Button register;

    private String uuid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle(R.string.register);
        new ValidateCodeTask(this, this).request();
    }

    @OnClick(R.id.image_code)
    public void showImageCode(View view) {
        new ValidateCodeTask(this, this).request();
    }

    @OnClick(R.id.register)
    public void register(View view) {
        String un = userName.getText().toString().trim();
        String pw = password.getText().toString().trim();
        String c = code.getText().toString().trim();

        new RegisterTask(this, this).setClass(un, pw, uuid, c).request();
    }

    public void show(ValidateCodeModel model) {
        uuid = model.uuuid;
        GlideManager.showImage(this, model.validUrl, imageCode);
    }

    @Override
    public void onLoadFinish(String s) {
        ToastUtils.Toast(this, "注册成功");
        finish();
    }
}
