package com.yunqukuailian.app.fragment;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.base.BaseFragment;
import com.yunqukuailian.app.model.OnBackPressedListener;
import com.yunqukuailian.app.presenter.RegisterconfigPrestener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/3/13/013.
 */

public class RegisteredFragmentStep2 extends BaseFragment {
    @BindView(R.id.registered_passwordnew)
    public EditText registeredPasswordnew;
    @BindView(R.id.registered_passwordagain)
    public EditText registeredPasswordagain;
    @BindView(R.id.register)
    TextView registerButt;
    Unbinder unbinder;
    RegisterconfigPrestener prestener;
    OnBackPressedListener listener ;

    @Override
    public int setLayout() {
        return R.layout.registeredstep2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        rootView.setClickable(true);
        unbinder = ButterKnife.bind(this, rootView);
        prestener = new RegisterconfigPrestener();
        prestener.attatchView(this);

        this.listener = (OnBackPressedListener) getActivity();
        listener.setSelectedFragment(this);

        return rootView;
    }

    @Override
    public void onBackPressed() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @OnClick(R.id.register)
    public void onViewClicked() {
        prestener.register();
    }

    public void showToast(String msg) {
        super.showToast(msg);
    }


}
