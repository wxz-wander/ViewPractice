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
 *  NestedScrollingChild。
 *  如果你有一个可以滑动的 View，需要被用来作为嵌入滑动的子 View，就必须实现本接口。
 *  在此 View 中，包含一个 NestedScrollingChildHelper 辅助类。
 *  NestedScrollingChild 接口的实现，基本上就是调用本 Helper 类的对应的函数即可，因为 Helper 类中已经实现好了 Child 和 Parent 交互的逻辑。原来的 View 的处理 Touch 事件，并实现滑动的逻辑大体上不需要改变。
 *
 * 需要做的就是，如果要准备开始滑动了，需要告诉 Parent，你要准备进入滑动状态了，调用 startNestedScroll()。
 * 你在滑动之前，先问一下你的 Parent 是否需要滑动，也就是调用 dispatchNestedPreScroll()。如
 * 果父类滑动了一定距离，你需要重新计算一下父类滑动后剩下给你的滑动距离余量。然后，你自己进行余下的滑动。
 * 最后，如果滑动距离还有剩余，你就再问一下，Parent 是否需要在继续滑动你剩下的距离，也就是调用 dispatchNestedScroll()。
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

    /**
     * 设置嵌套滑动是否可用
     * @param enabled :true表示view使用嵌套滚动,false表示禁用.
     * */
    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
//        super.setNestedScrollingEnabled(enabled);
        childHelper.setNestedScrollingEnabled(true);
    }

    /**
     * 开始嵌套滑动
     * @param axes :表示滚动的方向如:ViewCompat.SCROLL_AXIS_VERTICAL(垂直方向滚动)和
     *     ViewCompat.SCROLL_AXIS_HORIZONTAL(水平方向滚动)
     * @return :true表示本次滚动支持嵌套滚动,false不支持
     * */
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

    /**
     * 分发嵌套滚动的操作给当前View嵌套滚动的父级.这是一个代理委托方法
     * @param dxConsumed 表示view消费了x方向的距离长度
     * @param dyConsumed 表示view消费了y方向的距离长度
     * @param dxUnconsumed 表示滚动产生的x滚动距离还剩下多少没有消费
     * @param dyUnconsumed 表示滚动产生的y滚动距离还剩下多少没有消费
     * @param offsetInWindow  表示剩下的距离dxUnconsumed和dyUnconsumed使得view在父布局中的位置偏移了多少
     * @return 如果父级消费了嵌套滑动距离，返回true
     * */
    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow) {
        Log.d(TAG, "dispatchNestedScroll: dxConsumed : " + dxConsumed + " dyConsumed: " + dyConsumed + " dxUnconsumed: " + dxUnconsumed + " dyUnconsumed: " + dyUnconsumed + " offsetInWindow : " + Arrays.toString(offsetInWindow));
//        return super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
        return childHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    /**
     * 执行嵌套滑动操作之前，通知父级
     * @param dx 表示view本次x方向的滚动的总距离长度
     * @param dy 表示view本次y方向的滚动的总距离长度
     * @param consumed 表示父布局消费的距离,consumed[0]表示x方向,consumed[1]表示y方向
     * @param offsetInWindow 表示剩下的距离dxUnconsumed和dyUnconsumed使得view在父布局中的位置偏移了多少
     * @return 如果父级消费嵌套滑动返回 true
     * */
    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow) {
//        boolean b = super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
        boolean b = childHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
        Log.d(TAG, "dispatchNestedPreScroll: " + b);
        return b;
    }

    /**
     * 将嵌套的fling操作发送到当前嵌套的滚动父级。
     * */
    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        Log.d(TAG, "dispatchNestedFling: velocityX : " + velocityX + " velocityY: " + velocityY + " consumed: " + consumed);
//        return super.dispatchNestedFling(velocityX, velocityY, consumed);
        return childHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    /**
     * 将嵌套的预先执行操作分派给当前嵌套的滚动父级
     * @param velocityX x滚动速率
     * @param velocityY y滚动速度
     * */
    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        Log.d(TAG, "dispatchNestedPreFling: velocityX : " + velocityX + " velocityY :" + velocityY);
//        return super.dispatchNestedPreFling(velocityX, velocityY);
        return childHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

}
