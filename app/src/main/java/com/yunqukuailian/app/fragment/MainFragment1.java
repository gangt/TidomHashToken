package com.yunqukuailian.app.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classic.common.MultipleStatusView;
import com.yunqukuailian.app.MainActivity;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.base.BaseFragment;
import com.yunqukuailian.app.http.AbsAPICallback;
import com.yunqukuailian.app.http.LocalService;
import com.yunqukuailian.app.model.MainFragment1Bean;
import com.yunqukuailian.app.utils.DisplayUtil;
import com.yunqukuailian.app.utils.JumpUtils;
import com.yunqukuailian.app.utils.LoadingUtils;
import com.yunqukuailian.app.view.TabPageIndicator;

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

public class  MainFragment1 extends BaseFragment {
    @BindView(R.id.mainfragmen1tabpageindicator)
    TabPageIndicator indicator;
    @BindView(R.id.mainfragment1)
    ViewPager mainfragment1;


    List<MainFragment1Bean.DataBean> listTitle = new ArrayList<>();
    private List<Fragment> lisFragment = new ArrayList<>();


    @Override
    protected void initData() {
        super.initData();
        Bundle bundle1 = getArguments();
        if(bundle1 != null){
            List<MainFragment1Bean.DataBean> listbean = bundle1.getParcelableArrayList(JumpUtils.FIRSTTAG);
            for (int i = 0; i < listbean.size(); i++) {
                Bundle bundle = new Bundle();
                bundle.putInt(JumpUtils.FIRSTTAG,listbean.get(i).getId());
                Fragment fragment = new MainFragment1Item();
                fragment.setArguments(bundle);
                lisFragment.add(fragment);
            }
            listTitle.addAll(listbean);
            BasePagerAdapter adapter = new BasePagerAdapter(getActivity().getSupportFragmentManager());
            mainfragment1.setAdapter(adapter);
            indicator.setViewPager(mainfragment1);// 绑定indicator.setAdapter(adapter);// 设置adapter
            setTabPagerIndicator();
        }else{
            LoadingUtils.dissProgressFull();
        }

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
    public int setLayout() {
        return R.layout.mainfragment1;
    }

    @Override
    public void initView() {
        super.initView();

        MainActivity activity = (MainActivity) getActivity();
        activity.toolbar.setNavigationIcon(null);
        mainfragment1.setOffscreenPageLimit(listTitle.size());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onBackPressed() {

    }


    class BasePagerAdapter extends FragmentStatePagerAdapter {

        public BasePagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {

            return lisFragment.get(position);
        }

        @Override
        public int getCount() {
            return listTitle.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listTitle.get(position).getName();
        }
    }
}
