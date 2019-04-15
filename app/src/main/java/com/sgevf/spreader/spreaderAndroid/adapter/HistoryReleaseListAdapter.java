package com.sgevf.spreader.spreaderAndroid.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

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
        public TextView status;
        public TextView title;
        public TextView info;
        public TextView address;
        public TextView redPacketInfo;
        public TextView time;

        public ViewHolder(View view) {
            status = view.findViewById(R.id.status);
            title = view.findViewById(R.id.title);
            info = view.findViewById(R.id.info);
            address = view.findViewById(R.id.address);
            redPacketInfo = view.findViewById(R.id.redPacketInfo);
            time = view.findViewById(R.id.time);
        }

        @Override
        public void init(HistoryReleaseModel item, int position, FactoryAdapter<HistoryReleaseModel> adapter) {
            if ("-1".equals(item.activiting)) {
                status.setEnabled(false);
                status.setText("已结束");
            } else if ("0".equals(item.activiting)) {
                status.setEnabled(true);
                status.setText("进行中");
            } else if ("1".equals(item.activiting)) {
                status.setEnabled(true);
                status.setText("即将开始");
            }
            title.setText(item.title);
            info.setText(item.info);
            address.setText(item.pubAddress);
            redPacketInfo.setText(item.maxNumber+"人|"+item.amount+"元");
            time.setText(item.pubTime+"~"+item.endTime);
        }
    }
}
