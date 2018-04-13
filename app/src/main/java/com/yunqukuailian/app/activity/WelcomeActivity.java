package com.yunqukuailian.app.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.ImageView;

import com.yunqukuailian.app.MainActivity;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.base.BaseSimpleActivity;
import com.yunqukuailian.app.utils.JumpUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/3/003.
 */

public class WelcomeActivity extends BaseSimpleActivity {
    @BindView(R.id.wecomeimage)
    ImageView wecomeimage;
    @BindView(R.id.welcome_icon)
    ImageView welcomeIcon;

    // 要申请的权限
    private Timer timer;
    private TimerTask task;
    private Handler handler = new Handler();
    private String url;
    private int closetime = 4;//设置显示时间
    private int closes = closetime;






    @Override
    protected int setLayoutId() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_welcome;
    }
    @Override
    public void initView() {
        super.initView();
        timer = new Timer();
        task = new TimerTask() {
            public void run() {
                JumpUtils.JumpActivity(WelcomeActivity.this, MainActivity.class);
                finish();
            }
        };
        // checkPer();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadmessage();
            }
        }, 1000);

    }

    private void loadmessage() {
        timer.schedule(task, 1000 * 2); //呈现2秒跳转到MainActivity(主界面)中
    }


}
