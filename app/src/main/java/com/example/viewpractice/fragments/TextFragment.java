package com.example.viewpractice.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * 作者：wxz11 on 2019/2/15 11:55
 * Email : wangc@huitouche.com
 */
public class TextFragment extends Fragment {

    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        textView = new TextView(inflater.getContext());
        return textView;
    }

    public void setText(String text) {
        textView.setText(text);
    }
}
