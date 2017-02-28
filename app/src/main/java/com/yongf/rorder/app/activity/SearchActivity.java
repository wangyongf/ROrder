package com.yongf.rorder.app.activity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.yongf.rorder.R;
import com.yongf.rorder.widget.flowlayout.BaseFlowLayoutAdapter;
import com.yongf.rorder.widget.flowlayout.FlowLayout;

import java.util.Random;

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
    // TODO: 17-3-1 Fix流式布局数据量不足的显示问题，还是用固定间距吧。。。

    public static final String[] tips = {"aaaaa", "bbbbb", "ccccc", "ddddd"};
    private static final String TAG = SearchActivity.class.getSimpleName();
    private static final String[] mData = {
            "向往的生活", "三生三世十里桃花", "凌晨叫外卖", "爱来的刚好", "陈赫女儿"
    };

    @BindView(R.id.search_view)
    SearchView mSearchView;

    @BindView(R.id.list_view)
    ListView mListView;

    @BindView(R.id.flow_layout)
    FlowLayout mFlowLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        super.initView();

        mListView.setAdapter(new ListAdapter());
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setOnQueryTextListener(this);

        mFlowLayout.setAdapter(new FlowLayoutAdapter());
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

    public class FlowLayoutAdapter extends BaseFlowLayoutAdapter {

        @Override
        public View getView(int position) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_flow_layout, null);
            TextView textView = (TextView) view.findViewById(R.id.tv_flow_layout_item);

            GradientDrawable defaultDrawble = new GradientDrawable();
            Random random = new Random();
            int alpha = 255;
            int red = random.nextInt(190) + 30;     //30-220
            int green = random.nextInt(190) + 30;          //30-220
            int blue = random.nextInt(190) + 30;               //30-220
            int argb = Color.argb(alpha, red, green, blue);
            //设置填充颜色
            defaultDrawble.setColor(argb);

            GradientDrawable pressedDrawble = new GradientDrawable();
            pressedDrawble.setColor(Color.DKGRAY);
            //设置一个状态图片
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawble);
            stateListDrawable.addState(new int[]{}, defaultDrawble);

            textView.setText(mData[position]);
            textView.setBackground(stateListDrawable);
            textView.setClickable(true);
            textView.setOnClickListener(v -> {
                Toast.makeText(SearchActivity.this, textView.getText(), Toast.LENGTH_SHORT).show();
            });

            return view;
        }

        @Override
        public int getCount() {
            return mData.length;
        }
    }

    public class ListAdapter extends BaseAdapter {

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
