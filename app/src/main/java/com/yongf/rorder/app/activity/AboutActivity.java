/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: AboutActivity						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-16       新增：Create	
 */

package com.yongf.rorder.app.activity;

import com.yongf.rorder.R;
import com.yongf.rorder.widget.TitleLayout;

import butterknife.BindView;

/**
 * AboutActivity
 *
 * @author Scott Wang
 * @version 1.0, 17-1-16
 * @see
 * @since ROder V0.1
 */
public class AboutActivity extends BaseActivity {

    private static final String TAG = "AboutActivity";

    @BindView(R.id.tl_title)
    TitleLayout mTitleLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initEvent() {
        super.initEvent();

        mTitleLayout.setOnLeftIconClickListener(() -> {
            finish();
        });
    }
}
