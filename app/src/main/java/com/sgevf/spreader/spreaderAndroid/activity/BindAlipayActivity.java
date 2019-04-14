package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.alipay.sdk.app.EnvUtils;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.model.AliAuthInfoModel;
import com.sgevf.spreader.spreaderAndroid.model.BindAlipayModel;
import com.sgevf.spreader.spreaderAndroid.model.pay.AuthResult;
import com.sgevf.spreader.spreaderAndroid.task.BaseService;
import com.sgevf.spreader.spreaderAndroid.task.SearchBindAlipayTask;
import com.sgevf.spreader.spreaderAndroid.task.impl.PayService;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import utils.AppAlipayUtil;

public class BindAlipayActivity extends BaseLoadingActivity<BindAlipayModel> implements AppAlipayUtil.AliResultCallback {
    @BindView(R.id.bind)
    public Button bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_binf_alipay_account);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle("绑定");
        new SearchBindAlipayTask(this, this).request();

    }

    @Override
    public void onLoadFinish(BindAlipayModel bindAlipayModel) {
        if ("0".equals(bindAlipayModel.isGrant)) {
            bind.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.bind)
    public void bind() {
        new BaseService<PayService, AliAuthInfoModel>(this, this) {
            @Override
            public void onSuccess(AliAuthInfoModel aliAuthInfoModel) {
                new AppAlipayUtil(BindAlipayActivity.this, AppAlipayUtil.SDK_AUTH_FLAG).auth(aliAuthInfoModel.authInfo).setCallback(BindAlipayActivity.this);
            }

            @Override
            public Observable setObservable(Map<String, RequestBody> data) {
                return service.getAuthInfo(data);
            }
        }.request();
    }

    @Override
    public void success(Object obj) {
        final String authCode = ((AuthResult) obj).getAuthCode();
        new BaseService<PayService, String>(this, this) {

            @Override
            public void onSuccess(String s) {

            }

            @Override
            public Observable setObservable(Map<String, RequestBody> data) {
                params.put("authCode", authCode);
                return service.bindAlipayUserInfo(data);
            }
        }.request();
    }

    @Override
    public void error(String errorCode) {
        Log.d("TAG", "error: " + errorCode);
    }
}
