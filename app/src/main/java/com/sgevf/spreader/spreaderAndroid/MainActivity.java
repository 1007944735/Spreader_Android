package com.sgevf.spreader.spreaderAndroid;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.sgevf.spreader.http.base.BaseLoadingActivity;

public class MainActivity extends BaseLoadingActivity<Movie> {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.back);
    }

    public void click(View view) {
        new Api(this);
    }

    @Override
    public void onLoadFinish(Movie movie) {
        textView.setText(movie.title);
    }
}
