package utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

public class QrCodeUtils {
    /**
     * @param content    内容
     * @param width      宽
     * @param height     高
     * @param character  编码格式
     * @param errorLevel 容错等级 L，M，Q，H
     * @param margin     空白边距
     * @param color0     黑色小块的颜色
     * @param color1     白色小块的颜色
     * @return
     */
    public static Bitmap createQrCode(String content, int width, int height, String character, ErrorCorrectionLevel errorLevel, int margin, int color0, int color1) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }

        if (width < 0 || height < 0) {
            return null;
        }

        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        if (!TextUtils.isEmpty(character)) {
            hints.put(EncodeHintType.CHARACTER_SET, character);
        }
        hints.put(EncodeHintType.ERROR_CORRECTION, errorLevel);
        if (margin < 0 || margin > (width / 2)) {
            margin = 0;
        }
        hints.put(EncodeHintType.MARGIN, margin);

        try {
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = color0;
                    } else {
                        pixels[y * width + x] = color1;
                    }
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param srcBit 原二维码
     * @param logo   图片
     * @param scale  图片在二维码中的比例
     * @return
     */
    public static Bitmap addLogo(Bitmap srcBit, Bitmap logo, float scale) {
        if (srcBit == null) {
            return null;
        }

        if (logo == null) {
            return null;
        }

        if (scale < 0f || scale > 1f) {
            scale = 0.2f;
        }
        int srcWidth = srcBit.getWidth();
        int srcHeight = srcBit.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(srcBit, 0, 0, null);

        float scaleWidth = srcWidth * scale / logoWidth;
        float scaleHeight = srcHeight * scale / logoHeight;

        canvas.scale(scaleWidth, scaleHeight, srcWidth / 2, srcHeight / 2);
        canvas.drawBitmap(logo, srcWidth / 2 - logoWidth / 2, srcHeight / 2 - logoHeight / 2, null);
        return bitmap;
    }

    public static Bitmap createQrCodeWithLogo(String content, int width, int height, String character, ErrorCorrectionLevel errorLevel, int margin, int color0, int color1, Bitmap logo, float scale) {
        Bitmap srcBit = createQrCode(content, width, height, character, errorLevel, margin, color0, color1);
        return addLogo(srcBit, logo, scale);
    }

    public static Bitmap createQrCode(String content, int width, int height) {
        return createQrCode(content, width, height, "utf-8", ErrorCorrectionLevel.L, 1, Color.BLACK, Color.WHITE);
    }

    public static Bitmap createQrCodeWithLogo(String content, int width, int height, Bitmap logo) {
        return createQrCodeWithLogo(content, width, height, "utf-8", ErrorCorrectionLevel.L, 1, Color.BLACK, Color.WHITE, logo, 0.2f);
    }
}
