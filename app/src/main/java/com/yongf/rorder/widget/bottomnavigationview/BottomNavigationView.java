/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: BottomNavigationView						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-20       新增：Create	
 */

package com.yongf.rorder.widget.bottomnavigationview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yongf.rorder.R;

import java.util.ArrayList;
import java.util.List;

/**
 * BottomNavigationView
 *
 * @author Scott Wang
 * @version 1.0, 17-1-20
 * @see
 * @since ROder V0.1
 */
public class BottomNavigationView extends LinearLayout {

    private static final String TAG = "BottomNavigationView";

    private LinearLayout mLlNavContent;

    private int mDefaultTab;
    private int mSelectedTab;

    private BottomNavigationTab.OnTabSelectedListener mTabSelectedListener;
    private OnDefaultTabListener mDefaultTabListener;

    private List<BottomNavigationItem> mBottomNavigationItems = new ArrayList<>();
    private List<BottomNavigationTab> mBottomNavigationTabs = new ArrayList<>();

    public BottomNavigationView(Context context) {
        this(context, null);
    }

    public BottomNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.bottom_nav_view, this);

        mLlNavContent = (LinearLayout) findViewById(R.id.ll_nav_content);
    }

    public BottomNavigationView addItem(BottomNavigationItem item) {
        mBottomNavigationItems.add(item);
        return this;
    }

    /**
     * 设置默认第几个Tab
     *
     * @param position 默认第几个Tab，从0开始
     * @return
     */
    public BottomNavigationView defaultTab(int position) {
        position = position < 0 ? 0 : position;
        position = position >= mBottomNavigationItems.size() ? mBottomNavigationItems.size() - 1 : position;
        mDefaultTab = position;
        mSelectedTab = position;
        return this;
    }

    /**
     * 不要在这里执行耗时事件！
     *
     * @param listener
     * @return
     */
    public BottomNavigationView onDefaultTab(OnDefaultTabListener listener) {
        mDefaultTabListener = listener;
        return this;
    }

    /**
     * This method must be called after you have added all Items.
     */
    public void build() {
        for (int i = 0; i < mBottomNavigationItems.size(); i++) {
            BottomNavigationItem item = mBottomNavigationItems.get(i);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_nav_tab, null);
            BottomNavigationTab tab = new BottomNavigationTab(this, getContext(), view, i);
            mBottomNavigationTabs.add(tab);
            BottomNavigationHelper.bindTabWithItem(tab, item);
            LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            mLlNavContent.addView(tab.getTab(), params);
        }

        if (mDefaultTabListener != null) {
            mDefaultTabListener.onDefaultTab(mDefaultTab);
        }

        setSelectedTab(mSelectedTab);
    }

    public int getSelectedTab() {
        return mSelectedTab;
    }

    /**
     * 设置选中的Tab
     *
     * @param position
     */
    public void setSelectedTab(int position) {
        mSelectedTab = position;

        for (int i = 0; i < mBottomNavigationTabs.size(); i++) {
            BottomNavigationTab tab = mBottomNavigationTabs.get(i);
            tab.setSelected(i == mSelectedTab ? true : false);          //初始化tab之后再设置状态！
        }
    }

    public void setTabSelectedListener(BottomNavigationTab.OnTabSelectedListener tabSelectedListener) {
        mTabSelectedListener = tabSelectedListener;

        for (BottomNavigationTab tab : mBottomNavigationTabs) {
            tab.setOnTabSelectedListener(mTabSelectedListener);
        }
    }

    /**
     * 加载默认Tab时的监听事件
     */
    public interface OnDefaultTabListener {
        void onDefaultTab(int position);
    }
}
