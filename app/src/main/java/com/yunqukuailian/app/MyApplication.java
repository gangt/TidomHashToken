package com.yunqukuailian.app;

import android.app.Application;
import android.content.Context;

import com.bugtags.library.Bugtags;
import com.yunqukuailian.app.utils.JumpUtils;

/**
 * Created by Administrator on 2018/3/1/001.
 */

public class MyApplication extends Application {
    private static MyApplication context;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        SophixManager.getInstance().initialize();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
//        Bugtags.start(JumpUtils.BUGTAGKEY, this, Bugtags.BTGInvocationEventBubble);
    }

    public static MyApplication getContext(){
        return context ;
    }

}
