package com.yunqukuailian.app.model;

/**
 * Created by tidom on 2018/3/14/014.
 */

public class UserRegisterData {
    private static UserRegisterData instance ;

    private UserRegisterData(){}

    public static UserRegisterData getInstance(){
        if(instance == null){
            instance = new UserRegisterData();
        }
        return instance ;
    }

    public String userMobile ;

    public String userPassword ;

    public String userToken ;

    public String verifyCode ;

    public String inviteCode ;

    public String countryCode ;


}
