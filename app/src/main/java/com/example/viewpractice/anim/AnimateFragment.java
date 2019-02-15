package com.example.viewpractice.anim;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viewpractice.R;
import com.example.viewpractice.fragments.BaseFragment;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：wxz11 on 2019/2/15 14:47
 */
public class AnimateFragment extends BaseFragment {
    @BindView(R.id.tv_translation)
    TextView tvTranslation;
    @BindView(R.id.sb_x)
    SeekBar sbX;

    @Override
    protected View inflateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_animate, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sbX.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    //这里之所以绑定点击事件，就是为了看，动画前和动画后，点击区域的变化，来说明补间动画，不会改变view的触摸区域，
    // View原来位置在哪里，动画之后，还是需要点击原来的地方，View才能接收到事件，新位置是无法接收到事件的
    //可以在开发者选项里面，开启布局边界选项来验证
    @OnClick(R.id.tv_translation)
    public void onClick(View view){
        Toast.makeText(getContext(),"点到我了",Toast.LENGTH_SHORT).show();
    }
}
