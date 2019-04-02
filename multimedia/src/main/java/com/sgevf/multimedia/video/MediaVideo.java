package com.sgevf.multimedia.video;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sgevf.multimedia.R;
import com.sgevf.multimedia.utils.TimeUtils;

import java.io.IOException;

public class MediaVideo extends FrameLayout implements TextureView.SurfaceTextureListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnVideoSizeChangedListener, MediaPlayer.OnSeekCompleteListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    public enum Type {
        IDEL,
        PREPARING,
        PREPARED,
        STARTED,
        PAUSED,
        STOPED,
        COMPLETED,
        ERROR,
        END
    }

    private Type status;

    private LayoutInflater inflater;
    private Context context;

    private MediaPlayer player;
    private TextureView surface;
    private ImageView on_off;
    private SeekBar progress;
    private ProgressBar loading;
    private TextView curTime;
    private TextView allTime;
    private ImageView fillScreen;

    private Handler handler;
    private Runnable runnable;

    private String path;
    private boolean down = false;

    public MediaVideo(Context context) {
        this(context, null);
    }

    public MediaVideo(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MediaVideo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
        initSeekbar();
        initSurface();
        initMediaPlayer();

    }

    public MediaVideo setDataSource(String path) {
        this.path = path;
        return this;
    }

    private void initView(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_media_video, this, true);
        surface = view.findViewById(R.id.surface);
        on_off = view.findViewById(R.id.on_off);
        progress = view.findViewById(R.id.progress);
        loading = view.findViewById(R.id.loading);
        curTime = view.findViewById(R.id.curTime);
        allTime = view.findViewById(R.id.allTime);
        fillScreen = view.findViewById(R.id.fillScreen);

        on_off.setOnClickListener(this);
        fillScreen.setOnClickListener(this);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                updateTime();
                handler.postDelayed(runnable, 1000);
            }
        };

    }

    private void initSeekbar() {
        progress.setOnSeekBarChangeListener(this);
    }

    private void initSurface() {
        surface.setSurfaceTextureListener(this);
    }

    private void initMediaPlayer() {
        player = new MediaPlayer();
        status = Type.IDEL;
        player.setScreenOnWhilePlaying(true);
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
        player.setOnBufferingUpdateListener(this);
        player.setOnVideoSizeChangedListener(this);
        player.setOnSeekCompleteListener(this);
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        if (path != null) {
            try {
                if (status == Type.IDEL) {
                    player.setDataSource(path);
                    player.setSurface(new Surface(surface));
                    player.prepareAsync();
                    status = Type.PREPARING;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    public void onPrepared(MediaPlayer mp) {
        status = Type.PREPARED;
        updateTime();
        progress.setMax(mp.getDuration());
        allTime.setText(TimeUtils.formatTime(player.getDuration()));
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        status = Type.COMPLETED;
        Toast.makeText(context, "视频播放完毕", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        Log.d("TAG", "onBufferingUpdate: " + percent);
        progress.setSecondaryProgress(percent * mp.getDuration() / 100);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        status = Type.ERROR;
        Toast.makeText(context, "播放器发生错误:" + what + "," + extra, Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        ViewGroup.LayoutParams params = surface.getLayoutParams();
        params.height = height * getResources().getDisplayMetrics().widthPixels / width;
        surface.setLayoutParams(params);
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (down) {
            player.seekTo(progress);
            updateTime();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        pause();
        down = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        play();
        down = false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.on_off) {
            if (player != null) {
                if (status == Type.STARTED) {
                    pause();
                } else if (status == Type.PAUSED || status == Type.PREPARED) {
                    play();
                }
            }
        } else if (v.getId() == R.id.fillScreen) {

        }
    }

    public void play() {
        player.start();
        status = Type.STARTED;
        on_off.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_video_stop));
        handler.postDelayed(runnable, 1000);
    }

    public void pause() {
        player.pause();
        status = Type.PAUSED;
        on_off.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_video_play));
        handler.removeCallbacks(runnable);
    }

    public void stop() {
        player.stop();
        status = Type.STOPED;
        handler.removeCallbacks(runnable);
    }

    private void release() {
        player.release();
        status = Type.END;
    }

    public void reset() {
        player.reset();
        status = Type.IDEL;
    }

    /**
     * 更新进度和时间
     */
    private void updateTime() {
        progress.setProgress(player.getCurrentPosition());
        curTime.setText(TimeUtils.formatTime(player.getCurrentPosition()));
    }

    public Type getStatus() {
        return status;
    }

    public void destory() {
        handler.removeCallbacks(runnable);
        if (status == Type.STARTED || status == Type.PAUSED) {
            stop();
        }
        release();
        player = null;
    }

}
