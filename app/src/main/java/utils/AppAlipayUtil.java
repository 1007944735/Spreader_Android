package utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.model.pay.AuthResult;
import com.sgevf.spreader.spreaderAndroid.model.pay.PayResult;

import java.lang.ref.WeakReference;
import java.util.Map;

public class AppAlipayUtil {
    public static final int SDK_PAY_FLAG=1;//支付
    public static final int SDK_AUTH_FLAG=2;//授权登录
    private Activity activity;
    private PayHandler payHandler;
    private int type;

    public AppAlipayUtil(Activity activity,int type) {
        this.activity = activity;
        payHandler = new PayHandler(activity);
        this.type=type;
    }

    public AppAlipayUtil pay(final String orderString) {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                PayTask task = new PayTask(activity);
                Map<String, String> result = task.payV2(orderString, true);
                Message msg = new Message();
                msg.what=type;
                msg.obj = result;
                payHandler.sendMessage(msg);
            }
        };
        Thread thread=new Thread(runnable);
        thread.start();
        return this;
    }

    public AppAlipayUtil auth(final String authInfo) {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                AuthTask task = new AuthTask(activity);
                Map<String, String> result = task.authV2(authInfo, true);
                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                payHandler.sendMessage(msg);
            }
        };
        Thread thread=new Thread(runnable);
        thread.start();
        return this;
    }



    public AppAlipayUtil setCallback(AliResultCallback callback) {
        payHandler.setCallback(callback);
        return this;
    }

    public static class PayHandler extends Handler {
        private WeakReference<Activity> wr;
        private AliResultCallback callback;

        public PayHandler(Activity activity) {
            wr = new WeakReference<>(activity);
        }

        public void setCallback(AliResultCallback callback){
            this.callback = callback;
        }

        @Override
        public void handleMessage(Message msg) {
            if(msg.what==SDK_PAY_FLAG) {
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                String resultStatus = payResult.getResultStatus();
                if ("9000".equals(resultStatus)) {
                    Toast.makeText(wr.get(), "支付成功", Toast.LENGTH_SHORT).show();
                    callback.success(null);
                } else {
                    Toast.makeText(wr.get(), "支付失败", Toast.LENGTH_SHORT).show();
                    callback.error(resultStatus);
                }
            }else if(msg.what==SDK_AUTH_FLAG){
                AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                String resultStatus = authResult.getResultStatus();

                // 判断resultStatus 为“9000”且result_code
                // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                    // 获取alipay_open_id，调支付时作为参数extern_token 的value
                    // 传入，则支付账户为该授权账户
                    Toast.makeText(wr.get(), "授权成功", Toast.LENGTH_SHORT).show();
                    callback.success(authResult);
                } else {
                    // 其他状态值则为授权失败
                    Toast.makeText(wr.get(), "授权失败", Toast.LENGTH_SHORT).show();
                    callback.error(resultStatus);
                }
            }
        }
    }

    public interface AliResultCallback {
        void success(Object obj);

        void error(String errorCode);
    }
}
