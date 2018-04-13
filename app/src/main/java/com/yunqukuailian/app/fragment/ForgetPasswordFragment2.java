package com.yunqukuailian.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.yunqukuailian.app.R;
import com.yunqukuailian.app.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/3/12/012.
 */

public class ForgetPasswordFragment2 extends BaseFragment {

    @BindView(R.id.forget_passwordstep2_newpwd)
    EditText forgetPasswordstep2Newpwd;
    @BindView(R.id.forget_passwordstep2_againpwd)
    EditText forgetPasswordstep2Againpwd;
    @BindView(R.id.forget_password_step2subbit)
    TextView forgetPasswordStep2subbit;
    Unbinder unbinder;

    @Override
    public int setLayout() {
        return R.layout.forgetpasswordstep2;
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

    @OnClick(R.id.forget_password_step2subbit)
    public void onViewClicked() {
    }
}
