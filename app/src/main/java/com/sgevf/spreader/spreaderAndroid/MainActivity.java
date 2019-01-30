package com.sgevf.spreader.spreaderAndroid;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.sgevf.spreader.http.base.BaseLoadingActivity;
import com.sgevf.spreader.http.utils.NetConfig;
import com.sgevf.spreader.spreaderAndroid.glide.GlideManager;

import java.io.File;
import java.io.InputStream;

import utils.PropertiesUtils;

public class MainActivity extends BaseLoadingActivity<Movie> {
    TextView textView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetConfig.URL = PropertiesUtils.getUrl(this);
        textView = findViewById(R.id.back);
        imageView=findViewById(R.id.show);
    }

    public void click(View view) {
        new Api(this);
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
        new UploadApi(this);
    }
}
