package com.sgevf.spreader.spreaderAndroid.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.AttributeSet;
import android.view.View;

import com.sgevf.spreader.http.base.impl.UploadProgressListener;
import com.sgevf.spreader.spreaderAndroid.model.UploadFileModel;
import com.sgevf.spreader.spreaderAndroid.task.BaseService;
import com.sgevf.spreader.spreaderAndroid.task.impl.PubService;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class UploadImageView extends android.support.v7.widget.AppCompatImageView implements View.OnClickListener, UploadProgressListener {
    public static final int REQUEST_CODE = 2000;//默认2000
    private Boolean uploading = false;
    private Context context;
    private String filePath;
    private Uri fileUri;
    private float width;
    private float height;
    private Paint progress;
    private Paint back;
    private float progressWidth;
    private float padding = 20;
    private float curWidth = 0;
    private int code = 0;
    private String fPath;//数据库图片的地址

    public UploadImageView(Context context) {
        this(context, null);
    }

    public UploadImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UploadImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        setOnClickListener(this);
        progress = new Paint(Paint.ANTI_ALIAS_FLAG);
        progress.setStyle(Paint.Style.FILL);
        progress.setStrokeCap(Paint.Cap.ROUND);
        progress.setColor(Color.parseColor("#49afcd"));
        progress.setStrokeWidth(10);
        back = new Paint(Paint.ANTI_ALIAS_FLAG);
        back.setStyle(Paint.Style.FILL);
        back.setStrokeCap(Paint.Cap.ROUND);
        back.setColor(Color.WHITE);
        back.setStrokeWidth(10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        progressWidth = width - 2 * padding;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (uploading) {
            canvas.drawColor(Color.parseColor("#80cdcdcd"));
            canvas.drawLine(padding, height - 30, padding + progressWidth, height - 30, back);
            canvas.drawLine(padding, height - 30, padding + curWidth, height - 30, progress);
        }
    }

    @Override
    public void onClick(View view) {
        if (!uploading) {
            setFocusable(true);
            setFocusableInTouchMode(true);
            requestFocus();
            openSystemCamera();
        }
    }

    private void openSystemCamera() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        String fileName = System.currentTimeMillis() + ".jpg";
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/camera/" + fileName;
        File file=new File(filePath);
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            fileUri= FileProvider.getUriForFile(context,context.getApplicationContext().getPackageName() + ".provider",file);
        }else {
            fileUri = Uri.fromFile(file);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        ((Activity) context).startActivityForResult(intent, code == 0 ? REQUEST_CODE : code);
    }

    //上传图片，在onActivityForResult 中使用
    public void uploadFile() {
        File file = new File(filePath);
        if (file.exists()) {
            setImageBitmap(compressImages(filePath));
            uploading = true;
            new BaseService<PubService, UploadFileModel>(((Activity) context), this, this) {

                @Override
                public void onSuccess(UploadFileModel uploadFileModel) {
                    uploading = false;
                    fPath = uploadFileModel.filePath;
                    invalidate();
                }

                @Override
                public Observable setObservable(Map<String, RequestBody> data) {
                    params.put("file", new File(filePath), "上传中");
                    return service.uploadFile(data);
                }
            }.request();
        }
    }

    @Override
    public void progress(long currentBytesCount, long totalBytesCount, String name) {
        curWidth = currentBytesCount * 1.0f / totalBytesCount * progressWidth;
        invalidate();
    }

    private Bitmap compressImages(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        int originWidth = options.outWidth;
        int originHeight = options.outHeight;
        int inSampleSize = 1;
        if (originWidth > width || originHeight > height) {
            int widthRatio = Math.round(originWidth * 1.0f / width);
            int heightRatio = Math.round(originHeight * 1.0f / height);
            inSampleSize = widthRatio < heightRatio ? widthRatio : heightRatio;
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setFilePath(String filePath) {
        this.fPath = filePath;
    }

    public String getFilePath() {
        return fPath;
    }
}
