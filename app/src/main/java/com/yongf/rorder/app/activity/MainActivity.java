package com.yongf.rorder.app.activity;

import android.os.Bundle;

import com.yongf.rorder.R;
import com.yongf.rorder.base.BaseActivity;
import com.yongf.rorder.presenter.main.MainContract;
import com.yongf.rorder.presenter.main.MainPresenter;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 应用主页
 *
 * @author Scott Wang
 * @version 1.0, 17-1-1
 * @see
 * @since ROder V1.0
 */
public class MainActivity extends BaseActivity implements MainContract.View {

    private static final String TAG = "MainActivity";

    private MainContract.Presenter mPresenter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();

        MainPresenter presenter = new MainPresenter();
        setPresenter(presenter);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
