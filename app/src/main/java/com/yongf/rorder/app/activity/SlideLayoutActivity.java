/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: SlideLayoutActivity						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-26       新增：Create	
 */

package com.yongf.rorder.app.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yongf.rorder.R;
import com.yongf.rorder.widget.SlideLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import butterknife.BindView;

/**
 * SlideLayoutActivity
 *
 * @author Scott Wang
 * @version 1.0, 17-1-26
 * @see
 * @since ROder V0.1
 */
public class SlideLayoutActivity extends BaseActivity {

    private static final String TAG = "SlideLayoutActivity";

    @BindView(R.id.lv)
    ListView mListView;

    private List<String> mData;
    private List<SlideLayout> mOpenedViews = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_slide_layout;
    }

    @Override
    protected void initData() {
        super.initData();

        // 模拟数据
        mData = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            mData.add("内容--" + i);
        }
    }

    @Override
    protected void after() {
        super.after();

        // 给listView设置数据
        mListView.setAdapter(new MyAdapter());
    }

    public void closeAll() {
        ListIterator<SlideLayout> iterator = mOpenedViews.listIterator();
        while (iterator.hasNext()) {
            SlideLayout view = iterator.next();
            view.close();
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mData != null) {
                return mData.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mData != null) {
                return mData.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                // 没有复用
                convertView = View.inflate(SlideLayoutActivity.this, R.layout.item,
                        null);
                // 初始化Holder
                holder = new ViewHolder();
                // 设置标记
                convertView.setTag(holder);

                // view的初始化
                holder.sv = (SlideLayout) convertView.findViewById(R.id.item_sv);
                holder.tvContent = (TextView) convertView
                        .findViewById(R.id.item_tv_content);
                holder.tvDelete = (TextView) convertView
                        .findViewById(R.id.item_tv_delete);
                holder.sv.setOnSweepListener((view, isOpened) -> {
                    if (isOpened) {
                        if (!mOpenedViews.contains(view)) {
                            mOpenedViews.add(view);
                        }
                    } else {
                        // 移除
                        mOpenedViews.remove(view);
                    }
                });
            } else {
                // 有复用
                holder = (ViewHolder) convertView.getTag();
            }

            // 数据的加载
            final String data = mData.get(position);
            holder.tvContent.setText(data);

            holder.tvDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mData.remove(data);
                    closeAll();
                    notifyDataSetChanged();
                }
            });

            return convertView;
        }
    }

    class ViewHolder {
        SlideLayout sv;
        TextView tvContent;
        TextView tvDelete;
    }
}
