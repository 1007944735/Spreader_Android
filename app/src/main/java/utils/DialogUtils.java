package utils;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;
import com.sgevf.spreader.spreaderAndroid.animaiton.RotateXAnimaiton;
import com.sgevf.spreader.spreaderAndroid.view.DatePickerDialog;
import com.sgevf.spreader.spreaderAndroid.view.SemiCircleView;

public class DialogUtils {
    public static Dialog showConfirm(Context context, String title, String msg, String confirm, String cancel, View.OnClickListener confirmOnClick, View.OnClickListener cancelOnClick) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_common_style);
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.width = (int) (WindowHelper.getScreenWidth(context) * 0.9);
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

    public static Dialog showSelectTime(Context context, DatePickerDialog.OnConfirmListener confirmListener) {
        DatePickerDialog dialog = new DatePickerDialog(context);
        dialog.setConfirmListener(confirmListener);
        dialog.show();
        return dialog;
    }

    public static Dialog showRedPacket(Context context, final View.OnClickListener listener) {
        final Dialog dialog = new Dialog(context, R.style.DialogTheme);
        dialog.setContentView(R.layout.dialog_red_packet);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = (int) (WindowHelper.getScreenWidth(context) * 0.8f);
        params.height = (int) (WindowHelper.getScreenHeight(context) * 0.6f);
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setWindowAnimations(R.style.DialogRedAnimation);
        final SemiCircleView head = dialog.findViewById(R.id.head);
        TextView open = dialog.findViewById(R.id.open);
        final RotateXAnimaiton animaiton=new RotateXAnimaiton();
        animaiton.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dialog.dismiss();
                listener.onClick(null);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                head.startAnimation(animaiton);
            }
        });
        dialog.show();
        return dialog;
    }

}
