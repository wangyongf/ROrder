/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: SettingActivity						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-17       新增：Create	
 */

package com.yongf.rorder.app.activity;

import com.yongf.rorder.R;

/**
 * 设置界面
 *
 * @author Scott Wang
 * @version 1.0, 17-1-17
 * @see
 * @since ROder V0.1
 */
public class SettingActivity extends BaseActivity {

    private static final String TAG = "SettingActivity";

    // TODO: 17-1-17 重构ITEM_LAYOUT条目界面，参考安全卫士项目
    // TODO: 17-1-17 完成设置界面

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }
}
