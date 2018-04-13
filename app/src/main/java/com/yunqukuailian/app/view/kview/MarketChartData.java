package com.yunqukuailian.app.view.kview;

import java.text.SimpleDateFormat;

/**
 * Created by zhangliang on 16/11/5.
 * QQ:1179980507
 */
public class MarketChartData {
    long time = 0;
    double openPrice = 0f;
    double closePrice = 0f;
    double lowPrice = 0f;
    float highPrice = 0f;
    float vol = 0f;

    public MarketChartData() {

    }

    public long getTime() {
        return time;
    }

    public String getTime2() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(time);
    }
    public String getTime3() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(time);
    }
    public String getTime4() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(time);
    }
    public String getTime5() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        return sdf.format(time);
    }
    public void setTime(long time) {
        this.time = time;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public float getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(float highPrice) {
        this.highPrice = highPrice;
    }

    public float getVol() {
        return vol;
    }

    public void setVol(float vol) {
        this.vol = vol;
    }
}
