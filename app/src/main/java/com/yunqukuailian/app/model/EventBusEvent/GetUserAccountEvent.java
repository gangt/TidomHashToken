package com.yunqukuailian.app.model.EventBusEvent;

/**
 * Created by Tidom on 2018/3/17/017.
 */

public class GetUserAccountEvent {

    private String userAccount ;

    public GetUserAccountEvent(String userAccount) {
        this.userAccount = userAccount;
    }
}
