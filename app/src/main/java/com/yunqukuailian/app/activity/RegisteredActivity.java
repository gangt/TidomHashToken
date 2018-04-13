package com.yunqukuailian.app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.base.BaseActivity;
import com.yunqukuailian.app.base.BaseFragment;
import com.yunqukuailian.app.fragment.RegisteredFragmentStep1;
import com.yunqukuailian.app.fragment.RegisteredFragmentStep2;
import com.yunqukuailian.app.model.EventBusEvent.RegisterSuccessEvent;
import com.yunqukuailian.app.model.OnBackPressedListener;
import com.yunqukuailian.app.utils.hideOutSideTextUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisteredActivity extends BaseActivity implements OnBackPressedListener {
    @BindView(R.id.mainfragment4title)
    TextView mainfragment4title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.forget_password_fragment)
    FrameLayout forgetPasswordFragment;
    @BindView(R.id.forget_password_linearlayout)
    LinearLayout forgetPasswordLinearlayout;
    public FragmentTransaction transaction;
    private RegisteredFragmentStep1 fragment1;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.setTitleBar(this, toolbar);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getSupportFragmentManager().getBackStackEntryCount() > 1){
                    getSupportFragmentManager().popBackStack();
                    return ;
                }
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
        mainfragment4title.setText("账号注册");
        transaction = getSupportFragmentManager().beginTransaction();
        fragment1 = new RegisteredFragmentStep1();
        transaction.add(R.id.forget_password_fragment, fragment1);
        transaction.show(fragment1);
        transaction.addToBackStack(null);
        transaction.commit();
        forgetPasswordLinearlayout.setBackgroundColor(Color.WHITE);

    }

    public void setNextStep() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        RegisteredFragmentStep2 fragment2 = new RegisteredFragmentStep2();
        transaction.add(R.id.forget_password_fragment, fragment2);
        transaction.hide(fragment1);
        transaction.show(fragment2);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RegisterSuccessEvent event){
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private BaseFragment mBackHandedFragment;

    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {
        this.mBackHandedFragment = selectedFragment;
    }

    @Override
    public void onBackPressed() {

        if(getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
            return;
        }

        if(mBackHandedFragment !=null && getSupportFragmentManager().getBackStackEntryCount() > 0 ){
            mBackHandedFragment.onBackPressed();
            return ;
        }
        super.onBackPressed();
    }
}
