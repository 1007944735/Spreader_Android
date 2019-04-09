package com.sgevf.spreader.spreaderAndroid.adapter;

import android.content.Context;
import android.content.IntentFilter;
import android.view.View;
import android.widget.TextView;

import com.sgevf.multimedia.video.MediaVideo;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.model.HistoryDetailsModel;

import java.util.List;

public class WalletHistoryDetailsAdapter extends FactoryAdapter<HistoryDetailsModel.HistoryListModel> {
    public WalletHistoryDetailsAdapter(Context context, List<HistoryDetailsModel.HistoryListModel> items) {
        super(context, items);
    }

    @Override
    protected ViewHolderFactory<HistoryDetailsModel.HistoryListModel> createFactory(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.item_wallet_history_adapter;
    }

    class ViewHolder implements ViewHolderFactory<HistoryDetailsModel.HistoryListModel> {
        private TextView way;
        private TextView date;
        private TextView money;

        public ViewHolder(View view) {
            way = view.findViewById(R.id.way);
            date = view.findViewById(R.id.date);
            money = view.findViewById(R.id.money);
        }

        @Override
        public void init(HistoryDetailsModel.HistoryListModel item, int position, FactoryAdapter<HistoryDetailsModel.HistoryListModel> adapter) {
            if("1".equals(item.type)){
                way.setText("红包");
                money.setText("+"+item.money+"元");
            }else if("0".equals(item.type)){
                way.setText("提现");
                money.setText("-"+item.money+"元");
            }
            date.setText(item.time);
        }
    }
}
