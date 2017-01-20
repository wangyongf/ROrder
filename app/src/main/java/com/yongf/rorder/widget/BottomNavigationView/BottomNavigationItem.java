/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: BottomNavigationItem						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-20       新增：Create	
 */

package com.yongf.rorder.widget.BottomNavigationView;

/**
 * BottomNavigationItem
 *
 * @author Scott Wang
 * @version 1.0, 17-1-20
 * @see
 * @since ROder V0.1
 */
public class BottomNavigationItem {

    /**
     * 选中状态下的图标资源Id
     */
    private int mSelectedIconResId;

    /**
     * 非选中状态下的图标资源Id
     */
    private int mUnselectedIconResId;

    /**
     * 提示文字
     */
    private String mText;

    /**
     * 选中状态下文字颜色
     */
    private int mSelectedTextColor;

    /**
     * 非选中状态下文字颜色
     */
    private int mUnselectedTextColor;

    public BottomNavigationItem(int selectedIconResId, String text) {
        mSelectedIconResId = selectedIconResId;
        mText = text;
    }

    public int getSelectedIconResId() {
        return mSelectedIconResId;
    }

    public BottomNavigationItem setSelectedIconResId(int selectedIconResId) {
        mSelectedIconResId = selectedIconResId;
        return this;
    }

    public int getUnselectedIconResId() {
        return mUnselectedIconResId;
    }

    public BottomNavigationItem setUnselectedIconResId(int unselectedIconResId) {
        mUnselectedIconResId = unselectedIconResId;
        return this;
    }

    public String getText() {
        return mText;
    }

    public BottomNavigationItem setText(String text) {
        mText = text;
        return this;
    }

    public int getSelectedTextColor() {
        return mSelectedTextColor;
    }

    public BottomNavigationItem setSelectedTextColor(int selectedTextColor) {
        mSelectedTextColor = selectedTextColor;
        return this;
    }

    public int getUnselectedTextColor() {
        return mUnselectedTextColor;
    }

    public BottomNavigationItem setUnselectedTextColor(int unselectedTextColor) {
        mUnselectedTextColor = unselectedTextColor;
        return this;
    }
}
