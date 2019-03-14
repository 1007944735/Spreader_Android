package com.sgevf.spreader.spreaderAndroid.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgevf.multimedia.utils.TimeUtils;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.glide.GlideManager;
import com.sgevf.spreader.spreaderAndroid.model.ExpandVideoModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CustomVideoAdapter extends FactoryAdapter<ExpandVideoModel> {
    private Context context;

    public CustomVideoAdapter(Context context, List<ExpandVideoModel> items) {
        super(context, items);
        this.context = context;
    }

    @Override
    protected ViewHolderFactory<ExpandVideoModel> createFactory(View view) {
        return new VH(view);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.item_custom_video;
    }


    class VH implements ViewHolderFactory<ExpandVideoModel> {
        ImageView thumbVideo;
        TextView duration;
        TextView name;
        TextView createTime;
        CheckedTextView check;

        public VH(View view) {
            thumbVideo = view.findViewById(R.id.thumbVideo);
            duration = view.findViewById(R.id.duration);
            name = view.findViewById(R.id.name);
            createTime = view.findViewById(R.id.createTime);
            check = view.findViewById(R.id.check);
        }

        @Override
        public void init(ExpandVideoModel item, int position, FactoryAdapter<ExpandVideoModel> adapter) {
            GlideManager.showImage(context, item.path, thumbVideo);
            duration.setText(TimeUtils.formatTime(item.duration));
            name.setText(item.name);
            createTime.setText(long2String(item.createTime));
        }

        private String long2String(long curTime){
            String formatType="yyyy年MM月dd日 HH:mm";
            Date date=new Date(curTime);
            return new SimpleDateFormat(formatType).format(date);
        }
    }
}
