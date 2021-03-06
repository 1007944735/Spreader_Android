package com.sgevf.spreader.spreaderAndroid.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.model.HistoryDetailsModel;
import com.sgevf.spreader.spreaderAndroid.model.WalletHistoryRedPacketDetailsModel;
import com.sgevf.spreader.spreaderAndroid.task.WalletRedPacketDetailsTask;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletHistoryRedPacketDetailsActivity extends BaseLoadingActivity<WalletHistoryRedPacketDetailsModel> {
    @BindView(R.id.name)
    public TextView name;
    @BindView(R.id.money)
    public TextView money;
    @BindView(R.id.take_time)
    public TextView takeTime;
    private HistoryDetailsModel.HistoryListModel model;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_wallet_history_red_packet_details);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle(R.string.wallet_history_more_details);
        model = getIntent().getParcelableExtra("model");
        new WalletRedPacketDetailsTask(this,this).setClass(model.id).request();
    }

    @Override
    public void onLoadFinish(WalletHistoryRedPacketDetailsModel model) {
        SpannableString spannableString=SpannableString.valueOf("来自"+model.nickname+"的红包");
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#49afcd")),2,2+model.nickname.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        name.setText(spannableString);
        money.setText("￥"+model.robMoney);
        takeTime.setText(model.robTime);
    }
}
