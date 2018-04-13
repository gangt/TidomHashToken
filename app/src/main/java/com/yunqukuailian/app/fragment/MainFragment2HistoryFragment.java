package com.yunqukuailian.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.LinearLayout;

import com.classic.common.MultipleStatusView;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.adapter.MainFragment2CurrentAdapter;
import com.yunqukuailian.app.adapter.MainFragment2HistoryAdapter;
import com.yunqukuailian.app.base.BaseFragment;
import com.yunqukuailian.app.base.BaseSimpleFragment;
import com.yunqukuailian.app.http.LocalServiceUtil;
import com.yunqukuailian.app.model.EventBusEmpty;
import com.yunqukuailian.app.model.EventBusEvent.LoginSuccessEvent;
import com.yunqukuailian.app.model.EventBusEvent.UnRegisterEvent;
import com.yunqukuailian.app.model.EventModel;
import com.yunqukuailian.app.model.MainFragment1BeanItem;
import com.yunqukuailian.app.model.MainFragment2CurrentBean;
import com.yunqukuailian.app.model.MainFragment2CurrentResponBean;
import com.yunqukuailian.app.model.UserData;
import com.yunqukuailian.app.presenter.RegisterPresenter;
import com.yunqukuailian.app.utils.JumpUtils;
import com.yunqukuailian.app.utils.LoadingUtils;
import com.yunqukuailian.app.view.spinner.MaterialSpinner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/5/005.
 */

public class MainFragment2HistoryFragment extends BaseSimpleFragment implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.allspinner)
    MaterialSpinner allspinner;
    @BindView(R.id.pricespinner)
    MaterialSpinner pricespinner;
    Unbinder unbinder;
    @BindView(R.id.mainfragmenitem2recyclerview)
    LRecyclerView mainfragmenitem2recyclerview;
    private View rootView;
    private int page = 1;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    public static boolean isChange = false;//是否登录
    public static boolean isChange1 = false;//是否点击上方改变币种
    private MainFragment2HistoryAdapter adapter;
    public static EventModel event;
    public static int marketId;
    private List<MainFragment1BeanItem.DataBean> dataBeans;
    private List<MainFragment2CurrentBean.DataBean> listbean = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        LoadingUtils.showFullProgress(getActivity());
    }

    protected void initView() {
        LoadingUtils.showFullProgress(getActivity());

        mainfragmenitem2recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MainFragment2HistoryAdapter(listbean, new WeakReference<Context>(getActivity()));

        lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        mainfragmenitem2recyclerview.setOnRefreshListener(this);
        mainfragmenitem2recyclerview.setOnLoadMoreListener(this);
        mainfragmenitem2recyclerview.setAdapter(lRecyclerViewAdapter);
        mainfragmenitem2recyclerview.setLoadMoreEnabled(true);
        mainfragmenitem2recyclerview.setPullRefreshEnabled(true);
        pricespinner.setItems("限价委托");
        allspinner.setItems("全部");


    }

    public void setBunld(Bundle bunld){
        dataBeans = bunld.getParcelableArrayList(JumpUtils.FIRSTTAG);
        if(marketId ==0){
            marketId = dataBeans.get(0).getMarketId();
        }
        initData();

        if(event == null){
            adapter.setTitle(dataBeans.get(0).getTitle());
        }else {
            adapter.setTitle(event.getCountryName());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isChange || isChange1){
            if(event != null){
                if(adapter != null){
                    adapter.setTitle(event.getCountryName());
                }
            }
            initData();
        }
    }

    /**
     * type
     * 0 全部 1 买入 2 卖出
     * status
     * 0:获取委托订单
     * 1:获取已完成或者取消委托订单订单
     */
    protected void initData() {
        isChange= false;
        isChange1= false;
        event = null;
        MainFragment2CurrentResponBean bean = new MainFragment2CurrentResponBean();
        bean.setMarket(marketId);
        bean.setPage(page);
        bean.setSize(LocalServiceUtil.PAGESIZE);
        bean.setStatus(1);
        bean.setType(0);

        LocalServiceUtil.getApiRest().getEntrustedRecord(
                UserData.getApiKey(),
                UserData.getSecondTimestampTwo(),
                UserData.getSign("apikey", "timestamp").trim(), bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                LoadingUtils.dissProgressFull();
                dialog.showDialg(getActivity(),e.getMessage());
            }
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String str = responseBody.string();
                    Log.e("yongyitag", str);
                    MainFragment2CurrentBean bean = new Gson().fromJson(str, MainFragment2CurrentBean.class);
                    if(page ==1 && bean.getData().size() == 0){
                        listbean.clear();
                        showEmptyView();
                    }else {
                        if(page ==1){
                            listbean.clear();
                        }
                        listbean.addAll(bean.getData());
                        lRecyclerViewAdapter.notifyDataSetChanged();
                        showContent();
                    }
                    if(bean.getData().size() < LocalServiceUtil.PAGESIZE){
                        mainfragmenitem2recyclerview.setLoadMoreEnabled(false);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                stopRelfsh();
            }


        });
    }

    public void showEmptyView(){
        mainfragmenitem2recyclerview.setVisibility(View.GONE);
        LinearLayout linearLayout = rootView.findViewById(R.id.empty_view);
        if(linearLayout != null){
            linearLayout.setVisibility(View.VISIBLE);
        }
    }

    public void showContent(){
        mainfragmenitem2recyclerview.setVisibility(View.VISIBLE);
    }

    public void stopRelfsh(){
        mainfragmenitem2recyclerview.refreshComplete(LocalServiceUtil.PAGESIZE);
        lRecyclerViewAdapter.notifyDataSetChanged();
        LoadingUtils.dissProgressFull();
        LoadingUtils.dissProgressFull();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        event = null;
        isChange = false;
        isChange1 = false;
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        if(rootView != null) {
            unbinder = ButterKnife.bind(this, rootView);
            return rootView;
        }
        rootView = inflater.inflate(R.layout.mainfragment2currentfragment, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
//        initData();
        return rootView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusEmpty event) {
        //刷新本页面左下角可用数据
        initData();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        page = 1;
        initData();
        mainfragmenitem2recyclerview.setLoadMoreEnabled(true);
    }

    @Override
    public void onLoadMore() {
        page++;
        initData();
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventModel event) {
        page =1;
        adapter.setTitle(event.getCountryName());
        marketId = event.getMarketId();
        MainFragment2BuyFragment.isChange1 = true;
        MainFragment2SellFragment.isChange1 = true;
        MainFragment2CurrentFragment.isChange1 = true;
        MainFragment2HistoryFragment.isChange1 = true;
        MainFragment2BuyFragment.event = event;
        MainFragment2SellFragment.event = event;


        MainFragment2HistoryFragment.marketId = event.getMarketId();
        MainFragment2CurrentFragment.marketId = event.getMarketId();
        MainFragment2SellFragment.marketId = event.getMarketId();
        MainFragment2BuyFragment.marketId = event.getMarketId();

        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showMobileNumber(LoginSuccessEvent event){
        MainFragment2BuyFragment.isChange = true;
        MainFragment2SellFragment.isChange = true;
        MainFragment2CurrentFragment.isChange = true;
        MainFragment2HistoryFragment.isChange= true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showMobileNumber(UnRegisterEvent event) {
        listbean.clear();
        lRecyclerViewAdapter.notifyDataSetChanged();
        showEmptyView();
        MainFragment2BuyFragment.isChange = true;
        MainFragment2SellFragment.isChange = true;
        MainFragment2CurrentFragment.isChange = true;
        MainFragment2HistoryFragment.isChange= true;
    }


}
