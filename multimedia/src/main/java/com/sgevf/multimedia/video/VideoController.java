package com.sgevf.multimedia.video;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sgevf.multimedia.R;
import com.sgevf.multimedia.utils.TimeUtils;

public class VideoController extends LinearLayout implements View.OnClickListener {
    private LayoutInflater inflater;
    private ImageView on_off;
    private ProgressBar progress;
    private TextView curTime;
    private TextView allTime;

    private MediaPlayer mediaPlayer;

    private Handler handler;
    private Runnable runnable;

    public VideoController(Context context) {
        this(context, null);
    }

    public VideoController(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_video_controller, this, true);
        on_off = view.findViewById(R.id.on_off);
        progress = view.findViewById(R.id.progress);
        curTime = view.findViewById(R.id.curTime);
        allTime = view.findViewById(R.id.allTime);
        on_off.setOnClickListener(this);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                setProgress(mediaPlayer.getCurrentPosition());
                setCurTime(TimeUtils.formatTime(mediaPlayer.getCurrentPosition()));
                handler.postDelayed(runnable, 1000);
            }
        };
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.on_off) {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    on_off.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_video_play));
                    mediaPlayer.pause();
                    handler.removeCallbacks(runnable);
                } else {
                    on_off.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_video_stop));
                    mediaPlayer.start();
                    handler.postDelayed(runnable, 1000);
                }
            }
        }
    }

    /**
     * 设置当前进度
     *
     * @param current
     */
    public void setProgress(int current) {
        progress.setProgress(current);
    }

    /**
     * 设置当前进度
     *
     * @param current
     * @param total
     */
    public void setProgress(int current, int total) {
        progress.setMax(total);
        progress.setProgress(current);
    }

    public void setSecondTime(int secondTime) {
        progress.setSecondaryProgress(secondTime);
    }

    /**
     * 设置当前时间
     *
     * @param ct
     */
    public void setCurTime(String ct) {
        curTime.setText(ct);
    }

    /**
     * 设置总时间
     *
     * @param at
     */
    public void setAllTime(String at) {
        allTime.setText(at);
    }

    public void postHander() {
        handler.postDelayed(runnable, 1000);
    }

    public void removeHandler() {
        handler.removeCallbacks(runnable);
    }

}
