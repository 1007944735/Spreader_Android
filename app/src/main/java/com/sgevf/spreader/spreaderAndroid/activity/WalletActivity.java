package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.model.UserAccountModel;
import com.sgevf.spreader.spreaderAndroid.task.AccountSelectTask;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WalletActivity extends BaseLoadingActivity<UserAccountModel> {
    @BindView(R.id.balance)
    public TextView balance;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_wallet);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle(R.string.home_slip_wallet);
        new AccountSelectTask(this,this).request();
    }

    @OnClick(R.id.withdraw)
    public void withdraw(View view) {
        startActivity(new Intent(this, WalletWithdrawActivity.class));
    }

    @OnClick(R.id.history_details)
    public void historyDetails(View view) {
        startActivity(new Intent(this, WalletHistoryDetailsActivity.class));
    }

    @Override
    public void onLoadFinish(UserAccountModel userAccountModel) {
        balance.setText(userAccountModel.balance);
    }
}
