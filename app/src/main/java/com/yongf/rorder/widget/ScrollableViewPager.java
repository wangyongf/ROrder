package com.yongf.rorder.widget;
/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: ScrollableViewPager						
 *
 * 修改历史:
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-3-19       新增：Create	
 */

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自定义ViewPager，控制其是否可以滑动，以及是否有滑动时的动画效果
 *
 * @author Scott Wang
 * @version 1.0, 17-3-19
 * @see
 * @since ROder V1.0
 */
public class ScrollableViewPager extends ViewPager {

    private boolean isScrollable;               //ViewPager是否可以滑动
    private boolean isAnimationEnabled;             //ViewPager滑动时是否有动画

    public ScrollableViewPager(Context context) {
        super(context);
    }

    public ScrollableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isScrollable() {
        return isScrollable;
    }

    public void setScrollable(boolean scrollable) {
        isScrollable = scrollable;
    }

    public boolean isAnimationEnabled() {
        return isAnimationEnabled;
    }

    public void setAnimationEnabled(boolean animationEnabled) {
        isAnimationEnabled = animationEnabled;
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, isAnimationEnabled);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isScrollable) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isScrollable) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }
}
