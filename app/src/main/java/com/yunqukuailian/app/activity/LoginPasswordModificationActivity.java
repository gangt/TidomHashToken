package com.yunqukuailian.app.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.base.BaseActivity;
import com.yunqukuailian.app.utils.JumpUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginPasswordModificationActivity extends BaseActivity {
    @BindView(R.id.mainfragment4title)
    TextView mainfragment4title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ispaymoney)
    TextView ispaymoney;
    @BindView(R.id.currentpwd)
    EditText currentpwd;
    @BindView(R.id.newpwd)
    EditText newpwd;
    @BindView(R.id.newpwdagin)
    EditText newpwdagin;
    @BindView(R.id.phonecode)
    EditText phonecode;
    @BindView(R.id.subbit)
    TextView subbit;
    private int type;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_login_password_modification;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initView() {
        super.initView();
        type = getIntent().getIntExtra(JumpUtils.FIRSTTAG, 1);
        if (type == 1) {
            //登录密码修改
            ispaymoney.setVisibility(View.GONE);
            mainfragment4title.setText("登录密码修改");
        } else {
            //资金密码修改
            ispaymoney.setVisibility(View.VISIBLE);
            mainfragment4title.setText("资金密码修改");
        }


    }



    @OnClick(R.id.subbit)
    public void onViewClicked() {
        showToast("提交");
    }
}
