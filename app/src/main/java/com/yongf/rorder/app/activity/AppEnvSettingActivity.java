package com.yongf.rorder.app.activity;

import android.widget.Button;

import com.yongf.rorder.R;
import com.yongf.rorder.app.application.AppEnv;
import com.yongf.rorder.net.UrlCenter;
import com.yongf.rorder.widget.TitleLayout;

import butterknife.BindView;
import butterknife.OnClick;

public class AppEnvSettingActivity extends BaseActivity {

    private static final String TAG = AppEnvSettingActivity.class.getSimpleName();

    @BindView(R.id.tl_title)
    TitleLayout mTlTitle;
    @BindView(R.id.btn_aliyun)
    Button mBtnAliyun;
    @BindView(R.id.btn_local)
    Button mBtnLocal;
    @BindView(R.id.btn_emulator)
    Button mBtnEmulator;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_app_env_setting;
    }

    @OnClick(R.id.btn_aliyun)
    void onAppAliyun() {
        AppEnv.setMainSite(UrlCenter.ALIYUN_SITE);
        AppEnv.getUserToast().simpleToast("已切换到阿里云环境");
    }

    @OnClick(R.id.btn_local)
    void onAppLocal() {
        AppEnv.setMainSite(UrlCenter.LOCAL_SITE);
        AppEnv.getUserToast().simpleToast("已切换到PC本地环境");
    }

    @OnClick(R.id.btn_emulator)
    void onAppEmulator() {
        AppEnv.setMainSite(UrlCenter.EMULATOR_SITE);
        AppEnv.getUserToast().simpleToast("已切换到模拟器环境");
    }
}
