package com.yunqukuailian.app.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.activity.LoginActivity;
import com.yunqukuailian.app.adapter.BuyDepthsAdapter;
import com.yunqukuailian.app.adapter.SellDepthsAdapter;
import com.yunqukuailian.app.base.BaseSimpleFragment;
import com.yunqukuailian.app.http.AbsAPICallback;
import com.yunqukuailian.app.http.LocalService;
import com.yunqukuailian.app.http.LocalServiceUtil;
import com.yunqukuailian.app.model.DepthsBean;
import com.yunqukuailian.app.model.EventBusEmpty;
import com.yunqukuailian.app.model.EventBusEvent.LoginSuccessEvent;
import com.yunqukuailian.app.model.EventBusEvent.UnRegisterEvent;
import com.yunqukuailian.app.model.EventModel;
import com.yunqukuailian.app.model.MainFragment1BeanItem;
import com.yunqukuailian.app.model.MainFragment2BuyBean;
import com.yunqukuailian.app.model.MyTickerBean;
import com.yunqukuailian.app.model.PlaceOrderBean;
import com.yunqukuailian.app.model.SimpleBaseBean;
import com.yunqukuailian.app.model.UserData;
import com.yunqukuailian.app.presenter.RegisterPresenter;
import com.yunqukuailian.app.utils.DisplayUtil;
import com.yunqukuailian.app.utils.JumpUtils;
import com.yunqukuailian.app.utils.LoadingUtils;
import com.yunqukuailian.app.utils.ToastUtils;
import com.yunqukuailian.app.utils.Utils;
import com.yunqukuailian.app.view.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/5/005.
 */

