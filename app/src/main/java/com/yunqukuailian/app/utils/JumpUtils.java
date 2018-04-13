package com.yunqukuailian.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;


public class JumpUtils {
    public static String FIRSTTAG ="FIRSTTAG";
    public static String TYPE = "TYPE";
    public static String PIC = "PICTURE";
    public static int REQUESTCODE = 1001;
    public static int RESULTCODE = 1002;
    public static int KDATA =500;//k线每次获取数据量
    public static int KDATASIMPLE =30;//k线每次获取数据量

    public static final String BUGTAGKEY = "e1af8a006aa5600a31729e332d02d07c";
    public static void JumpActivity(Context context, Class<? extends Activity> toActivity){
        Intent intent = new Intent(context,toActivity);
        context.startActivity(intent);
    }
    public static void JumpActivity(Context context, Class<? extends Activity> toActivity, String tag){
        Intent intent = new Intent(context,toActivity);
        intent.putExtra(FIRSTTAG,tag);
        context.startActivity(intent);
    }
    public static void JumpActivity(Context context, Class<? extends Activity> toActivity, int tag){
        Intent intent = new Intent(context,toActivity);
        intent.putExtra(FIRSTTAG,tag);
        context.startActivity(intent);
    }
    public static void JumpActivity(Context context, Class<? extends Activity> toActivity, Bundle bundle){
        Intent intent = new Intent(context,toActivity);
        intent.putExtra(FIRSTTAG,bundle);
        context.startActivity(intent);
    }
    public static void JumpActivity(Context context, Class<? extends Activity> toActivity, String tag, int type){
        Intent intent = new Intent(context,toActivity);
        intent.putExtra(FIRSTTAG,tag);
        intent.putExtra(TYPE,type);
        context.startActivity(intent);
    }
    public static void JumpActivity(Context context, Class<? extends Activity> toActivity, String tag, String type){
        Intent intent = new Intent(context,toActivity);
        intent.putExtra(FIRSTTAG,tag);
        intent.putExtra(TYPE,type);
        context.startActivity(intent);
    }

    public static void JumpActivity(Context context, Class<? extends Activity> toActivity, String tag, String type, String str){
        Intent intent = new Intent(context,toActivity);
        intent.putExtra(FIRSTTAG,tag);
        intent.putExtra(TYPE,type);
        intent.putExtra(PIC,str);
        context.startActivity(intent);
    }

    public static void JumpActivity(Context context, Class<? extends Activity> toActivity, int tag, int type){
        Intent intent = new Intent(context,toActivity);
        intent.putExtra(FIRSTTAG,tag);
        intent.putExtra(TYPE,type);
        context.startActivity(intent);
    }
    public static void JumpActivityForResult(Activity activity, Context context, Class<? extends Activity> toActivity, int code){
        Intent intent = new Intent(context,toActivity);
        activity.startActivityForResult(intent,code);
    }

    public static void JumpActivityForResult(Fragment fragment, Context context, Class<? extends Activity> toActivity, int code){
        Intent intent = new Intent(context,toActivity);
        fragment.startActivityForResult(intent,code);
    }
    public static void JumpActivityForResult(Activity activity, Context context, Class<? extends Activity> toActivity, String tag, int code){
        Intent intent = new Intent(context,toActivity);
        intent.putExtra(FIRSTTAG,tag);
        activity.startActivityForResult(intent,code);
    }

    public static void JumpActivityForResult(Activity activity, Context context, Class<? extends Activity> toActivity, String tag, int code, int type){
        Intent intent = new Intent(context,toActivity);
        intent.putExtra(FIRSTTAG,tag);
        intent.putExtra(TYPE,type);
        activity.startActivityForResult(intent,code);
    }


}
