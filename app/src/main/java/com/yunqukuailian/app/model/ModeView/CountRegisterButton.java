package com.yunqukuailian.app.model.ModeView;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.yunqukuailian.app.utils.LogUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时器
 * Created by Tidom on 2018/3/17/017.
 */

public class CountRegisterButton extends android.support.v7.widget.AppCompatButton implements View.OnClickListener {
    private long countLength ;
    private Timer timer;
    private TimerTask task;
    private OnButtonClickListener listener;

    public CountRegisterButton(Context context) {
        super(context);
        init();
    }

    public CountRegisterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CountRegisterButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public void init() {
        setOnClickListener(this);
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
            setText(countLength + " s");
            LogUtils.v("countLength",countLength+"");
            countLength--;
            if (countLength < 0) {
                setEnabled(true);
                setText("重新获取");
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

        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }

        setText("重新获取");
        setEnabled(true);
    }


    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onButtonClick();
        }
    }

    public  void startAlarm(){
        setEnabled(false);
        initTimer();
        start();
    }

  /*  public void stopAlarm(){
        setEnabled(true);
        clear();
        mHandler.removeCallbacksAndMessages(null);
    }*/


    public void start() {
            timer.scheduleAtFixedRate(task, 0, 1000);
    }

    @Override
    protected void onDetachedFromWindow() {
        clear();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        super.onDetachedFromWindow();
    }

    public interface OnButtonClickListener {
        void onButtonClick();
    }

    public void setClickListener(OnButtonClickListener listener) {
        this.listener = listener;
    }

}
