package com.yunqukuailian.app.model;

import java.util.List;

/**
 * Created by Tidom on 2018/3/22/022.
 */

public class UserBillBean {

    private int code ;
    private String message ;
    private List<UserBillBeanItem> data ;



    public class UserBillBeanItem {
        private float amount ;
        private int type ;
        private String time ;
        int id ;

        public float getAmount() {
            return amount;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
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

    public List<UserBillBeanItem> getData() {
        return data;
    }

    public void setData(List<UserBillBeanItem> data) {
        this.data = data;
    }
}
