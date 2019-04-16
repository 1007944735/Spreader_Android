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
import com.sgevf.spreader.spreaderAndroid.model.HistoryReleaseListModel;
import com.sgevf.spreader.spreaderAndroid.task.HistoryReleaseTask;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryReleaseActivity extends BaseLoadingActivity<List<HistoryReleaseListModel.HistoryReleaseModel>> implements AdapterView.OnItemClickListener {
    @BindView(R.id.history)
    public ListView history;
    private HistoryReleaseListAdapter adapter;

    private List<HistoryReleaseListModel.HistoryReleaseModel> historyReleaseModels;

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
        new HistoryReleaseTask(this,this).request();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(this, HistoryReleaseDetailsActivity.class).putExtra("historyDetails",historyReleaseModels.get(position)));
    }

    @Override
    public void onLoadFinish(List<HistoryReleaseListModel.HistoryReleaseModel> historyReleaseModels) {
        this.historyReleaseModels=historyReleaseModels;
        adapter = new HistoryReleaseListAdapter(this, historyReleaseModels);
        history.setAdapter(adapter);
    }
}
