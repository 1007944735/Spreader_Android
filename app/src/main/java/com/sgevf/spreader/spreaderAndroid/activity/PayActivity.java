package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alipay.sdk.app.EnvUtils;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.model.PubOrderModel;
import com.sgevf.spreader.spreaderAndroid.task.PubOrderTask;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.AppAlipayUtil;

public class PayActivity extends BaseLoadingActivity<PubOrderModel> {
    @BindView(R.id.money)
    public TextView money;
    @BindView(R.id.pay_ali)
    public LinearLayout payAli;
    @BindView(R.id.ali)
    public RadioButton ali;
    @BindView(R.id.pay)
    public Button pay;
    private String redPacketId;
    private String amount;
    private String order;
    private PubOrderModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pub_pay);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle("支付");
        redPacketId = getIntent().getStringExtra("redPacketId");
        amount = getIntent().getStringExtra("amount");
        order = getIntent().getStringExtra("order");
        new PubOrderTask(this, this).setClass(amount, redPacketId, order == null ? "" : order).request();
    }

    @Override
    public void onLoadFinish(PubOrderModel pubOrderModel) {
        this.model = pubOrderModel;
        money.setText("￥" + pubOrderModel.amount);
    }

    @OnClick(R.id.pay)
    public void pay() {
        new AppAlipayUtil(this, AppAlipayUtil.SDK_PAY_FLAG).pay(model.orderString).setCallback(new AppAlipayUtil.AliResultCallback() {

            @Override
            public void success(Object obj) {
                startActivity(new Intent(PayActivity.this, HomeActivity.class));
            }

            @Override
            public void error(String errorCode) {

            }
        });
    }
}
