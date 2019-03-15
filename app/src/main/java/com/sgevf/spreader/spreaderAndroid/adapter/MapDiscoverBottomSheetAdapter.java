package com.sgevf.spreader.spreaderAndroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MapDiscoverBottomSheetAdapter extends RecyclerView.Adapter<MapDiscoverBottomSheetAdapter.ViewHolder> {
    private List<String> data;
    private LayoutInflater inflater;
    public MapDiscoverBottomSheetAdapter(Context context, List<String> data){
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=inflater.inflate(android.R.layout.simple_expandable_list_item_1,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.text1.setText(data.get(i));
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView text1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text1=itemView.findViewById(android.R.id.text1);
        }
    }
}
