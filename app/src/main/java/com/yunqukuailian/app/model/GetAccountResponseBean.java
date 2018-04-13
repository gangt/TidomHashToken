package com.yunqukuailian.app.model;

import java.util.List;

/**
 * Created by Tidom on 2018/3/17/017.
 */

public class GetAccountResponseBean {
    private int code;
    private String message;

    private AccountReponseData  data ;


    public AccountReponseData getData() {
        return data;
    }

    public void setData(AccountReponseData data) {
        this.data = data;
    }

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


    public class AccountReponseData {
        List<AccountDataBean> assertList;
        double totalAssert;
        double totalAssertUsd;

        public List<AccountDataBean> getAssertList() {
            return assertList;
        }

        public void setAssertList(List<AccountDataBean> assertList) {
            this.assertList = assertList;
        }

        public double getTotalAssert() {
            return totalAssert;
        }

        public void setTotalAssert(double totalAssert) {
            this.totalAssert = totalAssert;
        }

        public double getTotalAssertUsd() {
            return totalAssertUsd;
        }

        public void setTotalAssertUsd(double totalAssertUsd) {
            this.totalAssertUsd = totalAssertUsd;
        }

        /**
         * "coinId": 1,
         * "coinName": "cny",
         * "coinImgUrl": "http://image.hht.one/group1/M00/00/00/rB8tr1mL_XaANryHAAAHaYjwCWE604.png",
         * "coinNum": 0,
         * "totalNum": 0,
         * "coinFreezeNum": 0,
         * "feeRate": 0
         */

       public class AccountDataBean {
            int coinId;
            String coinName;
            String coinImgUrl;
            double coinNum;
            double totalNum;
            double coinFreezeNum;
            double feeRate;

            public int getCoinId() {
                return coinId;
            }

            public void setCoinId(int coinId) {
                this.coinId = coinId;
            }

            public String getCoinName() {
                return coinName;
            }

            public void setCoinName(String coinName) {
                this.coinName = coinName;
            }

            public String getCoinImgUrl() {
                return coinImgUrl;
            }

            public void setCoinImgUrl(String coinImgUrl) {
                this.coinImgUrl = coinImgUrl;
            }

            public double getCoinNum() {
                return coinNum;
            }

            public void setCoinNum(double coinNum) {
                this.coinNum = coinNum;
            }

            public double getTotalNum() {
                return totalNum;
            }

            public void setTotalNum(double totalNum) {
                this.totalNum = totalNum;
            }

            public double getCoinFreezeNum() {
                return coinFreezeNum;
            }

            public void setCoinFreezeNum(double coinFreezeNum) {
                this.coinFreezeNum = coinFreezeNum;
            }

            public double getFeeRate() {
                return feeRate;
            }

            public void setFeeRate(double feeRate) {
                this.feeRate = feeRate;
            }
        }
        }


    }



