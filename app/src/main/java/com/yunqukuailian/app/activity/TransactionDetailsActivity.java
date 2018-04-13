package com.yunqukuailian.app.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.adapter.NormalSpinerAdapter;
import com.yunqukuailian.app.base.BaseActivity;
import com.yunqukuailian.app.http.AbsAPICallback;
import com.yunqukuailian.app.http.LocalService;
import com.yunqukuailian.app.model.KDeatilHeaderBean;
import com.yunqukuailian.app.tool.SubscriberOnNextListener;
import com.yunqukuailian.app.utils.JumpUtils;
import com.yunqukuailian.app.utils.Utils;
import com.yunqukuailian.app.view.SpinerPopWindow;
import com.yunqukuailian.app.view.kview.KView;
import com.yunqukuailian.app.view.kview.MarketChartData;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class TransactionDetailsActivity extends BaseActivity {
    @BindView(R.id.mainfragment4title)
    TextView mainfragment4title;
    @BindView(R.id.add_account)
    ImageView addAccount;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.price)
    TextView price1;
    @BindView(R.id.rate)
    TextView rate;
    @BindView(R.id.vol)
    TextView vol;
    @BindView(R.id.hight)
    TextView hight;
    @BindView(R.id.low)
    TextView low;
    @BindView(R.id.my_charts_view)
    KView myChartsView;
    @BindView(R.id.title_lay)
    LinearLayout titleLay;
    @BindView(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;
    @BindView(R.id.spinerButton)
    Button spinerButton;
    @BindView(R.id.priceimage)
    ImageView priceimage;
    private String time = "15";

    private RadioGroup myRadioGroup;
    private String kChartTimeInterval = "900";                       //图表数据间隔
    //定时器
    Timer timer1 = null;  //刷新定时器
    Timer timer2 = null;  //刷新定时器
    private final int dataRefreshTime1 = 4 * 1000;                         //数据刷新间隔
    private final int dataRefreshTime2 = 60 * 1000;                         //数据刷新间隔
    private LinearLayout title_lay;

    private List<Map<String, Object>> titleList = new ArrayList<Map<String, Object>>();


    private List<MarketChartData> marketChartDataLists = new ArrayList<MarketChartData>();
    private SpinerPopWindow mSpinerPopWindow;
    private ArrayList data_list;
    private int marketId;


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.setTitleBar(this, toolbar);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mainfragment4title.setText(getIntent().getStringExtra(JumpUtils.FIRSTTAG));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initGroup();
    }


    @Override
    public void initView() {
        marketId = getIntent().getIntExtra(JumpUtils.TYPE, 0);
        initSpinner();
        myChartsView.setShowTitle(true);//是否显示头布局
        myChartsView.setShowLatitude(true);//是否显示横虚线
        myChartsView.setdrawBorders(true);//是否显示刻度横线
        myChartsView.setIsInnerYAxis(false);//是否显示刻度
        myChartsView.setCrossCLick(true);//是否有点击效果
//        myChartsView.setOpenAnimator(true);//没看出效果
        myChartsView.setIsArrow(true);//显示箭头
        myChartsView.setDefauliMiddleLatitudeNum(2);
        myChartsView.setOpenAnimator(true);
//        myChartsView.setShowMaxValue(true);//没有看出效果
        getHeaderData();
    }

    /**
     * 生成图表显示的数据
     *
     * @param
     */
    private void createChartDataSet(long[][] marketChartDatas) {
        if (marketChartDatas != null && marketChartDatas.length > 0) {
            marketChartDataLists.clear();
            for (int i = 0; i < marketChartDatas.length; i++) {
                long[] data = marketChartDatas[i];
                MarketChartData mMarketChartData = new MarketChartData();
                mMarketChartData.setTime(data[0]);
//                mMarketChartData.setOpenPrice(data[1]);
//                mMarketChartData.setClosePrice(data[2]);
//                mMarketChartData.setHighPrice(data[3]);
//                mMarketChartData.setLowPrice(data[4]);
//                mMarketChartData.setVol(data[5]);
                mMarketChartData.setOpenPrice(data[1]);
                mMarketChartData.setHighPrice(data[2]);
                mMarketChartData.setLowPrice(data[3]);
                mMarketChartData.setClosePrice(data[4]);
                mMarketChartData.setVol(data[5]);


                marketChartDataLists.add(mMarketChartData);
            }

            //更新图表
//            Collections.reverse(marketChartDataLists);
            myChartsView.setOHLCData(marketChartDataLists);
            myChartsView.postInvalidate();
        }
    }

    private void showSpinWindow() {
        mSpinerPopWindow.setWidth(spinerButton.getWidth());
        mSpinerPopWindow.showAtLocation(spinerButton, Gravity.BOTTOM | Gravity.RIGHT, 0, spinerButton.getHeight());
    }

    private void initSpinner() {
        //数据
        data_list = new ArrayList<String>();
        data_list.add(getString(R.string.close_quota));
        data_list.add("MACD");
        data_list.add("KDJ");

        NormalSpinerAdapter mAdapter = new NormalSpinerAdapter(this);
        mAdapter.refreshData(data_list, 0);

        mSpinerPopWindow = new SpinerPopWindow(this);

        mSpinerPopWindow.setAdatper(mAdapter);
        mSpinerPopWindow.setItemListener(new SpinerPopWindow.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                if (pos >= 0 && pos <= data_list.size()) {
                    String value = data_list.get(pos).toString();

                    switch (pos) {
                        case 0:
                            myChartsView.setClose();
                            spinerButton.setText(R.string.specifications);
                            break;
                        case 1:
                            myChartsView.setMACDShow();
                            spinerButton.setText(value.toString());
                            break;
                        case 2:
                            myChartsView.setKDJShow();
                            spinerButton.setText(value.toString());
                            break;
                    }
                }
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initGroup() {
        Map<String, Object> map = new HashMap<String, Object>();
        map = new HashMap<String, Object>();
        map.put("id", "1");
        map.put("title", getString(R.string.one_min));
        map.put("time", 1  + "");
        titleList.add(map);

        map = new HashMap<String, Object>();
        map.put("id", "2");
        map.put("title", getString(R.string.three_min));
        map.put("time", 3  + "");
        titleList.add(map);

        map = new HashMap<String, Object>();
        map.put("id", "3");
        map.put("title", getString(R.string.five_min));
        map.put("time", 5  + "");
        titleList.add(map);
        map = new HashMap<String, Object>();
        map.put("id", "4");
        map.put("title", getString(R.string.fifteen_min));
        map.put("time", 15  + "");
        titleList.add(map);
        map = new HashMap<String, Object>();
        map.put("id", "5");
        map.put("title", getString(R.string.thirty_min));
        map.put("time", 30 + "");
        titleList.add(map);
        map = new HashMap<String, Object>();
        map.put("id", "6");
        map.put("title", getString(R.string.one_hour));
        map.put("time", 60  + "");
        titleList.add(map);
        map = new HashMap<String, Object>();
        map.put("id", "7");
        map.put("title", getString(R.string.two_hour));
        map.put("time", 2 * 60  + "");
        titleList.add(map);
        map = new HashMap<String, Object>();
        map.put("id", "8");
        map.put("title", getString(R.string.four_hour));
        map.put("time", 4 * 60  + "");
        titleList.add(map);
        map = new HashMap<String, Object>();
        map.put("id", "9");
        map.put("title", getString(R.string.six_hour));
        map.put("time", 6 * 60  + "");
        titleList.add(map);
        map = new HashMap<String, Object>();
        map.put("id", "10");
        map.put("title", getString(R.string.twelve_hour));
        map.put("time", 12 * 60  + "");
        titleList.add(map);
        map = new HashMap<String, Object>();
        map.put("id", "11");
        map.put("title", getString(R.string.one_day));
        map.put("time", 24 * 60  + "");
        titleList.add(map);
        map = new HashMap<String, Object>();
        map.put("id", "12");
        map.put("title", getString(R.string.one_week));
        map.put("time", 7 * 24 * 60 + "");
        titleList.add(map);

        title_lay =  findViewById(R.id.title_lay);

        myRadioGroup = new RadioGroup(this);
        myRadioGroup.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        myRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
        title_lay.addView(myRadioGroup);
        for (int i = 0; i < titleList.size(); i++) {
            Map<String, Object> map1 = titleList.get(i);
            RadioButton radio = new RadioButton(this);
            radio.setButtonDrawable(R.color.kViewztblack);
            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT,
                    Gravity.CENTER);
            radio.setLayoutParams(l);
            radio.setGravity(Gravity.CENTER);
            radio.setPadding(20, 20, 20, 20);
            // radio.setPadding(left, top, right, bottom)
            radio.setId(1 + i);
            radio.setText(map1.get("title") + "");
            if (i == 2) {
                radio.setTextColor(getResources().getColor(R.color.subject_select));
                radio.setChecked(true);
                radio.setBackground(getResources().getDrawable(R.drawable.bg_bottom));
            } else {
                radio.setTextColor(getResources().getColor(R.color.kViewztblack));
                radio.setChecked(false);
                radio.setBackground(null);
            }

            radio.setTag(map1);
            myRadioGroup.addView(radio);
        }
        myRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonId = group.getCheckedRadioButtonId();
                // 根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) findViewById(radioButtonId);
                for (int i = 0; i < group.getChildCount(); i++) {
                    RadioButton vradio = (RadioButton) group.getChildAt(i);
                    vradio.setGravity(Gravity.CENTER);
                    vradio.setPadding(20, 20, 20, 20);
                    if (rb.getId() == vradio.getId()) {
                        vradio.setTextColor(getResources().getColor(R.color.subject_select));
                        vradio.setChecked(true);
                        vradio.setBackground(getResources().getDrawable(R.drawable.bg_bottom));
                    } else {
                        vradio.setTextColor(getResources().getColor(R.color.kViewztblack));
                        vradio.setChecked(false);
                        vradio.setBackground(null);
                    }

                }
                //           CustomProgress.show(mContext, true, null);
                kChartTimeInterval = titleList.get(checkedId - 1).get("time") + "";
                time = kChartTimeInterval;
                indexMarketChart(kChartTimeInterval);


            }
        });
    }


    private void indexMarketChart(String time) {
        //获取k线数据
        Map<String, String> parm = new HashMap<>();
        parm.put("market", String.valueOf(marketId));
        parm.put("lineType", time);
        parm.put("limit", String.valueOf(JumpUtils.KDATA));
        LocalService.getApi().getHealthClassify(parm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBody>() {
                    @Override
                    protected void onDone(ResponseBody responsebody) {
                        try {
                            String bean = responsebody.string();
                            JSONObject jb = new JSONObject(bean);
                            long[][] data = Utils.readTwoDimensionData(jb, "data");
                            createChartDataSet(data);
                            Log.e("yongyi", bean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        showToast(getString(R.string.neterror));
                    }
                });
    }


    public void getHeaderData(){
        //获取头数据
        LocalService.getApi().getDatilHeaherData(String.valueOf(marketId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBody>() {
                    @Override
                    protected void onDone(ResponseBody responsebody) {
                        try {
                            String str = responsebody.string();
                            KDeatilHeaderBean bean = new Gson().fromJson(str, KDeatilHeaderBean.class);
                            vol.setText(String.valueOf(bean.getData().getVol()));
                            hight.setText(String.valueOf(bean.getData().getHigh()));
                            low.setText(String.valueOf(bean.getData().getLow()));
                            rate.setText(String.valueOf(bean.getData().getChange())+"%");
                            price1.setText(String.valueOf(bean.getData().getLast()));
                            if (bean.getData().getChange() >= 0) {
                                price1.setTextColor(Color.parseColor("#E01324"));
                                priceimage.setImageResource(R.drawable.ico_up2_red);
                            }else {
                                price1.setTextColor(Color.parseColor("#36B58C"));
                                priceimage.setImageResource(R.drawable.ico_down2_green);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        showToast(getString(R.string.neterror));
                    }
                });
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_transaction_details;
    }


    @Override
    public void onPause() {
        super.onPause();
        this.stopTimer();
    }

    @Override
    public void onResume() {
        super.onResume();
        startTimer();

    }


    Handler handlerOfTrans = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
            } else if (msg.what == 2) {
                indexMarketChart(time);
                getHeaderData();
            }
            super.handleMessage(msg);
        }

        ;
    };

    /**
     * 启动定时器
     */
    public void startTimer() {
        if (timer1 == null) {
            Log.i("KD_startTimer", "KD_startTimer+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            timer1 = new Timer();
            TransTimerTask task1 = new TransTimerTask(handlerOfTrans, 1);
            timer1.schedule(task1, 0, dataRefreshTime1);
        }
        if (timer2 == null) {
            Log.i("KD_startTimer", "KD_startTimer+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            timer2 = new Timer();
            TransTimerTask task2 = new TransTimerTask(handlerOfTrans, 2);
            timer2.schedule(task2, 0, dataRefreshTime2);
        }
    }

    /**
     * 停止定时器
     */
    public void stopTimer() {
        if (timer1 != null) {
            Log.i("KD_stoptimer", "KD_stoptimer+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            timer1.cancel();
            timer1 = null;
        }
        if (timer2 != null) {
            Log.i("KD_stoptimer", "KD_stoptimer+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            timer2.cancel();
            timer2 = null;
        }
    }

    @OnClick(R.id.spinerButton)
    public void onViewClicked() {
        showSpinWindow();
    }


    /**
     * 定时器任务
     */
    class TransTimerTask extends TimerTask {
        private int code;
        private Handler mHandler;

        public TransTimerTask() {

        }

        public TransTimerTask(Handler handler, int code) {
            this.mHandler = handler;
            this.code = code;
        }

        @Override
        public void run() {
            // 需要做的事:发送消息
            Message message = new Message();
            message.what = code;
            this.mHandler.sendMessage(message);
        }
    }


}
