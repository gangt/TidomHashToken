package com.yunqukuailian.app.model.ModeView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yunqukuailian.app.R;
import com.yunqukuailian.app.model.TxUserBean;
import com.yunqukuailian.app.presenter.UserTBPresenter;
import com.yunqukuailian.app.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用户提现选择Dialog
 * Created by Tidom on 2018/3/20/020.
 */

public class User_TX_choose_dialog implements View.OnClickListener {

    @BindView(R.id.user_tb_choose)
    TextView userTbChoose;
    @BindView(R.id.user_tb_scan)
    TextView userTbScan;
    @BindView(R.id.user_tb_paste)
    TextView userTbPaste;
    @BindView(R.id.user_tb_cancel)
    TextView userTbCancel;
    AlertDialog  cardDLG ;
    UserTBPresenter presenter ;
    Context context ;

    public  void showDialog(Context context, UserTBPresenter presenter) {
        this.presenter = presenter ;
        this.context = context ;
        cardDLG = new AlertDialog.Builder(context).create();
        cardDLG.show();

        Window window = cardDLG.getWindow();
        LayoutInflater layout = LayoutInflater.from(context);
        View view = layout.inflate(R.layout.dialog_view_user, null);
        ButterKnife.bind(view);
        window.setContentView(view);
        initlisenter(view);
        cardDLG.getWindow().setBackgroundDrawable(new BitmapDrawable());

        cardDLG.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        cardDLG.getWindow().setWindowAnimations(R.style.AnimBottom);
        WindowManager.LayoutParams wlp = cardDLG.getWindow().getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        cardDLG.getWindow().setAttributes(wlp);

    }

    private void initlisenter(View view) {
        //   @OnClick({R.id.user_tb_choose, R.id.user_tb_scan, R.id.user_tb_paste, R.id.user_tb_cancel})

        view.findViewById(R.id.user_tb_choose).setOnClickListener(this);
        view.findViewById(R.id.user_tb_scan).setOnClickListener(this);
        view.findViewById(R.id.user_tb_paste).setOnClickListener(this);
        view.findViewById(R.id.user_tb_cancel).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_tb_choose:
                //点击选择
                cardDLG.cancel();
                if(TxUserBean.getInstance().getAddressList() == null){
                    ToastUtils.showToastCentre(R.string.user_no_address);
                    return ;
                }

                if(TxUserBean.getInstance().getAddressList().size()>0){
//                    showDialog();
                    new  User_Wallet_choose_dialog().showDialog(context,presenter);
                }
                break;

            case R.id.user_tb_scan:
                //二维码扫描
                presenter.startQRCodeScan();
                cardDLG.cancel();
                break;

            case R.id.user_tb_paste:

                //从粘贴板粘贴
                presenter.startPaste();
                cardDLG.cancel();
                break;

            case R.id.user_tb_cancel:

                cardDLG.cancel();
                break;
        }
    }



}
