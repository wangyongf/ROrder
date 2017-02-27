package com.yongf.rorder.app.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.yongf.rorder.R;

import butterknife.BindView;

/**
 * @author dingguo@wacai.com
 * @since 2017/2/23
 */

public class SearchActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    // TODO: 17-2-27 风格已经确定，就是MIUI全局搜索的风格！
    // TODO: 17-2-27 后期考虑做一个自定义控件，自定义SearchView
    // TODO: 17-2-28 先完成搜索历史部分，包括图标，布局等
    // TODO: 17-2-28 封装BaseAdapter
    // TODO: 17-2-28 预研幻灯片的实现，可以考虑开源库

    public static final String[] tips = {"aaaaa", "bbbbb", "ccccc", "ddddd"};
    private static final String TAG = SearchActivity.class.getSimpleName();

    @BindView(R.id.search_view)
    SearchView mSearchView;

    @BindView(R.id.list_view)
    ListView mListView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        super.initView();

        mListView.setAdapter(new MyAdapter());
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setOnQueryTextListener(this);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, "您的选择是：" + query, Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return true;
    }

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return tips.length;
        }

        @Override
        public Object getItem(int position) {
            return tips[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;
            if (convertView == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_simple_list, null);
                holder = new ViewHolder();
                holder.tvTips = (TextView) view.findViewById(R.id.tv_tip);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvTips.setText(tips[position]);

            return view;
        }
    }

    private class ViewHolder {
        TextView tvTips;
    }
}
