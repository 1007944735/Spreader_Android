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

public class MapDiscoverCouponAdapter extends FactoryAdapter<CardListModel.CardManagerModel> {
    public MapDiscoverCouponAdapter(Context context, List<CardListModel.CardManagerModel> items) {
        super(context, items);
    }

    @Override
    protected ViewHolderFactory<CardListModel.CardManagerModel> createFactory(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.item_map_discover_coupon;
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
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.NORMAL);
                Date date = null;
                date = sdf.parse(item.effectiveTime);
                StringBuffer sb = new StringBuffer();
                if (date.getDay() != 0) {
                    sb.append(date.getDay() + "天");
                }
                if (date.getHours() != 0) {
                    sb.append(date.getHours() + "时");
                }
                if (date.getMinutes() != 0) {
                    sb.append(date.getMonth() + "分");
                }
                effectiveTime.setText("有效时间：" + sb.toString() + "最迟活动结束之前");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
