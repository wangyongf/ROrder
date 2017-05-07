package com.yongf.rorder;

import android.os.SystemClock;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.rx.Result;
import com.yongf.rorder.app.application.AppEnv;
import com.yongf.rorder.net.UrlCenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import rx.Subscriber;
import rx.schedulers.Schedulers;

@RunWith(MyRobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class RxVolleyTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRxVolley1() throws Exception {
        AppEnv.setMainSite(UrlCenter.LOCALHOST_SITE);

        RxVolley.get(AppEnv.getMainSite() + "/api/v1/restaurant/1/cookbook", new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                System.out.println(t);
            }
        });

        SystemClock.sleep(3000);
    }

    @Test
    public void testRxVolley2() throws Exception {
        new RxVolley.Builder()
                .url(AppEnv.getMainSite() + "/api/v1/restaurant/1/cookbook")
                .contentType(RxVolley.ContentType.JSON)
                .getResult()
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("RxVolleyTest.onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("RxVolleyTest.onError");
                    }

                    @Override
                    public void onNext(Result result) {
                        System.out.println("RxVolleyTest.onNext");
                    }
                });
    }
}
