package com.yunqukuailian.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.classic.common.MultipleStatusView;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.activity.AccountManagementActivity;
import com.yunqukuailian.app.activity.LoginActivity;
import com.yunqukuailian.app.activity.RealNameAuthenticationActivity;
import com.yunqukuailian.app.activity.SecuritycCenterActivity;
import com.yunqukuailian.app.base.BaseFragment;
import com.yunqukuailian.app.model.EventBusEvent.LoginSuccessEvent;
import com.yunqukuailian.app.model.EventBusEvent.UnRegisterEvent;
import com.yunqukuailian.app.model.UserData;
import com.yunqukuailian.app.utils.JumpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/3/1/001.
 */

public class MainFragment4 extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.securitylinearlayout)
    LinearLayout securitylinearlayout;

    @BindView(R.id.minelinearlayout)
    LinearLayout minelinearlayout;

    @BindView(R.id.identitylinearlayout)
    LinearLayout identitylinearlayout;

    /*  @BindView(R.id.invitationlinearlayout)
      LinearLayout invitationlinearlayout;
      @BindView(R.id.customer_servicelinearlayout)
      LinearLayout customerServicelinearlayout;
      @BindView(R.id.aboutlinearlayout)
      LinearLayout aboutlinearlayout;*/
    @BindView(R.id.user_pic)
    ImageView userPic;
    @BindView(R.id.user_phone)
    TextView userPhone;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    @Override
    public int setLayout() {
        return R.layout.mainfragment4;
    }

    @Override
    public void initView() {
        super.initView();
        minelinearlayout.setVisibility(View.VISIBLE);
        identitylinearlayout.setVisibility(View.VISIBLE);
        setMobile();

    }

    private void setMobile() {
        if (UserData.isLogin() && !TextUtils.isEmpty(UserData.getMobile())) {
            userPhone.setText(UserData.getMobile());
        } else {
            userPhone.setText(getResources().getString(R.string.login_regiser));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
        return rootView;
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.securitylinearlayout, R.id.minelinearlayout, R.id.identitylinearlayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.securitylinearlayout:
//                showToast("个人中心");
                if (UserData.isLogin()) {
                    JumpUtils.JumpActivity(getActivity(), AccountManagementActivity.class);
                } else {
                    JumpUtils.JumpActivity(getActivity(), LoginActivity.class);
                }
                break;
            case R.id.minelinearlayout:
                JumpUtils.JumpActivity(getActivity(), SecuritycCenterActivity.class);
                break;
            case R.id.identitylinearlayout:
                JumpUtils.JumpActivity(getActivity(), RealNameAuthenticationActivity.class);
                break;
                /*
            case R.id.invitationlinearlayout:
                showToast("邀请享返佣");
                break;
            case R.id.customer_servicelinearlayout:
                showToast("客服中心");
                break;
            case R.id.aboutlinearlayout:
                showToast("关于我们");
                break;*/
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showMobileNumber(LoginSuccessEvent event) {
        setMobile();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showMobileNumber(UnRegisterEvent event) {
        setMobile();
    }

}
