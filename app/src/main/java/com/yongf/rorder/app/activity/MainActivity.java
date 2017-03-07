package com.yongf.rorder.app.activity;

import android.app.Fragment;
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

    // TODO: 17-3-5 然后计划做的页面：①美团订单页面未登录界面
    // TODO: 17-3-5 考虑美团登录页，注册入口放Title右边，手机号和账号登录切换 
    // TODO: 17-3-5 考虑引入Ultra-PTR，确定哪些页面需要带下拉刷新功能的
    // TODO: 17-3-5 完成点餐界面，参考美味不用等，带各种Filter，TabLayout
    // TODO: 2017/3/7 MainFragment搞个沉浸式状态栏，把幻灯片向上延伸

    public static final String UID = "uid";

    private static final String TAG = "MainActivity";

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

        initNavView();
    }

    /**
     * 初始化底部导航栏
     */
    private void initNavView() {
        mNavigationView
                .addItem(new BottomNavigationItem(R.drawable.home_selected, text[0])
                        .setUnselectedIconResId(R.drawable.home_unselected)
                        .setSelectedTextColor(R.color.app_color_bright_red)
                        .setUnselectedTextColor(R.color.app_color_gray))
                .addItem(new BottomNavigationItem(R.drawable.hot_selected, text[1])
                        .setUnselectedIconResId(R.drawable.hot_unselected)
                        .setSelectedTextColor(R.color.app_color_bright_red)
                        .setUnselectedTextColor(R.color.app_color_gray))
                .addItem(new BottomNavigationItem(R.drawable.search_selected, text[2])
                        .setUnselectedIconResId(R.drawable.search_unselected)
                        .setSelectedTextColor(R.color.app_color_bright_red)
                        .setUnselectedTextColor(R.color.app_color_gray))
                .addItem(new BottomNavigationItem(R.drawable.account_selected, text[3])
                        .setUnselectedIconResId(R.drawable.account_unselected)
                        .setSelectedTextColor(R.color.app_color_bright_red)
                        .setUnselectedTextColor(R.color.app_color_gray))
                .defaultTab(0)
                .onDefaultTab(position -> {
                    loadFragment(position);
                })
                .build();

        mNavigationView.setOnTabClickListener(position -> {
            loadFragment(position);
        });
        mNavigationView.setOnTabLongClickListener(position -> {

        });
    }

    /**
     * 加载Fragment
     *
     * @param index 加载第几个Fragment
     */
    private void loadFragment(int index) {
        Fragment fragment = null;
        switch (index) {
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
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
