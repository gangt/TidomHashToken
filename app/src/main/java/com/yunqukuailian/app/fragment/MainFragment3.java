package com.yunqukuailian.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.classic.common.MultipleStatusView;
import com.yunqukuailian.app.MainActivity;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.activity.LoginActivity;
import com.yunqukuailian.app.adapter.MainFragment3Adapter;
import com.yunqukuailian.app.base.BaseFragment;
import com.yunqukuailian.app.model.EventBusEvent.LoginSuccessEvent;
import com.yunqukuailian.app.model.EventBusEvent.MainFragement3AccountEvent;
import com.yunqukuailian.app.model.MainFragment3Bean;
import com.yunqukuailian.app.model.UserData;
import com.yunqukuailian.app.presenter.UserAccountPresenter;
import com.yunqukuailian.app.utils.JumpUtils;
import com.yunqukuailian.app.utils.LoadUtils2;
import com.yunqukuailian.app.utils.LoadingUtils;
import com.yunqukuailian.app.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 财务
 * Created by Administrator on 2018/3/1/001.
 */

public class MainFragment3 extends BaseFragment{
    @BindView(R.id.multiplestatusview)
    public MultipleStatusView multiplestatusview;
    @BindView(R.id.nologin)
    LinearLayout nologin;
    @BindView(R.id.loginfragment)
    ScrollView loginfragment;
    @BindView(R.id.mainfragment3gologintext)
    TextView mainfragment3gologintext;
    @BindView(R.id.mainfragmen3listview)
    RecyclerView mainfragmen3listview;
    private String TAG = "MainFragment3";
    Unbinder unbinder;
    UserAccountPresenter presenter;
    public MainFragment3Adapter adapter;
    public MainActivity mainActivity ;
    List<MainFragment3Bean> list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        presenter = new UserAccountPresenter();
        presenter.attatchView(this);
    }

    @Override
    public int setLayout() {
        return R.layout.mainfragment3;
    }


    @Override
    public void initView() {
        super.initView();
        mainfragmen3listview = mViewRoot.findViewById(R.id.mainfragmen3listview);
        mainfragmen3listview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MainFragment3Adapter(list, new WeakReference<Context>(getActivity()), presenter);
        mainfragmen3listview.setAdapter(adapter);
        choosePagertoShow();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void showListData(List<MainFragment3Bean> list) {
        this.list.addAll(list);
        adapter.notifyDataSetChanged();
//        LoadingUtils.dissProgressFull();
        LoadUtils2.getInstance().dissProgressFull();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        if(rootView == null){
            return null;
        }
        rootView.setClickable(true);
        unbinder = ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        return rootView;
    }

    @Override
    public void onBackPressed() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    public void choosePagertoShow() {
        if (UserData.isLogin()) {
            presenter.getUserAccount();
        }
    }

    @Override
    public void onStart() {
        if (!UserData.isLogin()) {
            loginfragment.setVisibility(View.GONE);
             nologin.setVisibility(View.VISIBLE);
        } else {
            nologin.setVisibility(View.GONE);
            loginfragment.setVisibility(View.VISIBLE);
        }
        super.onStart();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginSuccess(LoginSuccessEvent event) {
        LogUtils.v(TAG, "MainFragment3:");
        presenter.getUserAccount();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MainFragement3AccountEvent event) {
        showListData(event.getList());
    }

    @OnClick(R.id.mainfragment3gologintext)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mainfragment3gologintext:
                JumpUtils.JumpActivity(getActivity(), LoginActivity.class);
                break;
        }
    }
}
