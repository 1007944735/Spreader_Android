package com.sgevf.multimedia.camera;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageButton;

import com.sgevf.multimedia.R;
import com.sgevf.multimedia.config.CameraConstant;

public class CameraActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener, View.OnTouchListener, View.OnClickListener {
    private TextureView preview;
    private ImageButton capture;
    private ImageButton confirm;
    private ImageButton cancel;

    private CameraHelper helper;
    private AnimatorSet set;

    private enum ORIENTATION {
        zoomIn,
        zoomOut
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_camera);
        preview = findViewById(R.id.preview);
        capture = findViewById(R.id.capture);
        confirm = findViewById(R.id.confirm);
        cancel = findViewById(R.id.cancel);
        capture.setOnTouchListener(this);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
        preview.setSurfaceTextureListener(this);
        helper = new CameraHelper(this);
        helper.setTextureView(preview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (set != null) {
            set.resume();
        }
        if (preview.isAvailable()) {
            helper.openCamera(preview.getWidth(), preview.getHeight(), CameraConstant.CAMERA_REAR);
        } else {
            preview.setSurfaceTextureListener(this);
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        helper.openCamera(width, height, CameraConstant.CAMERA_REAR);

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
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                zoom(v, ORIENTATION.zoomIn);
                break;
            case MotionEvent.ACTION_UP:
                zoom(v, ORIENTATION.zoomOut).addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        capture.setVisibility(View.INVISIBLE);
                        confirm.setVisibility(View.VISIBLE);
                        cancel.setVisibility(View.VISIBLE);
                        //拍照
                        helper.capture();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                break;
        }
        return false;
    }

    /**
     * 缩放
     *
     * @param view
     * @param orientation 方向
     */
    private AnimatorSet zoom(View view, ORIENTATION orientation) {
        ObjectAnimator scaleX = null;
        ObjectAnimator scaleY = null;
        view.setPivotX(view.getWidth() / 2.0f);
        view.setPivotY(view.getHeight() / 2.0f);
        view.clearAnimation();
        if (orientation == ORIENTATION.zoomIn) {
            scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.75f);
            scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.75f);
        } else if (orientation == ORIENTATION.zoomOut) {
            scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f);
            scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f);
        }
        set = new AnimatorSet();
        set.play(scaleX).with(scaleY);
        set.setDuration(300);
        set.start();
        return set;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.confirm) {
            //
        } else if (v.getId() == R.id.cancel) {
            helper.startPreView();
            capture.setVisibility(View.VISIBLE);
            confirm.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (set != null) {
            set.pause();
        }
        helper.closeCamera();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (set != null) {
            set.cancel();
            set = null;
        }
        helper.closeCamera();

    }
}
