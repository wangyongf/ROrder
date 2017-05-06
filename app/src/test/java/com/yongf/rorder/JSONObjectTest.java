package com.yongf.rorder;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

@RunWith(MyRobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class JSONObjectTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testJSONObject() throws Exception {
        String rawJson = "{\n" +
                "  \"code\": 0,\n" +
                "  \"msg\": \"接口调用成功\",\n" +
                "  \"data\": {\n" +
                "      \"token\": \"JKLWESJKDS83\"\n" +
                "  }\n" +
                "}";
        JSONObject object = new JSONObject(rawJson);
        System.out.println(object.getJSONObject("data").toString());
    }
}
