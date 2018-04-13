package com.yunqukuailian.app.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.client.android.CaptureActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.yunqukuailian.app.MainActivity;
import com.yunqukuailian.app.MyApplication;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.activity.ConfirmTbActivity;
import com.yunqukuailian.app.base.BaseFragment;
import com.yunqukuailian.app.model.EventBusEvent.TbSuccessEvent;
import com.yunqukuailian.app.model.EventBusEvent.TbWalletAddressEvent;
import com.yunqukuailian.app.model.OnBackPressedListener;
import com.yunqukuailian.app.model.TBWalletAddress;
import com.yunqukuailian.app.model.TxUserBean;
import com.yunqukuailian.app.model.VmbTXRequestBean;
import com.yunqukuailian.app.presenter.UserTBPresenter;
import com.yunqukuailian.app.utils.JumpUtils;
import com.yunqukuailian.app.utils.LogUtils;
import com.yunqukuailian.app.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.yunqukuailian.app.utils.Utils.getAccount;

/***
 * 提币页面
 */

public  class MainFragemtn3TBFragment extends BaseFragment {

/*    @BindView(R.id.mainfragment4title)
    TextView mainfragment4title;*/
    @BindView(R.id.add_account)
    ImageView addAccount;
   /* @BindView(R.id.toolbar)
    Toolbar toolbar;*/
    @BindView(R.id.user_tb_text2_listener)
    public TextView userTbText2Listener;
    @BindView(R.id.user_tb_text3_listener)
    EditText userTbText3Listener;
    @BindView(R.id.user_tb_textview5)
    EditText userTbTextview5;
    @BindView(R.id.user_tb_no_set_textview_listener)
    TextView userTbNoSetTextviewListener;
    @BindView(R.id.user_tb_textview6)
    EditText userTbTextview6;
    @BindView(R.id.user_tb_gyz_textview_listener)
    TextView userTbGyzTextviewListener;
    @BindView(R.id.user_tb_zjm_ll)
    RelativeLayout userTbZjmLl;
    @BindView(R.id.user_tb_confirm_button_listener)
    public   TextView userTbConfirmButtonListener;
    @BindView(R.id.user_tb_textview1)
    public TextView userTbTextview1;
    @BindView(R.id.user_tb_textview4)
    TextView userTbTextview4;
    private Unbinder unbinder;
    UserTBPresenter tbPresenter ;
    private TxUserBean txUserBean;
    public  MainActivity activity ;
    String fee ;
    private OnBackPressedListener listener ;

    @Override
    public int setLayout() {
        return R.layout.fragment_user_tb;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
//        ImmersionBar.setTitleBar(getActivity(), toolbar);
        activity.toolbar.setNavigationIcon(R.drawable.back_icon);
        activity. mainfragment4title.setText(txUserBean.getTitle() + getString(R.string.toorbar_user_tx));
        activity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
                activity.setSelect(2);
            }
        });
    }

