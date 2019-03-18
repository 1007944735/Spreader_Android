package com.sgevf.spreader.spreaderAndroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.glide.GlideManager;

import java.util.List;

public class MapDiscoverBottomSheetAdapter extends RecyclerView.Adapter<MapDiscoverBottomSheetAdapter.ViewHolder> {
    private List<String> data;
    private LayoutInflater inflater;
    private Context context;
    private MapDiscoverBottomSheetAdapter.OnItemClickListener onItemClickListener;

    public MapDiscoverBottomSheetAdapter(Context context, List<String> data) {
        this.context=context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=inflater.inflate(R.layout.item_discover_bottom_sheet,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        GlideManager.showImage(context,"http://lorempixel.com/200/200/nature/",viewHolder.expandPicture);
        viewHolder.address.setText("浙江科技学院");
        viewHolder.message.setText("900m | 50人 | 30元");
        viewHolder.duration.setText("2018/2/20 1:00~2019/3/20 12:00");
        viewHolder.advertisement.setText("开业啦，开业啦，全场八折!!!!!");
        viewHolder.box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(viewHolder,i);
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
}
