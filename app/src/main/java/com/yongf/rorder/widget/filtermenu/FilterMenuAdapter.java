package com.yongf.rorder.widget.filtermenu;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public abstract class FilterMenuAdapter extends PagerAdapter {

    public FilterMenuAdapter() {
        //ignored
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(getTabMenu(position));
        return getTabMenu(position);
    }

    /**
     * 获取Tab Menu
     *
     * @param position
     *
     * @return
     */
    public abstract View getTabMenu(int position);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(getTabMenu(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 获取顶部Tab
     *
     * @param position
     *
     * @return
     */
    public abstract View getTab(int position);
}
