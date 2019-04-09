package com.sgevf.spreader.spreaderAndroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.model.HistoryDetailsModel;
import com.sgevf.spreader.spreaderAndroid.model.WalletHistoryWithdrawDetailsModel;
import com.sgevf.spreader.spreaderAndroid.task.WalletWithdrawDetailsTask;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletHistoryWithdrawDetailsActivity extends BaseLoadingActivity<WalletHistoryWithdrawDetailsModel> {
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
    @BindView(R.id.payee)
    public TextView payee;
    @BindView(R.id.order_number)
    public TextView orderNumber;
    private HistoryDetailsModel.HistoryListModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_wallet_history_withdraw_details);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle(R.string.wallet_history_more_details);
        model = getIntent().getParcelableExtra("model");
        new WalletWithdrawDetailsTask(this, this).setClass(model.id).request();
    }

    @Override
    public void onLoadFinish(WalletHistoryWithdrawDetailsModel model) {
        name.setText("提现");
        money.setText("￥" + model.money);
        takeTime.setText(model.time);
        status.setText(model.status);
        failReason.setText(model.failReason);
        if ("1".equals(model.way)) {
            way.setText("支付宝");
        }
    }
}
