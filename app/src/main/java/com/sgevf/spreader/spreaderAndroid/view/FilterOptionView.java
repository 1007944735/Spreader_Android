package com.sgevf.spreader.spreaderAndroid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;

import java.util.ArrayList;
import java.util.List;

public class FilterOptionView extends ViewGroup implements View.OnClickListener {
    private Context context;
    private int widthMode;
    private int widthSize;
    private int heightMode;
    private int heightSize;
    private int hspace = 20;
    private int vspace = 10;
    private int mode;
    private View selected;

    public FilterOptionView(Context context) {
        this(context, null);
    }

    public FilterOptionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterOptionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FilterOptionView);
        mode = a.getInt(R.styleable.FilterOptionView_mode, 0);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMode = MeasureSpec.getMode(widthMeasureSpec);
        widthSize = MeasureSpec.getSize(widthMeasureSpec);
        heightMode = MeasureSpec.getMode(heightMeasureSpec);
        heightSize = MeasureSpec.getMode(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() == 0) {
            setMeasuredDimension(0, 0);
            return;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, getTotalHeight());
        } else {
            setMeasuredDimension(widthSize, heightSize);
        }
        invalidate();
    }

    /**
     * 累计高度
     *
     * @return
     */
    private int getTotalHeight() {
        int line = 1;
        int firstHeight = getChildAt(0).getMeasuredHeight();
        int allWidth = widthSize;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view.getVisibility() == GONE)
                continue;
            if ((allWidth - hspace - view.getMeasuredWidth()) < 0) {
                line++;
                allWidth = widthSize;
            } else {
                allWidth = allWidth - hspace - view.getMeasuredWidth();
            }
        }
        return vspace * (line - 1) + firstHeight * line;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int curPositionX = 0;
        int curPositionY = 0;
        int allWidth = getMeasuredWidth();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            int width = view.getMeasuredWidth();
            int height = view.getMeasuredHeight();
            if (view.getVisibility() == GONE)
                continue;
            if (curPositionX + hspace + width > allWidth) {
                curPositionX = 0;
                curPositionY += height + vspace;
            }
            view.layout(curPositionX, curPositionY, curPositionX + width, curPositionY + height);
            curPositionX += width + hspace;
        }
    }

    public void setData(String[] option) {
        for (int i = 0; i < option.length; i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_filter_option, null);
            ((TextView) view).setText(option[i]);
            view.setOnClickListener(this);
            addView(view);
        }
    }


    @Override
    public void onClick(View v) {
        if (mode == 0) {
            if(v.isSelected()){
                v.setSelected(false);
                selected=null;
            }else {
                v.setSelected(true);
                if (selected != null) {
                    selected.setSelected(false);
                }
                selected = v;
            }
        } else if (mode == 1) {
            if(v.isSelected()){
                v.setSelected(false);
            }else {
                v.setSelected(true);
            }
        }
    }

    public List<Integer> getResult(){
        List<Integer> list=new ArrayList<>();
        for(int i=0;i<getChildCount();i++){
            if(getChildAt(i).isSelected()){
                list.add(i);
            }
        }
        return list;
    }

    public void reset(){
        for(int i=0;i<getChildCount();i++){
            getChildAt(i).setSelected(false);
        }
        selected=null;
    }
}
