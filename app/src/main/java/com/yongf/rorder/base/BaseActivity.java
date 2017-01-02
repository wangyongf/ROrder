/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: BaseActivity						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-1       新增：Create	
 */

package com.yongf.rorder.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * BaseActivity
 *
 * @author Scott Wang
 * @version 1.0, 17-1-1
 * @see
 * @since ROder V0.1
 */
public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        onInit();
        initView();
        initData();
        initEvent();
        onInited();
    }

    /**
     * 用于
     */
    protected void onInit() {

    }

    protected void initView() {

    }

    protected void initData() {

    }

    protected void initEvent() {

    }

    protected void onInited() {

    }


}
