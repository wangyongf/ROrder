package com.yongf.rorder.app.application;

import com.yongf.rorder.BuildConfig;
import com.yongf.rorder.MyRobolectricTestRunner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;

@RunWith(MyRobolectricTestRunner.class)
@org.robolectric.annotation.Config(constants = BuildConfig.class)
public class ConfigTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testInitConfig() {
        Config.init(RuntimeEnvironment.application);
        assertEquals(true, Config.DEBUG);
    }
}
