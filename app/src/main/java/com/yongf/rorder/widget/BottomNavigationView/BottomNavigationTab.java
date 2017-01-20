/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: BottomNavigationTab						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-20       新增：Create	
 */

package com.yongf.rorder.widget.BottomNavigationView;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yongf.rorder.R;

/**
 * BottomNavigationTab
 *
 * @author Scott Wang
 * @version 1.0, 17-1-20
 * @see
 * @since ROder V0.1
 */
public class BottomNavigationTab {

    private static final String TAG = "BottomNavigationTab";

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

    public BottomNavigationTab(Context context, View view) {
        mContext = context;

        initView(context, view);
    }

    private void initView(Context context, View view) {
        if (view instanceof LinearLayout) {
            mTab = (LinearLayout) view;
        } else {
            throw new IllegalArgumentException("Illegal argument view!");
        }

        mIvNavTab = (ImageView) mTab.findViewById(R.id.iv_nav_tab);
        mTvNavTab = (TextView) mTab.findViewById(R.id.tv_nav_tab);
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


}
