package com.sgevf.spreader.spreaderAndroid.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.model.HistoryReleaseDetailsModel;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import butterknife.ButterKnife;

public class HistoryReleaseDetails extends BaseLoadingActivity<HistoryReleaseDetailsModel> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_history_release_details);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle(R.string.history_release_details);
    }

    @Override
    public void onLoadFinish(HistoryReleaseDetailsModel historyReleaseDetailsModel) {

    }
}
