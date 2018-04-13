package com.yunqukuailian.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.model.ModeView.MessgeDialog;
import com.yunqukuailian.app.utils.NetWorkUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/3/1/001.
 */

public abstract class BaseFragment extends BaseSimpleFragment {
    Unbinder unbinder;
    protected View mViewRoot;
    protected ImmersionBar mImmersionBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mViewRoot = LayoutInflater.from(getActivity()).inflate(setLayout(),container,false);
        unbinder = ButterKnife.bind(this, mViewRoot);

        //初始化沉浸式
        if(!NetWorkUtils.isConnected()){
            new MessgeDialog().showDialg(getActivity(),getResources().getString(R.string.net_not_connected));
            return null;
        }
        if (isImmersionBarEnabled())
            initImmersionBar();
        initView();
        initData();

        return mViewRoot;
    }

    public abstract void onBackPressed();

    protected  void initData(){};

    protected void initView(){};

    public int setLayout(){
        return 0;
    }

    protected void showToast(String message){
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

}
