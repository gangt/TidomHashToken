package com.yunqukuailian.app.model;


/**
 * Created by Administrator on 2018/3/15/015.
 */

public class LoginBean {


    private String mobile;

    private String password;

    public LoginBean(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
//    }


}
