package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.config.UserConfig;
import com.sgevf.spreader.spreaderAndroid.model.UserModel;
import com.sgevf.spreader.spreaderAndroid.task.LoginTask;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.AesUtils;

public class LoginActivity extends BaseLoadingActivity<UserModel> {
    @BindView(R.id.username)
    public TextInputEditText userName;
    @BindView(R.id.password)
    public TextInputEditText password;
    @BindView(R.id.remember_pass)
    public CheckBox rememberPass;
    @BindView(R.id.auto_login)
    public CheckBox autoLogin;
    @BindView(R.id.register)
    public TextView register;

    private String originPass;
    private boolean isBackToHome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle(R.string.login);
        init();
    }

    private void init() {
        isBackToHome = getIntent().getBooleanExtra("isBackToHome", true);
        userName.setText(UserConfig.getUserName(this));
        if (UserConfig.isRememberPass(this)) {
            rememberPass.setChecked(true);
            password.setText(UserConfig.getPassword(this));
        } else {
            rememberPass.setChecked(false);
        }
        autoLogin.setChecked(UserConfig.isAutoLogin(this));
        SpannableString s = SpannableString.valueOf(getResources().getString(R.string.login_go_to_register));
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#0099EE")), 5, 9, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        register.setText(s);
    }

    @OnClick(R.id.login)
    public void login(View view) {
        String un = userName.getText().toString().trim();
        String pw = password.getText().toString().trim();
        originPass = pw;
        boolean rp = rememberPass.isChecked();
        boolean al = autoLogin.isChecked();
        UserConfig.setUserName(this, un);
        UserConfig.setRememberPass(this, rp);
        UserConfig.setAutoLogin(this, al);

        new LoginTask(this, this).setClass(un, pw).request();

    }

    @OnClick(R.id.register)
    public void register(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    public void onLoadFinish(UserModel userModel) {
        UserConfig.setToken(this, userModel.token);
        UserConfig.setPassword(this, AesUtils.AESEncode(userModel.username, originPass));
        UserConfig.setNickName(this, userModel.nickname);
        UserConfig.setUserId(this, userModel.id);
        UserConfig.setUserHead(this, userModel.headPortrait);
        UserConfig.setUserPhone(this, userModel.phone);
        UserConfig.setLoginStatus(this, true);
        if (isBackToHome) {
            startActivity(new Intent(this, HomeActivity.class));
        }else {
            finish();
        }
    }
}
