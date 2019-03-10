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
        mediaVideo=findViewById(R.id.mediaVideo);
        mediaVideo.setDataSource("http://m10.music.126.net/20190307143706/467d120980f80180c2d5b757692ff76f/ymusic/cb4b/34dc/fc81/cd3a190a35cb65480992ad3b1542ec7b.mp3");
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
