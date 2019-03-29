package com.sgevf.spreader.spreaderAndroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.glide.GlideManager;
import com.sgevf.spreader.spreaderAndroid.model.MapRedResultModels;

import java.util.ArrayList;
import java.util.List;

public class MapDiscoverBottomSheetAdapter extends RecyclerView.Adapter<MapDiscoverBottomSheetAdapter.ViewHolder> {
    private List<MapRedResultModels.MapRedResultModel> data;
    private LayoutInflater inflater;
    private Context context;
    private MapDiscoverBottomSheetAdapter.OnItemClickListener onItemClickListener;

    public MapDiscoverBottomSheetAdapter(Context context, List<MapRedResultModels.MapRedResultModel> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data == null ? new ArrayList<MapRedResultModels.MapRedResultModel>() : data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_discover_bottom_sheet, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        GlideManager.showImage(context, data.get(i).image1Url, viewHolder.expandPicture);
        viewHolder.address.setText(data.get(i).title);
        viewHolder.message.setText(data.get(i).distance + "M | " + data.get(i).maxNumber + "人 | " + data.get(i).amount + "元");
        viewHolder.duration.setText(data.get(i).startTime + "~" + data.get(i).endTime);
        viewHolder.advertisement.setText(data.get(i).info);
        viewHolder.box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(viewHolder, i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout box;
        ImageView expandPicture;
        TextView address;
        TextView message;
        TextView duration;
        TextView advertisement;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            box = itemView.findViewById(R.id.box);
            expandPicture = itemView.findViewById(R.id.expand_picture);
            address = itemView.findViewById(R.id.address);
            message = itemView.findViewById(R.id.message);
            duration = itemView.findViewById(R.id.duration);
            advertisement = itemView.findViewById(R.id.advertisement);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(MapDiscoverBottomSheetAdapter.ViewHolder viewHolder, int position);
    }

    public void setOnItemClickListener(MapDiscoverBottomSheetAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(List<MapRedResultModels.MapRedResultModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
