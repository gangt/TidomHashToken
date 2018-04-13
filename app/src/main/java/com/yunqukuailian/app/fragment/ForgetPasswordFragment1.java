package com.yunqukuailian.app.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunqukuailian.app.R;
import com.yunqukuailian.app.activity.ForgetPasswordActivity;
import com.yunqukuailian.app.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/3/12/012.
 */

public class ForgetPasswordFragment1 extends BaseFragment {


    @BindView(R.id.currentpwd)
    TextView currentpwd;
    @BindView(R.id.linearLayout1)
    LinearLayout linearLayout1;
    @BindView(R.id.forgetphone)
    EditText forgetphone;
    @BindView(R.id.forget_passwordrelative)
    RelativeLayout forgetPasswordrelative;
    @BindView(R.id.forget_password_subbit)
    TextView forgetPasswordSubbit;
    Unbinder unbinder;
    private ForgetPasswordActivity activity;

    @Override
    public int setLayout() {
        return R.layout.forgetpwdstep1;
    }

    @Override
    protected void initView() {
        super.initView();
        activity = (ForgetPasswordActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
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

    @OnClick({R.id.forget_passwordrelative, R.id.forget_password_subbit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forget_passwordrelative:

                break;
            case R.id.forget_password_subbit:
                activity.setNextStep();
                break;
        }
    }
}
