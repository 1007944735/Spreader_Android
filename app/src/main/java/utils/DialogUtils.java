package utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;

public class DialogUtils {
    public static Dialog showConfirm(Context context, String title, String msg, String confirm, String cancel, View.OnClickListener confirmOnClick, View.OnClickListener cancelOnClick) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_common_style);
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = (int) (WindowHelper.getScreentWidth(context) * 0.9);
        dialog.getWindow().setAttributes(p);
        dialog.setCanceledOnTouchOutside(false);
        if (!TextUtils.isEmpty(title)) {
            ((TextView) dialog.findViewById(R.id.title)).setText(title);
        }
        if (!TextUtils.isEmpty(msg)) {
            ((TextView) dialog.findViewById(R.id.msg)).setText(msg);
        }
        if (!TextUtils.isEmpty(confirm) && null != confirmOnClick) {
            ((Button) dialog.findViewById(R.id.confirm)).setText(confirm);
            ((Button) dialog.findViewById(R.id.confirm)).setOnClickListener(confirmOnClick);
        } else {
            ((Button) dialog.findViewById(R.id.confirm)).setVisibility(View.GONE);
            dialog.findViewById(R.id.cutting).setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(cancel) && null != cancelOnClick) {
            ((Button) dialog.findViewById(R.id.cancel)).setText(cancel);
            ((Button) dialog.findViewById(R.id.cancel)).setOnClickListener(cancelOnClick);
        } else {
            ((Button) dialog.findViewById(R.id.cancel)).setVisibility(View.GONE);
            dialog.findViewById(R.id.cutting).setVisibility(View.GONE);
        }
        dialog.show();
        return dialog;
    }

    public static Dialog showTips(Context context, String title, String msg, String confirm, View.OnClickListener confirmOnClick) {
        return showConfirm(context, title, msg, confirm, null, confirmOnClick, null);
    }

}
