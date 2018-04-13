package com.yunqukuailian.app.model;

/**
 * Created by Tidom on 2018/3/16/016.
 */


    public class LoginResponseBean {

        private int code;
        private String message;
        private UserBean data;
        public void setCode(int code) {
            this.code = code;
        }
        public int getCode() {
            return code;
        }

        public void setMessage(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }

        public void setData(UserBean data) {
            this.data = data;
        }
        public UserBean getData() {
            return data;
        }


    public class UserBean {

        private String userName;
        private String mobile;
        private String token;
        private int payPasswordStatus;
        private int authStatus;
        public void setUserName(String userName) {
            this.userName = userName;
        }
        public String getUserName() {
            return userName;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
        public String getMobile() {
            return mobile;
        }

        public void setToken(String token) {
            this.token = token;
        }
        public String getToken() {
            return token;
        }

        public void setPayPasswordStatus(int payPasswordStatus) {
            this.payPasswordStatus = payPasswordStatus;
        }
        public int getPayPasswordStatus() {
            return payPasswordStatus;
        }

        public void setAuthStatus(int authStatus) {
            this.authStatus = authStatus;
        }
        public int getAuthStatus() {
            return authStatus;
        }

    }

}
