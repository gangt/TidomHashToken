package com.yunqukuailian.app.presenter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.TextView;

//import com.google.zxing.client.android.CaptureActivity;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.fragment.MainFragemtn3TBFragment;
import com.yunqukuailian.app.http.LocalServiceUtil;
import com.yunqukuailian.app.model.EventBusEvent.TbSuccessEvent;
import com.yunqukuailian.app.model.EventBusEvent.TbWalletAddressEvent;
import com.yunqukuailian.app.model.ModeView.Model_TextView;
import com.yunqukuailian.app.model.ModeView.User_TX_choose_dialog;
import com.yunqukuailian.app.model.TBWalletAddress;
import com.yunqukuailian.app.model.Tb_ResponseDataBean;
import com.yunqukuailian.app.model.UserData;
import com.yunqukuailian.app.model.VmbTXRequestBean;
import com.yunqukuailian.app.utils.LoadUtils2;
import com.yunqukuailian.app.utils.LoadingUtils;
import com.yunqukuailian.app.utils.LogUtils;
import com.yunqukuailian.app.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by Tidom on 2018/3/20/020.
 */

public class UserTBPresenter {


    /**
     * show 选择付币对话框
     *
     * @param context
     */

    private static final String TAG = "UserTBPresenter";
    private Context context;
    private ClipboardManager mClipboardManager;
    private ClipData mClipData;
    private MainFragemtn3TBFragment fragemtn3TBFragment;
    private Model_TextView model_textView;

    public void attatchView(MainFragemtn3TBFragment fragemtn3TBFragment) {
        this.fragemtn3TBFragment = fragemtn3TBFragment;
    }


    public void showChooseDialog(Context context) {
        this.context = context;
        new User_TX_choose_dialog().showDialog(context, this);
    }


    public void showToastBottom(int textId) {
        ToastUtils.showToastBottom(textId);
    }


    public void  startQRCodeScan(){
        fragemtn3TBFragment.startScanQRCode();
    }



    /***
     * 粘贴数据
     */
    public void startPaste() {
        mClipboardManager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        mClipData = mClipboardManager.getPrimaryClip();

        try {
            if(mClipData == null){
                return;
            }
            //获取到内容
            ClipData.Item item = mClipData.getItemAt(0);

            String text = item.getText().toString();
            if (text != null) {
                fragemtn3TBFragment.userTbText2Listener.setText(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /***
     * 获取提币钱包地址
     */

    public void getTBWalletAddress(long coinId) {

     /*   String apiKey = getApiKey();
        String timestamp= getSecondTimestampTwo() ;
        String sign = getSign("apikey","timestamp") ;*/

//          LoadingUtils.showFullProgress(fragemtn3TBFragment.getActivity());
            LoadUtils2.getInstance().showFullProgress(fragemtn3TBFragment.getActivity());
        String apiKey = UserData.getApiKey();
        String timestamp = UserData.getSecondTimestampTwo();
        String sign = UserData.getSign("apikey", "timestamp");
        LocalServiceUtil.getApiRest().getTBwalletAddress(timestamp, sign, apiKey, coinId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<TBWalletAddress>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
//                LoadingUtils.dissProgressFull();
                LoadUtils2.getInstance().dissProgressFull();
                LogUtils.v(TAG, e.toString());
            }

            @Override
            public void onNext(TBWalletAddress tbWalletAddress) {
                LoadUtils2.getInstance().dissProgressFull();
                LogUtils.v(TAG, tbWalletAddress.toString());
                EventBus.getDefault().post(new TbWalletAddressEvent(tbWalletAddress));

            }

        });
    }

    /***
     * 提现虚拟币
     * @param withdrawRequestDTO
     */
    public void vmbTx(VmbTXRequestBean withdrawRequestDTO) {

        String apiKey = UserData.getApiKey();
        String timestamp = UserData.getSecondTimestampTwo();
        String sign = UserData.getSign("apikey", "timestamp");

        LoadUtils2.getInstance().showFullProgress(fragemtn3TBFragment.getActivity());
        LocalServiceUtil.getApiRest().getVmbTX(timestamp, sign, apiKey, withdrawRequestDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Tb_ResponseDataBean>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showToastCentre("网络请求超时");
                        LoadUtils2.getInstance().dissProgressFull();
                        fragemtn3TBFragment.userTbConfirmButtonListener.setClickable(true);
                    }

                    @Override
                    public void onNext(Tb_ResponseDataBean responseBody) {
                        LoadUtils2.getInstance().dissProgressFull();
                        fragemtn3TBFragment.userTbConfirmButtonListener.setClickable(true);
                        LogUtils.v(TAG, responseBody.toString());
                        ToastUtils.showToastCentre(responseBody.getMessage());
                        if(responseBody.getMessage().equals("SUCCESS")){
                            EventBus.getDefault().post(new TbSuccessEvent());
                        }
                    }
                });

    }



    public void showWalletAddress(String address){
        if(address!=null){
            fragemtn3TBFragment.userTbText2Listener .setText(address);
        }
    }



    /***
     * 获取验证码
     */
    public void getYzm(TextView textView) {
        new RegisterPresenter().startGetYZM(UserData.getMobile());
        ToastUtils.showToastCentre(R.string.yzm_has_sent);
        model_textView = Model_TextView.getInstance();
        model_textView.attatchView(textView);
        model_textView.startAlarm();
    }

    public void onDetach() {
        Model_TextView.getInstance().clear();
    }

}
