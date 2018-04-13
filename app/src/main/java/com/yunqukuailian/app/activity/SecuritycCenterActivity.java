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
import com.yunqukuailian.app.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecuritycCenterActivity extends BaseActivity {
    @BindView(R.id.mainfragment4title)
    TextView mainfragment4title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.loginpwdset)
    LinearLayout loginpwdset;
    @BindView(R.id.moneypwdset)
    LinearLayout moneypwdset;

  /*  @BindView(R.id.googlepwdset)
    LinearLayout googlepwdset;
    @BindView(R.id.loginvalidationset)
    LinearLayout loginvalidationset;
    @BindView(R.id.payforvalidationset)
    LinearLayout payforvalidationset;
    @BindView(R.id.payvalidationset)
    LinearLayout payvalidationset;*/
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
        return R.layout.activity_securityc_center;
    }

    @Override
    protected void initView() {
        super.initView();
        mainfragment4title.setText("安全中心");
    }

    @Override
    protected void initData() {
        super.initData();
    }



    @OnClick({R.id.loginpwdset, R.id.moneypwdset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginpwdset:
//                JumpUtils.JumpActivity(this, LoginPasswordModificationActivity.class, 1);
                ToastUtils.showToastBottom(R.string.user_forget_set_password);
                break;
            case R.id.moneypwdset:
//                JumpUtils.JumpActivity(this, LoginPasswordModificationActivity.class, 2);
                ToastUtils.showToastBottom(R.string.user_forget_set_password);
                break;


         /*   case R.id.mainfragment4title:
                break;
            case R.id.toolbar:
                break;
           case R.id.loginvalidationset:
                break;

            case R.id.googlepwdset:
                break;
            case R.id.payforvalidationset:
                break;
            case R.id.payvalidationset:
                break;
*/
        }
    }
}
