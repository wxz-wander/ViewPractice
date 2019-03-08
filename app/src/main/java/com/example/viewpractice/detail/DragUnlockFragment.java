package com.example.viewpractice.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viewpractice.R;
import com.example.viewpractice.fragments.BaseFragment;
import com.example.viewpractice.widgets.DragUnlockView;

import java.util.Random;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：wxz11 on 2019/2/15 14:47
 * 滑动解锁的例子
 */
public class DragUnlockFragment extends BaseFragment {
    @BindView(R.id.dlv_lock)
    DragUnlockView dlv;

    @Override
    protected View inflateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_drag_unlock, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dlv.setDragListener(left -> {
            if (!left) {
                final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setMessage("加载中...").create();
                dlv.postDelayed(() -> {
                    alertDialog.dismiss();
                    Random random = new Random();
                    int i = random.nextInt(3);
                    if (i == 0) {
                        dlv.setDraggable(false);
                        Toast.makeText(getContext(), "解锁成功", Toast.LENGTH_SHORT).show();
                    } else if (i == 1) {
                        dlv.restUI();
                        Toast.makeText(getContext(), "解锁失败", Toast.LENGTH_SHORT).show();
                    } else if (i == 2) {
                        dlv.setDraggable(false);
                        dlv.setEnabled(false);
                        Toast.makeText(getContext(), "锁定中...", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
                alertDialog.show();
            }
        });
    }

    @OnClick({R.id.dlv_lock})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dlv_lock:
                Toast.makeText(getContext(), "todo...", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
