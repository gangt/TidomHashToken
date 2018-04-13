package com.yunqukuailian.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.adapter.MainFragment3UserBillAdapter;
import com.yunqukuailian.app.base.BaseActivity;
import com.yunqukuailian.app.http.LocalServiceUtil;
import com.yunqukuailian.app.model.EventBusEvent.FinishActivityEvent;
import com.yunqukuailian.app.model.EventBusEvent.GetUserBillEvent;
import com.yunqukuailian.app.model.ModeView.MessgeDialog;
import com.yunqukuailian.app.model.TxUserBean;
import com.yunqukuailian.app.model.UserBillBean;
import com.yunqukuailian.app.presenter.UserAccountPresenter;
import com.yunqukuailian.app.utils.LoadingUtils;
import com.yunqukuailian.app.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tidom on 2018/3/22/022.
 */

public class UserBillActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {


    @BindView(R.id.mainfragment4title)
    TextView mainfragment4title;
    @BindView(R.id.add_account)
    ImageView addAccount;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mainfragmenitem3recyclerview)
    LRecyclerView mainfragmenitem3recyclerview;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;
    @BindView(R.id.month_title)
    TextView monthTitle;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    MainFragment3UserBillAdapter adapter;
    List<UserBillBean.UserBillBeanItem> listbean = new ArrayList<>();
    TxUserBean txUserBean;
    UserAccountPresenter presenter;
    int page = 1;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.setTitleBar(this, toolbar);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        mainfragment4title.setText("" + "账单");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_userbill;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txUserBean = TxUserBean.getInstance();
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        presenter = new UserAccountPresenter();
        presenter.attatchView(this);
        initView();
        presenter.getUserTBBill(txUserBean.getCoinId(), page);
    }

    public void initView() {
        mainfragmenitem3recyclerview.setLayoutManager(new LinearLayoutManager(this));

        mainfragmenitem3recyclerview.setOnRefreshListener(this);
        mainfragmenitem3recyclerview.setOnLoadMoreListener(this);
        adapter = new MainFragment3UserBillAdapter(listbean, this);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        mainfragmenitem3recyclerview.setAdapter(lRecyclerViewAdapter);

        mainfragmenitem3recyclerview.setLoadMoreEnabled(true);
        mainfragmenitem3recyclerview.setPullRefreshEnabled(true);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserBillEvent(GetUserBillEvent event) {

        UserBillBean userBillBean = event.getUserBillBean();
        if (userBillBean.getMessage().equals("SUCCESS")) {
            if (userBillBean.getData().size() > 0) {
                mainfragmenitem3recyclerview.setVisibility(View.VISIBLE);
                listbean.addAll(userBillBean.getData()) ;
                monthTitle.setVisibility(View.VISIBLE);
                monthTitle.setText(Utils.msToDate(Long.parseLong(userBillBean.getData().get(0).getTime())));
                adapter.notifyDataSetChanged();
                LoadingUtils.dissProgressFull();
                return;
            }
            new MessgeDialog().showDialg(this, getResources().getString(R.string.no_history_bill));
            LoadingUtils.dissProgressFull();
            return;
        }
        new MessgeDialog().showDialg(this, getResources().getString(R.string.get_bii_faild));
        LoadingUtils.dissProgressFull();

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FinishActivityEvent event) {
        finish();
    }


    @Override
    public void onRefresh() {
        page = 1;
        listbean.clear();
        presenter.getUserTBBill(txUserBean.getCoinId(), page);
    }

    @Override
    public void onLoadMore() {
        page++;
        presenter.getUserTBBill(txUserBean.getCoinId(), page);
    }

    public void stopRelfsh(){
        mainfragmenitem3recyclerview.refreshComplete(LocalServiceUtil.PAGESIZE);
        adapter.notifyDataSetChanged();
    }
}
