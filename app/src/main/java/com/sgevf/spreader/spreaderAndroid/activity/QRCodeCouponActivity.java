package com.sgevf.spreader.spreaderAndroid.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseActivity;
import com.sgevf.spreader.spreaderAndroid.activity.base.BaseLoadingActivity;
import com.sgevf.spreader.spreaderAndroid.model.UserCardModel;
import com.sgevf.spreader.spreaderAndroid.task.PollQueryUserCardTask;
import com.sgevf.spreader.spreaderAndroid.view.HeaderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QRCodeCouponActivity extends BaseLoadingActivity<UserCardModel> {
    @BindView(R.id.qrCode)
    ImageView qrCode;

    private Integer couponId;
    private Runnable runnable;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_qrcode);
        ButterKnife.bind(this);
        new HeaderView(this).setTitle("二维码");
        couponId = getIntent().getIntExtra("couponId", 0);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                new PollQueryUserCardTask(QRCodeCouponActivity.this, QRCodeCouponActivity.this).setClass(couponId).request();
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String json = toJson();
        qrCode.setImageBitmap(createQRImage(json, 1000, 1000));
    }

    public String toJson() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("couponId", couponId);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    private Bitmap createQRImage(String url, final int width, final int height) {
        try {
            // 判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url,
                    BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onLoadFinish(UserCardModel userCardModel) {
        if ("0".equals(userCardModel.isUse)) {
            //再次查询
            handler.postDelayed(runnable, 3000);
        } else if ("1".equals(userCardModel.isUse)) {
            startActivity(new Intent(this, UserCardSuccessActivity.class));
        }
    }
}
