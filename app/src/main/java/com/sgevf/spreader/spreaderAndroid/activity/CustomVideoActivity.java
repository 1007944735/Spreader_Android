package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseActivity;
import com.sgevf.spreader.spreaderAndroid.adapter.CustomVideoAdapter;
import com.sgevf.spreader.spreaderAndroid.model.ExpandVideoModel;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomVideoActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.list)
    public ListView list;
    private List<ExpandVideoModel> videos;
    private CustomVideoAdapter adapter;
    private int lastPosition = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_custom_video);
        ButterKnife.bind(this);
        new HeaderView(this)
                .setTitle(R.string.history_release_video)
                .setRight(R.string.user_center_complete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        int i = list.getCheckedItemPosition();
//                        Toast.makeText(CustomVideoActivity.this, "" + i, Toast.LENGTH_SHORT).show();
                        intent.putExtra("video", videos.get(i));
                        setResult(2001, intent);
                        finish();
                    }
                });
        init();
    }

    private void init() {
        list.setOnItemClickListener(this);
        videos = getSystemVideo();
        adapter = new CustomVideoAdapter(this, videos);
        list.setAdapter(adapter);
    }

    /**
     * 获取系统的视频
     *
     * @return
     */
    private List<ExpandVideoModel> getSystemVideo() {
        List<ExpandVideoModel> list = new ArrayList<>();
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null,null);
        if (cursor == null) {
            return list;
        }
        while (cursor.moveToNext()) {
            //不能显示全部视频的缩略图
//            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
//            Cursor thumbCursor = getContentResolver().query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, null, MediaStore.Video.Thumbnails.VIDEO_ID+"=?", new String[]{"" + id}, null);
//            if (thumbCursor.moveToFirst()) {
//                ExpandVideoModel model = new ExpandVideoModel();
//                model.path = thumbCursor.getString(thumbCursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA));
//                list.add(model);
//            }
//            thumbCursor.close();
            ExpandVideoModel model = new ExpandVideoModel();
            model.name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
            model.path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
            model.duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
            model.createTime = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED));
            list.add(model);
        }
        cursor.close();
        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CheckedTextView ctv = view.findViewById(R.id.check);
        if (lastPosition == -1) {
            lastPosition = position;
        } else if (lastPosition != position) {
            ((CheckedTextView) list.getChildAt(lastPosition).findViewById(R.id.check)).toggle();
            lastPosition = position;
        } else {
            lastPosition = -1;
        }
        ctv.toggle();
    }
}
