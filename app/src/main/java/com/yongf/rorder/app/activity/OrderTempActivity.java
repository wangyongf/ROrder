package com.yongf.rorder.app.activity;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.yongf.rorder.R;

import butterknife.BindView;

/**
 * @author dingguo@wacai.com
 * @since 2017/3/17
 */

public class OrderTempActivity extends BaseActivity {

    // TODO: 2017/3/17 复习ViewPager的用法！

    @BindView(R.id.tl_nav)
    TabLayout mTlNav;

    @BindView(R.id.vp_option)
    ViewPager mVpOption;

    private String[] mTitles = {"热卖", "价格", "人气", "筛选"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_temp;
    }

    class MyAdapter extends PagerAdapter {

        private Context mContext;

        public MyAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
