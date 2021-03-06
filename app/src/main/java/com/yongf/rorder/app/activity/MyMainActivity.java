package com.yongf.rorder.app.activity;

import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yongf.rorder.R;
import com.yongf.rorder.util.IntentHelper;
import com.yongf.rorder.widget.TitleLayout;

import butterknife.BindView;
import butterknife.OnClick;

public class MyMainActivity extends BaseActivity {

    private static final String TAG = MyMainActivity.class.getSimpleName();

    @BindView(R.id.tl_title)
    TitleLayout mTlTitle;
    @BindView(R.id.rl_order)
    RelativeLayout mRlOrder;
    @BindView(R.id.rl_myorder)
    RelativeLayout mRlMyorder;
    @BindView(R.id.rl_account)
    RelativeLayout mRlAccount;
    @BindView(R.id.rl_about)
    RelativeLayout mRlAbout;
    @BindView(R.id.tv_order)
    TextView mTvOrder;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_main;
    }

    @Override
    protected void initEvent() {
        mTlTitle.setOnLeftIconClickListener(() -> IntentHelper.simpleJump(MyMainActivity.this, EntranceActivity.class));
    }

    /**
     * 随便看看
     */
    @OnClick(R.id.rl_order)
    void onOrder() {
        // TODO: 17-5-6 传参~
        IntentHelper.simpleJump(this, ShoppingCartActivity.class);
    }

    /**
     * 开始点餐
     */
    @OnClick(R.id.tv_order)
    void onOrder1() {
        IntentHelper.simpleJump(this, ShoppingCartActivity.class);
    }

    /**
     * 我的订单
     */
    @OnClick(R.id.rl_myorder)
    void onMyOrder() {
        IntentHelper.simpleJump(this, MyOrderActivity.class);
    }

    /**
     * 个人中心
     */
    @OnClick(R.id.rl_account)
    void onAccount() {
        IntentHelper.simpleJump(this, AccountActivity.class);
    }

    /**
     * 关于我们
     */
    @OnClick(R.id.rl_about)
    void onAbout() {
        IntentHelper.simpleJump(this, AboutActivity.class);
    }
}
