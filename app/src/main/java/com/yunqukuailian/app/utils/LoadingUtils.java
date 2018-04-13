package com.yunqukuailian.app.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.yunqukuailian.app.R;

import static com.yunqukuailian.app.utils.DisplayUtil.dip2px;
import static com.yunqukuailian.app.utils.DisplayUtil.px2dip;

/**
 * Created by Administrator on 2018/3/20/020.
 */

public class LoadingUtils {
    private static Dialog dialog;
    private static Dialog dialogFull;

    public static void showProgress(Context context) {
        if (dialog == null) {
            dialog = new Dialog(context, R.style.DialogTheme);
            View view = LayoutInflater.from(context).inflate(R.layout.loading_view2, null);
            dialog.setContentView(view);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
        }
        dialog.show();
    }

    public static  void showFullProgress(Activity context) {
        if(dialogFull == null) {
            dialogFull = new Dialog(context, R.style.DialogTheme);
        }
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


    public static void dissProgress() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;

        }
    }

    public static void dissProgressFull() {
        if (dialogFull != null && dialogFull.isShowing()) {
            dialogFull.dismiss();
            dialogFull = null;
        }
    }
}


