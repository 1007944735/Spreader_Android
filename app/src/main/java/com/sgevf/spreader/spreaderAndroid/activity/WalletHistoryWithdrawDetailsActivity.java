package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.model.CashWithdrawModel;
import com.sgevf.spreader.spreaderAndroid.model.WalletHistoryWithdrawDetailsModel;
import com.sgevf.spreader.spreaderAndroid.task.QueryStatusTask;
import com.sgevf.spreader.spreaderAndroid.task.WalletWithdrawDetailsTask;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalletHistoryWithdrawDetailsActivity extends BaseLoadingActivity<WalletHistoryWithdrawDetailsModel> {
    public static final String FROM_WALLET = "wallet";
    public static final String FROM_RECODE = "recode";
    @BindView(R.id.name)
    public TextView name;
    @BindView(R.id.money)
    public TextView money;
    @BindView(R.id.way)
    public TextView way;
    @BindView(R.id.take_time)
    public TextView takeTime;
    @BindView(R.id.status)
    public TextView status;
    @BindView(R.id.failReason)
    public TextView failReason;
    @BindView(R.id.order)
    public TextView order;
    @BindView(R.id.alipayOrder)
    public TextView alipayOrder;
    @BindView(R.id.refresh)
    public TextView refresh;

    private int id;
    private String outBizNo;
    private String from;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_wallet_history_withdraw_details);
        ButterKnife.bind(this);
        from = getIntent().getStringExtra("from");
        new HeaderView(this).setTitle(R.string.wallet_history_more_details).setBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FROM_WALLET.equals(from)) {
                    Intent intent = new Intent(WalletHistoryWithdrawDetailsActivity.this, WalletActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else if (FROM_RECODE.equals(from)) {
                    finish();
                }
            }
        });
        id = getIntent().getIntExtra("id", 0);
        new WalletWithdrawDetailsTask(this, this).setClass(id).request();
    }

    @Override
    public void onLoadFinish(WalletHistoryWithdrawDetailsModel model) {
        this.outBizNo = model.withdrawOrder;
        name.setText("提现");
        money.setText("￥" + model.money);
        takeTime.setText(model.time);
        if ("1".equals(model.status)) {
            status.setText("转账成功");
        } else if ("-1".equals(model.status)) {
            status.setText("转账失败");
            failReason.setVisibility(View.VISIBLE);
            failReason.setText(model.failReason);
        } else {
            status.setText("转账中");
            refresh.setVisibility(View.VISIBLE);
        }
        if ("1".equals(model.way)) {
            way.setText("支付宝");
        }
        order.setText(model.withdrawOrder);
        alipayOrder.setText(model.alipayOrder);
    }

    @OnClick(R.id.refresh)
    public void refresh() {
        if (outBizNo != null) {
            new QueryStatusTask(this, this).setClass(outBizNo).request();
        }
    }

    public void queryResult(CashWithdrawModel model) {
        if ("SUCCESS".equals(model.status)) {
            refresh.setVisibility(View.GONE);
            status.setText("转账成功");
        } else if ("FAIL".equals(model.status)) {
            refresh.setVisibility(View.GONE);
            status.setText("转账失败");
            failReason.setVisibility(View.VISIBLE);
            failReason.setText(model.failReason);
        }
    }

    @Override
    public void onBackPressed() {
        if (FROM_WALLET.equals(from)) {
            Intent intent = new Intent(WalletHistoryWithdrawDetailsActivity.this, WalletActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (FROM_RECODE.equals(from)) {
            super.onBackPressed();
        }
    }
}
