package com.example.viewpractice.detail;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viewpractice.R;
import com.example.viewpractice.fragments.BaseFragment;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：wxz11 on 2019/2/15 14:47
 */
public class AnimationFragment extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    @BindView(R.id.tv_translation)
    TextView tvTranslation;
    @BindView(R.id.clt_root)
    ConstraintLayout cltRoot;

    @Override
    protected View inflateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_animation, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        getXAndLeft();
    }

//    private void getXAndLeft() {
//        tvTranslation.setOnTouchListener(new View.OnTouchListener() {
//            //在这里让view做唯一属性动画，打印left等边界值
//            int lastX, LastY;
//            Toast toast = Toast.makeText(getContext(), "", Toast.LENGTH_SHORT);
//            //获取状态栏高度
//            int toolbarHeight = getActivity().getWindow().getDecorView().getHeight() - cltRoot.getHeight();
//            int widthOffset = tvTranslation.getWidth() / 2;
//            int heightOffset = tvTranslation.getHeight() / 2;
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    Log.d(TAG, "onTouch: Down ");
//                    lastX = (int) event.getRawX();
//                    LastY = (int) event.getRawY();
//                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                    Log.d(TAG, "onTouch: move");
//                    int x = (int) event.getRawX();
//                    int y = (int) event.getRawY();
////                    tvTranslation.setTranslationX(x-tvTranslation.getLeft()-widthOffset);
////                    tvTranslation.setTranslationY(y-tvTranslation.getTop()-heightOffset-toolbarHeight);
//                    tvTranslation.setTranslationX(x - lastX);
//                    tvTranslation.setTranslationY(y - LastY);
//                    toast.setText(String.format("left:%d;top:%d;right:%d;bottom:%d", tvTranslation.getLeft(), tvTranslation.getTop(), tvTranslation.getRight(), tvTranslation.getBottom()));
//                    toast.show();
//                } else if (event.getAction() == MotionEvent.ACTION_UP) {
//                    Log.d(TAG, "onTouch: up");
//                    lastX = (int) event.getRawX();
//                    LastY = (int) event.getRawY();
//                }
//                return true;
//            }
//        });
//    }

    //这里之所以绑定点击事件，就是为了看，动画前和动画后，点击区域的变化，来说明补间动画，不会改变view的触摸区域，
    // View原来位置在哪里，动画之后，还是需要点击原来的地方，View才能接收到事件，新位置是无法接收到事件的
    //可以在开发者选项里面，开启布局边界选项来验证
    @OnClick({R.id.tv_translation, R.id.btn_animation})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_translation:
                Toast.makeText(getContext(), "点到我了", Toast.LENGTH_SHORT).show();
                showReason();
                break;
            case R.id.btn_animation:
                doAnimation();
                break;
        }

    }

    private void showReason() {
        tvTranslation.getX();
        tvTranslation.getLeft();
        /**
         * 具体参照这两个方法
         * tvTranslation.getX();getX获取的是View可见的X位置，以像素为单位，包括getLeft和translateX,而属性动画，改变的就是translateX这些变量
         * tvTranslation.getLeft();getLeft获取的是View相对于父布局的左边界位置，这个是布局参数确定的
         * 当开启开发模式的布局边界，可以看到View在做属性动画的时候，left，right，top，bottom都是没有改变的，
         * */

    }

    private void doAnimation() {
        //创建动画，x，y坐标是相对于View在父布局中的左上角，即(0,0)
        TranslateAnimation animation = new TranslateAnimation(0, 500, 0, 230);
        animation.setDuration(1000);
        //保持结束的状态
        animation.setFillAfter(true);
        tvTranslation.startAnimation(animation);
    }
}
