package com.sgevf.multimedia.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sgevf.multimedia.R;
@Deprecated
public class VideoActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_1;
    private Button btn_2;
    private Button btn_3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_video);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_1){
            startActivity(new Intent(this,VideoOneActivity.class));
        }else if(v.getId()==R.id.btn_2){
            startActivity(new Intent(this,VideoTwoActivity.class));
        }else if(v.getId()==R.id.btn_3){
            startActivity(new Intent(this,VideoThreeActivity.class));
        }
    }
}
