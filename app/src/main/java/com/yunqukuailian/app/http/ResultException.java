package com.yunqukuailian.app.http;

/**
 * Created by hedong on 2016/4/19.
 */

/**
 * 用于捕获服务器约定的错误类型
 */
public class ResultException extends RuntimeException {
    private int errCode = 0;

    public ResultException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
    }

    public int getErrCode() {
        return errCode;
    }
}
