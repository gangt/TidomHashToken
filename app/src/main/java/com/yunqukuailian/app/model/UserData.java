package com.yunqukuailian.app.model;

import android.text.TextUtils;
import android.util.Log;

import com.yunqukuailian.app.utils.HMacSha256;
import com.yunqukuailian.app.utils.SharedPrefsUtil;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by Tidom on 2018/3/16/016.
 */

public class UserData {

    public static String getMobile() {
        return SharedPrefsUtil.getString("mobile");
    }

    public static void setMobile(String mobile) {
        SharedPrefsUtil.setString("mobile", mobile);
    }


    public static String getToken() {
        return SharedPrefsUtil.getToken();
    }

    public static void setToken(String token) {
        SharedPrefsUtil.setString("token", token);
    }

    public static String getUserName(String userName) {

        return SharedPrefsUtil.getString(userName);
    }

    public static void setUserName(String userName) {
        SharedPrefsUtil.setString("userName", userName);
    }

    public static void setPassWord(String passWord){
        SharedPrefsUtil.setString("password", passWord);
    }

    public static String getPassWord(String passWord){
        return SharedPrefsUtil.getString(passWord);
    }

    /**
     * 注销登录
     */
    public static void clean(){
        setMobile("");
        setToken("");
        setUserName("");
        setPassWord("");
    }

    /**
     * 登录判断
     * @return
     */
    public static  boolean isLogin(){
        return !TextUtils.isEmpty(getMobile()) ;
    }

    /**
     * 获取登录返回的token
     * token
     * @return
     */
    public   static String getApiKey(){
        return SharedPrefsUtil.getToken().trim();
    }


    /***
     * 获取时间戳
     * @return
     */
    public static String getSecondTimestampTwo(){
        Date date = new Date();
        int secends = Integer.parseInt(String.valueOf(date.getTime() / 1000))-8*3600;
        return String.valueOf(secends).trim();
    }


    /***
     * 获取签名
     * @param apiKey
     * @param time
     * @return
     */
    public static String getSign(String apiKey,String time){
        //用户登陆双重MD5;
        try {
            String key = SharedPrefsUtil.getSignKey();
            String sorceCode = getSource(apiKey,time).trim();
//            Log.e("yongyyixxx",sorceCode);
            String sign = HMacSha256.hash(sorceCode.getBytes(),key.getBytes()).trim();
//            Log.e("yongyisigin",sign);
            return sign;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null ;
    }

    /***
     * 获取加密source
     * @param apikey
     * @param teim
     * @return
     */
    private static String getSource(String apikey,String teim){
        //apikey=351dca7e0d23c1b39d8f2e07fecc5561&timestamp=20171025012614

        return  apikey+"="+getApiKey()+"&"+teim+"="+getSecondTimestampTwo();
    }
}
