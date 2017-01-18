/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: DisplayItemView						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-1-18       新增：Create	
 */

package com.yongf.rorder.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yongf.rorder.R;

/**
 * DisplayItemView
 *
 * @author Scott Wang
 * @version 1.0, 17-1-18
 * @see
 * @since ROder V0.1
 */
public class DisplayItemView extends RelativeLayout {

    //Left Mode
    public static final int LEFTMODE_TEXT = 0;
    public static final int LEFTMODE_TEXT_ICON = 1;
    public static final int LEFTMODE_TEXT_SUBTEXT = 2;
    public static final int LEFTMODE_NONE = 3;
    //Right Mode
    public static final int RIGHTMODE_TEXT = 0;
    public static final int RIGHTMODE_ICON = 1;
    public static final int RIGHTMODE_TEXT_ICON = 2;
    public static final int RIGHTMODE_CHECKBOX = 3;
    public static final int RIGHTMODE_NONE = 4;

    private static final String TAG = "DisplayItemView";

    /**
     * 左侧显示模式(LeftMode)
     */
    private int mLeftMode;
    /**
     * 右侧显示模式(RightMode)
     */
    private int mRightMode;
    /**
     * 左侧图标资源Id
     */
    private int mLeftIcon;
    /**
     * 左侧文字
     */
    private String mLeftText;
    /**
     * 左侧下部文字
     */
    private String mLeftSubText;
    /**
     * 右侧图标
     */
    private int mRightIcon;
    /**
     * 右侧文字
     */
    private String mRightText;

    public DisplayItemView(Context context) {
        this(context, null);
    }

    public DisplayItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DisplayItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.item_view_display, this);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DisplayItemView);
        mLeftMode = array.getInt(R.styleable.DisplayItemView_div_leftMode, 0);
        mLeftIcon = array.getResourceId(R.styleable.DisplayItemView_div_leftIcon, R.drawable.information);
        mLeftText = array.getString(R.styleable.DisplayItemView_div_leftText);
        mLeftSubText = array.getString(R.styleable.DisplayItemView_div_leftSubText);
        mRightMode = array.getInt(R.styleable.DisplayItemView_div_rightMode, 0);
        mRightIcon = array.getResourceId(R.styleable.DisplayItemView_div_rightIcon, R.drawable.more);
        mRightText = array.getString(R.styleable.DisplayItemView_div_rightText);
        array.recycle();

        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mLeftText = TextUtils.isEmpty(mLeftText) ? getContext().getString(R.string.setting) : mLeftText;
        mLeftSubText = TextUtils.isEmpty(mLeftSubText) ? getContext().getString(R.string.scan) : mLeftSubText;
        mRightText = TextUtils.isEmpty(mRightText) ? getContext().getString(R.string.submit) : mRightText;

        TextView leftText = (TextView) findViewById(R.id.tv_left_text);
        ImageView leftIcon = (ImageView) findViewById(R.id.iv_left_icon);
        TextView leftSubText = (TextView) findViewById(R.id.tv_left_subtext);

        LinearLayout leftLlTextIcon = (LinearLayout) findViewById(R.id.ll_left_text_icon);
        LinearLayout rightLlTextIcon = (LinearLayout) findViewById(R.id.ll_text_icon);
        LinearLayout rightLlCheckbox = (LinearLayout) findViewById(R.id.ll_checkbox);
        TextView rightText = (TextView) findViewById(R.id.tv_right_text);
        ImageView rightIcon = (ImageView) findViewById(R.id.iv_right_icon);
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox);

        switch (mLeftMode) {
            case LEFTMODE_TEXT:
                leftLlTextIcon.setVisibility(VISIBLE);
                leftText.setVisibility(VISIBLE);
                leftIcon.setVisibility(GONE);
                leftSubText.setVisibility(GONE);

                leftText.setText(mLeftText);

                break;
            case LEFTMODE_TEXT_ICON:
                leftLlTextIcon.setVisibility(VISIBLE);
                leftText.setVisibility(VISIBLE);
                leftIcon.setVisibility(VISIBLE);
                leftSubText.setVisibility(GONE);

                leftText.setText(mLeftText);
                leftIcon.setImageResource(mLeftIcon);

                break;
            case LEFTMODE_TEXT_SUBTEXT:
                leftLlTextIcon.setVisibility(VISIBLE);
                leftText.setVisibility(VISIBLE);
                leftSubText.setVisibility(VISIBLE);
                leftIcon.setVisibility(GONE);

                leftText.setText(mLeftText);
                leftSubText.setText(mLeftSubText);

                break;
            case LEFTMODE_NONE:
                leftLlTextIcon.setVisibility(GONE);

                break;
            default:
                throw new IllegalArgumentException("Illegal argument mLeftMode");
        }
        switch (mRightMode) {
            case RIGHTMODE_TEXT:
                rightLlTextIcon.setVisibility(VISIBLE);
                rightText.setVisibility(VISIBLE);
                rightIcon.setVisibility(GONE);
                rightLlCheckbox.setVisibility(GONE);

                rightText.setText(mRightText);

                break;
            case RIGHTMODE_ICON:
                rightLlTextIcon.setVisibility(VISIBLE);
                rightIcon.setVisibility(VISIBLE);
                rightText.setVisibility(GONE);
                rightLlCheckbox.setVisibility(GONE);

                rightIcon.setImageResource(mRightIcon);

                break;
            case RIGHTMODE_TEXT_ICON:
                rightLlTextIcon.setVisibility(VISIBLE);
                rightText.setVisibility(VISIBLE);
                rightIcon.setVisibility(VISIBLE);
                rightLlCheckbox.setVisibility(GONE);

                rightText.setText(mRightText);
                rightIcon.setImageResource(mRightIcon);

                break;
            case RIGHTMODE_CHECKBOX:
                rightLlCheckbox.setVisibility(VISIBLE);
                checkBox.setVisibility(VISIBLE);
                rightLlTextIcon.setVisibility(GONE);

                break;
            case RIGHTMODE_NONE:
                rightLlTextIcon.setVisibility(GONE);
                rightLlCheckbox.setVisibility(GONE);

                break;
            default:
                throw new IllegalArgumentException("Illegal argument mRightMode");
        }
    }

    public int getLeftMode() {
        return mLeftMode;
    }

    /**
     * 设置左侧显示模式
     *
     * @param leftMode 左侧显示模式
     */
    public void setLeftMode(int leftMode) {
        mLeftMode = leftMode;
    }

    public int getRightMode() {
        return mRightMode;
    }

    public void setRightMode(int rightMode) {
        mRightMode = rightMode;
    }

    public int getLeftIcon() {
        return mLeftIcon;
    }

    public void setLeftIcon(int leftIcon) {
        mLeftIcon = leftIcon;
    }

    public String getLeftText() {
        return mLeftText;
    }

    public void setLeftText(String leftText) {
        mLeftText = leftText;
    }

    public String getLeftSubText() {
        return mLeftSubText;
    }

    public void setLeftSubText(String leftSubText) {
        mLeftSubText = leftSubText;
    }

    public int getRightIcon() {
        return mRightIcon;
    }

    public void setRightIcon(int rightIcon) {
        mRightIcon = rightIcon;
    }

    public String getRightText() {
        return mRightText;
    }

    public void setRightText(String rightText) {
        mRightText = rightText;
    }
}
