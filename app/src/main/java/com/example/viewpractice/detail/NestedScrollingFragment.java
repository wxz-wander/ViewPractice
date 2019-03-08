package com.example.viewpractice.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.viewpractice.R;
import com.example.viewpractice.fragments.BaseFragment;

/**
 * 作者：wxz11 on 2019/3/8 16:31
 */
public class NestedScrollingFragment extends BaseFragment {
    @Override
    protected View inflateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nested_srcoll, container, false);
    }
}
