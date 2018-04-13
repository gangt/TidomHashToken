package com.yunqukuailian.app.presenter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yunqukuailian.app.MainActivity;
import com.yunqukuailian.app.MyApplication;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.activity.UserBillActivity;
import com.yunqukuailian.app.adapter.MainFragment3Adapter;
import com.yunqukuailian.app.fragment.MainFragemtn3TBFragment;
import com.yunqukuailian.app.fragment.MainFragment3UserAccountFragment;
import com.yunqukuailian.app.fragment.MainFragment3;
import com.yunqukuailian.app.http.AbsAPICallback;
import com.yunqukuailian.app.http.LocalServiceUtil;
import com.yunqukuailian.app.model.EventBusEvent.GetUserBillEvent;
import com.yunqukuailian.app.model.EventBusEvent.GetUserWalletAddress;
import com.yunqukuailian.app.model.EventBusEvent.MainFragement3AccountEvent;
import com.yunqukuailian.app.model.GetAccountResponseBean;
import com.yunqukuailian.app.model.MainFragment3Bean;
import com.yunqukuailian.app.model.ModeView.MessgeDialog;
import com.yunqukuailian.app.model.TxUserBean;
import com.yunqukuailian.app.model.UserAccout;
import com.yunqukuailian.app.model.UserBillBean;
import com.yunqukuailian.app.model.UserCzAddressBean;
import com.yunqukuailian.app.model.UserData;
import com.yunqukuailian.app.utils.HMacSha256;
import com.yunqukuailian.app.utils.JumpUtils;
import com.yunqukuailian.app.utils.LoadUtils2;
import com.yunqukuailian.app.utils.LoadingUtils;
import com.yunqukuailian.app.utils.LogUtils;
import com.yunqukuailian.app.utils.SharedPrefsUtil;
import com.yunqukuailian.app.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Tidom on 2018/3/17/017.
 */

public class UserAccountPresenter implements UserAccountContract {

    private MainFragment3 mView;
    private MainFragment3UserAccountFragment view;
    private UserBillActivity activity;
    MainFragment3Adapter adapter ;

    public void attatchView(MainFragment3 view) {
        this.mView = view;
    }

    public void attatchView(MainFragment3UserAccountFragment view) {
        this.view = view;
    }

    public void attatchView(UserBillActivity activity) {
        this.activity = activity;
    }

    public void attatchView(MainFragment3Adapter adapter){
        this.adapter = adapter ;
    }

    private static String TAG = "UserAccountPresenter";

    @Override
    public void getUserAccount() {

        String apikey = UserData.getApiKey();
        String timestamp = UserData.getSecondTimestampTwo();
        String sign = UserData.getSign("apikey", "timestamp");
    //    LoadingUtils.showFullProgress(mView.getActivity());

        LoadUtils2.getInstance().showFullProgress(mView.getActivity());
        LocalServiceUtil.getApiRest().getUserAccount(timestamp, sign, apikey).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<GetAccountResponseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                LoadingUtils.dissProgressFull();
//                ToastUtils.showToastCentre(R.string.get_bii_faild);
                LoadUtils2.getInstance().dissProgressFull();
            }

