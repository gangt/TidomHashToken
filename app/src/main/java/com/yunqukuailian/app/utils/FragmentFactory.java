package com.yunqukuailian.app.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.yunqukuailian.app.fragment.MainFragment1Item;
import com.yunqukuailian.app.fragment.MainFragment2;
import com.yunqukuailian.app.fragment.MainFragment2BuyFragment;
import com.yunqukuailian.app.fragment.MainFragment2CurrentFragment;
import com.yunqukuailian.app.fragment.MainFragment2HistoryFragment;
import com.yunqukuailian.app.fragment.MainFragment2SellFragment;
import com.yunqukuailian.app.fragment.PupupWindowFragment;
import com.yunqukuailian.app.model.MainFragment1Bean;
import com.yunqukuailian.app.model.MainFragment1BeanItem;

import java.util.ArrayList;
import java.util.List;


public class FragmentFactory {

    private static List<Fragment> listfragmen1 = new ArrayList<>();//交易popupwindowfragment集合

    public static Fragment getPopupWindowFragmentItem(int position, List<MainFragment1Bean.DataBean> list){
        Log.e("yongyipostion",String.valueOf(position)+" 当前集合大小"+listfragmen1.size());
        if(position >= listfragmen1.size()){
            PupupWindowFragment fragment = new PupupWindowFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(JumpUtils.FIRSTTAG,list.get(position).getId());
            fragment.setArguments(bundle);
            listfragmen1.add(fragment);
        }
        return listfragmen1.get(position);
    }


    public static Fragment createMainFragmen1Item(int position, List<MainFragment1Bean.DataBean> list) {
        Bundle bundle = new Bundle();
        bundle.putInt(JumpUtils.FIRSTTAG,list.get(position).getId());
        Fragment fragment = new MainFragment1Item();
        fragment.setArguments(bundle);

        return fragment;
    }

}
