package utils;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

public class AndroidBugsSolution {
    /**
     * 关于 全屏Activity adjustResize属性无效解决方案
     * 在Activity setContentView 之后调用
     *
     * @param activity
     * @param listener 软键盘显示隐藏监听, 可以为空
     */
    public static void assistActivity(Activity activity, OnKeyboardListener listener) {
        boolean subOffset = false;
        if (Build.VERSION.SDK_INT < 18) {
            subOffset = true;
        }
        new AndroidBugsSolution(activity, subOffset, listener);
    }

    private boolean mSubOffset;
    private int mCurrHeight;
    private Rect mRootRect;
    private View mContentView;
    private FrameLayout.LayoutParams mLayoutParams;
    private OnKeyboardListener mListener;

    /**
     * @param activity
     * @param isSubOffset 是否需要偏移
     * @param listener    软键盘监听 监听隐藏，显示
     */
    private AndroidBugsSolution(final Activity activity, boolean isSubOffset, OnKeyboardListener listener) {
        this.mSubOffset = isSubOffset;
        this.mListener = listener;
        this.mContentView = ((FrameLayout) activity.findViewById(android.R.id.content)).getChildAt(0);
        this.mLayoutParams = (FrameLayout.LayoutParams) mContentView.getLayoutParams();
        this.mContentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });
        activity.getWindow().getDecorView().getRootView().getWindowVisibleDisplayFrame(mRootRect = new Rect());
    }

    private void possiblyResizeChildOfContent() {
        Rect r = new Rect();
        mContentView.getWindowVisibleDisplayFrame(r);
        int usableHeightNow = r.height();
        // Log.i("III", "usableHeightNow " + usableHeightNow + ", r " + r.toString());
        if (usableHeightNow != mCurrHeight) {
            int usableHeightSansKeyboard = r.bottom;
            if (mSubOffset) {
                usableHeightSansKeyboard -= r.top;
            }
            // android.util.Log.i("III", "usableHeightSansKeyboard " + usableHeightSansKeyboard + ", r " + r.toString());
            // keyboard probably just became hidden
            mLayoutParams.height = usableHeightSansKeyboard;
            if (mListener != null) {
                if (mRootRect.bottom != r.bottom) {
                    mListener.onKeyboardChanged(OnKeyboardListener.SHOW);
                } else {
                    mListener.onKeyboardChanged(OnKeyboardListener.HIDE);
                }
            }

            mContentView.requestLayout();
            mCurrHeight = usableHeightNow;
        }
    }

    public interface OnKeyboardListener {
        int SHOW = 1;
        int HIDE = 0;

        void onKeyboardChanged(int state);
    }
}