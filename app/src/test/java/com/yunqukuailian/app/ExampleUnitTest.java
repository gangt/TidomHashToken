package com.yunqukuailian.app;

import com.google.gson.Gson;
import com.yunqukuailian.app.model.KlineBean;
import com.yunqukuailian.app.utils.Utils;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
        String jsonstr ="{\"code\": 1,\"str\": [[1, 2],[1, 2]]}";
        JSONObject jsonObject = new JSONObject(jsonstr);
        long[][] data ={{1,2},{1,2}};
        KlineBean bean = new Gson().fromJson(jsonstr,KlineBean.class);
        Assert.assertEquals(Utils.getDoubleArray(bean),data);
    }
}