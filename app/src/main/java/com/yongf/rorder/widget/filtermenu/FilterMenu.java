package com.yongf.rorder.widget.filtermenu;
/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: FilterMenu						
 * 					
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-3-20       新增：Create	
 */

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.yongf.rorder.R;
import com.yongf.rorder.widget.ScrollableViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Filter Menu
 *
 * @author Scott Wang
 * @version 1.0, 17-3-20
 * @see
 * @since ROder V1.0
 */
public class FilterMenu extends LinearLayout {

    // TODO: 17-3-20 后续还可以考虑把ScrollableViewPager抽出来，继续完善
    // TODO: 17-3-22 将Menu主体部分和阴影部分剥离，FrameLayout

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ScrollableViewPager mViewPager;

    private final boolean DEFAULT_CAN_CANCEL = true;

    private boolean mCanCancel = DEFAULT_CAN_CANCEL;            //点击阴影部分是否收起

    private FilterMenuAdapter mMenuAdapter;

    public FilterMenu(Context context) {
        this(context, null);
    }

    public FilterMenu(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(getContext()).inflate(R.layout.layout_filter_menu, this, true);
        ButterKnife.bind(this);

        init();
    }

    private void init() {

    }

    public void setAdapter(FilterMenuAdapter adapter) {
        mMenuAdapter = adapter;

        initView();
        initEvent();
    }

    /**
     * 初始化控件视图
     */
    private void initView() {
        mViewPager.setAdapter(mMenuAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mMenuAdapter);                //这一步不能少！！！

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(mMenuAdapter.getTab(i));
            }
        }

        //禁止ViewPager左右滑动，取消滑动动画
        mViewPager.setScrollable(false);
        mViewPager.setAnimationEnabled(false);
    }

    private void initEvent() {
        //设置TabLayout的点击事件
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab.getCustomView() != null) {
                View tabView = (View) tab.getCustomView().getParent();
                tabView.setTag(i);
                tabView.setOnClickListener(v -> {
                    int position = (int) v.getTag();
                    if (position == mTabLayout.getSelectedTabPosition()) {
                        if (mViewPager.getVisibility() == View.VISIBLE) {
                            toggleFilterMenu(false);
                        } else if (mViewPager.getVisibility() == View.GONE) {
                            toggleFilterMenu(true);
                        }
                    } else {
                        mTabLayout.getTabAt(position).select();
                        toggleFilterMenu(true);
                    }
                });
            }
        }

        //设置ViewPager阴影区域的点击事件
        for (int i = 0; i < mMenuAdapter.getCount(); i++) {
            View view = mMenuAdapter.getTabMenu(i);
            View viewBg = view.findViewById(R.id.view_bg);
            viewBg.setOnClickListener(v -> {
                if (mCanCancel) {
                    toggleFilterMenu(false);
                }
            });
        }
    }

    /**
     * 显示or隐藏FilterMenu
     *
     * @param isMenuVisible
     */
    private void toggleFilterMenu(boolean isMenuVisible) {
        mViewPager.setVisibility(isMenuVisible ? View.VISIBLE : View.GONE);
    }
}
