package com.yongf.rorder.component.dialog;
/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: CustomDialog						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-3-14       新增：Create	
 */

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

public class CustomDialog extends Dialog {

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    /**
     * 底部显示多少个按钮，可以显示1个，2个，3个
     */
    public enum ButtonMode {
        SINGLE, DOUBLE, TRIPLE;

        /**
         * 获取实际的按钮数量
         *
         * @return
         */
        public int count() {
            return 0;
        }
    }

    /**
     * 构造CustomDialog
     */
    public class Builder {

        private CustomDialog mCustomDialog;
        private Context mContext;
        private String mTitle;
        private String mMessage;
        private ButtonMode mButtonMode;

        /**
         * 初始化Builder，赋予上下文
         *
         * @param context 上下文
         *
         * @return
         */
        public Builder Builder(Context context) {
            mContext = context;
            return this;
        }

        /**
         * 设置标题
         *
         * @param title 标题文字
         *
         * @return
         */
        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        /**
         * 设置描述文字
         *
         * @param message 主体部分的描述文字
         *
         * @return
         */
        public Builder setMessage(String message) {
            mMessage = message;
            return this;
        }

        /**
         * 设置底部按钮的个数
         *
         * @param buttonMode
         *
         * @return
         */
        public Builder setButtonMode(ButtonMode buttonMode) {

        }
    }
}
