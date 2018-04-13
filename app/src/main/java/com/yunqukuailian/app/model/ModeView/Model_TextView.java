package com.yunqukuailian.app.model.ModeView;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.yunqukuailian.app.utils.LogUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tidom on 2018/3/21/021.
 */

public class Model_TextView {

    private static  Model_TextView instance = null;
    private long countLength = 60;
    private Timer timer;
    private TimerTask task;
    private TextView view ;

    private Model_TextView (){}

    public void attatchView(TextView view){
        this.view = view ;
    }

    public static Model_TextView getInstance(){
        if(instance == null){
            instance = new Model_TextView();
        }
        return  instance ;
    }


    public void setCOuntLength(){
        countLength = 60 ;
    }

    private void initTimer() {
        setCOuntLength();
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if(countLength>= 0){
                    mHandler.sendEmptyMessage(0);
                }
            }
        };
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            view.setText(countLength + " s");
            LogUtils.v("countLength",countLength+"");
            countLength--;
            if (countLength < 0) {
                view.setEnabled(true);
                view.setText("重新获取");
                clear();
            }
            super.handleMessage(msg);
        }
    };

    public void clear() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (task != null) {
            task.cancel();
            task = null;
        }
        if(mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    public  void startAlarm(){
        view.setEnabled(false);
        initTimer();
        start();
    }

    public void start() {
        timer.scheduleAtFixedRate(task, 0, 1000);
    }


}