public class MainFragment2BuyFragment extends BaseSimpleFragment implements View.OnClickListener {
    @BindView(R.id.buyentrustpriceedite)
    EditText buyentrustpriceedite;
    @BindView(R.id.buyentrustpricetext)
    TextView buyentrustpricetext;
    @BindView(R.id.buyentrustpricemoney)
    TextView buyentrustpricemoney;
    @BindView(R.id.buyentrustnumberedite)
    EditText buyentrustnumberedite;
    @BindView(R.id.buyentrustnumbertext)
    TextView buyentrustnumbertext;
    @BindView(R.id.buypaymoneyedit)
    EditText buypaymoneyedit;
    @BindView(R.id.buypaymoneytext)
    TextView buypaymoneytext;
    @BindView(R.id.gobuy)
    TextView gobuy;
    @BindView(R.id.circleimage)
    CircleImageView circleimage;
    @BindView(R.id.nlogin)
    LinearLayout nlogin;
    @BindView(R.id.listbuy)
    RecyclerView listbuy;
    @BindView(R.id.buyfragmentrightrelative)
    RelativeLayout buyfragmentrightrelative;
    @BindView(R.id.listsell)
    RecyclerView listsell;
    @BindView(R.id.showgear)
    RelativeLayout showgear;
    @BindView(R.id.consolidationdepth)
    RelativeLayout consolidationdepth;
    @BindView(R.id.buyfragmentrightlinearlayout)
    LinearLayout buyfragmentrightlinearlayout;
    Unbinder unbinder;
    @BindView(R.id.canbuy)
    TextView canbuy;
    @BindView(R.id.canbuypay)
    TextView canbuypay;
    @BindView(R.id.available)
    TextView available;
    @BindView(R.id.availablepay)
    TextView availablepay;
    @BindView(R.id.availablegray)
    TextView availablegray;
    @BindView(R.id.availablegraypay)
    TextView availablegraypay;
    @BindView(R.id.freeze)
    TextView freeze;
    @BindView(R.id.freezepay)
    TextView freezepay;
    @BindView(R.id.coinmoney)
    TextView coinmoney;
    @BindView(R.id.coinchange)
    ImageView coinchange;
    @BindView(R.id.islogin)
    LinearLayout islogin;
    private int showRMB=0;
    public static int marketId;
    private String title;
    private BuyDepthsAdapter buyDepthsAdapter;
    private SellDepthsAdapter sellDepthsAdapter;
    private PopupWindow gearPopupWindow;
    private PopupWindow consolidationdepthpopup;
    private List<MainFragment1BeanItem.DataBean> dataBeans;
    private View rootView;
    public class MyMoneyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                buyentrustpricemoney.setText("¥ " + s.toString());
                if(!buyentrustnumberedite.getText().toString().trim().startsWith(".")&&buyentrustnumberedite.length() > 0){
                    buypaymoneyedit.setText(Utils.getAccount(buyentrustpriceedite.getText().toString(),buyentrustnumberedite.getText().toString()));
                }
            } else {
                buyentrustpricemoney.setText("¥ 0");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.toString().trim().startsWith("0") &&s.toString().trim().length() >1){
                int  index = s.toString().trim().indexOf(".");
                if(index != 1){
                    buyentrustpriceedite.setText(s.toString().substring(0,1));
                    buyentrustpriceedite.setSelection(1);
                }
            }else if(s.toString().trim().startsWith(".")){
                buyentrustpriceedite.setText("0");
                buyentrustpriceedite.setSelection(1);
            }

        }
    }

    public class TurnoverTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!buyentrustnumberedite.getText().toString().trim().startsWith(".")&&
                !buyentrustpriceedite.getText().toString().trim().startsWith(".")&&
                buypaymoneyedit.length() >0&&
                s.length() > 0&&
                buyentrustpriceedite.length() >0&&
                 buyentrustnumberedite.length() >0) {
                buypaymoneyedit.setText(Utils.getAccount(buyentrustpriceedite.getText().toString(), buyentrustnumberedite.getText().toString()));
            } else {
                buypaymoneyedit.setText(buyentrustpriceedite.getText().toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.toString().trim().startsWith("0") &&s.toString().trim().length() >1){
                int  index = s.toString().trim().indexOf(".");
                if(index != 1){
                    buyentrustnumberedite.setText(s.toString().substring(0,1));
                    buyentrustnumberedite.setSelection(1);
                }
            }else if(s.toString().trim().startsWith(".")){
                buyentrustnumberedite.setText("0");
                buyentrustnumberedite.setSelection(1);
            }
        }
    }

    public class TurnoverTextWatcher1 implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.toString().trim().startsWith("0") &&s.toString().trim().length() >1){
                int  index = s.toString().trim().indexOf(".");
                if(index != 1){
                    buypaymoneyedit.setText(s.toString().substring(0,1));
                    buypaymoneyedit.setSelection(1);
                }
            }else if(s.toString().trim().startsWith(".")){
                buypaymoneyedit.setText("0");
                buypaymoneyedit.setSelection(1);
            }

        }
    }
    private String headertitle;
    private String endtitle;
    private List<DepthsBean.DataBean.BuyBean> listBuy = new ArrayList<>();
    private List<DepthsBean.DataBean.SellBean> listSell = new ArrayList<>();
    private String newPrice;
    private String newPriceCNY;
    public static boolean isChange = false;//是否登录
    public static boolean isChange1 = false;//是否点击上方改变币种
    public static EventModel event;
    private int limit = 10;//默认档位


    Timer timer2 = null;  //刷新定时器
    private final int dataRefreshTime2 = 5 * 1000;                         //数据刷新间隔

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    public void setBunld(Bundle bunld){
        dataBeans = bunld.getParcelableArrayList(JumpUtils.FIRSTTAG);
        title = dataBeans.get(0).getTitle();
        if(marketId ==0){
            marketId = dataBeans.get(0).getMarketId();
        }
        setIcon(marketId,title,dataBeans.get(0).getNewPrice(),dataBeans.get(0).getNewPriceCNY());

    }

    public void initView() {

        buyentrustpriceedite.addTextChangedListener(new MyMoneyTextWatcher());
        buyentrustnumberedite.addTextChangedListener(new TurnoverTextWatcher());
        buypaymoneyedit.addTextChangedListener(new TurnoverTextWatcher1());
        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        layout.setStackFromEnd(true);//列表再底部开始展示，反转后由上面开始展示
        layout.setReverseLayout(true);//列表翻转
        listbuy.setLayoutManager(layout);
        listsell.setLayoutManager(new LinearLayoutManager(getActivity()));
        buyDepthsAdapter  = new BuyDepthsAdapter(listBuy);
        sellDepthsAdapter = new SellDepthsAdapter(listSell);
        listsell.setAdapter(buyDepthsAdapter);
        listbuy.setAdapter(sellDepthsAdapter);
        buyDepthsAdapter.setOnItemClickLisering(new BuyDepthsAdapter.OnItemClickLisering() {
            @Override
            public void setOnItemClickListering(int postion) {
                if(!listBuy.get(postion).getPrice().startsWith("--")){
                    buyentrustpriceedite.setText(Utils.subZeroAndDot(listBuy.get(postion).getPrice()));
                    if(Float.parseFloat(listBuy.get(postion).getVolum()) > Float.parseFloat(availablepay.getText().toString().trim())){
                        buyentrustnumberedite.setText(availablepay.getText().toString());
                        buypaymoneyedit.setText(String.valueOf(Float.parseFloat(availablepay.getText().toString()) * Float.parseFloat(listBuy.get(postion).getPrice())));
                    }else {
                        buyentrustnumberedite.setText(listBuy.get(postion).getVolum());
                        buypaymoneyedit.setText(String.valueOf(Float.parseFloat(listBuy.get(postion).getVolum()) * Float.parseFloat(listBuy.get(postion).getPrice())));
                    }
                }

            }
        });
        sellDepthsAdapter.setOnItemClickLisering(new BuyDepthsAdapter.OnItemClickLisering() {
            @Override
            public void setOnItemClickListering(int postion) {
                if(!listSell.get(postion).getPrice().startsWith("--")){
                    buyentrustpriceedite.setText(Utils.subZeroAndDot(String.valueOf(listSell.get(postion).getPrice())));
                    if(Float.parseFloat(listSell.get(postion).getVolum()) > Float.parseFloat(availablepay.getText().toString().trim())){
                        buyentrustnumberedite.setText(availablepay.getText().toString());
                        buypaymoneyedit.setText(String.valueOf(Float.parseFloat(availablepay.getText().toString()) * Float.parseFloat(listSell.get(postion).getPrice())));
                    }else {
                        buyentrustnumberedite.setText(listSell.get(postion).getVolum());
                        buypaymoneyedit.setText(String.valueOf(Float.parseFloat(listSell.get(postion).getVolum()) * Float.parseFloat(listSell.get(postion).getPrice())));
                    }
                }
            }
        });
        seeLogin();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isChange){
            seeLogin();
        }
        if(dataBeans!= null&&isChange1){
            setIcon(event.getMarketId(),event.getCountryName(),event.getNewPrice(),event.getNewPriceCNY());
        }

    }

    public void setIcon(int marketId, String title, String newPrice, String newPriceCNY){
        isChange1 = false;
        event = null;
        this.newPrice= newPrice;
        this.newPriceCNY = newPriceCNY;
        this.marketId = marketId;
        String[] titles = title.split("/");
        headertitle = titles[0];
        endtitle= titles[1];
        buyentrustpricetext.setText(endtitle);
        buyentrustnumbertext.setText(headertitle);
        buypaymoneytext.setText(endtitle);
        gobuy.setText("买入"+headertitle);
        canbuy.setText("可用"+endtitle);
        available.setText("可买"+headertitle);
        availablegray.setText("可用"+headertitle);
        freeze.setText("冻结"+endtitle);
        coinmoney.setText(newPriceCNY);
        getDepath();
        getMyCenterMoney();
    }


    public void getDepath(){
        //根据id去获取数据
        LocalService.getApi().getDepths(marketId,limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBody>() {
                    @Override
                    protected void onDone(ResponseBody responseBody) {
                        try {
                            DepthsBean bean = new Gson().fromJson(responseBody.string(),DepthsBean.class);
                            listBuy.clear();
                            listSell.clear();
                            listBuy.addAll(bean.getData().getBuy());
                            listSell.addAll(bean.getData().getSell());
                            int listBuySize = listBuy.size();
                            int listSellSize = listSell.size();
                            if(listBuy.size() <limit){
                                for (int i = 0; i < limit-listBuySize ; i++) {
                                    DepthsBean.DataBean.BuyBean beanSimple = new DepthsBean.DataBean.BuyBean();
                                    beanSimple.setPrice("----");
                                    beanSimple.setVolum("----");
                                    listBuy.add(beanSimple);
                                }
                            }
                            if(listSell.size() <limit){
                                for (int i = 0; i < limit-listSellSize ; i++) {
                                    DepthsBean.DataBean.SellBean beanSimple = new DepthsBean.DataBean.SellBean();
                                    beanSimple.setPrice("----");
                                    beanSimple.setVolum("----");
                                    listSell.add(beanSimple);
                                }
                            }
                            sellDepthsAdapter.notifyDataSetChanged();
                            buyDepthsAdapter.notifyDataSetChanged();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        LoadingUtils.dissProgressFull();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(listSell.size() == 0){
                            listSell.clear();
                            if(listSell.size() <limit){
                                for (int i = 0; i < limit ; i++) {
                                    DepthsBean.DataBean.SellBean beanSimple = new DepthsBean.DataBean.SellBean();
                                    beanSimple.setPrice("----");
                                    beanSimple.setVolum("----");
                                    listSell.add(beanSimple);
                                }
                            }
                            sellDepthsAdapter.notifyDataSetChanged();
                        }

                        if(listBuy.size() ==0){
                            listBuy.clear();
                            if(listBuy.size() <limit){
                                for (int i = 0; i < limit ; i++) {
                                    DepthsBean.DataBean.BuyBean beanSimple = new DepthsBean.DataBean.BuyBean();
                                    beanSimple.setPrice("----");
                                    beanSimple.setVolum("----");
                                    listBuy.add(beanSimple);
                                }
                            }
                        }
                        buyDepthsAdapter.notifyDataSetChanged();
                        LoadingUtils.dissProgressFull();

                    }
                });

    }

    public void getMyCenterMoney(){
        LocalService.getApi().getUserAccountById(RegisterPresenter.getSecondTimestampTwo().trim(),RegisterPresenter.getSign("apikey","timestamp").trim(),RegisterPresenter.getApiKey().trim(),String.valueOf(marketId).trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBody>() {
                    @Override
                    protected void onDone(ResponseBody responseBody) {
                        try {
                            String beanString = responseBody.string();
                            MainFragment2BuyBean bean = new Gson().fromJson(beanString,MainFragment2BuyBean.class);
                            canbuypay.setText(Utils.numberFormattoString(bean.getData().getBuyBalance()));
                            freezepay.setText(Utils.numberFormattoString(bean.getData().getBuyLock()));
                            availablepay.setText(Utils.numberFormattoString(bean.getData().getCanBuy()));
                            availablegraypay.setText(Utils.numberFormattoString(bean.getData().getSellBalance()));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        if(rootView != null){
            unbinder = ButterKnife.bind(this, rootView);
            return rootView;
        }
        rootView = inflater.inflate(R.layout.mainfragment2buyfragment,container,false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        event = null;
        isChange = false;
        isChange1 = false;
        handlerOfTrans.removeCallbacksAndMessages(null);
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    @OnClick({R.id.gobuy, R.id.nlogin, R.id.showgear, R.id.consolidationdepth,R.id.coinchange})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gobuy:
                if(UserData.isLogin()){
                    if(buyentrustpriceedite.getText().length() == 0){
                        Toast.makeText(getActivity(),"请输入价格",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(buyentrustnumberedite.getText().length() == 0){
                        Toast.makeText(getActivity(),"请输入数量",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    LoadingUtils.showFullProgress(getActivity());
                    gobuy.setClickable(false);
                    goBuyCoin();
                }else {
                    JumpUtils.JumpActivity(getActivity(),LoginActivity.class);
                }

                break;
            case R.id.nlogin:
                JumpUtils.JumpActivity(getActivity(), LoginActivity.class);
                break;
            case R.id.showgear:
                showGearPopupWindow();
                break;
            case R.id.consolidationdepth:
                consolidationdepth();
                break;
            case R.id.coinchange:
                showRMB++;
                if(showRMB%2 ==0){
                    coinmoney.setText(newPriceCNY);
                }else {
                    coinmoney.setText(newPrice);
                }
                break;
        }
    }

    //选择档位
    public void showGearPopupWindow(){
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.gear,null);
        gearPopupWindow =  new PopupWindow(popupView,LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT, true);
        //必须设置BackgroundDrawable后setOutsideTouchable(true)才会有效。这里在XML中定义背景，所以这里设置为null;
        gearPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        gearPopupWindow.setOutsideTouchable(false); //点击外部关闭。
        // 设置背景颜色变暗

        gearPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);    //设置一个动画。
        gearPopupWindow.showAtLocation(showgear, Gravity.BOTTOM, DisplayUtil.dip2px(getActivity(),50), DisplayUtil.dip2px(getActivity(), 140));
//        popupWindow.showAsDropDown(showgear);
        initGearPopup(popupView);
    }

    public void initGearPopup(View view){
        TextView listsize15 = view.findViewById(R.id.listsize15);
        TextView listsize10 = view.findViewById(R.id.listsize10);
        TextView listsize5 = view.findViewById(R.id.listsize5);
        TextView listsizedefault = view.findViewById(R.id.listsizedefault);
        listsize5.setOnClickListener(this);
        listsize10.setOnClickListener(this);
        listsize15.setOnClickListener(this);
        listsizedefault.setOnClickListener(this);
    }

    //显示深度
    public void consolidationdepth(){
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.consolidationdepth,null);
        consolidationdepthpopup =  new PopupWindow(popupView,LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT, true);
        //必须设置BackgroundDrawable后setOutsideTouchable(true)才会有效。这里在XML中定义背景，所以这里设置为null;
        consolidationdepthpopup.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        consolidationdepthpopup.setOutsideTouchable(false); //点击外部关闭。
        // 设置背景颜色变暗
        consolidationdepthpopup.setAnimationStyle(android.R.style.Animation_Dialog);    //设置一个动画。
        consolidationdepthpopup.showAtLocation(showgear, Gravity.BOTTOM, DisplayUtil.dip2px(getActivity(),150), DisplayUtil.dip2px(getActivity(), 140));
        initGearPopup1(popupView);
    }

    public void initGearPopup1(View view){
        TextView listdefault = view.findViewById(R.id.listdefault);
        listdefault.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.listsize5:
                limit =5;
                getDepath();
                gearPopupWindow.dismiss();
                break;
            case R.id.listsize15:
                limit =15;
                getDepath();
                gearPopupWindow.dismiss();
                break;
            case R.id.listsize10:
                limit =10;
                getDepath();
                gearPopupWindow.dismiss();
                break;
            case R.id.listsizedefault:
                limit =10;
                getDepath();
                gearPopupWindow.dismiss();
                break;
            case R.id.listdefault:
                consolidationdepthpopup.dismiss();
                break;
        }
    }


    public void goBuyCoin(){
        PlaceOrderBean bean = new PlaceOrderBean();
        bean.setMarket(marketId);//id
        bean.setNum(Float.parseFloat(buyentrustnumberedite.getText().toString().trim()));//数量
        bean.setPrice(Float.parseFloat(buyentrustpriceedite.getText().toString().trim()));//价格
        bean.setType(1);//类型:买入，卖出
        LocalServiceUtil.getApiRest().getPlaceOrderCreate(
                RegisterPresenter.getApiKey().trim(),
                RegisterPresenter.getSecondTimestampTwo().trim(),
                RegisterPresenter.getSign("apikey","timestamp").trim(), bean).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Subscriber<SimpleBaseBean>() {
            @Override
            public void onCompleted() {
                LoadingUtils.dissProgressFull();
            }
            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                gobuy.setClickable(true);
                LoadingUtils.dissProgressFull();

            }

            @Override
            public void onNext(SimpleBaseBean listbean) {
                if(listbean.getCode() ==1){
                    ToastUtils.showToastCentre("下单成功");
//                    buyentrustnumberedite.setText("");
//                    buyentrustpriceedite.setText("");
//                    buypaymoneyedit.setText("");
                    EventBus.getDefault().post(new EventBusEmpty());
                }else {
                    ToastUtils.showToastCentre(listbean.getMessage());
                }
                gobuy.setClickable(true);
                LoadingUtils.dissProgressFull();
            }

        });

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusEmpty event) {
        //刷新本页面左下角可用数据
        getMyCenterMoney();
        MainFragment2CurrentFragment.isChange = true;
        MainFragment2HistoryFragment.isChange = true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventModel event) {
        this.event = event;
        isChange1 = true;
        MainFragment2SellFragment.isChange1 = true;
        MainFragment2CurrentFragment.isChange1 = true;
        MainFragment2HistoryFragment.isChange1 = true;
        MainFragment2BuyFragment.event = event;
        MainFragment2SellFragment.event = event;
        MainFragment2CurrentFragment.event = event;
        MainFragment2HistoryFragment.event = event;

        MainFragment2HistoryFragment.marketId = event.getMarketId();
        MainFragment2CurrentFragment.marketId = event.getMarketId();
        MainFragment2SellFragment.marketId = event.getMarketId();
        MainFragment2BuyFragment.marketId = event.getMarketId();
        setIcon(event.getMarketId(),event.getCountryName(),event.getNewPrice(),event.getNewPriceCNY());

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showMobileNumber(LoginSuccessEvent event){
        seeLogin();
        getMyCenterMoney();
        isChange = true;
        MainFragment2SellFragment.isChange = true;
        MainFragment2CurrentFragment.isChange = true;
        MainFragment2HistoryFragment.isChange= true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showMobileNumber(UnRegisterEvent event){
        seeLogin();
        isChange = true;
        MainFragment2SellFragment.isChange = true;
        MainFragment2CurrentFragment.isChange = true;
        MainFragment2HistoryFragment.isChange= true;
    }

    public void seeLogin(){
        if(UserData.isLogin()){
            nlogin.setVisibility(View.GONE);
            islogin.setVisibility(View.VISIBLE);
        }else {
            nlogin.setVisibility(View.VISIBLE);
            islogin.setVisibility(View.GONE);
        }
        isChange = false;

    }

    /**
     * 启动定时器
     */
    public void startTimer() {
        if (timer2 == null) {
            Log.i("KD_startTimer", "KD_startTimer1111111+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            timer2 = new Timer();
            TransTimerTask task2 = new TransTimerTask(handlerOfTrans, 2);
            timer2.schedule(task2, 0, dataRefreshTime2);
        }
    }

    /**
     * 停止定时器
     */
    public void stopTimer() {
        if (timer2 != null) {
            Log.i("KD_stoptimer", "KD_stoptimer11111+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            timer2.cancel();
            timer2 = null;
        }
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


      Handler handlerOfTrans = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
            } else if (msg.what == 2) {
                //从这开始请求数据
                if(dataBeans != null){
                    startGetNewData();
                    getDepath();
                }
            }
            super.handleMessage(msg);
        }

        ;
    };

    public void  startGetNewData(){
        LocalService.getApi().startGetNewData(String.valueOf(marketId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBody>() {
                    @Override
                    protected void onDone(ResponseBody responseBody) {
                        try {
                            MyTickerBean bean = new Gson().fromJson(responseBody.string(),MyTickerBean.class);
                            newPrice= bean.getData().getLast();
                            newPriceCNY = bean.getData().getLastCny();
                            coinmoney.setText(newPriceCNY);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            startTimer();
        }else {
            stopTimer();
        }
    }
}
