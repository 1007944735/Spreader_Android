package com.sgevf.spreader.spreaderAndroid;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sgevf.spreader.http.base.BaseLoadingActivity;
import com.sgevf.spreader.http.base.impl.UploadProgressListener;
import com.sgevf.spreader.http.utils.NetConfig;
import com.sgevf.spreader.spreaderAndroid.glide.GlideManager;

import utils.PropertiesUtils;

public class MainActivity extends BaseLoadingActivity<Movie> implements UploadProgressListener {
    TextView textView;
    ImageView imageView;
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetConfig.URL = PropertiesUtils.getUrl(this);
        textView = findViewById(R.id.back);
        imageView=findViewById(R.id.show);
        progress=findViewById(R.id.progress);
    }

    public void click(View view) {
        new Api(this).request();
    }

    @Override
    public void onLoadFinish(Movie movie) {
        textView.setText(movie.title);
    }

    public void finish(Movie movie) {
        textView.setText(movie.title);
        GlideManager.showImage(this,Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath()+"/Camera/IMG_20181129_144117.jpg",imageView);
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