            @Override
            public void onNext(GetAccountResponseBean responseBody) {
                LogUtils.v(TAG, responseBody.toString());
                if (responseBody.getData() != null) {
                    UserAccout.setRmbAccount(getRmbAccount(responseBody));
                    UserAccout.setDollarAccpunt(getDollarAccpunt(responseBody));
                    //save data
                    List<GetAccountResponseBean.AccountReponseData.AccountDataBean> assertList = responseBody.getData().getAssertList();
                    saveListData(assertList);
                } else {
                    LoadingUtils.dissProgressFull();
                    ToastUtils.showToastCentre(R.string.get_bii_faild);
                }
            }

        });
    }

    public String getRmbAccount(GetAccountResponseBean responseBody) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
        return "￥ " + df.format(responseBody.getData().getTotalAssert());

    }

    public String getDollarAccpunt(GetAccountResponseBean responseBody) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
        return "$ " + df.format(responseBody.getData().getTotalAssertUsd());

    }

    public String getPrice(GetAccountResponseBean.AccountReponseData.AccountDataBean bean) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
        return df.format(bean.getCoinNum());

    }

    public String getFreezeNum(GetAccountResponseBean.AccountReponseData.AccountDataBean bean) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
        return df.format(bean.getCoinFreezeNum());

    }


    /***
     * 获取用户账单
     * @param coinId
     * @param page
     */
    public void getUserTBBill(int coinId, int page) {

       /* //test data
        String apikey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXNzd29yZCI6IjE5MjE5ODk4ZmE1NzY0ZjY3NzUxMjI1ODNmOWIxZTA2IiwiaXRhIjoxNTIxNzk1ODE3Nzk3LCJtb2JpbGUiOiIrODZ8MTc3NTI3OTU4NzgiLCJ1c2VyTmFtZSI6IiIsImV4cCI6MTUyNDM4NzgxNywidXNlcklkIjoyMzM4OSwicGF5UGFzc3dvcmQiOiJlYTA1MTkyOGNhZjg0ZDI2Y2YzOGE5NDc3MWRhMmE5NSJ9.rM0KpTxE0cXtP0VjLKQr0Yp7uKUCbTBIvFSDMPOYJ64";
        String timestamp = "1521795818416";
        String sign = "BolHB5Vrw+NmPMiwmEXaZqW2OEycqm8kM1utc0PQpRU=";
        coinId = 24;
        page = 1;*/


        String apiKey = UserData.getApiKey();
        String timestamp = UserData.getSecondTimestampTwo();
        String sign = UserData.getSign("apikey","timestamp") ;

        LoadingUtils.showFullProgress(activity);
        LocalServiceUtil.getApiRest().getUserBill(timestamp, sign, apiKey, coinId, LocalServiceUtil.PAGESIZE, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserBillBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.v(TAG, "------------");
                        LoadingUtils.dissProgressFull();
                        EventBus.getDefault().post(new GetUserBillEvent(null));
                    }

                    @Override
                    public void onNext(UserBillBean userBillBean) {
                        LogUtils.v(TAG, userBillBean.toString());
                        EventBus.getDefault().post(new GetUserBillEvent(userBillBean));

                    }
                });
        activity.stopRelfsh();

    }


    /***
     * 获取用户钱包地址
     */

    public void getUserWalletAddress(long coId) {

        String apiKey = UserData.getApiKey();
        String timestamp = UserData.getSecondTimestampTwo();
        String sign = UserData.getSign("apikey", "timestamp");
        /***
         * (@Header("timestamp") String timestamp ,
         @Header("sign") String sign,
         @Path("coinId") long coinId,
         @Header("apikey") String apikey);
         */
        LoadingUtils.showFullProgress(view.getActivity());
        LocalServiceUtil.getApiRest().getUserWalletAddress(timestamp, sign, coId, apiKey).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<UserCzAddressBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LoadingUtils.dissProgressFull();
                LogUtils.v(TAG, e.toString());
            }

            @Override
            public void onNext(UserCzAddressBean userCzAddressBean) {
                LogUtils.v(TAG, userCzAddressBean.toString());
                LoadingUtils.dissProgressFull();
                if(userCzAddressBean.getMessage().equals("SUCCESS")){
                    EventBus.getDefault().post(new GetUserWalletAddress(userCzAddressBean.getData()));
                    return ;
                }
                new MessgeDialog().showDialg(view.getActivity(),userCzAddressBean.getMessage());

            }

        });
    }


    public void saveListData(List<GetAccountResponseBean.AccountReponseData.AccountDataBean> assertList) {

        List<MainFragment3Bean> list = new ArrayList<>();
        for (GetAccountResponseBean.AccountReponseData.AccountDataBean accountBean : assertList) {
            MainFragment3Bean bean = new MainFragment3Bean();
            bean.setTitle(accountBean.getCoinName()+"");
            bean.setFreeze(getFreezeNum(accountBean));
            bean.setPrice(getPrice(accountBean));
            bean.setCoinId(accountBean.getCoinId());
            bean.setFeeRate(accountBean.getFeeRate());
            list.add(bean);
        }
        EventBus.getDefault().post(new MainFragement3AccountEvent(list));
    }


    public void gotoUserAccountCz() {

        MainFragment3UserAccountFragment mainFragment3UserAccountFragment = new MainFragment3UserAccountFragment();
//        mainFragment3UserAccountFragment.setTxUserData(txUserBean);
        FragmentTransaction transaction = mView.getFragmentManager()
                .beginTransaction();


        transaction.add(R.id.fragmentmain, mainFragment3UserAccountFragment).addToBackStack(null);
        transaction.setCustomAnimations(
                R.anim.in_from_right,
                R.anim.out_to_left);
        transaction.commitAllowingStateLoss();
        MainActivity activity = (MainActivity) mView.getActivity();
        activity.showRadioGroup(false);

    }

    public void gotoUserAccountTb() {
        MainFragemtn3TBFragment mainFragment3UserTbAccount = new MainFragemtn3TBFragment();
//        mainFragment3UserTbAccount.setTxUserData(txUserBean); //TxUserBean txUserBean
        FragmentTransaction transaction = mView.getFragmentManager()
                .beginTransaction();

        transaction.add(R.id.fragmentmain, mainFragment3UserTbAccount).addToBackStack(null);
        transaction.setCustomAnimations(
                R.anim.in_from_right,
                R.anim.out_to_left);
        transaction.commitAllowingStateLoss();
        MainActivity activity = (MainActivity) mView.getActivity();
        activity.showRadioGroup(false);

    }

    public void gotoUserBillActivity() {

        JumpUtils.JumpActivity(MyApplication.getContext(), UserBillActivity.class);
    }

}
