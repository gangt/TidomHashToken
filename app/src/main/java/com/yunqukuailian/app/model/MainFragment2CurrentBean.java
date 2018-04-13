package com.yunqukuailian.app.model;

import java.util.List;

/**
 * Created by Administrator on 2018/3/20/020.
 */

public class MainFragment2CurrentBean {


    /**
     * code : 1
     * message : SUCCESS
     * data : [{"id":150509,"amount":10,"dealAmount":0,"mum":0,"price":1.01,"avgPrice":0,"time":1521601206000,"type":1},{"id":150510,"amount":110,"dealAmount":0,"mum":0,"price":1.01,"avgPrice":0,"time":1521601256000,"type":1}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 150509
         * amount : 10.0
         * dealAmount : 0.0
         * mum : 0.0
         * price : 1.01
         * avgPrice : 0
         * time : 1521601206000
         * type : 1
         */

        private int id;
        private double amount;
        private double dealAmount;
        private double mum;
        private double price;
        private float avgPrice;
        private long time;
        private int type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getDealAmount() {
            return dealAmount;
        }

        public void setDealAmount(double dealAmount) {
            this.dealAmount = dealAmount;
        }

        public double getMum() {
            return mum;
        }

        public void setMum(double mum) {
            this.mum = mum;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public float getAvgPrice() {
            return avgPrice;
        }

        public void setAvgPrice(float avgPrice) {
            this.avgPrice = avgPrice;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
