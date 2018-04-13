package com.yunqukuailian.app.model.EventBusEvent;

import com.yunqukuailian.app.model.TBWalletAddress;

/**
 * Created by Tidom on 2018/3/24/024.
 */

public class TbWalletAddressEvent {

    private TBWalletAddress address ;

    public TbWalletAddressEvent(TBWalletAddress address){
        this.address = address ;
    }

    public TBWalletAddress getAddress() {
        return address;
    }
}
