package com.yunqukuailian.app.model;

/**
 * Created by Administrator on 2018/3/14/014.
 */

public class KDeatilHeaderBean {

    /**
     * code : 1
     * message : SUCCESS
     * data : {"last":89000,"high":89000,"low":89000,"vol":0,"change":0,"lastCny":89000}
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
         * last : 89000.0
         * high : 89000.0
         * low : 89000.0
         * vol : 0
         * change : 0.0
         * lastCny : 89000.0
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
