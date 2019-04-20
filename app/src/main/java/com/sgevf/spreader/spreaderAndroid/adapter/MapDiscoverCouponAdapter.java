package com.sgevf.spreader.spreaderAndroid.adapter;

import android.content.Context;
import android.view.View;

import com.sgevf.spreader.spreaderAndroid.model.CardListModel;

import java.util.List;

public class MapDiscoverCouponAdapter extends FactoryAdapter<CardListModel.CardManagerModel>{
    public MapDiscoverCouponAdapter(Context context, List<CardListModel.CardManagerModel> items) {
        super(context, items);
    }

    @Override
    protected ViewHolderFactory<CardListModel.CardManagerModel> createFactory(View view) {
        return null;
    }

    @Override
    public int getLayoutResourceId() {
        return 0;
    }
}
