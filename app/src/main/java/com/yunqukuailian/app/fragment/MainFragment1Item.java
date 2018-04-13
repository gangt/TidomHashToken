package com.yunqukuailian.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.classic.common.MultipleStatusView;
import com.google.gson.Gson;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.activity.TransactionDetailsActivity;
import com.yunqukuailian.app.adapter.MainFragmentAdapter;
import com.yunqukuailian.app.base.BaseSimpleFragment;
import com.yunqukuailian.app.http.AbsAPICallback;
import com.yunqukuailian.app.http.LocalService;
import com.yunqukuailian.app.model.KDeatilHeaderBean;
import com.yunqukuailian.app.model.MainFragment1BeanItem;
import com.yunqukuailian.app.utils.JumpUtils;
import com.yunqukuailian.app.utils.LoadingUtils;
import com.yunqukuailian.app.utils.Utils;
import com.yunqukuailian.app.view.kview.MarketChartData;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/13/013.
 */

public class MainFragment1Item extends BaseSimpleFragment {
    @BindView(R.id.main_fragment1_expandablelist)
    ExpandableListView mainFragment1Expandablelist;

    Unbinder unbinder1;
    private List<MainFragment1BeanItem.DataBean> list = new ArrayList<>();
    Unbinder unbinder;
    private MainFragmentAdapter adapter;
    private int countryId;
    private List<MarketChartData> marketChartDataLists = new ArrayList<MarketChartData>();
    private  KDeatilHeaderBean bean  =  new KDeatilHeaderBean();
    private String marketId;
    private View rootView;
    private int groupPostion;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.mainfragment2item, container, false);
            unbinder1 = ButterKnife.bind(this, rootView);
            initView();
            initData();
        }

        return rootView;
    }

    protected void initData() {
        countryId = getArguments().getInt(JumpUtils.FIRSTTAG);
        LocalService.getApi().getMainFragmentItemData(String.valueOf(countryId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<MainFragment1BeanItem>() {
                    @Override
                    protected void onDone(MainFragment1BeanItem mainFragment1BeanItems) {
                        list.addAll(mainFragment1BeanItems.getData());
                        adapter.notifyDataSetChanged();
                        LoadingUtils.dissProgressFull();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LoadingUtils.dissProgressFull();
                        dialog.showDialg(getActivity(),e.getMessage());
                    }
                });
    }


    public void initView() {
        adapter = new MainFragmentAdapter(getActivity(), list, getActivity());
        mainFragment1Expandablelist.setGroupIndicator(null);
        mainFragment1Expandablelist.setAdapter(adapter);
        mainFragment1Expandablelist.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int count = mainFragment1Expandablelist.getExpandableListAdapter().getGroupCount();
                for (int j = 0; j < count; j++) {
                    if (j != groupPosition) {
                        mainFragment1Expandablelist.collapseGroup(j);
                    }
                }
            }
        });

        mainFragment1Expandablelist.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //如果分组被打开 直接关闭
                if (mainFragment1Expandablelist.isGroupExpanded(groupPosition)) {
                    mainFragment1Expandablelist.collapseGroup(groupPosition);
                } else {
                    marketId = String.valueOf(list.get(groupPosition).getMarketId());
                    marketChartDataLists.clear();
                    adapter.postion = 2;
                    getHeaderData(marketId);
                    groupPostion = groupPosition;
                    if(adapter.marketChartDataLists != null){
                        adapter.marketChartDataLists.clear();
                    }
                }
                return false;
            }
        });

        mainFragment1Expandablelist.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                JumpUtils.JumpActivity(getActivity(), TransactionDetailsActivity.class, list.get(groupPosition).getTitle(), list.get(groupPosition).getMarketId());
                return true;
            }
        });

        adapter.setCheckChangeRadioButton(new MainFragmentAdapter.CheckChangeRadioButton() {
            @Override
            public void setOnCheckChangeRadioButton(String time) {
                getKData(time);
            }
        });

    }


    public void getKData(String time) {
        Map<String, String> parm = new HashMap<>();
        parm.put("market", marketId);
        parm.put("lineType", time);
        parm.put("limit", String.valueOf(JumpUtils.KDATASIMPLE));
        LocalService.getApi().getHealthClassify(parm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBody>() {
                    @Override
                    protected void onDone(ResponseBody responseBody) {
                        try {
                            String bean = responseBody.string();
                            Log.e("yongyi", bean);
                            JSONObject jb = new JSONObject(bean);
                            long[][] data = Utils.readTwoDimensionData(jb, "data");
                            createChartDataSet(data);
//                            openAnimation();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        openAnimation();
                        dialog.showDialg(getActivity(),e.getMessage());
                    }
                });


    }
    //展开
    public void openAnimation(){
        mainFragment1Expandablelist.expandGroup(groupPostion);

    }

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
//            Collections.reverse(marketChartDataLists);
            adapter.setKData(marketChartDataLists);
        }
    }

    public void getHeaderData(String marketId) {
        //获取头数据
        LocalService.getApi().getDatilHeaherData(marketId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<ResponseBody>() {
                    @Override
                    protected void onDone(ResponseBody responsebody) {
                        try {
                            String str = responsebody.string();
                            bean = new Gson().fromJson(str, KDeatilHeaderBean.class);
                            adapter.setHeader(bean);
                            getKData(String.valueOf(60));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dialog.showDialg(getActivity(),e.getMessage());
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }


}
