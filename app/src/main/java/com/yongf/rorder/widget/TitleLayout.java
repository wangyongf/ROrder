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
import android.graphics.Canvas;
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
     * 左侧图片资源Id
     */
    private int mLeftIcon;

    /**
     * 左侧是否可见
     */
    private boolean mLeftVisible;

    /**
     * 标题文字
     */
    private String mTitleText;

    /**
     * 右侧文字
     */
    private String mRightText;

    /**
     * 右侧是否可见
     */
    private boolean mRightVisible;

    public TitleLayout(Context context) {
        this(context, null);
    }

    public TitleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.toolbar_default, this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleLayout);
        mLeftIcon = typedArray.getResourceId(R.styleable.TitleLayout_tl_leftImage,
                R.drawable.back);
        mLeftVisible = typedArray.getBoolean(R.styleable.TitleLayout_tl_leftVisible, true);
        mTitleText = typedArray.getString(R.styleable.TitleLayout_tl_titleText);
        mRightText = typedArray.getString(R.styleable.TitleLayout_tl_rightText);
        mRightVisible = typedArray.getBoolean(R.styleable.TitleLayout_tl_rightVisible, true);
        typedArray.recycle();

        setWillNotDraw(false);
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

    /**
     * 设置右侧是否可见（默认可见）
     *
     * @param rightVisible 右侧是否可见
     */
    public void setRightVisible(boolean rightVisible) {
        mRightVisible = rightVisible;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mTitleText = TextUtils.isEmpty(mTitleText) ?
                getContext().getString(R.string.feedback) : mTitleText;
        mRightText = TextUtils.isEmpty(mRightText) ?
                getContext().getString(R.string.submit) : mRightText;
        ImageView leftIcon = (ImageView) findViewById(R.id.iv_left);
        ((TextView) findViewById(R.id.tv_title)).setText(mTitleText);
        TextView rightText = (TextView) findViewById(R.id.tv_right);
        if (mLeftVisible) {
            leftIcon.setVisibility(VISIBLE);
            leftIcon.setBackgroundResource(mLeftIcon);
        } else {
            leftIcon.setVisibility(GONE);
        }
        if (mRightVisible) {
            rightText.setVisibility(VISIBLE);
            rightText.setText(mRightText);
        } else {
            rightText.setVisibility(GONE);
        }
    }
}
