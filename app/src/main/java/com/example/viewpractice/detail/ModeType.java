package com.example.viewpractice.detail;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 作者：wxz11 on 2019/2/15 16:41
 */
public class ModeType {
    /**
     * 补间动画
     */
    public static final int TYPE_ANIMATION = 0;
    /**
     * 属性动画
     */
    public static final int TYPE_ANIMATE = 1;
    /**
     * 属性动画
     */
    public static final int TYPE_DRAG_UNLOCK = 2;

    //定义注解
    @IntDef({TYPE_ANIMATION, TYPE_ANIMATE,TYPE_DRAG_UNLOCK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }
}
