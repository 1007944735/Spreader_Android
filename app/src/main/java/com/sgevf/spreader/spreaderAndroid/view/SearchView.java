package com.sgevf.spreader.spreaderAndroid.view;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sgevf.spreader.spreaderAndroid.R;

import utils.WindowHelper;

public class SearchView implements View.OnClickListener, View.OnFocusChangeListener, AdapterView.OnItemClickListener {
    private Activity activity;
    private ImageView location;
    private ImageView left;
    private EditText values;
    private View line;
    private TextView search;
    private LinearLayout searchView;
    private OnSearchListener searchListener;
    private boolean skip = true;
    private FrameLayout mask;
    private ViewGroup box;
    private ListView tipList;
    private OnTipExpandListener tipExpandListener;
    private OnItemClickListener itemClickListener;
    private BaseAdapter adapter;
    private boolean reveal = true;

    public SearchView(Activity activity) {
        this.activity = activity;
        searchView = activity.findViewById(R.id.searchView);
        location = activity.findViewById(R.id.location);
        left = activity.findViewById(R.id.left);
        values = activity.findViewById(R.id.values);
        line = activity.findViewById(R.id.line);
        search = activity.findViewById(R.id.search);
        tipList = activity.findViewById(R.id.tipList);
        search.setOnClickListener(this);
        values.addTextChangedListener(new ValuesTextWatcher());
        values.setOnFocusChangeListener(this);
        tipList.setOnItemClickListener(this);
        box = (ViewGroup) ((ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0)).getChildAt(0);
        addMask();
    }

    /**
     * 添加遮罩
     */
    private void addMask() {
        mask = new FrameLayout(activity);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mask.setLayoutParams(params);
        mask.setBackgroundColor(Color.LTGRAY);
        mask.setAlpha(0.0f);
        mask.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (values.isFocused() && event.getAction() == MotionEvent.ACTION_DOWN) {
                    values.clearFocus();
                    return true;
                }
                return false;
            }
        });
        box.addView(mask);
    }


    public SearchView setLeftIcon(int resId) {
        left.setImageResource(resId);
        return this;
    }

    public SearchView setLeftVisibility(boolean visible) {
        if (visible) {
            left.setVisibility(View.VISIBLE);
        } else {
            left.setVisibility(View.GONE);
        }
        return this;
    }

    public SearchView setLocationVisibility(boolean visible) {
        if (visible) {
            location.setVisibility(View.VISIBLE);
        } else {
            location.setVisibility(View.GONE);
        }
        return this;
    }

    public SearchView setSearchVisibility(boolean visible) {
        if (visible) {
            search.setVisibility(View.VISIBLE);
        } else {
            search.setVisibility(View.GONE);
        }
        return this;
    }

    public SearchView setLineVisibility(boolean visible) {
        if (visible) {
            line.setVisibility(View.VISIBLE);
        } else {
            line.setVisibility(View.GONE);
        }
        return this;
    }

    public EditText getInput() {
        return values;
    }

    public ListView getTipList() {
        return tipList;
    }

    public SearchView setOnSearchListener(OnSearchListener searchListener) {
        this.searchListener = searchListener;
        return this;
    }

    public SearchView setOnTipExpandListener(OnTipExpandListener tipExpandListener) {
        this.tipExpandListener = tipExpandListener;
        return this;
    }

    public SearchView setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        return this;
    }

    public SearchView isSkip(boolean skip) {
        this.skip = skip;
        return this;
    }

    public SearchView setHint(String hint) {
        values.setHint(hint);
        return this;
    }

    public SearchView setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
        tipList.setAdapter(adapter);
        return this;
    }

    @Override
    public void onClick(View v) {
        String key = values.getText().toString().trim();
        if (searchListener != null) {
            searchListener.search(key);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus && skip) {
            dimBackGround(0f, 0.6f, mask);
            line.setVisibility(View.VISIBLE);
            search.setVisibility(View.VISIBLE);
        } else {
            dimBackGround(0.6f, 0f, mask);
            WindowHelper.hideSoftInput(activity);
            line.setVisibility(View.GONE);
            search.setVisibility(View.GONE);
        }
    }

    /**
     * 使屏幕的亮度从from到to
     *
     * @param from
     * @param to
     */
    private void dimBackGround(float from, float to, final View view) {
        ValueAnimator animator = ValueAnimator.ofFloat(from, to);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mask.setAlpha((Float) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    /**
     * 清除editText的焦点，设置editText的内容
     *
     * @param name
     */
    public void clearValuesFocus(String name) {
        if (values.isFocused()) {
            values.clearFocus();
        }
        reveal = false;
        values.setText(name);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (itemClickListener != null && adapter != null) {
            itemClickListener.onItemClick(adapter.getItem(position), position);
        }
    }

    public interface OnSearchListener {
        void search(String values);
    }

    public interface OnTipExpandListener {
        void showTip(String key);
    }

    public interface OnItemClickListener {
        void onItemClick(Object item, int position);
    }

    class ValuesTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (tipExpandListener != null && reveal) {
                tipExpandListener.showTip(s.toString());
            }
            reveal = true;
        }
    }


}
