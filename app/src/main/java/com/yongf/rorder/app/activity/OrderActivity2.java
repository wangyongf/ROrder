package com.yongf.rorder.app.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.yongf.rorder.R;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 正式版点餐页面
 *
 * @author dingguo@wacai.com
 * @since 2017/3/16
 */

public class OrderActivity2 extends BaseActivity {

    // TODO: 2017/3/16 引入DropDownMenu
    // TODO: 2017/3/16 最好能实现红色的那个效果，封装成控件 ，提供多种选项，同时又可扩展性，不过肯定要往后推啦
    // TODO: 2017/3/16 先把下边的列表RecyclerView做好！

    @BindView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;

    private String headers[] = {"城市", "年龄", "性别", "星座"};
    private List<View> popupViews = new ArrayList<>();

    private GirdDropDownAdapter cityAdapter;
    private ListDropDownAdapter ageAdapter;
    private ListDropDownAdapter sexAdapter;
    private ConstellationAdapter constellationAdapter;

    private String citys[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private String ages[] = {"不限", "18岁以下", "18-22岁", "23-26岁", "27-35岁", "35岁以上"};
    private String sexs[] = {"不限", "男", "女"};
    private String constellations[] = {"不限", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座"};

    private int constellationPosition = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order2;
    }

    @Override
    protected void initView() {
        //init city menu
        final ListView cityView = new ListView(this);
        cityAdapter = new GirdDropDownAdapter(this, Arrays.asList(citys));
        cityView.setDividerHeight(0);
        cityView.setAdapter(cityAdapter);

        //init age menu
        final ListView ageView = new ListView(this);
        ageView.setDividerHeight(0);
        ageAdapter = new ListDropDownAdapter(this, Arrays.asList(ages));
        ageView.setAdapter(ageAdapter);

        //init sex menu
        final ListView sexView = new ListView(this);
        sexView.setDividerHeight(0);
        sexAdapter = new ListDropDownAdapter(this, Arrays.asList(sexs));
        sexView.setAdapter(sexAdapter);

        //init constellation
        final View constellationView = getLayoutInflater().inflate(R.layout.custom_layout, null);
        GridView constellation = ButterKnife.findById(constellationView, R.id.constellation);
        constellationAdapter = new ConstellationAdapter(this, Arrays.asList(constellations));
        constellation.setAdapter(constellationAdapter);
        TextView ok = ButterKnife.findById(constellationView, R.id.ok);
        ok.setOnClickListener(v -> {
            mDropDownMenu.setTabText(constellationPosition == 0 ? headers[3] : constellations[constellationPosition]);
            mDropDownMenu.closeMenu();
        });

        //init popupViews
        popupViews.add(cityView);
        popupViews.add(ageView);
        popupViews.add(sexView);
        popupViews.add(constellationView);

        //add item click event
        cityView.setOnItemClickListener((parent, view, position, id) -> {
            cityAdapter.setCheckItem(position);
            mDropDownMenu.setTabText(position == 0 ? headers[0] : citys[position]);
            mDropDownMenu.closeMenu();
        });

        ageView.setOnItemClickListener((parent, view, position, id) -> {
            ageAdapter.setCheckItem(position);
            mDropDownMenu.setTabText(position == 0 ? headers[1] : ages[position]);
            mDropDownMenu.closeMenu();
        });

        sexView.setOnItemClickListener((parent, view, position, id) -> {
            sexAdapter.setCheckItem(position);
            mDropDownMenu.setTabText(position == 0 ? headers[2] : sexs[position]);
            mDropDownMenu.closeMenu();
        });

        constellation.setOnItemClickListener((parent, view, position, id) -> {
            constellationAdapter.setCheckItem(position);
            constellationPosition = position;
        });

        //init context view
        View contentView = getLayoutInflater().inflate(R.layout.item_order, null);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

    /*Adapters*/

    public class ListDropDownAdapter extends BaseAdapter {

        private Context context;
        private List<String> list;
        private int checkItemPosition = 0;

        public ListDropDownAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        public void setCheckItem(int position) {
            checkItemPosition = position;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        class ViewHolder {
            @BindView(R.id.text)
            TextView mText;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView != null) {
                viewHolder = (ViewHolder) convertView.getTag();
            } else {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_default_drop_down, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            fillValue(position, viewHolder);
            return convertView;
        }

        private void fillValue(int position, ViewHolder viewHolder) {
            viewHolder.mText.setText(list.get(position));
            if (checkItemPosition != -1) {
                if (checkItemPosition == position) {
                    viewHolder.mText.setTextColor(context.getResources().getColor(R.color.drop_down_selected));
                    viewHolder.mText.setBackgroundResource(R.color.check_bg);
                } else {
                    viewHolder.mText.setTextColor(context.getResources().getColor(R.color.drop_down_unselected));
                    viewHolder.mText.setBackgroundResource(R.color.app_color_white);
                }
            }
        }


    }

    public class GirdDropDownAdapter extends BaseAdapter {

        private Context context;
        private List<String> list;
        private int checkItemPosition = 0;

        public GirdDropDownAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        public void setCheckItem(int position) {
            checkItemPosition = position;
            notifyDataSetChanged();
        }

        class ViewHolder {
            @BindView(R.id.text)
            TextView mText;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView != null) {
                viewHolder = (ViewHolder) convertView.getTag();
            } else {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_list_drop_down, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            fillValue(position, viewHolder);
            return convertView;
        }

        private void fillValue(int position, ViewHolder viewHolder) {
            viewHolder.mText.setText(list.get(position));
            if (checkItemPosition != -1) {
                if (checkItemPosition == position) {
                    viewHolder.mText.setTextColor(context.getResources().getColor(R.color.drop_down_selected));
                    viewHolder.mText.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.mipmap.drop_down_checked), null);
                } else {
                    viewHolder.mText.setTextColor(context.getResources().getColor(R.color.drop_down_unselected));
                    viewHolder.mText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
            }
        }


    }

    public class ConstellationAdapter extends BaseAdapter {

        private Context context;
        private List<String> list;
        private int checkItemPosition = 0;

        public ConstellationAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        public void setCheckItem(int position) {
            checkItemPosition = position;
            notifyDataSetChanged();
        }

        class ViewHolder {
            @BindView(R.id.text)
            TextView mText;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView != null) {
                viewHolder = (ViewHolder) convertView.getTag();
            } else {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_constellation_layout, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            fillValue(position, viewHolder);
            return convertView;
        }

        private void fillValue(int position, ViewHolder viewHolder) {
            viewHolder.mText.setText(list.get(position));
            if (checkItemPosition != -1) {
                if (checkItemPosition == position) {
                    viewHolder.mText.setTextColor(context.getResources().getColor(R.color.drop_down_selected));
                    viewHolder.mText.setBackgroundResource(R.drawable.check_bg);
                } else {
                    viewHolder.mText.setTextColor(context.getResources().getColor(R.color.drop_down_unselected));
                    viewHolder.mText.setBackgroundResource(R.drawable.uncheck_bg);
                }
            }
        }


    }
}
