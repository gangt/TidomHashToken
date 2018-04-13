package com.yunqukuailian.app.model;

/**
 * Created by Tidom on 2018/3/21/021.
 */

public class Tb_ResponseDataBean {
    int code ;
    String message ;
    String data ;

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

    public Tb_ResponseDataBean(int code, String message, String data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
