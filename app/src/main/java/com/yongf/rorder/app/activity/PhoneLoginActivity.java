package com.yongf.rorder.app.activity;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.yongf.rorder.R;
import com.yongf.rorder.app.application.AppEnv;
import com.yongf.rorder.app.application.UserProfile;
import com.yongf.rorder.model.auth.RegisterFreeResultBean;
import com.yongf.rorder.net.DataObservable;
import com.yongf.rorder.util.CheckUtils;
import com.yongf.rorder.util.IntentHelper;
import com.yongf.rorder.widget.TitleLayout;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PhoneLoginActivity extends BaseActivity {

    // TODO: 17-5-3 MVP化

    public static final String REGION_CODE = "86";
    private static final int NOT_CODE = -1;                 //默认状态,尚未请求验证码
    private static final int CODE_ING = 0;                //已发送,倒计时
    private static final int CODE_REPEAT = 1;           //重新发送
    private static final int SMSSDK_HANDLER = 2;        //短信回调
    private final int SMS_INTERVAL = 60;                     //倒计时60s

    @BindView(R.id.tl_title)
    TitleLayout mTlTitle;
    @BindView(R.id.et_phone)
    MaterialEditText mEtPhone;
    @BindView(R.id.et_code)
    MaterialEditText mEtCode;
    @BindView(R.id.tv_code)
    TextView mTvCode;
    @BindView(R.id.btn_login)
    Button mBtnLogin;

    private int mStatus;                    //当前状态
    private int mCountDown = SMS_INTERVAL;              //倒计时
    private EventHandler mEventHandler;
    private boolean isCodeVerified = false;                     //验证码验证是否通过
    private String mCode;
    private String mPhone;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_ING:                  //已发送,倒计时
                    mTvCode.setText("重新发送\n(" + --mCountDown + "s)");

                    break;
                case CODE_REPEAT:           //重新发送
                    mTvCode.setText("获取验证码");
                    mTvCode.setClickable(true);

                    break;
                case SMSSDK_HANDLER:            //处理短信回调
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;

                    //回调完成
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        //验证码验证成功
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            AppEnv.getUserToast().simpleToast("登录成功", LENGTH_LONG);

                            // TODO: 17-5-3 验证成功,后台登录
                            isCodeVerified = true;
                            //登录自己的后台
                            registerFree();

                        } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            AppEnv.getUserToast().simpleToast("验证码已经发送");
                        } else {
                            ((Throwable) data).printStackTrace();
                        }
                    } else if (result == SMSSDK.RESULT_ERROR) {
                        try {
                            Throwable throwable = (Throwable) data;
                            throwable.printStackTrace();
                            JSONObject object = new JSONObject(throwable.getMessage());
                            String desc = object.optString("detail");               //错误描述
                            int status = object.optInt("status");                       //错误代码
                            if (status > 0 && !TextUtils.isEmpty(desc)) {
                                AppEnv.getUserToast().simpleToast(desc);
                            }
                        } catch (Exception e) {
                            //do sth
                        }
                    }

                    break;
            }
        }
    };
    private int VERIFY_CODE_LENGTH = 6;

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SMSSDK.unregisterEventHandler(mEventHandler);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_phone_login;
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initEvent() {
        super.initEvent();

        mTlTitle.setOnLeftIconClickListener(() -> finish());

        mEventHandler = new EventHandler() {
            @Override
            public void afterEvent(int i, int i1, Object o) {
                Message msg = Message.obtain();
                msg.arg1 = i;
                msg.arg2 = i1;
                msg.obj = o;
                msg.what = SMSSDK_HANDLER;
                mHandler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(mEventHandler);
    }

    @OnClick(R.id.tv_code)
    void onVerifyCode() {
        mPhone = mEtPhone.getText().toString().trim();
        if (!CheckUtils.isPhone(mPhone)) {
            AppEnv.getUserToast().simpleToast("请输入正确的手机号");
            return;
        }

        SMSSDK.getVerificationCode(REGION_CODE, mPhone);
        mTvCode.setClickable(false);

        // TODO: 17-5-3 将直接开子线程的代码替换掉,现在只是为了跑通
        new Thread(() -> {
            for (int i = mCountDown; i > 0; i--) {
                mHandler.sendEmptyMessage(CODE_ING);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mHandler.sendEmptyMessage(CODE_REPEAT);
        }).start();
    }

    @OnClick(R.id.btn_login)
    void onLogin() {
        mCode = mEtCode.getText().toString().trim();
        if (!CheckUtils.isPhone(mPhone)) {
            AppEnv.getUserToast().simpleToast("请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(mCode)) {
            AppEnv.getUserToast().simpleToast("请输入正确的验证码");
            return;
        }

        SMSSDK.submitVerificationCode(REGION_CODE, mPhone, mCode);
    }

    /**
     * 登录自己的后台
     */
    private void registerFree() {
        getSubscription().add(
                DataObservable.registerFree(DataObservable.TYPE_NETWORK, mPhone)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<RegisterFreeResultBean>() {
                            @Override
                            public void onCompleted() {
                                //ignore
                            }

                            @Override
                            public void onError(Throwable e) {
                                AppEnv.getUserToast().simpleToast("登录失败,请检查网络后重试");
                            }

                            @Override
                            public void onNext(RegisterFreeResultBean registerFreeResultBean) {
                                initUserProfile(registerFreeResultBean);
                                IntentHelper.simpleJump(PhoneLoginActivity.this, MyMainActivity.class);
                                finish();
                            }
                        })
        );
    }

    /**
     * 初始化用户信息
     *
     * @param bean
     */
    private void initUserProfile(@NonNull RegisterFreeResultBean bean) {
        // FIXME: 17-5-9 UID应该是字符串的...
        UserProfile.getInstance().setUid(Integer.parseInt(bean.getUid()));
        UserProfile.getInstance().setMobile(bean.getMobile());
        UserProfile.getInstance().setNickname(bean.getNickname());
        UserProfile.getInstance().setSignature(bean.getSignature());
        UserProfile.getInstance().setSex(bean.getSex());
        UserProfile.getInstance().setBirthday(bean.getBirthday());
        UserProfile.getInstance().setRealname(bean.getRealname());
        UserProfile.getInstance().setEmail(bean.getEmail());
        UserProfile.getInstance().setUser_avatar(bean.getUser_avatar());
        UserProfile.getInstance().setToken(bean.getToken());
        UserProfile.getInstance().setRefresh_token(bean.getRefresh_token());
    }
}
