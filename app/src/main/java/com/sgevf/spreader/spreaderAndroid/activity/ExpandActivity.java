package com.sgevf.spreader.spreaderAndroid.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sgevf.multimedia.utils.TimeUtils;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseActivity;
import com.sgevf.spreader.spreaderAndroid.glide.GlideManager;
import com.sgevf.spreader.spreaderAndroid.model.ExpandPhotoModel;
import com.sgevf.spreader.spreaderAndroid.model.ExpandVideoModel;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.AndroidBugsSolution;

public class ExpandActivity extends BaseActivity {
    @BindView(R.id.floatButton)
    public FloatingActionButton floatButton;
    @BindView(R.id.input)
    public EditText input;
    @BindView(R.id.gridLayout)
    public GridLayout gridLayout;
    @BindView(R.id.videoBox)
    public LinearLayout videoBox;

    private int gridLayoutWidth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_expand);
        ButterKnife.bind(this);
        AndroidBugsSolution.assistActivity(this, null);
        new HeaderView(this).setTitle(R.string.history_release);

    }

    @OnClick(R.id.floatButton)
    public void popMore(View view) {
        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        dialog.setContentView(R.layout.dialog_expand_more);
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(p);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
        dialog.show();
        dialog.findViewById(R.id.picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ExpandActivity.this, CustomPhotoActivity.class), 1000);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ExpandActivity.this, CustomVideoActivity.class), 2000);
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 1001 && data != null) {
            List<ExpandPhotoModel> pictures = data.getParcelableArrayListExtra("pictures");
            gridLayoutWidth = gridLayout.getWidth();
            gridLayout.removeAllViews();
            for (ExpandPhotoModel model : pictures) {
                createImageView(false, model.path);
            }
            createImageView(true,null);
        } else if (requestCode == 2000 && resultCode == 2001 && data != null) {
            ExpandVideoModel video = data.getParcelableExtra("video");
            videoBox.removeAllViews();
            View view = LayoutInflater.from(this).inflate(R.layout.layout_video_expand, videoBox, true);
            ImageView thumbVideo = view.findViewById(R.id.thumbVideo);
            TextView duration = view.findViewById(R.id.duration);
            ImageView exit = view.findViewById(R.id.exit);
            GlideManager.showImage(this, video.path, thumbVideo);
            duration.setText(TimeUtils.formatTime(video.duration));
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoBox.removeAllViews();
                }
            });
        }
    }

    /**
     * 创建imageView
     */
    private void createImageView(boolean first, String url) {
        final FrameLayout frameLayout = new FrameLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((gridLayoutWidth - 6 * 5) / 3, (gridLayoutWidth - 6 * 5) / 3);
        params.setMargins(5, 5, 5, 5);
        frameLayout.setLayoutParams(params);

        ImageView imageView = new ImageView(this);
        FrameLayout.LayoutParams ivParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(ivParams);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if(first) {
            imageView.setImageResource(R.mipmap.icon_photo_more);
            imageView.setBackgroundResource(R.drawable.bg_photo_more);
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(ExpandActivity.this, CustomPhotoActivity.class), 1000);
                }
            });
        }else {
            GlideManager.showImage(this, url, imageView);
        }
        frameLayout.addView(imageView);

        if (!first) {
            ImageView exit = new ImageView(this);
            FrameLayout.LayoutParams eParams = new FrameLayout.LayoutParams(40, 40);
            eParams.gravity = Gravity.END;
            exit.setImageResource(R.drawable.bg_expand_resource_exit);
            exit.setLayoutParams(eParams);
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(gridLayout.getChildCount()>2) {
                        gridLayout.removeView(frameLayout);
                    }else {
                        gridLayout.removeAllViews();
                    }
                }
            });
            frameLayout.addView(exit);
        }
        gridLayout.addView(frameLayout);
    }
}
