package com.sgevf.spreader.spreaderAndroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.services.route.BusStep;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.WalkStep;
import com.sgevf.spreader.spreaderAndroid.R;

import java.util.ArrayList;
import java.util.List;

public class MapRoutesPathAdapter<T> extends RecyclerView.Adapter<MapRoutesPathAdapter.ViewHolder> {
    private Context context;
    private List<T> datas;

    public MapRoutesPathAdapter(Context context) {
        this(context, new ArrayList<T>());
    }

    public MapRoutesPathAdapter(Context context, List<T> datas) {
        this.context = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_map_route_path, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        T item = datas.get(i);
        if (i == 0) {
            viewHolder.dirIcon.setImageResource(R.mipmap.icon_dir_start);
            viewHolder.lineName.setText("出发");
            viewHolder.dirUp.setVisibility(View.INVISIBLE);
            viewHolder.dirDown.setVisibility(View.VISIBLE);
            viewHolder.splitLine.setVisibility(View.INVISIBLE);
        } else if (i == datas.size() - 1) {
            viewHolder.dirIcon.setImageResource(R.mipmap.icon_dir_end);
            viewHolder.lineName.setText("到达终点");
            viewHolder.dirUp.setVisibility(View.VISIBLE);
            viewHolder.dirDown.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.splitLine.setVisibility(View.VISIBLE);
            viewHolder.dirUp.setVisibility(View.VISIBLE);
            viewHolder.dirDown.setVisibility(View.VISIBLE);
            if (item instanceof WalkStep) {
                String actionName = ((WalkStep) item).getAction();
                int resID = getWalkActionID(actionName);
                viewHolder.dirIcon.setImageResource(resID);
                viewHolder.lineName.setText(((WalkStep) item).getInstruction());
            } else if (item instanceof DriveStep) {
                String actionName = ((DriveStep) item).getAction();
                int resID = getWalkActionID(actionName);
                viewHolder.dirIcon.setImageResource(resID);
                viewHolder.lineName.setText(((DriveStep) item).getInstruction());
            } else if (item instanceof BusStep) {
            }

        }
    }

    @Override
    public int getItemCount() {
        return datas != null ? datas.size() : 0;
    }

    public void addData(List<T> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView lineName;
        ImageView dirIcon;
        ImageView dirUp;
        ImageView dirDown;
        ImageView splitLine;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lineName = itemView.findViewById(R.id.bus_line_name);
            dirIcon = itemView.findViewById(R.id.bus_dir_icon);
            dirUp = itemView.findViewById(R.id.bus_dir_icon_up);
            dirDown = itemView.findViewById(R.id.bus_dir_icon_down);
            splitLine = itemView.findViewById(R.id.bus_seg_split_line);
        }
    }

    public static int getWalkActionID(String actionName) {
        if (actionName == null || actionName.equals("")) {
            return R.mipmap.dir13;
        }
        if ("左转".equals(actionName)) {
            return R.mipmap.dir2;
        }
        if ("右转".equals(actionName)) {
            return R.mipmap.dir1;
        }
        if ("向左前方".equals(actionName) || "靠左".equals(actionName) || actionName.contains("向左前方")) {
            return R.mipmap.dir6;
        }
        if ("向右前方".equals(actionName) || "靠右".equals(actionName) || actionName.contains("向右前方")) {
            return R.mipmap.dir5;
        }
        if ("向左后方".equals(actionName) || actionName.contains("向左后方")) {
            return R.mipmap.dir7;
        }
        if ("向右后方".equals(actionName) || actionName.contains("向右后方")) {
            return R.mipmap.dir8;
        }
        if ("直行".equals(actionName)) {
            return R.mipmap.dir3;
        }
        if ("通过人行横道".equals(actionName)) {
            return R.mipmap.dir9;
        }
        if ("通过过街天桥".equals(actionName)) {
            return R.mipmap.dir11;
        }
        if ("通过地下通道".equals(actionName)) {
            return R.mipmap.dir10;
        }

        return R.mipmap.dir13;
    }
}
