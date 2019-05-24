package com.sgevf.spreader.spreaderAndroid.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.model.HistoryStatisticListModel;

import java.util.List;

public class HistoryStatisicAdapter extends FactoryAdapter<HistoryStatisticListModel.HistoryStatisticModel>{
    List<HistoryStatisticListModel.HistoryStatisticModel> items;
    public HistoryStatisicAdapter(Context context, List<HistoryStatisticListModel.HistoryStatisticModel> items) {
        super(context, items);
        this.items=items;
    }

    @Override
    protected ViewHolderFactory createFactory(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.item_history_statisic;
    }


    private class ViewHolder implements ViewHolderFactory {
        TextView name;
        TextView money;
        TextView time;
        public ViewHolder(View view) {
            name=view.findViewById(R.id.name);
            money=view.findViewById(R.id.money);
            time=view.findViewById(R.id.time);
        }

        @Override
        public void init(Object item, int position, FactoryAdapter adapter) {
            name.setText(items.get(position).name);
            money.setText(items.get(position).account);
            time.setText(items.get(position).time);
        }
    }
}
