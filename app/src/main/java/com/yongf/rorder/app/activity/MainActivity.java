package com.yongf.rorder.app.activity;

import android.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.yongf.rorder.R;
import com.yongf.rorder.app.fragment.AccountFragment;
import com.yongf.rorder.app.fragment.HotFragment;
import com.yongf.rorder.app.fragment.MainFragment;
import com.yongf.rorder.app.fragment.SearchFragment;
import com.yongf.rorder.presenter.main.MainContract;
import com.yongf.rorder.presenter.main.MainPresenter;
import com.yongf.rorder.widget.bottomnavigationview.BottomNavigationItem;
import com.yongf.rorder.widget.bottomnavigationview.BottomNavigationView;

import butterknife.BindView;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 点菜宝主页
 *
 * @author Scott Wang
 * @version 1.0, 17-1-1
 * @see
 * @since ROder V1.0
 */
public class MainActivity extends BaseActivity implements MainContract.View {

    public static final String UID = "uid";

    private static final String TAG = "MainActivity";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.fl_content)
    FrameLayout mFlContent;

    @BindView(R.id.nav_view)
    BottomNavigationView mNavigationView;

    private MainContract.Presenter mPresenter;

    private String[] text = {"首页", "热门", "搜索", "我的"};

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
    protected void initView() {
        super.initView();

        setSupportActionBar(mToolbar);

        initNavView();
    }

    /**
     * 初始化底部导航栏
     */
    private void initNavView() {
        mNavigationView
                .addItem(new BottomNavigationItem(R.drawable.home_selected, "首页")
                        .setUnselectedIconResId(R.drawable.home_unselected)
                        .setSelectedTextColor(R.color.app_color_bright_red)
                        .setUnselectedTextColor(R.color.app_color_gray))
                .addItem(new BottomNavigationItem(R.drawable.hot_selected, "热门")
                        .setUnselectedIconResId(R.drawable.hot_unselected)
                        .setSelectedTextColor(R.color.app_color_bright_red)
                        .setUnselectedTextColor(R.color.app_color_gray))
                .addItem(new BottomNavigationItem(R.drawable.search_selected, "搜索")
                        .setUnselectedIconResId(R.drawable.search_unselected)
                        .setSelectedTextColor(R.color.app_color_bright_red)
                        .setUnselectedTextColor(R.color.app_color_gray))
                .addItem(new BottomNavigationItem(R.drawable.account_selected, "我的")
                        .setUnselectedIconResId(R.drawable.account_unselected)
                        .setSelectedTextColor(R.color.app_color_bright_red)
                        .setUnselectedTextColor(R.color.app_color_gray))
                .setDefaultTab(0)
                .build();

        mNavigationView.setOnTabSelectedListener(position -> {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = MainFragment.newInstance();
                    break;
                case 1:
                    fragment = HotFragment.newInstance();
                    break;
                case 2:
                    fragment = SearchFragment.newInstance();
                    break;
                case 3:
                    fragment = AccountFragment.newInstance();
                    break;
            }
            getFragmentManager().beginTransaction()
                    .replace(R.id.fl_content, fragment)
                    .commit();
        });
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
