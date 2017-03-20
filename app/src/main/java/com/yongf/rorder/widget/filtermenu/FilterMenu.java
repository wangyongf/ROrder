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
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yongf.rorder.R;
import com.yongf.rorder.widget.ScrollableViewPager;

import java.util.ArrayList;
import java.util.List;

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

    // TODO: 17-3-20 完成各种setter, getter
    // TODO: 17-3-20 加入设置各种Tab
    // TODO: 17-3-20 后续还可以考虑把ScrollableViewPager抽出来，继续完善

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ScrollableViewPager mViewPager;

    private String[] mTitles = {"热卖", "价格", "人气", "筛选"};
    private List<View> mPages;

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

        initView();
        initEvent();
    }

    /**
     * 初始化控件视图
     */
    private void initView() {
        View page1 = LayoutInflater.from(getContext()).inflate(R.layout.filter_menu_tab1, null);
        View page2 = LayoutInflater.from(getContext()).inflate(R.layout.filter_menu_tab2, null);
        View page3 = LayoutInflater.from(getContext()).inflate(R.layout.filter_menu_tab1, null);
        View page4 = LayoutInflater.from(getContext()).inflate(R.layout.filter_menu_tab2, null);
        mPages = new ArrayList<>();
        mPages.add(page1);
        mPages.add(page2);
        mPages.add(page3);
        mPages.add(page4);

        MyAdapter adapter = new MyAdapter(getContext(), mPages);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);                //这一步不能少！！！

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(adapter.getTabView(i));
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
        for (int i = 0; i < mPages.size(); i++) {
            View view = mPages.get(i);
            View viewBg = view.findViewById(R.id.view_bg);
            viewBg.setOnClickListener(v -> {
                toggleFilterMenu(false);
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

    public interface OnTabSelectedListener {
        void onTabSelected(int position);
    }

    class MyAdapter extends PagerAdapter {

        private Context mContext;
        private List<View> mTabs;

        public MyAdapter(Context context, List<View> tabs) {
            mContext = context;
            mTabs = tabs;
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mTabs.get(position));
            return mTabs.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mTabs.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public View getTabView(int position) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.filter_menu_tabitem, null);
            TextView tvTab = (TextView) view.findViewById(R.id.tv_tab);
            ImageView iv1 = (ImageView) view.findViewById(R.id.iv1);
            ImageView iv2 = (ImageView) view.findViewById(R.id.iv2);

            tvTab.setVisibility(View.VISIBLE);
            tvTab.setText(mTitles[position]);

            switch (position) {
                case 0:
                case 2:
                    iv1.setVisibility(View.GONE);
                    iv2.setVisibility(View.GONE);
                    break;
                case 1:
                    iv1.setVisibility(View.VISIBLE);
                    iv2.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    iv1.setVisibility(View.VISIBLE);
                    iv2.setVisibility(View.GONE);
                    break;
            }
            return view;
        }
    }
}
