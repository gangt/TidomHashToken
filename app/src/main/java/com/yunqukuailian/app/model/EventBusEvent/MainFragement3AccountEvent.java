package com.yunqukuailian.app.model.EventBusEvent;

import com.yunqukuailian.app.model.MainFragment3Bean;

import java.util.List;

/**
 * Created by Tidom on 2018/3/22/022.
 */

public class MainFragement3AccountEvent {

    List<MainFragment3Bean> list ;

    public  MainFragement3AccountEvent(List<MainFragment3Bean> list ){
        this.list = list ;
    }

    public List<MainFragment3Bean> getList() {
        return list;
    }
}
