package com.sgevf.spreader.spreaderAndroid.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.sgevf.spreader.spreaderAndroid.R;

import java.util.List;

public class LocationResultAdapter extends FactoryAdapter<PoiItem> {


    public LocationResultAdapter(Context context) {
        super(context);
    }

    public LocationResultAdapter(Context context, List<PoiItem> items) {
        super(context, items);
    }

    @Override
    protected ViewHolderFactory<PoiItem> createFactory(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.item_location_result;
    }

    class ViewHolder implements ViewHolderFactory<PoiItem> {
        TextView name;
        TextView address;
        View divider;

        public ViewHolder(View view) {
            name = view.findViewById(R.id.name);
            address = view.findViewById(R.id.address);
            divider = view.findViewById(R.id.divider);
        }

        @Override
        public void init(PoiItem item, int position, FactoryAdapter<PoiItem> adapter) {
            name.setText(item.getTitle());
            if (!TextUtils.isEmpty(item.getTitle())) {
                address.setVisibility(View.VISIBLE);
                address.setText(item.getCityName() + item.getAdName() + item.getSnippet());
            } else {
                address.setVisibility(View.GONE);
            }
            if (position == adapter.getCount() - 1) {
                divider.setVisibility(View.GONE);
            } else {
                divider.setVisibility(View.VISIBLE);
            }
        }
    }
}
