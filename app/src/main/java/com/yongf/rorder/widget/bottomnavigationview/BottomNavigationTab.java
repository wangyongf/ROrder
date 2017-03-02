/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: BottomNavigationTab						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-20       新增：Create	
 */

package com.yongf.rorder.widget.bottomnavigationview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yongf.rorder.R;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * BottomNavigationTab
 *
 * @author Scott Wang
 * @version 1.0, 17-1-20
 * @see
 * @since ROder V0.1
 */
public class BottomNavigationTab {

    public static final int TYPE_SINGLE_CLICK = 0;                  //单击
    public static final int TYPE_DOUBLE_CLICK = 1;                  //双击
    private static final String TAG = "BottomNavigationTab";

    private static final int TYPE_CLICK = 0;

    private BottomNavigationView mNavView;

    private int mIndex;          //标记当前是第几个Tab

    private Context mContext;
    private LinearLayout mTab;
    private ImageView mIvNavTab;
    private TextView mTvNavTab;

    /**
     * 选中状态
     */
    private boolean isSelected;

    private int mSelectedIconResId;
    private int mUnselectedIconResId;
    private String mText;
    private int mSelectedTextColor;
    private int mUnselectedTextColor;

    private OnTabClickListener mOnTabClickListener;
    private OnTabDoubleClickListener mOnTabDoubleClickListener;

    private long mDownTime = 0;              //按下的时刻
    private long mUpTime = 0;                    //放松的时刻
    private AtomicInteger mClickCount = new AtomicInteger(0);               //点击次数

    public BottomNavigationTab(BottomNavigationView bottomNavigationView, Context context, View view, int index) {
        mNavView = bottomNavigationView;
        mContext = context;
        mIndex = index;

        initView(context, view);
        initEvent();
    }

    /**
     * 初始化视图
     *
     * @param context
     * @param view
     */
    private void initView(Context context, View view) {
        if (view instanceof LinearLayout) {
            mTab = (LinearLayout) view;
        } else {
            throw new IllegalArgumentException("Illegal argument view!");
        }

        mIvNavTab = (ImageView) mTab.findViewById(R.id.iv_nav_tab);
        mTvNavTab = (TextView) mTab.findViewById(R.id.tv_nav_tab);
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        MyClickHandler handler = new MyClickHandler();

        // FIXME: 17-3-3 啥时候完成单击事件和双击事件
        // TODO: 17-3-3 到时候顺便把长按事件给做了
        mTab.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mDownTime = System.currentTimeMillis();
                    if (mDownTime - mUpTime > getDoubleClickInterval()) {
                        mClickCount.set(0);
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    mUpTime = System.currentTimeMillis();
                    if (mUpTime - mDownTime <= getClickInterval()) {
                        mClickCount.addAndGet(1);
                        Message message = Message.obtain();
                        message.what = TYPE_CLICK;
                        message.arg1 = mClickCount.intValue();
                        handler.sendMessageDelayed(message, getDoubleClickInterval());
                    } else {
                        Message message = Message.obtain();
                        message.what = TYPE_CLICK;
                        message.arg1 = mClickCount.intValue();
                        handler.sendMessage(message);
                        mClickCount.set(0);
                    }

                    break;
                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_CANCEL:
                default:
                    break;
            }

            return true;
        });
    }

    /**
     * 上一次抬起和本次按下之间不超过300ms，算作一次连续点击
     *
     * @return
     */
    public int getDoubleClickInterval() {
        return 800;
    }

    /**
     * 按下和抬起之间不超过100ms，算作一次点击
     *
     * @return
     */
    public int getClickInterval() {
        return 300;
    }

    /**
     * 处理单击
     */
    private void performSingleClick() {
        if (mOnTabClickListener != null) {
            mOnTabClickListener.onTabClick(mIndex);
        }
    }

    /**
     * 处理双击
     */
    private void performDoubleClick() {
        if (mOnTabDoubleClickListener != null) {
            mOnTabDoubleClickListener.onTabDoubleClick(mIndex);
        }
    }

    public LinearLayout getTab() {
        return mTab;
    }

    /**
     * 设置文字
     *
     * @param text
     */
    public void setText(String text) {
        mText = text;
    }

    public int getSelectedIconResId() {
        return mSelectedIconResId;
    }

    public void setSelectedIconResId(int selectedIconResId) {
        mSelectedIconResId = selectedIconResId;
    }

    public int getUnselectedIconResId() {
        return mUnselectedIconResId;
    }

    public void setUnselectedIconResId(int unselectedIconResId) {
        mUnselectedIconResId = unselectedIconResId;
    }

    public String getText() {
        return mText;
    }

    /**
     * 直接设置文字
     *
     * @param resId
     */
    public void setText(int resId) {
        mTvNavTab.setText(getContext().getString(resId));
    }

    private Context getContext() {
        return mContext;
    }

    public int getSelectedTextColor() {
        return mSelectedTextColor;
    }

    public void setSelectedTextColor(int selectedTextColor) {
        mSelectedTextColor = selectedTextColor;
    }

    public int getUnselectedTextColor() {
        return mUnselectedTextColor;
    }

    public void setUnselectedTextColor(int unselectedTextColor) {
        mUnselectedTextColor = unselectedTextColor;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        toggle();
    }

    /**
     * 切换选中状态
     */
    private void toggle() {
        mTvNavTab.setText(mText);
        if (isSelected) {
            mIvNavTab.setImageResource(mSelectedIconResId);
            mTvNavTab.setTextColor(getContext().getResources().getColor(mSelectedTextColor));
        } else {
            mIvNavTab.setImageResource(mUnselectedIconResId);
            mTvNavTab.setTextColor(getContext().getResources().getColor(mUnselectedTextColor));
        }
    }

    public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
        mOnTabClickListener = onTabClickListener;
    }

    public void setOnTabDoubleClickListener(OnTabDoubleClickListener onTabDoubleClickListener) {
        mOnTabDoubleClickListener = onTabDoubleClickListener;
    }

    /**
     * 重置计数器
     */
    private void reset() {
        mClickCount.set(0);
        mDownTime = 0;
        mUpTime = 0;
    }

    public interface OnTabClickListener {
        void onTabClick(int position);
    }

    public interface OnTabDoubleClickListener {
        void onTabDoubleClick(int position);
    }

    /**
     * 根据点击次数，执行点击事件
     */
    public class MyClickHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            // TODO: 2017/3/2 在这里判断点击次数，执行相应逻辑
            // FIXME: 2017/3/2 上面的写法还是有问题！
            switch (msg.what) {
                case TYPE_CLICK:
                    if (mClickCount.intValue() < msg.arg1) {
                        if (mClickCount.intValue() == 1) {              //处理单击事件
                            performSingleClick();
                        } else if (mClickCount.intValue() == 2) {           //处理双击事件
                            performDoubleClick();
                        }

                        //消费完点击事件之后，重置计数器
                        reset();
                    }
                    break;
            }
        }
    }
}
