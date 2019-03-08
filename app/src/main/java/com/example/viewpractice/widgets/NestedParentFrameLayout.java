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
 * Email : wangc@huitouche.com
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

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int nestedScrollAxes) {
        Log.d(TAG, "onStartNestedScroll : child " + child + " target : " + target + " nestedScrollAxes : " + nestedScrollAxes);
        if (nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL) {
            return true;
        }
        return super.onStartNestedScroll(child, target, nestedScrollAxes);
    }


    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes) {
        Log.d(TAG, "onNestedScrollAccepted : child " + child + " target : " + target + " axes : " + axes);
//        super.onNestedScrollAccepted(child, target, axes);
        mParentHelper.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onStopNestedScroll(@NonNull View child) {
        Log.d(TAG, "onStopNestedScroll : child " + child);
//        super.onStopNestedScroll(child);
        mParentHelper.onStopNestedScroll(child);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.d(TAG, "onNestedScroll : target " + target + " dxConsumed:" + dxConsumed + " dyConsumed: " + dyConsumed + " dxUnconsumed : " + dxUnconsumed + " dyUnconsumed: " + dyUnconsumed);
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

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
            consumedY = - (int) getY();
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

    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        Log.d(TAG, "onNestedFling: target : " + target + " velocityX : " + velocityX + " velocityY : " + velocityY + " consumed : " + consumed);
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        Log.d(TAG, "onNestedPreFling: target : " + target + " velocityX : " + velocityX + " velocityY : " + velocityY);
        return super.onNestedPreFling(target, velocityX, velocityY);
    }

    @Override
    public int getNestedScrollAxes() {
//        return super.getNestedScrollAxes();
        return mParentHelper.getNestedScrollAxes();
    }
}
