package com.yunqukuailian.app.model;

import java.util.List;

/**
 * Created by Administrator on 2018/3/28/028.
 */

public class KlineBean {

    /**
     * code : 1
     * str : [[1,2],[1,2]]
     */

    private int code;
    private List<List<Integer>> str;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<List<Integer>> getStr() {
        return str;
    }

    public void setStr(List<List<Integer>> str) {
        this.str = str;
    }
}
