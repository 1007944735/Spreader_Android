package com.sgevf.spreader.spreaderAndroid.animaiton;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

public class RotateXAnimaiton extends Animation {
    private Camera camera;
    private int centerX;
    private int centerY;
    public RotateXAnimaiton(){
        camera=new Camera();
    }
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        centerX=width/2;
        centerY=0;
        setDuration(2000);
        setInterpolator(new LinearInterpolator());
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        Matrix matrix=t.getMatrix();
        camera.save();
        camera.rotateX(180*interpolatedTime);
        camera.getMatrix(matrix);
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
        camera.restore();
    }
}
