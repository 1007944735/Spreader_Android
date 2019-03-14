package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.adapter.HistoryReleaseListAdapter;
import com.sgevf.spreader.spreaderAndroid.model.HistoryReleaseModel;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryReleaseActivity extends BaseLoadingActivity<List<HistoryReleaseModel>> implements AdapterView.OnItemClickListener {
    @BindView(R.id.history)
    public ListView history;
    private HistoryReleaseListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_release);
        new HeaderView(this).setTitle(R.string.history_release);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        history.setOnItemClickListener(this);
        List<HistoryReleaseModel> list=new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add(new HistoryReleaseModel());
        }
        adapter = new HistoryReleaseListAdapter(this, list);
        history.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(this, HistoryReleaseDetailsActivity.class));
    }

    @Override
    public void onLoadFinish(List<HistoryReleaseModel> historyReleaseModels) {
        adapter = new HistoryReleaseListAdapter(this, historyReleaseModels);
        history.setAdapter(adapter);
    }
}
