package com.yongf.rorder.util;

import com.yongf.rorder.BuildConfig;
import com.yongf.rorder.MyRobolectricTestRunner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(MyRobolectricTestRunner.class)
@org.robolectric.annotation.Config(constants = BuildConfig.class)
public class CheckUtilsTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void isPhone() throws Exception {
        assertEquals(true, CheckUtils.isPhone("15221382253"));
        assertEquals(false, CheckUtils.isPhone("1522138225"));
        assertEquals(false, CheckUtils.isPhone("a522138227d"));
    }

    @Test
    public void isEmail() throws Exception {
        assertEquals(true, CheckUtils.isEmail("1059613472@qq.com"));
        assertEquals(true, CheckUtils.isEmail("scottwang1996@gmail.com"));
        assertEquals(false, CheckUtils.isEmail("15221382253"));
    }

}
