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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersionalRealNameActivity extends BaseActivity {
    @BindView(R.id.mainfragment4title)
    TextView mainfragment4title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.authenticationcountry)
    LinearLayout authenticationcountry;
    @BindView(R.id.authenticationname)
    LinearLayout authenticationname;
    @BindView(R.id.authenticationcode)
    LinearLayout authenticationcode;
    @BindView(R.id.authenticationnext)
    TextView authenticationnext;

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
    protected void initView() {
        super.initView();

        mainfragment4title.setText("个人实名认证");
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_persional_real_name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.authenticationnext,R.id.authenticationcountry})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.authenticationnext:
                JumpUtils.JumpActivity(this,RealNameAuthenticationActivity.class);
                break;
            case R.id.authenticationcountry:
                showToast("显示国家选择");
                break;
        }
    }
}
