package com.sgevf.multimedia.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sgevf.multimedia.R;

public class VideoThreeActivity extends AppCompatActivity {
    private MediaVideo mediaVideo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_video_three);
        mediaVideo = findViewById(R.id.mediaVideo);
        mediaVideo.setDataSource("http://vfx.mtime.cn/Video/2019/03/18/mp4/190318231014076505.mp4");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaVideo.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaVideo.destory();
    }
}
