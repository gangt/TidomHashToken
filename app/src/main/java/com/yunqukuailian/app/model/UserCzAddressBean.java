package com.yunqukuailian.app.model;

/**
 * Created by admin on 2018/3/22.
 */

public class UserCzAddressBean {

    /***
     * [text={"code":1,"message":"SUCCESS","data":"--"}]
     */

    private int code ;

    private String message ;

    private String data ;

    public UserCzAddressBean(int code, String message, String data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
