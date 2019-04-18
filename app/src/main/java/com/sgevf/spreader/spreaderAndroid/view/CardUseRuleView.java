package com.sgevf.spreader.spreaderAndroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sgevf.spreader.spreaderAndroid.R;

public class CardUseRuleView extends LinearLayout {
    private Context context;

    public CardUseRuleView(Context context) {
        this(context, null);
    }

    public CardUseRuleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardUseRuleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        setOrientation(VERTICAL);
        createView();
    }

    private View createView() {
        View view = LayoutInflater.from(context).inflate(R.layout.view_card_use_rule, this, false);
        ImageView add = view.findViewById(R.id.add);
        ImageView delete = view.findViewById(R.id.delete);
        View divider = view.findViewById(R.id.divider);
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(GONE);
                ((View) v.getParent().getParent()).findViewById(R.id.divider).setVisibility(VISIBLE);
                createView();
            }
        });
        delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getChildCount() != 1) {
                    if (((View) v.getParent()).findViewById(R.id.add).getVisibility() == VISIBLE) {
                        getChildAt(getChildCount() - 2).findViewById(R.id.add).setVisibility(VISIBLE);
                        getChildAt(getChildCount() - 2).findViewById(R.id.divider).setVisibility(GONE);
                    }
                    removeView((View) v.getParent().getParent());
                }
            }
        });
        addView(view);
        return view;
    }

    public String getUseRule(){
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<getChildCount();i++){
            EditText rule=getChildAt(i).findViewById(R.id.useRule);
            if(i==getChildCount()-1){
                sb.append(rule.getText().toString().trim());
            }else {
                sb.append(rule.getText().toString().trim()+"|");
            }
        }
        return sb.toString();
    }
}
