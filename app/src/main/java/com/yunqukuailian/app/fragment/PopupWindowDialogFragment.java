package com.yunqukuailian.app.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.yunqukuailian.app.R;
import com.yunqukuailian.app.model.EventModel;
import com.yunqukuailian.app.model.MainFragment1Bean;
import com.yunqukuailian.app.utils.DisplayUtil;
import com.yunqukuailian.app.utils.FragmentFactory;
import com.yunqukuailian.app.utils.JumpUtils;
import com.yunqukuailian.app.utils.LoadingUtils;
import com.yunqukuailian.app.view.TabPageIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/15/015.
 */

public class PopupWindowDialogFragment extends DialogFragment{


    private View rootView;
    private List<MainFragment1Bean.DataBean> listbean;
    private static List<Fragment> listFragment = new ArrayList<>();

    public void setData(List<MainFragment1Bean.DataBean> listbean) {
        this.listbean = listbean;
        if (listbean != null && listFragment.size() == 0) {
            for (int i = 0; i < listbean.size(); i++) {
                PupupWindowFragment fragment = new PupupWindowFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(JumpUtils.FIRSTTAG,listbean.get(i).getId());
                fragment.setArguments(bundle);
                listFragment.add(fragment);
            }
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        //添加这一行
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if(rootView == null){
            rootView = inflater.inflate(R.layout.mainfragment2popuplayout,null);
            initView();
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Window dialogWindow = getDialog().getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.TOP);
        lp.dimAmount=0f;
        lp.x = 0;
        lp.y = DisplayUtil.dip2px(getActivity(),50);
        lp.width = lp.MATCH_PARENT;
        lp.height = DisplayUtil.dip2px(getActivity(),300);
        dialogWindow.setAttributes(lp);
    }

    public void initView(){
        TabPageIndicator tabPageIndicator = rootView.findViewById(R.id.popuptitle);
        ViewPager popupViewpager = rootView.findViewById(R.id.popupViewpager1);
        popupViewpager.setOffscreenPageLimit(listbean.size());
        BasePagerPopupWindowAdapter adapter = new BasePagerPopupWindowAdapter(getChildFragmentManager());

        popupViewpager.setAdapter(adapter);// 设置adapter
        tabPageIndicator.setViewPager(popupViewpager);// 绑定indicator

        tabPageIndicator.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_NOWEIGHT_EXPAND_SAME);// 设置模式，一定要先设置模式
        tabPageIndicator.setDividerPadding(10);//设置
        tabPageIndicator.setIndicatorColor(Color.parseColor("#E70101"));// 设置底部导航线的颜色
        tabPageIndicator.setTextColorSelected(Color.parseColor("#E70101"));// 设置tab标题选中的颜色
        tabPageIndicator.setTextColor(Color.parseColor("#797979"));// 设置tab标题未被选中的颜色
        tabPageIndicator.setTextSize(DisplayUtil.sp2px(getActivity(), 14));// 设置字体大小
        tabPageIndicator.setIndicatorHeight(DisplayUtil.sp2px(getActivity(), 2));

    }
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();
        if (activity instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
        }
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventModel event) {
        dismiss();
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




    //popupwindow主选项卡
    class BasePagerPopupWindowAdapter extends FragmentStatePagerAdapter {

        public BasePagerPopupWindowAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            return listFragment.get(position);
        }

        @Override
        public int getCount() {
            return listbean == null?0:listbean.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listbean.get(position).getName();
        }
    }
}
