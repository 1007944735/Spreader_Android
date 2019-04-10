package utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.sgevf.spreader.spreaderAndroid.model.pay.PayResult;

import java.lang.ref.WeakReference;
import java.util.Map;

public class AppAlipayUtil {
    private Activity activity;
    private PayHandler payHandler;

    public AppAlipayUtil(Activity activity) {
        this.activity = activity;
        payHandler = new PayHandler(activity);
    }

    public AppAlipayUtil pay(final String orderString) {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                PayTask task = new PayTask(activity);
                Map<String, String> result = task.payV2(orderString, true);
                Message msg = new Message();
                msg.obj = result;
                payHandler.sendMessage(msg);
            }
        };
        Thread thread=new Thread(runnable);
        thread.start();
        return this;
    }

    public AppAlipayUtil setCallback(PayResultCallback callback) {
        payHandler.setCallback(callback);
        return this;
    }

    public static class PayHandler extends Handler {
        private WeakReference<Activity> wr;
        private PayResultCallback callback;

        public PayHandler(Activity activity) {
            wr = new WeakReference<>(activity);
        }

        public void setCallback(PayResultCallback callback){
            this.callback = callback;
        }

        @Override
        public void handleMessage(Message msg) {
            PayResult payResult=new PayResult((Map<String, String>) msg.obj);
            String resultStatus=payResult.getResultStatus();
            if("9000".equals(resultStatus)){
                Toast.makeText(wr.get(), "支付成功", Toast.LENGTH_SHORT).show();
                callback.success();
            }else {
                Toast.makeText(wr.get(), "支付失败", Toast.LENGTH_SHORT).show();
                callback.error(resultStatus);
            }
        }
    }

    public interface PayResultCallback {
        void success();

        void error(String errorCode);
    }
}
