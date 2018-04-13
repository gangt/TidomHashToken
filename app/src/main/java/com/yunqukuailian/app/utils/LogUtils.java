package com.yunqukuailian.app.utils;

import android.util.Log;

/**
 * Created by Administrator on 2018/3/14/014.
 */



public class LogUtils {
    private static final  boolean isDebug= true ;
    public static void v(String TAG,String msg){
        if(isDebug){
            Log.v(TAG,msg);
        }
    }

}
