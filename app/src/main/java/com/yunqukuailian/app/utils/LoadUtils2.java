package com.yunqukuailian.app.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.yunqukuailian.app.R;

import static com.yunqukuailian.app.utils.DisplayUtil.dip2px;

/**
 * Created by Tidom on 2018/4/2/002.
 */

public class LoadUtils2 {

    private  Dialog dialogFull;

    private  static LoadUtils2 instance ;

    private LoadUtils2(){}

    public static synchronized LoadUtils2 getInstance(){
        if(instance == null){
            instance = new LoadUtils2();
        }
        return  instance ;
    }

    public  void showFullProgress(Activity context) {
//        if(dialogFull == null) {
            dialogFull = new Dialog(context, R.style.DialogTheme);
//        }
        View view = LayoutInflater.from(context).inflate(R.layout.loading_view2, null);

//            Display display = context.getWindowManager().getDefaultDisplay();
        int width = dip2px(context, 80);
        int height = dip2px(context, 80);
        //设置dialog的宽高为屏幕的宽高
        WindowManager.LayoutParams wlp = dialogFull.getWindow().getAttributes();
        wlp.gravity = Gravity.CENTER;
        dialogFull.getWindow().setAttributes(wlp);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
        dialogFull.setContentView(view, layoutParams);
        dialogFull.setCancelable(false);
        dialogFull.setCanceledOnTouchOutside(false);
        dialogFull.show();
    }

    public  void dissProgressFull() {
        if (dialogFull != null && dialogFull.isShowing()) {
            dialogFull.dismiss();
            dialogFull = null;
        }
    }
}
