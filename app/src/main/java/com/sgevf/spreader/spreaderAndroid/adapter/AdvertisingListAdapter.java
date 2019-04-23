package com.sgevf.spreader.spreaderAndroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.glide.GlideManager;
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        GlideManager.roundImage(context, list.get(i).image1Url, viewHolder.image, 8f);
        viewHolder.address.setText(list.get(i).title);
        viewHolder.advertisement.setText(list.get(i).info);
        viewHolder.message.setText(list.get(i).maxNumber + "人|" + list.get(i).amount + "元");
        viewHolder.duration.setText(list.get(i).startTime + "~" + list.get(i).endTime);

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView address;
        TextView advertisement;
        TextView message;
        TextView duration;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            address = itemView.findViewById(R.id.address);
            advertisement = itemView.findViewById(R.id.advertisement);
            message = itemView.findViewById(R.id.message);
            duration = itemView.findViewById(R.id.duration);
        }


    }
}
