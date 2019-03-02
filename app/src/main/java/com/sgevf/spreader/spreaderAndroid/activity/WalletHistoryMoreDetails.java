package com.sgevf.spreader.spreaderAndroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.model.WalletHistoryMoreDetailsModel;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import butterknife.ButterKnife;

public class WalletHistoryMoreDetails extends BaseLoadingActivity<WalletHistoryMoreDetailsModel> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_wallet_history_more_details);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle(R.string.wallet_history_more_details);
    }

    @Override
    public void onLoadFinish(WalletHistoryMoreDetailsModel walletHistoryMoreDetailsModel) {

    }
}
