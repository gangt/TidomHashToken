package com.yunqukuailian.app.model;

/**
 * Created by Administrator on 2018/3/21/021.
 */

public class PlaceOrderBean {

    /**
     * market : 0
     * num : 0
     * price : 0
     * type : 0
     */

    private int market;
    private float num;
    private float price;
    private int type;


    public int getMarket() {
        return market;
    }

    public void setMarket(int market) {
        this.market = market;
    }

    public float getNum() {
        return num;
    }

    public void setNum(float num) {
        this.num = num;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
