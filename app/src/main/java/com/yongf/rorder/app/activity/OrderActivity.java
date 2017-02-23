/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: OrderActivity						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-24       新增：Create	
 */

package com.yongf.rorder.app.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yongf.rorder.R;
import com.yongf.rorder.widget.TitleLayout;

import butterknife.BindView;

/**
 * OrderActivity
 *
 * @author Scott Wang
 * @version 1.0, 17-1-24
 * @see
 * @since ROder V0.1
 */
public class OrderActivity extends BaseActivity {

    private static final String TAG = "OrderActivity";

    @BindView(R.id.tl_title)
    TitleLayout mTlTitle;

    @BindView(R.id.rl_header_intro)
    LinearLayout mRlHeaderIntro;

    @BindView(R.id.lv_category)
    ListView mLvCategory;

    @BindView(R.id.lv_food_item)
    ListView mLvFoodItem;

    @BindView(R.id.btn_settle)
    Button mBtnSettle;

    private String[] mCategories = {"热销", "主食类", "碳烤类", "小吃类", "甜点类", "组合餐"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    protected void initView() {
        super.initView();

        MyCategoryAdapter categoryAdapter = new MyCategoryAdapter();
        mLvCategory.setAdapter(categoryAdapter);
    }

    @Override
    protected void initEvent() {
        super.initEvent();

        mTlTitle.setOnLeftIconClickListener(() -> {
            finish();
        });
        mRlHeaderIntro.setOnClickListener(v -> {
            Toast.makeText(this, "店铺信息", Toast.LENGTH_SHORT).show();
        });
    }

    private class MyCategoryAdapter extends BaseAdapter {

        private int mSelectedItem = -1;

        @Override
        public int getCount() {
            return mCategories.length;
        }

        @Override
        public Object getItem(int position) {
            return mCategories[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            CategoryViewHolder holder;
            if (convertView == null) {
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_order_category, null);
                holder = new CategoryViewHolder();
                holder.ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
                holder.tvDesc = (TextView) view.findViewById(R.id.tv_desc);
                holder.rlOrderCategoryItem = (LinearLayout)
                        view.findViewById(R.id.rl_order_category_item);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (CategoryViewHolder) convertView.getTag();
            }

            holder.tvDesc.setText(mCategories[position]);

            return view;
        }
    }

    private class CategoryViewHolder {
        ImageView ivIcon;
        TextView tvDesc;
        LinearLayout rlOrderCategoryItem;
    }

    private class MyFoodItemAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

    private class FoodItemViewHolder {

    }
}
