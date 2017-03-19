package com.yongf.rorder.app.activity;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yongf.rorder.R;
import com.yongf.rorder.widget.ScrollableViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author dingguo@wacai.com
 * @since 2017/3/17
 */

public class OrderTempActivity extends BaseActivity {

    // TODO: 2017/3/17 复习ViewPager的用法！
    // TODO: 17-3-19 将Component包下的类移到Widget包下去
    // TODO: 17-3-19 把FilterMenu抽象成一个独立的控件！

    @BindView(R.id.tl_nav)
    TabLayout mTlNav;

    @BindView(R.id.vp_option)
    ScrollableViewPager mVpOption;

    private String[] mTitles = {"热卖", "价格", "人气", "筛选"};
    private List<View> mPages;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_temp;
    }

    @Override
    protected void initView() {
        super.initView();

        View page1 = LayoutInflater.from(this).inflate(R.layout.filter_menu_tab1, null);
        View page2 = LayoutInflater.from(this).inflate(R.layout.filter_menu_tab2, null);
        View page3 = LayoutInflater.from(this).inflate(R.layout.filter_menu_tab1, null);
        View page4 = LayoutInflater.from(this).inflate(R.layout.filter_menu_tab2, null);
        mPages = new ArrayList<>();
        mPages.add(page1);
        mPages.add(page2);
        mPages.add(page3);
        mPages.add(page4);

        MyAdapter adapter = new MyAdapter(this, mPages);
        mVpOption.setAdapter(adapter);
        mTlNav.setupWithViewPager(mVpOption);
        mTlNav.setTabsFromPagerAdapter(adapter);                //这一步不能少！！！

        for (int i = 0; i < mTlNav.getTabCount(); i++) {
            TabLayout.Tab tab = mTlNav.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(adapter.getTabView(i));
            }
        }

        //禁止ViewPager左右滑动，取消滑动动画
        mVpOption.setScrollable(false);
        mVpOption.setAnimationEnabled(false);
    }

    @Override
    protected void initEvent() {
        super.initEvent();

        //设置TabLayout的点击事件
        for (int i = 0; i < mTlNav.getTabCount(); i++) {
            TabLayout.Tab tab = mTlNav.getTabAt(i);
            if (tab.getCustomView() != null) {
                View tabView = (View) tab.getCustomView().getParent();
                tabView.setTag(i);
                tabView.setOnClickListener(v -> {
                    int position = (int) v.getTag();
                    if (position == mTlNav.getSelectedTabPosition()) {
                        if (mVpOption.getVisibility() == View.VISIBLE) {
                            toggleFilterMenu(false);
                        } else if (mVpOption.getVisibility() == View.GONE) {
                            toggleFilterMenu(true);
                        }
                    } else {
                        mTlNav.getTabAt(position).select();
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
        mVpOption.setVisibility(isMenuVisible ? View.VISIBLE : View.GONE);
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
