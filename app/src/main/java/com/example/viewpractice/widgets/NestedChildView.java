package com.example.viewpractice.widgets;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Arrays;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;

/**
 * 作者：wxz11 on 2019/3/8 17:58
 * Email : wangc@huitouche.com
 */
public class NestedChildView extends View implements NestedScrollingChild {
    private static final String TAG = "NestedChildView";

    private NestedScrollingChildHelper childHelper;
    private int downY;
    private int[] consumed = new int[2];
    private int[] offsetInWindow = new int[2];

    public NestedChildView(Context context) {
        super(context);
        init(context);
    }

    public NestedChildView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NestedChildView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NestedChildView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        childHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = y;
                //开启垂直方向的NestedScrolling
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = y - downY;
                Log.d(TAG, String.format("dy = %d",dy));
                if (dispatchNestedPreScroll(0, dy, consumed, offsetInWindow)) {
                    dy -= consumed[1];
                    Log.d(TAG, String.format("dy = %d",dy));
                }
                setY(Math.min(Math.max(getY() + dy, 0), ((View) getParent()).getHeight() - getHeight()));
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                stopNestedScroll();
                break;
        }
        return true;
    }

    @Override
    public boolean isNestedScrollingEnabled() {
//        boolean b = super.isNestedScrollingEnabled();
        boolean b = childHelper.isNestedScrollingEnabled();
        Log.d(TAG, "isNestedScrollingEnabled: " + b);
        return b;
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
//        super.setNestedScrollingEnabled(enabled);
        childHelper.setNestedScrollingEnabled(true);
    }

    @Override
    public boolean startNestedScroll(int axes) {
        Log.d(TAG, "startNestedScroll: axes" + axes);
//        return super.startNestedScroll(axes);
        return childHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        Log.d(TAG, "stopNestedScroll: ");
//        super.stopNestedScroll();
        childHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
//        boolean b = super.hasNestedScrollingParent();
        boolean b = childHelper.hasNestedScrollingParent();
        Log.d(TAG, "hasNestedScrollingParent: " + b);
        return b;
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow) {
        Log.d(TAG, "dispatchNestedScroll: dxConsumed : " + dxConsumed + " dyConsumed: " + dyConsumed + " dxUnconsumed: " + dxUnconsumed + " dyUnconsumed: " + dyUnconsumed + " offsetInWindow : " + Arrays.toString(offsetInWindow));
//        return super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
        return childHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow) {
//        boolean b = super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
        boolean b = childHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
        Log.d(TAG, "dispatchNestedPreScroll: " + b);
        return b;
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        Log.d(TAG, "dispatchNestedFling: velocityX : " + velocityX + " velocityY: " + velocityY + " consumed: " + consumed);
//        return super.dispatchNestedFling(velocityX, velocityY, consumed);
        return childHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        Log.d(TAG, "dispatchNestedPreFling: velocityX : " + velocityX + " velocityY :" + velocityY);
//        return super.dispatchNestedPreFling(velocityX, velocityY);
        return childHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

}
