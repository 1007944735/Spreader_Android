package com.sgevf.multimedia.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.ImageReader;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class CameraHelper implements View.OnTouchListener {

    private Activity context;
    private TextureView textureView;
    private ImageReader imageReader;
    private CameraManager cameraManager;
    private CameraDevice cameraDevice;
    private Size previewSize;
    private Size captureSize;
    private String cameraId;
    private CameraCaptureSession cameraCaptureSession;
    private CaptureRequest.Builder captureRequestBuilder;
    private CaptureRequest captureRequest;
    private Surface surface;

    public CameraHelper(Activity context) {
        this.context = context;
    }

    public void setTextureView(TextureView view) {
        this.textureView = view;
    }

//    public void setImageReader(ImageReader imageReader) {
//        this.imageReader = imageReader;
//    }

    private static final SparseIntArray ORIENTATION = new SparseIntArray();

    static {
        ORIENTATION.append(Surface.ROTATION_0, 90);
        ORIENTATION.append(Surface.ROTATION_90, 0);
        ORIENTATION.append(Surface.ROTATION_180, 270);
        ORIENTATION.append(Surface.ROTATION_270, 180);
    }

    /***
     * 打开相机
     * @param width
     * @param height
     * @param cameraId
     */
    public void openCamera(int width, int height, int cameraId) {
        if (checkPermission(context)) {
            cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
            setupCamera(width, height, cameraId);
            if (imageReader == null) {
                imageReader = ImageReader.newInstance(captureSize.getWidth(), captureSize.getHeight(), ImageFormat.JPEG, 2);
                imageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                    @Override
                    public void onImageAvailable(ImageReader reader) {
//                        ByteBuffer buffer=reader.acquireNextImage().getPlanes()[0].getBuffer();
//                        byte[] data=new byte[buffer.remaining()];
//                        buffer.get(data);
//                        final Bitmap bitmap= BitmapFactory.decodeByteArray(data,0,data.length);
                    }
                }, null);
            }
            open(this.cameraId);
        } else {
            Toast.makeText(context, "没有相机权限", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 检查是否有相机权限
     *
     * @return
     */
    private boolean checkPermission(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 根据textureView的尺寸设置相机的尺寸
     *
     * @param width
     * @param height
     * @param cameraId 0为后置 1为前置
     */
    private void setupCamera(int width, int height, int cameraId) {
        try {
            for (String id : cameraManager.getCameraIdList()) {
                CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(id);
                if (characteristics.get(CameraCharacteristics.LENS_FACING) == cameraId)
                    continue;
                StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                previewSize = getOptionsSize(map.getOutputSizes(SurfaceTexture.class), width, height);
                captureSize = Collections.max(Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)), new Comparator<Size>() {
                    @Override
                    public int compare(Size o1, Size o2) {
                        return Long.signum(o1.getWidth() * o1.getHeight() - o2.getWidth() * o2.getHeight());
                    }
                });
                this.cameraId = id;
                break;
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 选择合适的分辨率
     *
     * @param outputSizes
     * @param width
     * @param height
     * @return
     */
    private Size getOptionsSize(Size[] outputSizes, int width, int height) {
        Size s = null;
        double f = (height * 1.0f) / (width * 1.0f);
        double min = Double.MAX_VALUE;
        for (Size size : outputSizes) {
            if (Math.abs(f - (size.getWidth() * 1.0f) / (size.getHeight() * 1.0f)) < min) {
                s = size;
                min = Math.abs(f - (size.getWidth() * 1.0f) / (size.getHeight() * 1.0f));
            }
        }
        return s;
    }

    /**
     * 打开相机
     *
     * @param cameraId 前置还是后置摄像头
     */
    private void open(String cameraId) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        try {
            cameraManager.openCamera(cameraId, CDStateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private CameraDevice.StateCallback CDStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            cameraDevice = camera;
            startPreView();
        }

        @Override
        public void onDisconnected(CameraDevice camera) {
            camera.close();
            camera = null;
        }

        @Override
        public void onError(CameraDevice camera, int error) {
            camera.close();
            camera = null;
        }
    };

    /**
     * 开启预览
     */
    public void startPreView() {
        if (textureView != null) {
            textureView.setOnTouchListener(this);
            SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
            surfaceTexture.setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());
            surface = new Surface(surfaceTexture);
            try {
                captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                //自动对焦
                captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                captureRequestBuilder.addTarget(surface);
                cameraDevice.createCaptureSession(Arrays.asList(surface, imageReader.getSurface()), CCSStateCallback, null);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private CameraCaptureSession.StateCallback CCSStateCallback = new CameraCaptureSession.StateCallback() {
        @Override
        public void onConfigured(CameraCaptureSession session) {
            try {
                captureRequest = captureRequestBuilder.build();
                cameraCaptureSession = session;
                cameraCaptureSession.setRepeatingRequest(captureRequest, null, null);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConfigureFailed(CameraCaptureSession session) {
            Toast.makeText(context, "预览失败", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 拍照
     */
    public void capture() {
        try {
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            int rotation = context.getWindowManager().getDefaultDisplay().getRotation();
            captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            captureRequestBuilder.addTarget(imageReader.getSurface());
            captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATION.get(rotation));
            cameraCaptureSession.stopRepeating();
            cameraCaptureSession.capture(captureRequestBuilder.build(), null, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    public void closeCamera() {
        if (cameraCaptureSession != null) {
            cameraCaptureSession.close();
            cameraCaptureSession = null;
        }
        if (cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }
        if (imageReader != null) {
            imageReader.close();
            imageReader = null;
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Rect rect = getFocusRect((int) event.getX(), (int) event.getY());
            autoFocusing(rect);
        }
        return false;
    }

    /**
     * 获取点击区域
     *
     * @param x：手指触摸点x坐标
     * @param y:         手指触摸点y坐标
     */
    private Rect getFocusRect(int x, int y) {
        int screenW = context.getResources().getDisplayMetrics().widthPixels;//获取屏幕长度
        int screenH = context.getResources().getDisplayMetrics().heightPixels;//获取屏幕宽度

        //因为获取的SCALER_CROP_REGION是宽大于高的，也就是默认横屏模式，竖屏模式需要对调width和height
        int realPreviewWidth = previewSize.getHeight();
        int realPreviewHeight = previewSize.getWidth();

        //根据预览像素与拍照最大像素的比例，调整手指点击的对焦区域的位置
        float focusX = realPreviewWidth * 1.0f / screenW * x;
        float focusY = realPreviewHeight * 1.0f / screenH * y;

        //获取SCALER_CROP_REGION，也就是拍照最大像素的Rect
        Rect totalPicSize = captureRequestBuilder.get(CaptureRequest.SCALER_CROP_REGION);

        //计算出摄像头剪裁区域偏移量
        float cutDx = (totalPicSize.height() * 1.0f - previewSize.getHeight() * 1.0f) / 2;

        //我们默认使用10dp的大小，也就是默认的对焦区域长宽是10dp，这个数值可以根据需要调节
        int width = dip2px(10f);
        int height = dip2px(10f);

        //返回最终对焦区域Rect
        return new Rect((int) focusY, (int) (focusX + cutDx), (int) (focusY + height), (int) (focusX + cutDx + width));
    }

    private int dip2px(float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 自动对焦
     */
    private void autoFocusing(Rect rect) {
        try {
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_AUTO);
            captureRequestBuilder.set(CaptureRequest.CONTROL_AE_REGIONS, new MeteringRectangle[]{new MeteringRectangle(rect, 1000)});
            captureRequestBuilder.set(CaptureRequest.CONTROL_AF_REGIONS, new MeteringRectangle[]{new MeteringRectangle(rect, 1000)});
            captureRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CaptureRequest.CONTROL_AF_TRIGGER_START);
            captureRequestBuilder.addTarget(surface);
            cameraCaptureSession.setRepeatingRequest(captureRequestBuilder.build(), null, null);
            cameraCaptureSession.capture(captureRequestBuilder.build(), null, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
}
