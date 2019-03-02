package com.sgevf.spreader.spreaderAndroid.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.model.HistoryDetailsModel;

import java.util.List;

public class WalletHistoryDetailsAdapter extends FactoryAdapter<HistoryDetailsModel> {
    public WalletHistoryDetailsAdapter(Context context, List<HistoryDetailsModel> items) {
        super(context, items);
    }

    @Override
    protected ViewHolderFactory<HistoryDetailsModel> createFactory(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.item_wallet_history_adapter;
    }

    class ViewHolder implements ViewHolderFactory<HistoryDetailsModel> {
        private TextView way;
        private TextView date;
        private TextView money;
        public ViewHolder(View view){
            way=view.findViewById(R.id.way);
            date=view.findViewById(R.id.date);
            money=view.findViewById(R.id.money);
        }
        @Override
        public void init(HistoryDetailsModel item, int position, FactoryAdapter<HistoryDetailsModel> adapter) {

        }
    }
}
