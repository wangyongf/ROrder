package com.yongf.rorder.app.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yongf.rorder.R;
import com.yongf.rorder.widget.filtermenu.FilterMenu;
import com.yongf.rorder.widget.filtermenu.FilterMenuAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.yongf.rorder.app.application.MyApplication.getContext;

/**
 * @author dingguo@wacai.com
 * @since 2017/3/17
 */

public class OrderTempActivity extends BaseActivity {

    @BindView(R.id.filter_menu)
    FilterMenu mFilterMenu;

    private String[] mTitles = {"热卖", "价格", "人气", "筛选"};
    private List<View> mPages = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_temp;
    }

    @Override
    protected void initView() {
        super.initView();

        View page1 = LayoutInflater.from(getContext()).inflate(R.layout.filter_menu_tab1, null);
        View page2 = LayoutInflater.from(getContext()).inflate(R.layout.filter_menu_tab2, null);
        View page3 = LayoutInflater.from(getContext()).inflate(R.layout.filter_menu_tab1, null);
        View page4 = LayoutInflater.from(getContext()).inflate(R.layout.filter_menu_tab2, null);
        mPages.add(page1);
        mPages.add(page2);
        mPages.add(page3);
        mPages.add(page4);

        MyAdapter adapter = new MyAdapter();
        mFilterMenu.setAdapter(adapter);
    }

    class MyAdapter extends FilterMenuAdapter {

        @Override
        public View getTabMenu(int position) {
            return mPages.get(position);
        }

        @Override
        public View getTab(int position) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.filter_menu_tabitem, null);
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

        @Override
        public int getCount() {
            return mTitles.length;
        }
    }
}
