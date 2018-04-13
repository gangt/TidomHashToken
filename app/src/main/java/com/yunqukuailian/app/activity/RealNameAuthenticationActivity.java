package com.yunqukuailian.app.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.base.BaseActivity;
import com.yunqukuailian.app.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RealNameAuthenticationActivity extends BaseActivity {
    @BindView(R.id.mainfragment4title)
    TextView mainfragment4title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.autext1)
    TextView autext1;
    @BindView(R.id.identity_information)
    RelativeLayout identityInformation;
  /*  @BindView(R.id.authentication_subbit)
    TextView authenticationSubbit;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.setTitleBar(this, toolbar);
        mainfragment4title.setText("实名认证");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_real_name_authentication;
    }




    @OnClick({ R.id.identity_information,R.id.identity_information2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.identity_information:

            case R.id.identity_information2:
                ToastUtils.showToastBottom(R.string.user_itentify);
                break;
        }
    }

}
