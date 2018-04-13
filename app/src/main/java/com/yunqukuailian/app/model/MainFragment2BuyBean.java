package com.yunqukuailian.app.model;

/**
 * Created by Administrator on 2018/3/20/020.
 */

public class MainFragment2BuyBean {


    /**
     * code : 1
     * message : SUCCESS
     * data : {"buyBalance":18871.06063114,"buyLock":73486.12679731,"canBuy":11794.413,"sellBalance":7698.3281,"sellLock":0,"canSell":12317.32488301672}
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
         * buyBalance : 18871.06063114
         * buyLock : 73486.12679731
         * canBuy : 11794.413
         * sellBalance : 7698.3281
         * sellLock : 0.0
         * canSell : 12317.32488301672
         */

        private float buyBalance;
        private float buyLock;
        private float canBuy;
        private float sellBalance;
        private float sellLock;
        private float canSell;

        public float getBuyBalance() {
            return buyBalance;
        }

        public void setBuyBalance(float buyBalance) {
            this.buyBalance = buyBalance;
        }

        public float getBuyLock() {
            return buyLock;
        }

        public void setBuyLock(float buyLock) {
            this.buyLock = buyLock;
        }

        public float getCanBuy() {
            return canBuy;
        }

        public void setCanBuy(float canBuy) {
            this.canBuy = canBuy;
        }

        public float getSellBalance() {
            return sellBalance;
        }

        public void setSellBalance(float sellBalance) {
            this.sellBalance = sellBalance;
        }

        public float getSellLock() {
            return sellLock;
        }

        public void setSellLock(float sellLock) {
            this.sellLock = sellLock;
        }

        public float getCanSell() {
            return canSell;
        }

        public void setCanSell(float canSell) {
            this.canSell = canSell;
        }
    }
}
