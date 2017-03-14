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
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yongf.rorder.R;

/**
 * 定制对话框，暂时只支持底部最多两个按钮！
 */
public class CustomDialog extends Dialog {

    // TODO: 2017/3/14 这东西还有很多值得完善的地方！！！

    private String mTitle;
    private String mMessage;

    private TextView mTvDialogTitle;
    private TextView mTvDialogMessage;
    private Button mBtnCancel;
    private Button mBtnConfirm;
    private LinearLayout mLlButtonContainer;

    private String[] mButtons;                  //底部按钮文字描述，数组大小代表按钮个数
    private OnButtonClickListener mOnButtonClickListener;               //底部按钮点击事件

    public CustomDialog(@NonNull Context context, String title, String message, String[] buttons, OnButtonClickListener onButtonClickListener) {
        super(context);

        mTitle = title;
        mMessage = message;
        mButtons = buttons;
        mOnButtonClickListener = onButtonClickListener;

        initView();
        initEvent();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        setContentView(getLayoutId());

        mTvDialogTitle = (TextView) findViewById(R.id.tv_dialog_title);
        mTvDialogMessage = (TextView) findViewById(R.id.tv_dialog_message);
        mBtnCancel = (Button) findViewById(R.id.btn_cancel);
        mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
        mLlButtonContainer = (LinearLayout) findViewById(R.id.ll_button_container);
        mLlButtonContainer.setVisibility(View.VISIBLE);

        mTvDialogTitle.setText(mTitle);
        mTvDialogMessage.setText(mMessage);

        //设置按钮的文字
        if (mButtons.length == 1) {
            mBtnConfirm.setVisibility(View.GONE);
            mBtnCancel.setText(mButtons[0]);
        } else if (mButtons.length == 2) {
            mBtnConfirm.setVisibility(View.VISIBLE);
            mBtnCancel.setText(mButtons[0]);
            mBtnConfirm.setText(mButtons[1]);
        }
    }

    /**
     * 初始化按钮点击事件
     */
    private void initEvent() {
        for (int i = 0; i < mButtons.length; i++) {
            int j = i;
            mLlButtonContainer.getChildAt(i).setOnClickListener(view -> {
                if (mOnButtonClickListener != null) {
                    mOnButtonClickListener.onButtonClick(this, j);
                }
            });
        }
    }

    /**
     * 定制布局
     *
     * @return
     */
    protected int getLayoutId() {
        return R.layout.default_custom_dialog;
    }

    /**
     * 获取底部按钮的LinearLayout容器
     *
     * @return
     */
    private LinearLayout getButtonLayout() {
        return null;
    }

    /**
     * 定制对话框底部按钮点击事件
     */
    public interface OnButtonClickListener {
        /**
         * 按钮点击事件
         *
         * @param position 被点击按钮是第几个按钮
         */
        void onButtonClick(CustomDialog dialog, int position);
    }

    /**
     * 构造CustomDialog
     */
    public static class Builder {

        private CustomDialog mCustomDialog;
        private Context mContext;
        private String mTitle;
        private String mMessage;
        private String[] mButtons;                  //底部按钮文字描述，数组大小代表按钮个数
        private OnButtonClickListener mOnButtonClickListener;               //底部按钮点击事件

        /**
         * 初始化Builder，赋予上下文
         *
         * @param context 上下文
         * @return
         */
        public Builder(Context context) {
            mContext = context;
        }

        /**
         * 设置标题
         *
         * @param title 标题文字
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
         * @return
         */
        public Builder setMessage(String message) {
            mMessage = message;
            return this;
        }

        /**
         * 设置底部按钮
         *
         * @param buttons
         * @return
         */
        public Builder setButton(String[] buttons) {
            mButtons = buttons;
            return this;
        }

        /**
         * 设置底部按钮点击事件
         *
         * @param listener
         * @return
         */
        public Builder setOnButtonClickListener(OnButtonClickListener listener) {
            mOnButtonClickListener = listener;
            return this;
        }

        /**
         * 完成构造CustomDialog
         */
        public CustomDialog build() {
            mCustomDialog = new CustomDialog(mContext, mTitle, mMessage, mButtons, mOnButtonClickListener);
            return mCustomDialog;
        }
    }
}
