package com.sgevf.spreader.spreaderAndroid.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.model.CardListModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import utils.DateUtils;

public class RedPacketCouponAdapter extends FactoryAdapter<CardListModel.CardManagerModel> {
    public RedPacketCouponAdapter(Context context, List<CardListModel.CardManagerModel> items) {
        super(context, items);
    }

    @Override
    protected ViewHolderFactory<CardListModel.CardManagerModel> createFactory(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int getCount() {
        return items.size() > 2 ? 2 : items.size();
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.item_red_packet_coupon;
    }

    private class ViewHolder implements ViewHolderFactory<CardListModel.CardManagerModel> {
        TextView rule;
        TextView effectiveTime;

        public ViewHolder(View view) {
            rule = view.findViewById(R.id.rule);
            effectiveTime = view.findViewById(R.id.effective_time);
        }

        @Override
        public void init(CardListModel.CardManagerModel item, int position, FactoryAdapter<CardListModel.CardManagerModel> adapter) {
            rule.setText(item.discountRule);
            effectiveTime.setText("有效时间：" + item.effectiveStartTime + "~" + item.endTime);
        }
    }
}
