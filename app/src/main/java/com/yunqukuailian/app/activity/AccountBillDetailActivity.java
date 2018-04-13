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
import com.yunqukuailian.app.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tidom on 2018/3/24/024.
 */

public class AccountBillDetailActivity extends BaseActivity {


    @BindView(R.id.bill_account)   //-19.0000
            TextView billAccount;
    @BindView(R.id.text_1)   //余额
            TextView text1;
    @BindView(R.id.text_2)  //时间
            TextView text2;
    @BindView(R.id.text_3)  //类型
            TextView text3;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mainfragment4title)
    TextView mainfragment4title;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.setTitleBar(this, toolbar);
        toolbar.setNavigationIcon(R.drawable.back_icon);
        mainfragment4title.setText(TxUserBean.getInstance().getTitle().toUpperCase()+"账单");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected int setLayoutId() {
        return R.layout.layout_account_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        showView();
    }

    private void showView() {
        billAccount.setText(TxUserBean.getInstance().getSxf());
        text1.setText(TxUserBean.getInstance().getPrice());
        text2.setText(Utils.msToDate2(Long.parseLong(TxUserBean.getInstance().gettimeStamp())));
        text3.setText(TxUserBean.getInstance().gettype());
    }
}
