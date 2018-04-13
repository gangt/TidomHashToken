package com.yunqukuailian.app.model;

/**
 * Created by Administrator on 2018/3/21/021.
 */

public class SimpleBaseBean {

    /**
     * code : 10027
     * message : 参数出错,下单失败
     * data : null
     */

    private int code;
    private String message;
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
