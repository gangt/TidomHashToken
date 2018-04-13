package com.yunqukuailian.app.model;

/**
 * Created by Administrator on 2018/3/22/022.
 */

public class MyTickerBean {

    /**
     * code : 1
     * message : SUCCESS
     * data : {"last":1.59999999,"high":1.59999999,"low":1.01,"vol":3974.3402,"change":5.27,"lastCny":1.59999999}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * last : 1.59999999
         * high : 1.59999999
         * low : 1.01
         * vol : 3974.3402
         * change : 5.27
         * lastCny : 1.59999999
         */

        private String last;
        private double high;
        private double low;
        private double vol;
        private double change;
        private String lastCny;

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }

        public double getHigh() {
            return high;
        }

        public void setHigh(double high) {
            this.high = high;
        }

        public double getLow() {
            return low;
        }

        public void setLow(double low) {
            this.low = low;
        }

        public double getVol() {
            return vol;
        }

        public void setVol(double vol) {
            this.vol = vol;
        }

        public double getChange() {
            return change;
        }

        public void setChange(double change) {
            this.change = change;
        }

        public String getLastCny() {
            return lastCny;
        }

        public void setLastCny(String lastCny) {
            this.lastCny = lastCny;
        }
    }
}
