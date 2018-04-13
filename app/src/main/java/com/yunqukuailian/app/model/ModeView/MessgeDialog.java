package com.yunqukuailian.app.model.ModeView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yunqukuailian.app.R;
import com.yunqukuailian.app.model.EventBusEvent.FinishActivityEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tidom on 2018/3/22/022.
 */

public class MessgeDialog implements View.OnClickListener{
    @BindView(R.id.user_bii_show)
    TextView userBiiShow;
    @BindView(R.id.got_it)
    TextView gotIt;
    private Context context;

    private Dialog showDialog;



    public void showDialg(Context context ,String text) {
        showDialog = new AlertDialog.Builder(context).create();

        if(!showDialog.isShowing()){
            showDialog.show();
        }


        Window window = showDialog.getWindow();
        LayoutInflater layout = LayoutInflater.from(context);
        View view = layout.inflate(R.layout.dialog_view, null);
        ButterKnife.bind(view);
        window.setContentView(view);

        view.findViewById(R.id.message_show_ll).setOnClickListener(this);
        TextView showView = (TextView)view.findViewById(R.id.user_bii_show);
        showView .setText(text);

        showDialog.getWindow().setBackgroundDrawable(new BitmapDrawable());

//        showDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        WindowManager.LayoutParams wlp = showDialog.getWindow().getAttributes();
        wlp.gravity = Gravity.CENTER;
        showDialog.getWindow().setAttributes(wlp);
        showDialog.setCanceledOnTouchOutside(false);

    }


    @Override
    public void onClick(View v) {
        showDialog.cancel();
        showDialog = null;
        EventBus.getDefault().post(new FinishActivityEvent());
    }

}
