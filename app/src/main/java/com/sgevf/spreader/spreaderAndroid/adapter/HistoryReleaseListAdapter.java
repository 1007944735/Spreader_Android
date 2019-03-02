package com.sgevf.spreader.spreaderAndroid.adapter;

import android.content.Context;
import android.view.View;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.model.HistoryReleaseModel;

import java.util.List;

public class HistoryReleaseListAdapter extends FactoryAdapter<HistoryReleaseModel> {
    public HistoryReleaseListAdapter(Context context, List<HistoryReleaseModel> items) {
        super(context, items);
    }

    @Override
    protected ViewHolderFactory<HistoryReleaseModel> createFactory(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.item_history_release_list;
    }

    class ViewHolder implements ViewHolderFactory<HistoryReleaseModel> {
        public ViewHolder(View view) {
        }

        @Override
        public void init(HistoryReleaseModel item, int position, FactoryAdapter<HistoryReleaseModel> adapter) {

        }
    }
}
