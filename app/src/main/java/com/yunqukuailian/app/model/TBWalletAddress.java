package com.yunqukuailian.app.model;

import java.util.List;

/**
 * Created by Tidom on 2018/3/24/024.
 */

public class TBWalletAddress {

    private int code ;
    private String message ;
    private List<AddressBean> data ;


    public class AddressBean{
        private int addressId;
        private String name ;
        private String address ;

        public int getAddressId() {
            return addressId;
        }

        public void setAddressId(int addressId) {
            this.addressId = addressId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

    public List<AddressBean> getData() {
        return data;
    }

    public void setData(List<AddressBean> data) {
        this.data = data;
    }
}
