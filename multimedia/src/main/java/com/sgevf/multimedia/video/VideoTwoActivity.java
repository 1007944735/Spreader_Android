package com.sgevf.multimedia.video;

import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.sgevf.multimedia.R;
import com.sgevf.multimedia.utils.TimeUtils;

import java.io.IOException;
@Deprecated
public class VideoTwoActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnInfoListener, MediaPlayer.OnVideoSizeChangedListener {
//    private static final String url="http://vfx.mtime.cn/Video/2019/03/01/mp4/190301141332024373.mp4";
    private static final String url="/storage/emulated/0/DCIM/Camera/1551941276299_weibo.mp4";
    private TextureView surface;
    private MediaPlayer player;
    private VideoController control;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_video_two);
        initView();
    }

    private void initView() {
        surface=findViewById(R.id.surface);
        surface.setSurfaceTextureListener(this);
        control=findViewById(R.id.control);
        player=new MediaPlayer();
        player.setScreenOnWhilePlaying(true);
        player.setOnPreparedListener(this);
        player.setOnErrorListener(this);
        player.setOnCompletionListener(this);
        player.setOnBufferingUpdateListener(this);
        player.setOnInfoListener(this);
        player.setOnVideoSizeChangedListener(this);

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        control.setMediaPlayer(mp);
        control.setProgress(mp.getCurrentPosition(),mp.getDuration());
        control.setCurTime(TimeUtils.formatTime(mp.getCurrentPosition()));
        control.setAllTime(TimeUtils.formatTime(mp.getDuration()));
//        surface.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        try {
            player.setDataSource(url);
            player.setSurface(new Surface(surface));
            player.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(player.isPlaying()){
            player.pause();
            control.postHander();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(player.isPlaying()){
            player.stop();
            player=null;
            control.removeHandler();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Toast.makeText(this, "视频发生错误", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Toast.makeText(this, "视频播放完毕", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        control.setSecondTime(percent);
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }


    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        ViewGroup.LayoutParams params=surface.getLayoutParams();
        params.height=height*getResources().getDisplayMetrics().widthPixels/width;
        surface.setLayoutParams(params);
    }
}