package com.yongf.rorder.app.activity;

import android.widget.SearchView;

import com.yongf.rorder.R;

import butterknife.BindView;

/**
 * @author dingguo@wacai.com
 * @since 2017/2/23
 */

public class SearchActivity extends BaseActivity {

    private static final String TAG = SearchActivity.class.getSimpleName();

    @BindView(R.id.search_view)
    SearchView mSearchView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        super.initView();

        mSearchView.setSubmitButtonEnabled(true);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }
}
