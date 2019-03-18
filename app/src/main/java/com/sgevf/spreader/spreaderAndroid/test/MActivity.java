package com.sgevf.spreader.spreaderAndroid.test;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sgevf.spreader.http.base.impl.UploadProgressListener;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.config.HttpConfig;
import com.sgevf.spreader.spreaderAndroid.glide.GlideManager;

public class MActivity extends BaseLoadingActivity<Movie> implements UploadProgressListener {
    TextView textView;
    ImageView imageView;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpConfig.init(getApplicationContext());
        textView = findViewById(R.id.back);
        imageView = findViewById(R.id.show);
        progress = findViewById(R.id.progress);
    }

    public void click(View view) {
        new Api(this).setClass().request();
    }

    @Override
    public void onLoadFinish(Movie movie) {
        textView.setText(movie.title);
    }

    public void finish(Movie movie) {
        textView.setText(movie.title);
        GlideManager.showImage(this, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + "/Camera/IMG_20181129_144117.jpg", imageView);
    }

    public void upload(View view) {
        new UploadApi(this, this).request();
    }

    @Override
    public void progress(long currentBytesCount, long totalBytesCount) {
        progress.setMax((int) totalBytesCount);
        progress.setProgress((int) currentBytesCount);
    }
}
