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
    public static final String FROM_HOME = "home";
    public static final String FROM_USER_CENTER = "user_center";
    public static final String FROM_STATISTIC="statisic";
    @BindView(R.id.history)
    public ListView history;
    private HistoryReleaseListAdapter adapter;

    private List<HistoryReleaseListModel.HistoryReleaseModel> historyReleaseModels;
    private String from;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_release);
        from = getIntent().getStringExtra("from");
        new HeaderView(this).setTitle(R.string.history_release);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        history.setOnItemClickListener(this);
        if (FROM_HOME.equals(from)) {
            new HistoryReleaseTask(this, this).setClass("1").request();
        } else if (FROM_USER_CENTER.equals(from)) {
            new HistoryReleaseTask(this, this).setClass("0").request();
        } else if(FROM_STATISTIC.equals(from)){
            new HistoryReleaseTask(this, this).setClass("0").request();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (FROM_HOME.equals(from)) {
            Intent intent = new Intent();
            intent.putExtra("historyDetails", historyReleaseModels.get(position));
            setResult(1001, intent);
            finish();
        } else if (FROM_USER_CENTER.equals(from)) {
            startActivity(new Intent(this, HistoryReleaseDetailsActivity.class).putExtra("historyDetails", historyReleaseModels.get(position)));
        } else if(FROM_STATISTIC.equals(from)){
            startActivity(new Intent(this, HistoryStatisticActivity.class).putExtra("historyDetails", historyReleaseModels.get(position)));
        }
    }

    @Override
    public void onLoadFinish(List<HistoryReleaseListModel.HistoryReleaseModel> historyReleaseModels) {
        this.historyReleaseModels = historyReleaseModels;
        adapter = new HistoryReleaseListAdapter(this, historyReleaseModels);
        history.setAdapter(adapter);
    }
}
