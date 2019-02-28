package com.example.viewpractice.widgets;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.example.viewpractice.R;

import androidx.annotation.RequiresApi;
import androidx.core.view.ViewConfigurationCompat;

/**
 * 作者：wxz11 on 2019/2/28 11:01
 */
public class DragUnlockView extends RelativeLayout {
    private static final String TAG = "DragUnlockView";
    private View dragView;
    private int originalLeft;
    private boolean draggable = true;

    public DragUnlockView(Context context) {
        super(context);
    }

    public DragUnlockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragUnlockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DragUnlockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
        //先恢复原样
        restUI();
        if (draggable) {
            dragView.setVisibility(VISIBLE);
        } else {
            dragView.setVisibility(GONE);
        }
    }

    public void restUI() {
        dragView.setTranslationX(originalLeft);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount != 2)
            throw new IllegalArgumentException("DragUnlockView only support two children");
        dragView = getChildAt(1);
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) dragView.getLayoutParams();
        int[] rules = layoutParams.getRules();
        if (rules[ALIGN_PARENT_RIGHT] == 1)
            throw new IllegalArgumentException("DragUnlockView only support swipe from left");
        originalLeft = dragView.getLeft();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (draggable) {
            return true;
        } else
            return super.onInterceptTouchEvent(ev);
    }

    private int startX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (draggable) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = (int) event.getX();
//                    dragView.setTranslationX(startX);
                    Log.d(TAG, "onTouchEvent: startX : " + startX);
                    return true;
                case MotionEvent.ACTION_MOVE:
                    setAlpha(0.8f);
                    int moveX = (int) event.getX();
                    int deltaX = moveX - startX;
                    if (deltaX < 0) deltaX = 0;
                    if (deltaX > (getWidth() - getPaddingLeft() - getPaddingRight() - dragView.getWidth()))
                        deltaX = getWidth() - getPaddingLeft() - getPaddingRight() - dragView.getWidth();
                    dragView.setTranslationX(deltaX);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    int upX = (int) event.getX();
                    int dx = upX - startX;
//                    判断抬手的位置，大于二分之一，就自动移动到右边，否则就回到起点
                    if (dx >= (getWidth() - getPaddingLeft() - getPaddingRight() - dragView.getWidth()) / 2) {
                        animateEnd(getWidth() - dragView.getWidth() - getPaddingRight() - getPaddingLeft(), computeDuration(dx), false);
                    } else {
                        animateEnd(originalLeft, computeDuration(dx), true);
                    }
                    startX = upX;
                    break;
            }
            return true;
        } else
            return super.onTouchEvent(event);
    }

    private int computeDuration(int dx) {
        if (dx < 0) dx = 0;
        if (dx > (getWidth() - getPaddingLeft() - getPaddingRight() - dragView.getWidth()))
            dx = getWidth() - getPaddingLeft() - getPaddingRight() - dragView.getWidth();
        int max = 250;//最大动画时间250ms
        double peer = max * 1.0 / (getWidth() - getPaddingLeft() - getPaddingRight() - dragView.getWidth());
        if (dx >= getWidth() / 2) {
            return (int) ((getWidth() - dx) * peer);
        } else {
            return (int) (dx * peer);
        }
    }

    private boolean isAnimating = false;

    private void animateEnd(int endX, int duration, final boolean left) {
        if (isAnimating) return;
        ObjectAnimator translationX = ObjectAnimator.ofFloat(dragView, "translationX", endX);
        translationX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimating = false;
                setAlpha(1);
                if (null != dragListener)
                    dragListener.onDragEnd(left);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        translationX.setDuration(duration).start();
    }

    private DragListener dragListener;

    public void setDragListener(DragListener dragListener) {
        this.dragListener = dragListener;
    }

    public interface DragListener {
        void onDragEnd(boolean left);
    }
}
