package com.sgevf.spreader.spreaderAndroid.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.sgevf.spreader.spreaderAndroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFixedFragment extends Fragment {
    private Context context;
    @BindView(R.id.count)
    public EditText count;
    @BindView(R.id.price)
    public EditText price;

    public static HomeFixedFragment newInstance() {

        Bundle args = new Bundle();

        HomeFixedFragment fragment = new HomeFixedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_fixed, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @OnClick(R.id.start_time)
    public void startTime() {

    }

    @OnClick(R.id.end_time)
    public void endTime() {

    }

    @OnClick(R.id.release_address)
    public void releaseAddress() {

    }
}
