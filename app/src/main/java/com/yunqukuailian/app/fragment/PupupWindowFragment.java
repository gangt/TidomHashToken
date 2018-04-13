package com.yunqukuailian.app.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.classic.common.MultipleStatusView;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.adapter.MainFragmen2PopupWindowAdapter;
import com.yunqukuailian.app.base.BaseSimpleFragment;
import com.yunqukuailian.app.http.AbsAPICallback;
import com.yunqukuailian.app.http.LocalService;
import com.yunqukuailian.app.model.EventModel;
import com.yunqukuailian.app.model.MainFragment1BeanItem;
import com.yunqukuailian.app.utils.JumpUtils;
import com.yunqukuailian.app.utils.LoadingUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.yunqukuailian.app.utils.DisplayUtil.dip2px;

/**
 * Created by Administrator on 2018/3/15/015.
 */

public class PupupWindowFragment extends BaseSimpleFragment {
    @BindView(R.id.main_fragment2_popupview)
    RecyclerView mainFragment2Popupview;
    Unbinder unbinder;
    @BindView(R.id.loadingView)
    RelativeLayout loadingView;

    private List<MainFragment1BeanItem.DataBean> popupList = new ArrayList<>();
    private int countryId;
    private MainFragmen2PopupWindowAdapter adapter;
    private View rootView;
    private Subscription subscription;


    protected void initData() {
        initPopuView1Data();
        mainFragment2Popupview.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        adapter = new MainFragmen2PopupWindowAdapter(getActivity(), popupList);
        mainFragment2Popupview.setAdapter(adapter);
        adapter.setOnItemClickLister(new MainFragmen2PopupWindowAdapter.OnItemClickLister() {
            @Override
            public void setOnClick(int i) {
                EventBus.getDefault().post(new EventModel(popupList.get(i).getTitle(), popupList.get(i).getMarketId(), popupList.get(i).getNewPrice(), popupList.get(i).getNewPriceCNY()));
            }
        });
    }

    private void initPopuView1Data() {
        countryId = getArguments().getInt(JumpUtils.FIRSTTAG);
        subscription = LocalService.getApi().getMainFragmentItemData(String.valueOf(countryId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<MainFragment1BeanItem>() {
                    @Override
                    protected void onDone(MainFragment1BeanItem mainFragment1BeanItems) {
                        popupList.clear();
                        popupList.addAll(mainFragment1BeanItems.getData());
                        adapter.notifyDataSetChanged();
                        if (loadingView != null) {
                            loadingView.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (loadingView != null) {
                            loadingView.setVisibility(View.GONE);
                        }
                        dialog.showDialg(getActivity(),e.getMessage());
                    }
                });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.mainfragment2popuplayout1, null);
            unbinder = ButterKnife.bind(this, rootView);
            initData();
        }
        unbinder = ButterKnife.bind(this, rootView);
        if(popupList.size() == 0){
            loadingView.setVisibility(View.VISIBLE);
        }

        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        subscription.unsubscribe();
    }
}
