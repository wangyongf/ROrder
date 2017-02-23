package com.yongf.rorder.app.activity;

import com.yongf.rorder.R;
import com.yongf.rorder.widget.TitleLayout;

import butterknife.BindView;

/**
 * @author dingguo@wacai.com
 * @since 2017/2/23
 */

public class InputEmailActivity extends BaseActivity {

    private static final String TAG = InputEmailActivity.class.getSimpleName();

    @BindView(R.id.tl_title)
    TitleLayout mTlTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_input_email;
    }

    @Override
    protected void initEvent() {
        super.initEvent();

        mTlTitle.setOnLeftIconClickListener(() -> finish());
    }
}
