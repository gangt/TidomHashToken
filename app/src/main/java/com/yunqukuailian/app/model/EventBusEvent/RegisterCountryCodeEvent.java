package com.yunqukuailian.app.model.EventBusEvent;

/**
 * Created by Tidom on 2018/3/26/026.
 */

public class RegisterCountryCodeEvent {

    private String name ;

    private String code ;

    public RegisterCountryCodeEvent(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
