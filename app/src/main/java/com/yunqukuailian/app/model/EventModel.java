package com.yunqukuailian.app.model;

/**
 * Created by Administrator on 2018/3/16/016.
 */

public class EventModel {
    private String countryName;
    private int marketId;
    private String newPrice;
    private String newPriceCNY;

    public EventModel() {

    }

    public EventModel(String countryName, int marketId, String newPrice, String newPriceCNY) {
        this.countryName = countryName;
        this.marketId = marketId;
        this.newPrice = newPrice;
        this.newPriceCNY = newPriceCNY;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    public String getNewPriceCNY() {
        return newPriceCNY;
    }

    public void setNewPriceCNY(String newPriceCNY) {
        this.newPriceCNY = newPriceCNY;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }
}
