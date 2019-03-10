package com.sgevf.multimedia.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import com.sgevf.multimedia.R;

public class VideoOneActivity extends AppCompatActivity {
    private static final String url="http://vfx.mtime.cn/Video/2019/03/01/mp4/190301141332024373.mp4";
    private VideoView videoView;
    private MediaController mediaController;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_video_one);
        videoView=findViewById(R.id.videoView);
        videoView.setVideoPath(url);
        mediaController=new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.requestFocus();
    }
}
