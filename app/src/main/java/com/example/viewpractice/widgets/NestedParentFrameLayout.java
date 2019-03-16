package com.example.viewpractice.widgets;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;

/**
 * 作者：wxz11 on 2019/3/8 16:53
 * 作为一个可以嵌入 NestedScrollingChild 的父 View，需要实现 NestedScrollingParent，这个接口方法和 NestedScrollingChild 大致有一一对应的关系。
 * 同样，也有一个 NestedScrollingParentHelper 辅助类来默默的帮助你实现和 Child 交互的逻辑。
 * 滑动动作是 Child 主动发起，Parent 就收滑动回调并作出响应。
 *
 * 从Child 分析可知，滑动开始的调用 startNestedScroll()，Parent 收到 onStartNestedScroll() 回调，决定是否需要配合 Child 一起进行处理滑动，如果需要配合，还会回调 onNestedScrollAccepted()。
 *
 * 每次滑动前，Child 先询问 Parent 是否需要滑动，即 dispatchNestedPreScroll()，这就回调到 Parent 的 onNestedPreScroll()，Parent 可以在这个回调中“劫持”掉 Child 的滑动，也就是先于 Child 滑动。
 *
 * Child 滑动以后，会调用 onNestedScroll()，回调到 Parent 的 onNestedScroll()，这里就是 Child 滑动后，剩下的给 Parent 处理，也就是 后于 Child 滑动。
 *
 * 最后，滑动结束，调用 onStopNestedScroll() 表示本次处理结束。
 */
public class NestedParentFrameLayout extends FrameLayout implements NestedScrollingParent {
    private static final String TAG = "NestedParentFrameLayout";
    private NestedScrollingParentHelper mParentHelper;

    public NestedParentFrameLayout(@NonNull Context context) {
        super(context);
        init(context);
    }

    public NestedParentFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NestedParentFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NestedParentFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mParentHelper = new NestedScrollingParentHelper(this);
    }

    /**
     * 有嵌套滑动到来了，问下该父View是否接受嵌套滑动
     * @param child 嵌套滑动对应的父类的子类(因为嵌套滑动对于的父View不一定是一级就能找到的，可能挑了两级父View的父View，child的辈分>=target)
     * @param target 具体嵌套滑动的那个子类
     * @param nestedScrollAxes 支持嵌套滚动轴。水平方向，垂直方向，或者不指定
     * @return 是否接受该嵌套滑动
     */
    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int nestedScrollAxes) {
        Log.d(TAG, "onStartNestedScroll : child " + child + " target : " + target + " nestedScrollAxes : " + nestedScrollAxes);
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
//        if (nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL) {
//            return true;
//        }
//        return super.onStartNestedScroll(child, target, nestedScrollAxes);
    }

    /**
     * 该View接受了嵌套滑动的请求该函数调用。onStartNestedScroll返回true该函数会被调用。
     * 参数和onStartNestedScroll一样
     */
    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes) {
        Log.d(TAG, "onNestedScrollAccepted : child " + child + " target : " + target + " axes : " + axes);
//        super.onNestedScrollAccepted(child, target, axes);
        mParentHelper.onNestedScrollAccepted(child, target, axes);
    }

    /**
     * 停止嵌套滑动
     * */
    @Override
    public void onStopNestedScroll(@NonNull View child) {
        Log.d(TAG, "onStopNestedScroll : child " + child);
//        super.onStopNestedScroll(child);
        mParentHelper.onStopNestedScroll(child);
    }

    /**
     * 嵌套滑动的子View在滑动之后报告过来的滑动情况
     *
     * @param target 具体嵌套滑动的那个子类
     * @param dxConsumed 水平方向嵌套滑动的子View滑动的距离(消耗的距离)
     * @param dyConsumed 垂直方向嵌套滑动的子View滑动的距离(消耗的距离)
     * @param dxUnconsumed 水平方向嵌套滑动的子View未滑动的距离(未消耗的距离)
     * @param dyUnconsumed 垂直方向嵌套滑动的子View未滑动的距离(未消耗的距离)
     */
    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.d(TAG, "onNestedScroll : target " + target + " dxConsumed:" + dxConsumed + " dyConsumed: " + dyConsumed + " dxUnconsumed : " + dxUnconsumed + " dyUnconsumed: " + dyUnconsumed);
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    /**
     * 嵌套滑动的子View在滑动操作之前告诉父级，滑动情况
     * @param target 嵌套滑动的具体子类
     * @param dx 嵌套滑动子View在x方向想要滑动的距离
     * @param dy 嵌套滑动子View在y方向想要滑动的距离
     * @param consumed 这个参数要我们在实现这个函数的时候指定，回头告诉子View当前父View消耗的距离，
     *  consumed[0] 水平消耗的距离，consumed[1] 垂直消耗的距离 好让子view做出相应的调整
     * */
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed) {
        Log.d(TAG, "onNestedPreScroll: target : " + target + " dx : " + dx + " dy : " + dy + " consumed : " + Arrays.toString(consumed));
//        super.onNestedPreScroll(target, dx, dy, consumed);
        // 应该移动的Y距离
        final float shouldMoveY = getY() + dy;

        // 获取到父View的容器的引用，这里假定父View容器是View
        final View parent = (View) getParent();

        int consumedY;
        // 如果超过了父View的上边界，只消费子View到父View上边的距离
        if (shouldMoveY <= 0) {
            consumedY = -(int) getY();
        } else if (shouldMoveY >= parent.getHeight() - getHeight()) {
            // 如果超过了父View的下边界，只消费子View到父View
            consumedY = (int) (parent.getHeight() - getHeight() - getY());
        } else {
            // 其他情况下全部消费
            consumedY = dy;
        }

        // 对父View进行移动
        setY(getY() + consumedY);

        // 将父View消费掉的放入consumed数组中
        consumed[1] = consumedY;
        Log.d(TAG, "onNestedPreScroll: target : " + target + " dx : " + dx + " dy : " + dy + " consumed : " + Arrays.toString(consumed));

    }

    /**
     * 嵌套滑动的子View在fling之后告诉父级的fling情况
     * @param target 具体嵌套滑动的子类
     * @param velocityX
     * @param velocityY
     * @param consumed 子View是否消费了fling事件
     * @return 是否消费了fling事件
     * */
    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        Log.d(TAG, "onNestedFling: target : " + target + " velocityX : " + velocityX + " velocityY : " + velocityY + " consumed : " + consumed);
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    /**
     * 嵌套滑动的子View在fling之前告诉fling的情况
     * @param target 具体嵌套滑动的子类
     * @param velocityX 水平方向的速度
     * @param velocityY
     * */
    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        Log.d(TAG, "onNestedPreFling: target : " + target + " velocityX : " + velocityX + " velocityY : " + velocityY);
        return super.onNestedPreFling(target, velocityX, velocityY);
    }

    /**
     *获取当前ViewGroup嵌套滑动的轴，水平滑动，还是垂直滑动
     * @see ViewCompat#SCROLL_AXIS_HORIZONTAL 垂直
     * @see ViewCompat#SCROLL_AXIS_VERTICAL 水平
     * @see ViewCompat#SCROLL_AXIS_NONE 都支持
     * */
    @Override
    public int getNestedScrollAxes() {
//        return super.getNestedScrollAxes();
        return mParentHelper.getNestedScrollAxes();
    }
}
