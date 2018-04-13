package com.yunqukuailian.app.model;

/**
 * Created by Tidom on 2018/3/16/016.
 */

public class RegisterResponseBean {
    /**
     * "code": 10005,
     * "message": "此用户已存在",
     * "data": null
     */

    private int code;

    private String message;
    private RegisterData data;

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

    public RegisterData getData() {
        return data;
    }

    public void setData(RegisterData data) {
        this.data = data;
    }

    class RegisterData {
    }

}
