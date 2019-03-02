package com.sgevf.spreader.spreaderAndroid.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.amap.api.services.help.Tip;
import com.sgevf.spreader.spreaderAndroid.R;

import java.util.List;

/**
 * 输入提示adapter，展示item名称和地址
 * Created by ligen on 16/11/25.
 */
public class InputTipsAdapter extends FactoryAdapter<Tip> {
    public InputTipsAdapter(Context context, List<Tip> items) {
        super(context, items);
    }

    @Override
    protected ViewHolderFactory<Tip> createFactory(View view) {
        return new ViewHolder(view);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.item_inputtips;
    }

    class ViewHolder implements ViewHolderFactory<Tip> {
        TextView name;
        TextView address;
        View divider;

        public ViewHolder(View view) {
            name = view.findViewById(R.id.name);
            address = view.findViewById(R.id.address);
            divider = view.findViewById(R.id.divider);
        }

        @Override
        public void init(Tip item, int position, FactoryAdapter<Tip> adapter) {
            name.setText(item.getName());
            if (!TextUtils.isEmpty(item.getAddress())) {
                address.setVisibility(View.VISIBLE);
                address.setText(item.getAddress());
            }else {
                address.setVisibility(View.GONE);
            }
            if (position == adapter.getCount() - 1) {
                divider.setVisibility(View.GONE);
            }else {
                divider.setVisibility(View.VISIBLE);
            }
        }
    }
}
