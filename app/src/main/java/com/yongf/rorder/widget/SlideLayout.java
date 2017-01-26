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

    private static final String TAG = "SweepView";

    private View mContentView;
    private View mDeleteView;
    private int mDeleteWidth;

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
        // 布局孩子
        // 布局内容区域
        mContentView.layout(0, 0, mContentView.getMeasuredWidth(),
                mContentView.getMeasuredHeight());

        // 布局删除的部分
        mDeleteView.layout(mContentView.getMeasuredWidth(), 0,
                mContentView.getMeasuredWidth() + mDeleteWidth,
                mDeleteView.getMeasuredHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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
        mDeleteView = getChildAt(1);

        LayoutParams params = mDeleteView.getLayoutParams();
        mDeleteWidth = params.width;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // 测量内容
        mContentView.measure(widthMeasureSpec, heightMeasureSpec);

        // 测量删除部分
        int deleteWidthMeasureSpec = MeasureSpec.makeMeasureSpec(mDeleteWidth,
                MeasureSpec.EXACTLY);
        mDeleteView.measure(deleteWidthMeasureSpec, heightMeasureSpec);

        // 确定自己的高度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    public void open() {
        isOpened = true;
        if (mListener != null) {
            mListener.onSweepChanged(SlideLayout.this, isOpened);
        }

        int contentWidth = mContentView.getMeasuredWidth();
        int contentHeight = mContentView.getMeasuredHeight();
        int deleteHeight = mDeleteView.getMeasuredHeight();

        // 平滑滚动
        mHelper.smoothSlideViewTo(mContentView, -mDeleteWidth, 0);
        mHelper.smoothSlideViewTo(mDeleteView, contentWidth - mDeleteWidth, 0);

        ViewCompat.postInvalidateOnAnimation(SlideLayout.this);
    }

    public void close() {
        isOpened = false;

        if (mListener != null) {
            mListener.onSweepChanged(SlideLayout.this, isOpened);
        }

        // 平滑滚动
        mHelper.smoothSlideViewTo(mContentView, 0, 0);
        mHelper.smoothSlideViewTo(mDeleteView, mContentView.getMeasuredWidth(), 0);

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

            int contentWidth = mContentView.getMeasuredWidth();
            int contentHeight = mContentView.getMeasuredHeight();
            int deleteHeight = mDeleteView.getMeasuredHeight();
            if (changedView == mContentView) {
                // 如果移动的是内容的view
                mDeleteView.layout(contentWidth + left, 0, contentWidth + left
                        + mDeleteWidth, deleteHeight);

            } else if (changedView == mDeleteView) {
                mContentView.layout(left - contentWidth, 0, left, contentHeight);
            }

        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int left = mContentView.getLeft();
            if (-left < mDeleteWidth / 2f) {
                // 关闭
                close();
            } else {
                // 打开
                open();
            }
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mContentView || child == mDeleteView;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == mContentView) {
                if (left < 0 && -left > mDeleteWidth) {
                    return -mDeleteWidth;
                } else if (left > 0) {
                    return 0;
                }
            } else if (child == mDeleteView) {
                int measuredWidth = mContentView.getMeasuredWidth();
                if (left < measuredWidth - mDeleteWidth) {
                    return measuredWidth - mDeleteWidth;
                } else if (left > measuredWidth) {
                    return measuredWidth;
                }
            }

            // 确定要移动多少
            return left;
        }

    }

}
