package com.yunqukuailian.app.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunqukuailian.app.MainActivity;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.base.BaseFragment;
import com.yunqukuailian.app.http.AbsAPICallback;
import com.yunqukuailian.app.http.LocalService;
import com.yunqukuailian.app.model.EventModel;
import com.yunqukuailian.app.model.MainFragment1Bean;
import com.yunqukuailian.app.model.MainFragment1BeanItem;
import com.yunqukuailian.app.utils.DisplayUtil;
import com.yunqukuailian.app.utils.JumpUtils;
import com.yunqukuailian.app.utils.LoadingUtils;
import com.yunqukuailian.app.view.TabPageIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/1/001.
 */

public class MainFragment2 extends BaseFragment implements DialogInterface.OnDismissListener {
    @BindView(R.id.indicator)
    TabPageIndicator indicator;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private View trans;


    List<MainFragment1Bean.DataBean> listbean = new ArrayList<>();
    private List<Fragment> lsitFragment = new ArrayList<>();
    private MainFragment2BuyFragment fragment2BuyFragment;
    private MainFragment2SellFragment fragment2SellFragment;
    private MainFragment2CurrentFragment fragment2CurrentFragment;
    private MainFragment2HistoryFragment fragment2HistoryFragment;
    private MainActivity activity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        activity.toolbar.setNavigationIcon(null);

        return rootView;
    }


    @Override
    public void onBackPressed() {

    }


    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getArguments();
        if (bundle != null) {
            listbean = bundle.getParcelableArrayList(JumpUtils.FIRSTTAG);
            if (listbean != null && listbean.size() > 0) {
                getPageDta(listbean.get(0).getId());
            }
        }else {
           LoadingUtils.dissProgressFull();
        }


    }

    public void getPageDta(int marketId) {
        LocalService.getApi().getMainFragmentItemData(String.valueOf(marketId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<MainFragment1BeanItem>() {
                    @Override
                    protected void onDone(MainFragment1BeanItem listbean) {
                        //请求成功，做相应的页面操作
                        if (listbean.getData().size() > 0) {
                            activity.mainfragment4title.setText(listbean.getData().get(0).getTitle());
                            activity.titleList.set(1, listbean.getData().get(0).getTitle());
                        }

                    }
                    @Override
                    public void onNext(MainFragment1BeanItem dataBeans) {
                        super.onNext(dataBeans);
                        //将market传到下一级Fragment中，并刷新数据
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList(JumpUtils.FIRSTTAG, (ArrayList<MainFragment1BeanItem.DataBean>) dataBeans.getData());

                        fragment2BuyFragment.setBunld(bundle);
                        fragment2SellFragment.setBunld(bundle);
                        fragment2CurrentFragment.setBunld(bundle);
                        fragment2HistoryFragment.setBunld(bundle);

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dialog.showDialg(getActivity(),e.getMessage());
                    }
                });
    }


    public void setFragment() {
        fragment2BuyFragment = new MainFragment2BuyFragment();
        lsitFragment.add(fragment2BuyFragment);

        fragment2SellFragment = new MainFragment2SellFragment();
        lsitFragment.add(fragment2SellFragment);

        fragment2CurrentFragment = new MainFragment2CurrentFragment();
        lsitFragment.add(fragment2CurrentFragment);

        fragment2HistoryFragment = new MainFragment2HistoryFragment();
        lsitFragment.add(fragment2HistoryFragment);

        BasePagerAdapter adapter = new BasePagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);// 设置adapter
        indicator.setViewPager(viewPager);// 绑定indicator
        setTabPagerIndicator();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        trans = activity.trans;

    }


    @Override
    public int setLayout() {
        return R.layout.mainfragment2;
    }

    @Override
    public void initView() {
        super.initView();
        LoadingUtils.showFullProgress(getActivity());
        Drawable drawable1 = getResources().getDrawable(R.drawable.mainfragmentitledown);
        drawable1.setBounds(0, 0, DisplayUtil.dip2px(getActivity(), 15), DisplayUtil.dip2px(getActivity(), 10));
        activity.mainfragment4title.setCompoundDrawables(null, null, drawable1, null);
        viewPager.setOffscreenPageLimit(4);
        setFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    /**
     * 通过一些set方法，设置控件的属性
     */
    private void setTabPagerIndicator() {
        indicator.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_NOWEIGHT_EXPAND_SAME);// 设置模式，一定要先设置模式
        indicator.setDividerPadding(10);//设置
        indicator.setIndicatorColor(Color.parseColor("#E70101"));// 设置底部导航线的颜色
        indicator.setTextColorSelected(Color.parseColor("#E70101"));// 设置tab标题选中的颜色
        indicator.setTextColor(Color.parseColor("#797979"));// 设置tab标题未被选中的颜色
        indicator.setTextSize(DisplayUtil.sp2px(getActivity(), 14));// 设置字体大小
        indicator.setIndicatorHeight(DisplayUtil.sp2px(getActivity(), 2));
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Drawable drawable1 = getResources().getDrawable(R.drawable.mainfragmentitledown);
        drawable1.setBounds(0, 0, DisplayUtil.dip2px(getActivity(), 15), DisplayUtil.dip2px(getActivity(), 10));
        activity.mainfragment4title.setCompoundDrawables(null, null, drawable1, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventModel event) {
        activity.titleList.set(1, event.getCountryName());
        activity.mainfragment4title.setText(event.getCountryName());
        Drawable drawable1 = getResources().getDrawable(R.drawable.mainfragmentitledown);
        drawable1.setBounds(0, 0, DisplayUtil.dip2px(getActivity(), 15), DisplayUtil.dip2px(getActivity(), 10));
        activity.mainfragment4title.setCompoundDrawables(null, null, drawable1, null);
    }




    //主选项卡
    class BasePagerAdapter extends FragmentStatePagerAdapter {
        String[] titles = {"买入", "卖出", "当前委托", "历史委托"};

        public BasePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return lsitFragment.get(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

}
