package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.adapter.WalletHistoryDetailsAdapter;
import com.sgevf.spreader.spreaderAndroid.model.HistoryDetailsModel;
import com.sgevf.spreader.spreaderAndroid.task.WalletDetailedHistoryTask;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletHistoryDetailsActivity extends BaseLoadingActivity<HistoryDetailsModel> implements AdapterView.OnItemClickListener {
    @BindView(R.id.history)
    public ListView history;

    private WalletHistoryDetailsAdapter adapter;
    private ArrayList<HistoryDetailsModel.HistoryListModel> models;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_wallet_history_details);
        new HeaderView(this).setTitle(R.string.wallet_history_details);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        new WalletDetailedHistoryTask(this, this).request();
        history.setOnItemClickListener(this);
    }

    @Override
    public void onLoadFinish(HistoryDetailsModel historyDetailsModel) {
        this.models = historyDetailsModel.list;
        adapter = new WalletHistoryDetailsAdapter(this, historyDetailsModel.list);
        history.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if ("0".equals(models.get(position).type)) {
            //提现详情
            startActivity(new Intent(this, WalletHistoryWithdrawDetailsActivity.class).putExtra("id", models.get(position).id).putExtra("from",WalletHistoryWithdrawDetailsActivity.FROM_RECODE));
        }else if("1".equals(models.get(position).type)){
            //红包详情
            startActivity(new Intent(this, WalletHistoryRedPacketDetailsActivity.class).putExtra("model", models.get(position)));
        }
    }
}
