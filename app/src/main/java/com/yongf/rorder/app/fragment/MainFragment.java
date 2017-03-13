/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014	
 * 文件名: MainFragment						
 * 描述: 								
 * 修改历史: 
 * 版本号    作者                日期              简要介绍相关操作
 *  0.1         Scott Wang     17-3-1       新增：Create	
 */

package com.yongf.rorder.app.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.yongf.rorder.R;
import com.yongf.rorder.component.dialog.NativeDialogHelper;

/**
 * MainFragment
 *
 * @author Scott Wang
 * @version 1.0, 17-3-1
 * @see
 * @since ROder V0.1
 */
public class MainFragment extends BaseFragment {

    private static final String TAG = "MainFragment";

    private static MainFragment INSTANCE;

    private int[] mSampleImages = {
            R.drawable.image_1, R.drawable.image_2,
            R.drawable.image_3, R.drawable.image_4, R.drawable.image_5
    };

    ImageListener mImageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(mSampleImages[position]);
        }
    };

    private CarouselView mCarouselView;
    private TextView mTvCouponOriginalPrice;
    private LinearLayout mLlActivityDesc;
    private Button mBtnAppoint;

    // FIXME: 17-3-1 单例模式，线程安全
    public static MainFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainFragment();
        }

        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mCarouselView = (CarouselView) view.findViewById(R.id.carousel_view);
        mCarouselView.setPageCount(mSampleImages.length);
        mCarouselView.setImageListener(mImageListener);

        mTvCouponOriginalPrice = (TextView) view.findViewById(R.id.tv_coupon_original_price);
        mTvCouponOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);                    //添加删除线
        ((TextView) view.findViewById(R.id.tv_coupon_original_price2)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);            //添加删除线

        mBtnAppoint = (Button) view.findViewById(R.id.btn_appoint);
        mBtnAppoint.setOnClickListener(v -> {
            getUserToast().toast("点击了预约", LENGTH_LONG);
        });

        mLlActivityDesc = (LinearLayout) view.findViewById(R.id.ll_activity_desc);
        mLlActivityDesc.setOnClickListener(v -> {
            NativeDialogHelper.nativeDialog(getActivity(), "活动说明",
                    R.string.fragment_main_coupon_activity_desc, "我知道了", false);
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
