package com.sgevf.spreader.spreader_android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import utils.PropertiesUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Api(PropertiesUtils.getUrl(this));
    }
}
