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
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletHistoryDetailsActivity extends BaseLoadingActivity<List<HistoryDetailsModel>> implements AdapterView.OnItemClickListener {
    @BindView(R.id.history)
    public ListView history;

    private WalletHistoryDetailsAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_wallet_history_details);
        new HeaderView(this).setTitle(R.string.wallet_history_details);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        history.setOnItemClickListener(this);
        List<HistoryDetailsModel> list=new ArrayList<>();
        for(int i=0;i<20;i++){
            list.add(new HistoryDetailsModel());
        }
        adapter=new WalletHistoryDetailsAdapter(this,list);
        history.setAdapter(adapter);
    }

    @Override
    public void onLoadFinish(List<HistoryDetailsModel> historyDetailsModels) {
        adapter=new WalletHistoryDetailsAdapter(this,historyDetailsModels);
        history.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(this, WalletHistoryMoreDetailsActivity.class));
    }
}
