package com.yunqukuailian.app.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.base.BaseActivity;
import com.yunqukuailian.app.utils.JumpUtils;
import com.yunqukuailian.app.utils.SharedPrefsUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IdentityAuthenticationActivity extends BaseActivity {

    @BindView(R.id.mainfragment4title)
    TextView mainfragment4title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.noauth)
    ImageView noauth;
    @BindView(R.id.isauth)
    ImageView isauth;
    @BindView(R.id.goauthentication)
    LinearLayout goauthentication;

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
        return R.layout.activity_identity_authentication;
    }

    @Override
    protected void initView() {
        super.initView();
        if (SharedPrefsUtil.isAuthentication()) {
            isauth.setImageResource(R.drawable.high_blue);
            noauth.setImageResource(R.drawable.low_gray);
        } else {
            isauth.setImageResource(R.drawable.high_gray);
            noauth.setImageResource(R.drawable.low_blue);
        }
        mainfragment4title.setText("身份认证");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.goauthentication})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.goauthentication:
                if(!SharedPrefsUtil.isAuthentication()){
//                    showToast("您没有实名认证，请前往认证");
                    JumpUtils.JumpActivity(this,PersionalRealNameActivity.class);
                }else {
                    showToast("您已经实名认证，不需要重复认证");
                }
                break;
        }
    }
}
