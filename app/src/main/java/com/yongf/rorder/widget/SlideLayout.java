/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014
 * 文件名: SlideLayout.java
 * 描述:
 * 修改历史:
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     17-2-19        新增:Create
 *  1.1         Scott Wang      17-2-19       新增：初步完善SlideLayout，使其支持多个子View（2个以上）
 */

package com.yongf.rorder.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * 侧滑Layout
 *
 * @author Scott Wang
 * @version 1.0, 17-1-26
 * @see
 * @since ROder V1.0
 */
public class SlideLayout extends ViewGroup {

    private static final String TAG = "SlideLayout";

    private View mContentView;

    private ViewDragHelper mHelper;

    private boolean isOpened;
    private OnSweepListener mListener;

    public SlideLayout(Context context) {
        this(context, null);
    }

    public SlideLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mHelper = ViewDragHelper.create(this, new MyCallBack());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        if (childCount == 1) {
            // 布局内容区域
            mContentView.layout(0, 0, mContentView.getMeasuredWidth(),
                    mContentView.getMeasuredHeight());
        } else if (childCount > 1) {
            // 布局内容区域
            mContentView.layout(0, 0, mContentView.getMeasuredWidth(),
                    mContentView.getMeasuredHeight());

            //布局其他子View
            int left = mContentView.getMeasuredWidth();
            for (int i = 1; i < childCount; i++) {
                View child = getChildAt(i);
                child.layout(left, 0, left + child.getMeasuredWidth(), child.getMeasuredHeight());
                left += child.getMeasuredWidth();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getChildCount() < 2) {
            return false;
        }
        mHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(SlideLayout.this);
        }
    }

    @Override
    protected void onFinishInflate() {
        mContentView = getChildAt(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        if (childCount == 1) {
            // 测量内容
            mContentView.measure(widthMeasureSpec, heightMeasureSpec);
        } else if (childCount > 1) {
            // 测量内容
            mContentView.measure(widthMeasureSpec, heightMeasureSpec);

            //测量其他子View
            for (int i = 1; i < childCount; i++) {
                View child = getChildAt(i);
                int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(child.getLayoutParams().width,
                        MeasureSpec.EXACTLY);
                child.measure(childWidthMeasureSpec, heightMeasureSpec);
            }
        }

        // 确定自己的高度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    /**
     * 打开控件
     */
    public void open() {
        isOpened = true;
        if (mListener != null) {
            mListener.onSweepChanged(SlideLayout.this, isOpened);
        }

        // 平滑滚动

        int childCount = getChildCount();
        int totalWidth = 0;
        for (int i = 1; i < childCount; i++) {
            totalWidth += getChildAt(i).getMeasuredWidth();
        }
        mHelper.smoothSlideViewTo(mContentView, -totalWidth, 0);
        int tempLeft = mContentView.getMeasuredWidth();
        for (int i = 1; i < childCount; i++) {
            View child = getChildAt(i);
            mHelper.smoothSlideViewTo(child, tempLeft - totalWidth, 0);
            tempLeft += child.getMeasuredWidth();
        }

        ViewCompat.postInvalidateOnAnimation(SlideLayout.this);
    }

    /**
     * 关闭控件
     */
    public void close() {
        isOpened = false;
        if (mListener != null) {
            mListener.onSweepChanged(SlideLayout.this, isOpened);
        }

        // 平滑滚动

        int childCount = getChildCount();
        mHelper.smoothSlideViewTo(mContentView, 0, 0);
        int tempLeft = mContentView.getMeasuredWidth();
        for (int i = 1; i < childCount; i++) {
            View child = getChildAt(i);
            mHelper.smoothSlideViewTo(child, tempLeft, 0);
            tempLeft += child.getMeasuredWidth();
        }

        ViewCompat.postInvalidateOnAnimation(SlideLayout.this);
    }

    public void setOnSweepListener(OnSweepListener listener) {
        this.mListener = listener;
    }

    public interface OnSweepListener {
        void onSweepChanged(SlideLayout view, boolean isOpened);
    }

    class MyCallBack extends ViewDragHelper.Callback {

        @Override
        public void onViewPositionChanged(View changedView, int left, int top,
                                          int dx, int dy) {
            ViewCompat.postInvalidateOnAnimation(SlideLayout.this);

            if (changedView == mContentView) {
                // 如果移动的是内容的view
                int dLeft = 0;
                for (int i = 1; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    child.layout(mContentView.getMeasuredWidth() + left + dLeft, 0,
                            mContentView.getMeasuredWidth() + left + dLeft + child.getMeasuredWidth(),
                            child.getMeasuredHeight());
                    dLeft += child.getMeasuredWidth();
                }
            } else {
                int index = -1;
                for (int i = 0; i < getChildCount(); i++) {
                    if (changedView == getChildAt(i)) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    //需要重新Layout各个子View
                    int leftWidth = 0;
                    for (int i = 0; i < index; i++) {
                        leftWidth += getChildAt(i).getMeasuredWidth();
                    }
                    int rightWidth = getChildAt(index).getMeasuredWidth();
                    for (int i = 0; i < getChildCount(); i++) {
                        //布局index左边的
                        if (i < index) {
                            getChildAt(i).layout(left - leftWidth, 0, left - leftWidth + getChildAt(i).getMeasuredWidth(),
                                    getChildAt(i).getMeasuredHeight());
                            leftWidth -= getChildAt(i).getMeasuredWidth();
                        } else if (i > index) {         //布局index右边的
                            getChildAt(i).layout(left + rightWidth, 0, left + rightWidth + getChildAt(i).getMeasuredWidth(),
                                    getChildAt(i).getMeasuredHeight());
                            rightWidth += getChildAt(i).getMeasuredWidth();
                        }
                    }
                }
            }

        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int totalWidth = 0;
            for (int i = 1; i < getChildCount(); i++) {
                totalWidth += getChildAt(i).getMeasuredWidth();
            }
            int left = mContentView.getLeft();
            if (-left < totalWidth / 2f) {
                // 关闭
                close();
            } else {
                // 打开
                open();
            }
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mContentView/* || child == mDeleteView*/;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            int totalWidth = 0;
            for (int i = 1; i < getChildCount(); i++) {
                totalWidth += getChildAt(i).getMeasuredWidth();
            }
            if (child == mContentView) {
                if (left < 0 && -left > totalWidth) {
                    return -totalWidth;
                } else if (left > 0) {
                    return 0;
                }
            }

            // 确定要移动到什么地方
            return left;
        }

    }

}
