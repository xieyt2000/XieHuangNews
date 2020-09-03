package com.java.xieyuntong.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewsFragment extends Fragment {
    private String type;
    public static NewsFragment newInstance(String type){
        NewsFragment newsFragment = new NewsFragment();
        Bundle b = new Bundle();
        b.putCharSequence("type",type);
        newsFragment.setArguments(b);
        return newsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = (String) savedInstanceState.getCharSequence("type");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrameLayout f = new FrameLayout(getActivity());
        TextView t = new TextView(getActivity());
        t.setText(type);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        f.setLayoutParams(params);
        t.setLayoutParams(params);

        return f;
    }
}
