package com.yunqukuailian.app.presenter;

import com.yunqukuailian.app.model.LoginBean;

/**
 * Created by tidom on 2018/3/14/014.
 */

public interface LoginContract {
    interface  LoginView{

       void showToast(String msg);
    }


    interface  UserLogin{
        void login(LoginBean loginRequestDTO);
//        void login(String moblie,String password);
    }

}
