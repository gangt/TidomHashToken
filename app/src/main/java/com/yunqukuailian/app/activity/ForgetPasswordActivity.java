package com.yunqukuailian.app.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.base.BaseActivity;
import com.yunqukuailian.app.base.BaseFragment;
import com.yunqukuailian.app.fragment.ForgetPasswordFragment1;
import com.yunqukuailian.app.fragment.ForgetPasswordFragment2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswordActivity extends BaseActivity {


    @BindView(R.id.mainfragment4title)
    TextView mainfragment4title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.forget_password_fragment)
    FrameLayout forgetPasswordFragment;
    private FragmentTransaction transaction;
    private ForgetPasswordFragment1 fragment1;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.setTitleBar(this, toolbar);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initView() {
        super.initView();
        mainfragment4title.setText("找回密码");
        transaction = getSupportFragmentManager().beginTransaction();
        fragment1 = new ForgetPasswordFragment1();
        transaction.add(R.id.forget_password_fragment, fragment1);
        transaction.show(fragment1);
        transaction.commit();

    }

    public void setNextStep() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ForgetPasswordFragment2 fragment2 = new ForgetPasswordFragment2();
        transaction.add(R.id.forget_password_fragment, fragment2);

        transaction.hide(fragment1);
        transaction.show(fragment2);
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
