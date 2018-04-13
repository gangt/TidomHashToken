package com.yunqukuailian.app.model;

/**
 * Created by Administrator on 2018/3/10/010.
 */

public class MainFragment3Bean {
    private String title;
    private String price;
    private String freeze;
    private int coinId ;
    private double feeRate ;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFreeze() {
        return freeze;
    }

    public void setFreeze(String freeze) {
        this.freeze = freeze;
    }

    public void setCoinId(int coinId) {
        this.coinId = coinId;
    }

    public int getCoinId() {
        return coinId;
    }

    public double getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(double fee) {
        this.feeRate = fee;
    }
}
