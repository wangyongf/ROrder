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

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order;
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
}
