package com.sgevf.spreader.spreaderAndroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.glide.GlideManager;
import com.sgevf.spreader.spreaderAndroid.model.ExpandPhotoModel;

import java.util.ArrayList;
import java.util.List;

import utils.WindowHelper;

public class CustomPhotoAdapter extends RecyclerView.Adapter<CustomPhotoAdapter.ViewHolder> {
    private List<ExpandPhotoModel> list;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private ArrayList<ExpandPhotoModel> pictures;

    public CustomPhotoAdapter(Context context, List<ExpandPhotoModel> list) {
        this.list = list;
        this.context = context;
        pictures = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_custom_photo, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        ViewGroup.LayoutParams params = viewHolder.picture.getLayoutParams();
        params.height = WindowHelper.getScreenWidth(context) / 4;
        viewHolder.picture.setLayoutParams(params);
        if (i == 0) {
            viewHolder.picture.setImageResource(R.mipmap.icon_photo_camera);
            viewHolder.picture.setScaleType(ImageView.ScaleType.CENTER);
            viewHolder.selected.setVisibility(View.GONE);
        } else {
            GlideManager.showImage(context, list.get(i - 1).path, viewHolder.picture);
            viewHolder.picture.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewHolder.selected.setVisibility(View.VISIBLE);
        }
        viewHolder.picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(viewHolder, i, i == 0 ? "" : list.get(i - 1).path);
                }
            }
        });
        viewHolder.selected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pictures.add(list.get(i - 1));
                } else {
                    pictures.remove(list.get(i - 1));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 1 : list.size() + 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public CheckBox selected;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.picture);
            selected = itemView.findViewById(R.id.selected);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(ViewHolder viewHolder, int position, String url);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ArrayList<ExpandPhotoModel> getPictures() {
        return pictures;
    }

}