//    public void setTxUserData(TxUserBean txUserBean){
//        this.txUserBean = txUserBean ;
//    }

    public void initTitleShow() {
//        userTbTextview1.setText(txUserBean.getPrice() + " " + txUserBean.getTitle());
        transtoRed(txUserBean.getPrice() + " " + txUserBean.getTitle(),userTbTextview1);
        userTbTextview4.setText("0" + txUserBean.getTitle());
    }

    /***
     * 富文本
     * @param text
     * @param mTextView
     */
    public void transtoRed(String text ,TextView mTextView){
        Spannable spannable = Spannable.Factory.getInstance().newSpannable(text);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#FF0000"));
        spannable.setSpan(foregroundColorSpan, 0, text.indexOf(" "), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mTextView.setText(spannable);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        MainActivity activity =(MainActivity)getActivity();
        EventBus.getDefault().unregister(this);
        activity.toolbar.setNavigationIcon(null);
        activity.showRadioGroup(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        tbPresenter.onDetach();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        txUserBean = TxUserBean.getInstance() ;
        activity =(MainActivity) getActivity();
        EventBus.getDefault().register(this);
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        rootView.setClickable(true);

        unbinder = ButterKnife.bind(this, rootView);
        initTitleShow();
        tbPresenter = new UserTBPresenter();
        tbPresenter.attatchView(this);
        tbPresenter.getTBWalletAddress(txUserBean.getCoinId());
        initEditTextChangeListener();
        this.listener = (OnBackPressedListener) getActivity();
        listener.setSelectedFragment(this);
        return rootView;
    }

    private void initEditTextChangeListener() {

        userTbText3Listener.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(userTbText3Listener.getText().toString().equals("")){
                    userTbTextview4.setText("0 "+txUserBean.getTitle());
                    return ;
                }
                String v1  = userTbText3Listener.getText().toString();
                String v2  = String.valueOf(txUserBean.getFeeRate()) ;
                fee =  getAccount(v1, v2);
                userTbTextview4.setText(fee+" "+ txUserBean.getTitle());
            }
        });
    }

    @Override
    public void onBackPressed() {
          getActivity().getSupportFragmentManager().popBackStack();
          activity.setSelect(2);
    }


    @OnClick({R.id.user_tb_text2_listener, R.id.user_tb_no_set_textview_listener, R.id.user_tb_gyz_textview_listener, R.id.user_tb_confirm_button_listener})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_tb_text2_listener:
                //选择付币地址
                tbPresenter.showChooseDialog(getActivity());
                break;

            case R.id.user_tb_no_set_textview_listener: //从未设置过
                tbPresenter.showToastBottom(R.string.user_bt_not_set);
                break;

            case R.id.user_tb_gyz_textview_listener:
                tbPresenter.getYzm(userTbGyzTextviewListener);
                break;

            case R.id.user_tb_confirm_button_listener:
                if( checkRequestData()){
                    saveData();
                    userTbConfirmButtonListener.setClickable(false);
                    tbPresenter.vmbTx(geneateTxBean());
                }
                break;
        }
    }

    private void saveData() {
        double number = Double.parseDouble(userTbText3Listener.getText().toString());
        TxUserBean.getInstance().setTxNum(userTbText3Listener.getText().toString().trim())
                                .setKgf(fee + txUserBean.getTitle())
                                .setAddress(userTbText2Listener.getText().toString().trim());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private boolean checkRequestData() {
        if(TextUtils.isEmpty(userTbText3Listener.getText().toString().trim())){
            ToastUtils.showToastBottom("转账数量不能为空");
            return false;
        }
        if(TextUtils.isEmpty(userTbTextview5.getText().toString().trim())){
            ToastUtils.showToastBottom("资金密码不能为空");
            return false;
        }
        if(TextUtils.isEmpty(userTbTextview6.getText().toString().trim())){
            ToastUtils.showToastBottom("验证码不能为空");
            return false;
        }
        return  true ;

    }

    public void startScanQRCode(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                    1);}
        else{
            Intent intent = new Intent(getActivity(), CaptureActivity.class);
            startActivityForResult(intent,100);

        }

    }



   /* public void startQRCodeScan(){
        JumpUtils.JumpActivityForResult(getActivity(),MyApplication.getContext(), ScanActivity.class,102);
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length >= 1) {
                int cameraResult = grantResults[0];//相机权限
                boolean cameraGranted = cameraResult == PackageManager.PERMISSION_GRANTED;//拍照权限
                if (cameraGranted) {
                    Intent intent = new Intent(getActivity(), CaptureActivity.class);
                    startActivityForResult(intent,100);
                } else {
                    //不具有相关权限，给予用户提醒，比如Toast或者对话框，让用户去系统设置-应用管理里把相关权限开启
                }
            }
        }
    }


    /***
     * 扫码结果回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 111){
            String s = data.getStringExtra("result");//这个绿色的result是在第三方类库里面定义的key
            userTbText2Listener .setText(s);
        }
    }



    public VmbTXRequestBean geneateTxBean(){
        VmbTXRequestBean vmbTXRequestBean = new VmbTXRequestBean();
        double number = Double.parseDouble(userTbText3Listener.getText().toString());

        vmbTXRequestBean.setCode(userTbTextview6.getText().toString().trim());
        vmbTXRequestBean.setAddress(userTbText2Listener.getText().toString().trim());
        vmbTXRequestBean.setPayPwd(userTbTextview5.getText().toString().trim());
        vmbTXRequestBean.setNum(Double.parseDouble(userTbText3Listener.getText().toString().trim()));
        vmbTXRequestBean.setCoinId(txUserBean.getCoinId());
        vmbTXRequestBean.setFee(Double.parseDouble(fee));
        return vmbTXRequestBean ;

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTbWalletAddressEvent(TbWalletAddressEvent event){
        TBWalletAddress tbWalletAddress = event.getAddress();
        if(tbWalletAddress.getMessage().equals("SUCCESS")&& tbWalletAddress.getData()!=null &&tbWalletAddress.getData().size()>0 ){
            //save wallet data
            TxUserBean.getInstance().setAddressList(tbWalletAddress.getData());
            return;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTbSuccessEvent(TbSuccessEvent event){
        JumpUtils.JumpActivity(MyApplication.getContext(), ConfirmTbActivity.class);
        onBackPressed();
    }

}
