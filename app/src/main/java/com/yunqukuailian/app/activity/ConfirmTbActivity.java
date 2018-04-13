package com.yunqukuailian.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.base.BaseActivity;
import com.yunqukuailian.app.model.TxUserBean;
import com.yunqukuailian.app.utils.JumpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tidom on 2018/3/27/027.
 */

public class ConfirmTbActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mainfragment4title)
    TextView mainfragment4title;
    @BindView(R.id.tb_confirm_num)
    TextView tbConfirmNum;
    @BindView(R.id.tb_confirm_fee)
    TextView tbConfirmFee;
    @BindView(R.id.tb_confirm_address)
    TextView tbConfirmAddress;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_confirm_tb;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.setTitleBar(this, toolbar);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        mainfragment4title.setText("确认付币");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initShowData();
    }

    private void initShowData() {
        tbConfirmNum.setText(TxUserBean.getInstance().getTxNum());
        tbConfirmFee.setText(TxUserBean.getInstance().getKgf());
        tbConfirmAddress.setText(TxUserBean.getInstance().getAddress());
    }


    @OnClick({R.id.user_tb_confirm_back, R.id.user_tb_confirm_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_tb_confirm_back:
                    finish();
                break;
            case R.id.user_tb_confirm_history:
                JumpUtils.JumpActivity(this,UserBillActivity.class);
                break;
        }
    }
}
