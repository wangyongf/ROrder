/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: Toolbar						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-17       新增：Create	
 */

package com.yongf.rorder.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yongf.rorder.R;

/**
 * TitleLayout
 *
 * @author Scott Wang
 * @version 1.0, 17-1-17
 * @see
 * @since ROder V0.1
 */
public class TitleLayout extends RelativeLayout {

    private static final String TAG = "TitleLayout";

    /**
     * 左侧显示模式(LeftMode)
     */
    private int mLeftMode;

    /**
     * 右侧显示模式(RightMode)
     */
    private int mRightMode;

    /**
     * 右侧图标资源Id
     */
    private int mRightIcon;

    /**
     * 左侧图标资源Id
     */
    private int mLeftIcon;

    /**
     * 标题文字
     */
    private String mTitleText;

    /**
     * 右侧文字
     */
    private String mRightText;

    /**
     * 左侧文字
     */
    private String mLeftText;

    private ImageView mIvLeft;

    private OnLeftIconClickListener mOnLeftIconClickListener;
    private TextView mTvLeft;
    private TextView mTvTitle;
    private ImageView mIvRight;
    private TextView mTvRight;

    public TitleLayout(Context context) {
        this(context, null);
    }

    public TitleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs);
        initEvent();
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.layout_title, this);

        mIvLeft = (ImageView) findViewById(R.id.iv_left);
        mTvLeft = (TextView) findViewById(R.id.tv_left);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mIvRight = (ImageView) findViewById(R.id.iv_right);
        mTvRight = (TextView) findViewById(R.id.tv_right);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleLayout);
        mLeftMode = typedArray.getInt(R.styleable.TitleLayout_tl_leftMode, 0);
        mRightMode = typedArray.getInt(R.styleable.TitleLayout_tl_rightMode, 0);
        mLeftText = typedArray.getString(R.styleable.TitleLayout_tl_leftText);
        mLeftIcon = typedArray.getResourceId(R.styleable.TitleLayout_tl_leftIcon,
                R.drawable.icon_arrow_left_white);
        mTitleText = typedArray.getString(R.styleable.TitleLayout_tl_titleText);
        mRightText = typedArray.getString(R.styleable.TitleLayout_tl_rightText);
        mRightIcon = typedArray.getResourceId(R.styleable.TitleLayout_tl_rightIcon,
                R.drawable.information);
        typedArray.recycle();

        mLeftText = TextUtils.isEmpty(mLeftText) ?
                getContext().getString(R.string.goback) : mLeftText;
        mTitleText = TextUtils.isEmpty(mTitleText) ?
                getContext().getString(R.string.feedback) : mTitleText;
        mRightText = TextUtils.isEmpty(mRightText) ?
                getContext().getString(R.string.submit) : mRightText;

        mTvTitle.setText(mTitleText);

        switch (mLeftMode) {
            case 0:
                mIvLeft.setVisibility(VISIBLE);
                mTvLeft.setVisibility(GONE);

                mIvLeft.setImageResource(mLeftIcon);

                break;
            case 1:
                mTvLeft.setVisibility(VISIBLE);
                mIvLeft.setVisibility(GONE);

                mTvLeft.setText(mLeftText);

                break;
            case 2:
                mTvLeft.setVisibility(GONE);
                mIvLeft.setVisibility(GONE);

                break;
            default:
                throw new IllegalArgumentException("Illegal argument mLeftMode!");
        }
        switch (mRightMode) {
            case 0:
                mTvRight.setVisibility(VISIBLE);
                mIvRight.setVisibility(GONE);

                mTvRight.setText(mRightText);

                break;
            case 1:
                mIvRight.setVisibility(VISIBLE);
                mTvRight.setVisibility(GONE);

                mIvRight.setImageResource(mRightIcon);

                break;
            case 2:
                mIvRight.setVisibility(GONE);
                mTvRight.setVisibility(GONE);

                break;
            default:
                throw new IllegalArgumentException("Illegal argument mRightMode");
        }
    }

    /**
     * 初始化各种点击事件
     */
    private void initEvent() {
        mIvLeft.setOnClickListener(v -> {
            if (mOnLeftIconClickListener != null) {
                mOnLeftIconClickListener.onLeftIconClick();
            }
        });
    }

    /**
     * 设置左侧图片
     *
     * @param resId 图片资源Id
     */
    public void setLeftIcon(int resId) {
        mLeftIcon = resId;
        invalidate();
    }

    /**
     * 设置标题文字
     *
     * @param titleText 标题文字
     */
    public void setTitleText(String titleText) {
        mTitleText = titleText;
        invalidate();
    }

    /**
     * 设置右侧文字
     *
     * @param rightText 右侧文字
     */
    public void setRightText(String rightText) {
        mRightText = rightText;
        invalidate();
    }

    public void setOnLeftIconClickListener(OnLeftIconClickListener onLeftIconClickListener) {
        mOnLeftIconClickListener = onLeftIconClickListener;
    }

    public interface OnLeftIconClickListener {
        void onLeftIconClick();
    }
}
