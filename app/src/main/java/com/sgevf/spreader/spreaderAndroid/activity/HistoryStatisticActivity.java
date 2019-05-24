package com.sgevf.spreader.spreaderAndroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.adapter.HistoryStatisicAdapter;
import com.sgevf.spreader.spreaderAndroid.model.HistoryReleaseListModel;
import com.sgevf.spreader.spreaderAndroid.model.HistoryStatisticListModel;
import com.sgevf.spreader.spreaderAndroid.task.HistoryStaticitsTask;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryStatisticActivity extends BaseLoadingActivity<HistoryStatisticListModel> {
    private HistoryReleaseListModel.HistoryReleaseModel model;
    @BindView(R.id.listView)
    ListView list;
    @BindView(R.id.title1)
    TextView title1;
    @BindView(R.id.account)
    TextView account;
    @BindView(R.id.reAccount)
    TextView reAccount;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.reNum)
    TextView reNum;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_history_statistic);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle("统计");
        model = getIntent().getParcelableExtra("historyDetails");
        title1.setText(model.title);
        account.setText(model.amount);
        num.setText(model.maxNumber);
        new HistoryStaticitsTask(this,this).setClass(model.id,model.amount,model.maxNumber).request();

    }

    @Override
    public void onLoadFinish(HistoryStatisticListModel historyStatisticModel) {
        reAccount.setText(historyStatisticModel.reMoney);
        reNum.setText(historyStatisticModel.reNum);
        HistoryStatisicAdapter adapter=new HistoryStatisicAdapter(this,historyStatisticModel.list);
        list.setAdapter(adapter);
    }
}
