package com.yunqukuailian.app.model;

import java.util.List;

/**
 * Created by Administrator on 2018/3/16/016.
 */

public class DepthsBean {

    /**
     * code : 1
     * message : SUCCESS
     * data : {"buy":[{"price":"2.01000000","volum":923.8836},{"price":"2.00000000","volum":271.0914},{"price":"1.98000000","volum":56.3847},{"price":"1.83000000","volum":22},{"price":"1.81000000","volum":68389.4592},{"price":"1.80000000","volum":1500},{"price":"1.60000000","volum":239.1742},{"price":"1.50000000","volum":100},{"price":"1.40000000","volum":43.6891},{"price":"1.25000000","volum":633.1862}],"sell":[{"price":"2.01000000","volum":813.8836},{"price":"2.78000000","volum":760.9114},{"price":"2.80000000","volum":10099.99},{"price":"2.83000000","volum":199.3098},{"price":"2.85000000","volum":1399.8},{"price":"2.86000000","volum":100},{"price":"2.86800000","volum":190.2831},{"price":"2.86900000","volum":5135.9966},{"price":"2.87000000","volum":3082.9225},{"price":"3.00000000","volum":547.8014}],"last":2.01,"lastCny":2.01,"priceCny":1}
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
         * buy : [{"price":"2.01000000","volum":923.8836},{"price":"2.00000000","volum":271.0914},{"price":"1.98000000","volum":56.3847},{"price":"1.83000000","volum":22},{"price":"1.81000000","volum":68389.4592},{"price":"1.80000000","volum":1500},{"price":"1.60000000","volum":239.1742},{"price":"1.50000000","volum":100},{"price":"1.40000000","volum":43.6891},{"price":"1.25000000","volum":633.1862}]
         * sell : [{"price":"2.01000000","volum":813.8836},{"price":"2.78000000","volum":760.9114},{"price":"2.80000000","volum":10099.99},{"price":"2.83000000","volum":199.3098},{"price":"2.85000000","volum":1399.8},{"price":"2.86000000","volum":100},{"price":"2.86800000","volum":190.2831},{"price":"2.86900000","volum":5135.9966},{"price":"2.87000000","volum":3082.9225},{"price":"3.00000000","volum":547.8014}]
         * last : 2.01
         * lastCny : 2.01
         * priceCny : 1
         */

        private String last;
        private String lastCny;
        private double priceCny;
        private List<BuyBean> buy;
        private List<SellBean> sell;

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }

        public String getLastCny() {
            return lastCny;
        }

        public void setLastCny(String lastCny) {
            this.lastCny = lastCny;
        }

        public double getPriceCny() {
            return priceCny;
        }

        public void setPriceCny(int priceCny) {
            this.priceCny = priceCny;
        }

        public List<BuyBean> getBuy() {
            return buy;
        }

        public void setBuy(List<BuyBean> buy) {
            this.buy = buy;
        }

        public List<SellBean> getSell() {
            return sell;
        }

        public void setSell(List<SellBean> sell) {
            this.sell = sell;
        }

        public static class BuyBean {
            /**
             * price : 2.01000000
             * volum : 923.8836
             */

            private String price;
            private String volum;

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getVolum() {
                return volum;
            }

            public void setVolum(String volum) {
                this.volum = volum;
            }
        }

        public static class SellBean {
            /**
             * price : 2.01000000
             * volum : 813.8836
             */

            private String price;
            private String volum;

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getVolum() {
                return volum;
            }

            public void setVolum(String volum) {
                this.volum = volum;
            }
        }
    }
}
