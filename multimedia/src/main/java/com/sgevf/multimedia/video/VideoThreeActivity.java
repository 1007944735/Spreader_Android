package com.sgevf.multimedia.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.sgevf.multimedia.R;

public class VideoThreeActivity extends AppCompatActivity {
    private MediaVideo mediaVideo;
    private IjkVideoView player;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_video_three);
        mediaVideo = findViewById(R.id.mediaVideo);
        player = findViewById(R.id.player);
        StandardVideoController controller = new StandardVideoController(this);
        player.setVideoController(controller);
        player.setUrl("http://47.103.8.72:8080/spreader/video/20190417115413.mp4");
//        player.start(); //开始
//        mediaVideo.setDataSource("http://47.103.8.72:8080/spreader/video/20190417115413.mp4");
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mediaVideo.pause();
        player.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mediaVideo.destory();
        player.release();
    }
}
