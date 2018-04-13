package com.yunqukuailian.app.model.EventBusEvent;

import com.yunqukuailian.app.model.UserBillBean;

import java.util.List;

/**
 * Created by Tidom on 2018/3/22/022.
 */

public class GetUserBillEvent {

    private UserBillBean userBillBean ;

    public GetUserBillEvent(UserBillBean  userBillBean){
        this.userBillBean = userBillBean ;
    }

    public UserBillBean getUserBillBean() {
        return userBillBean;
    }
}
