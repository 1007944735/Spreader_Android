package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseActivity;
import com.sgevf.spreader.spreaderAndroid.glide.GlideManager;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import butterknife.ButterKnife;

public class CustomPhotoActivity extends BaseActivity {

    private ImageView pic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_custom_photo);
        new HeaderView(this).setTitle(R.string.history_release_picture);
        ButterKnife.bind(this);
        pic = findViewById(R.id.pic);
        getSystemPhoto();
    }

    /**
     * 获取手机相册的所有图片
     */
    private void getSystemPhoto() {
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null);
        cursor.moveToNext();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        Glide.with(this).load(path).into(pic);
    }
}
