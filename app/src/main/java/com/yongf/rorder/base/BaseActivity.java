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
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * BaseActivity
 *
 * @author Scott Wang
 * @version 1.0, 17-1-1
 * @see
 * @since ROder V0.1
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        onInit();
        initPresenter();
        initData();
        initEvent();
        onInited();
    }

    /**
     * 获取布局文件的id
     *
     * @return 布局文件的id
     */
    protected abstract int getLayoutId();

    /**
     * 用于initView, initData, initEvent之前的初始化工作
     */
    protected void onInit() {

    }

    /**
     * 初始化视图
     */
    protected void initPresenter() {

    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 初始化事件
     */
    protected void initEvent() {

    }

    /**
     * 用于initView, initData, initEvent之后的初始化工作
     */
    protected void onInited() {

    }
}
