/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: HotFragment						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-3-1       新增：Create	
 */

package com.yongf.rorder.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yongf.rorder.R;

/**
 * HotFragment
 *
 * @author Scott Wang
 * @version 1.0, 17-3-1
 * @see
 * @since ROder V0.1
 */
public class HotFragment extends BaseFragment {

    private static final String TAG = "HotFragment";

    private static HotFragment INSTANCE;

    public static HotFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HotFragment();
        }

        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_square, container, false);
        return view;
    }
}
