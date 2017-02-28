/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: BaseAdapter						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-2-28       新增：Create	
 */

package com.yongf.rorder.widget.flowlayout;

import android.view.View;

/**
 * BaseFlowLayoutAdapter
 *
 * @author Scott Wang
 * @version 1.0, 17-2-28
 * @see
 * @since ROder V0.1
 */
public abstract class BaseFlowLayoutAdapter {

    private static final String TAG = "BaseFlowLayoutAdapter";

    /**
     * 获取单个Item的布局
     *
     * @return
     */
    public abstract View getView(int position);

    /**
     * 获取数据的个数
     *
     * @return
     */
    public abstract int getCount();
}
