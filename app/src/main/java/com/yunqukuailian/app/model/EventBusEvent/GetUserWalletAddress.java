package com.yunqukuailian.app.model.EventBusEvent;

/**
 * Created by Tidom on 2018/3/21/021.
 */

public class GetUserWalletAddress {
    private String address ;

    public GetUserWalletAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
