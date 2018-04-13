package com.yunqukuailian.app.model;

import com.yunqukuailian.app.utils.SharedPrefsUtil;

/**
 * Created by Tidom on 2018/3/17/017.
 */

public class UserAccout {


    public static String getRmbAccount() {
        return SharedPrefsUtil.getString("rmbAccount");
    }

    public static void setRmbAccount(String rmbAccount) {
        SharedPrefsUtil.setString("rmbAccount", rmbAccount);
    }

    public static String getDollarAccount() {
        return SharedPrefsUtil.getString("dollarAccount");
    }

    public static void setDollarAccpunt(String dollarAccpunt) {
        SharedPrefsUtil.setString("dollarAccount", dollarAccpunt);
    }

}
