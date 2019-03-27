package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.sgevf.multimedia.camera.CameraActivity;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseActivity;
import com.sgevf.spreader.spreaderAndroid.adapter.CustomPhotoAdapter;
import com.sgevf.spreader.spreaderAndroid.adapter.DividerGridItemDecoration;
import com.sgevf.spreader.spreaderAndroid.model.ExpandPhotoModel;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomPhotoActivity extends BaseActivity {
    @BindView(R.id.recycler)
    public RecyclerView recycler;
    private CustomPhotoAdapter adapter;
    private GridLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_custom_photo);
        new HeaderView(this)
                .setTitle(R.string.history_release_picture)
                .setRight(R.string.user_center_complete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putParcelableArrayListExtra("pictures", adapter.getPictures());
                        setResult(1001, intent);
                        finish();
                    }
                });
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        layoutManager = new GridLayoutManager(this, 4);
        recycler.setLayoutManager(layoutManager);
        recycler.addItemDecoration(new DividerGridItemDecoration(this));
        adapter = new CustomPhotoAdapter(this, getSystemPhoto());
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new CustomPhotoAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(CustomPhotoAdapter.ViewHolder viewHolder, int position, String url) {
                if (position == 0) {
                    startActivity(new Intent(CustomPhotoActivity.this, CameraActivity.class));
                } else {
                    startActivityForResult(new Intent(CustomPhotoActivity.this, SinglePictureDetailActivity.class)
                            .putExtra("url", url)
                            .putExtra("position", position)
                            .putExtra("isCheck", viewHolder.selected.isChecked()), 1000);
                }
            }
        });
    }

    /**
     * 获取手机相册的所有图片
     */
    private List<ExpandPhotoModel> getSystemPhoto() {
        List<ExpandPhotoModel> list = new ArrayList<>();
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,null);
        while (cursor.moveToNext()) {
            ExpandPhotoModel model = new ExpandPhotoModel();
            model.path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            Log.d("pathPhoto", model.path);
            list.add(model);
        }
        cursor.close();
        return list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 1001 && data != null) {
            int p = data.getIntExtra("position", -1);
            boolean icCheck = data.getBooleanExtra("isCheck", false);
            ((CheckBox) layoutManager.findViewByPosition(p).findViewById(R.id.selected)).setChecked(icCheck);
        }
    }
}
