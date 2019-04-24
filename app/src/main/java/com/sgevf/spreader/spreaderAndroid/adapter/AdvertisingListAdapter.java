package com.sgevf.spreader.spreaderAndroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.glide.GlideManager;
import com.sgevf.spreader.spreaderAndroid.map.MapDiscoverActivity;
import com.sgevf.spreader.spreaderAndroid.model.HomeAdvertisingListModel;

import java.util.List;

public class AdvertisingListAdapter extends RecyclerView.Adapter<AdvertisingListAdapter.ViewHolder> {
    private Context context;
    private List<HomeAdvertisingListModel.HomeAdvertingModel> list;

    public AdvertisingListAdapter(Context context, List<HomeAdvertisingListModel.HomeAdvertingModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_advertising, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        GlideManager.roundImage(context, list.get(i).image1Url, viewHolder.image, 8f);
        viewHolder.address.setText(list.get(i).title);
        viewHolder.advertisement.setText(list.get(i).info);
        viewHolder.amount.setText(list.get(i).amount + "");
        viewHolder.maxNumber.setText(list.get(i).maxNumber + "");
        viewHolder.duration.setText(list.get(i).startTime + "~" + list.get(i).endTime);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, MapDiscoverActivity.class).putExtra("model", list.get(i)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView address;
        TextView advertisement;
        TextView amount;
        TextView maxNumber;
        TextView duration;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
            image = itemView.findViewById(R.id.image);
            address = itemView.findViewById(R.id.address);
            advertisement = itemView.findViewById(R.id.advertisement);
            amount = itemView.findViewById(R.id.amount);
            maxNumber = itemView.findViewById(R.id.maxNumber);
            duration = itemView.findViewById(R.id.duration);
        }

    }
}
