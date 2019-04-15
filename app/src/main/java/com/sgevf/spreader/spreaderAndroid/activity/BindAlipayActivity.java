package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.EnvUtils;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.glide.GlideManager;
import com.sgevf.spreader.spreaderAndroid.model.AliAuthInfoModel;
import com.sgevf.spreader.spreaderAndroid.model.BindAlipayModel;
import com.sgevf.spreader.spreaderAndroid.model.pay.AuthResult;
import com.sgevf.spreader.spreaderAndroid.task.BaseService;
import com.sgevf.spreader.spreaderAndroid.task.SearchBindAlipayTask;
import com.sgevf.spreader.spreaderAndroid.task.impl.PayService;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import java.net.URI;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Url;
import utils.AppAlipayUtil;

public class BindAlipayActivity extends BaseLoadingActivity<BindAlipayModel> {
    @BindView(R.id.bind)
    public Button bind;
    @BindView(R.id.alipayHead)
    public ImageView alipayHead;
    @BindView(R.id.alipayName)
    public TextView alipayName;
    @BindView(R.id.alipayUid)
    public TextView alipayUid;
    @BindView(R.id.cardView)
    public CardView cardView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_binf_alipay_account);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle("支付宝信息");
    }

    @Override
    protected void onResume() {
        super.onResume();
        new SearchBindAlipayTask(this, this).request();
    }

    @Override
    public void onLoadFinish(BindAlipayModel bindAlipayModel) {
        if ("0".equals(bindAlipayModel.isGrant)) {
            bind.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.GONE);
        } else {
            bind.setVisibility(View.GONE);
            cardView.setVisibility(View.VISIBLE);
            GlideManager.showImage(this, bindAlipayModel.alipayHead, alipayHead);
            alipayName.setText(bindAlipayModel.alipayName);
            alipayUid.setText(bindAlipayModel.alipayAccount);
        }
    }

    @OnClick(R.id.bind)
    public void bind() {
        new BaseService<PayService, AliAuthInfoModel>(this, this) {
            @Override
            public void onSuccess(AliAuthInfoModel aliAuthInfoModel) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(aliAuthInfoModel.url));
                startActivity(intent);
            }

            @Override
            public Observable setObservable(Map<String, RequestBody> data) {
                return service.getAuthInfo(data);
            }
        }.request();
    }
}
