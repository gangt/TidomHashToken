package com.yunqukuailian.app.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yunqukuailian.app.MyApplication;
import com.yunqukuailian.app.R;

/**
 * Created by Tidom on 2018/3/20/020.
 */

public class ToastUtils  {


    /**
     * 中间toast
     * @param textId
     */
    public static void showToastCentre(int textId){
        MyApplication context = MyApplication.getContext();
        Toast toast =  new Toast(context);
        View view = View.inflate(context, R.layout.toast_view,null);
        TextView textView = (TextView)view.findViewById(R.id.showview);
        textView.setText(textId);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showToastBottom(int textId){
        MyApplication context = MyApplication.getContext();
        Toast toast =  new Toast(context);
        View view = View.inflate(context, R.layout.toast_view,null);
        TextView textView = (TextView)view.findViewById(R.id.showview);
        textView.setText(textId);
        toast.setGravity(Gravity.BOTTOM,0,150);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showToastBottom(String text){
        MyApplication context = MyApplication.getContext();
        Toast toast =  new Toast(context);
        View view = View.inflate(context, R.layout.toast_view,null);
        TextView textView = (TextView)view.findViewById(R.id.showview);
        textView.setText(text);
        toast.setGravity(Gravity.BOTTOM,0,150);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showToastCentre(String textId){
        MyApplication context = MyApplication.getContext();
        Toast toast =  new Toast(context);
        View view = View.inflate(context, R.layout.toast_view,null);
        TextView textView = (TextView)view.findViewById(R.id.showview);
        textView.setText(textId);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }


}
