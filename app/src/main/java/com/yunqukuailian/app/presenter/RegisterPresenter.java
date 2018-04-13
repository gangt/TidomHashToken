package com.yunqukuailian.app.presenter;


import android.util.Log;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.fragment.RegisteredFragmentStep1;
import com.yunqukuailian.app.http.AbsAPICallback;
import com.yunqukuailian.app.http.LocalServiceUtil;
import com.yunqukuailian.app.model.EventBusEvent.RegisterUserExsistedEvent;
import com.yunqukuailian.app.model.RegisterResponseBean;
import com.yunqukuailian.app.model.UserData;
import com.yunqukuailian.app.model.UserRegisterData;
import com.yunqukuailian.app.utils.HMacSha256;
import com.yunqukuailian.app.utils.LogUtils;
import com.yunqukuailian.app.utils.SharedPrefsUtil;
import com.yunqukuailian.app.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/14/014.
 */

public class RegisterPresenter implements RegisterContract {

    private RegisteredFragmentStep1 mView;
    private static final String TAG ="RegisterPresenter";
    public static final String API_BASE_URL = "http://47.52.170.220:8808/api/v1/";

    private static Retrofit mRetrofit;
    private static OkHttpClient mOkHttpClient;

    public final static int CONNECT_TIMEOUT = 60;        //设置连接超时时间
    public final static int READ_TIMEOUT = 60;            //设置读取超时时间
    public final static int WRITE_TIMEOUT = 60;           //设置写的超时时间
    private  String apiKey ;
    private  String defaultStamp = "1508874756";


    public void attatchView(RegisteredFragmentStep1 view) {
        this.mView = view;
    }


    public  void saveTempUserData() {
        UserRegisterData.getInstance().userMobile = mView.registeredPhone.getText().toString().trim();
        UserRegisterData.getInstance().verifyCode = mView.registeredCode.getText().toString().trim();
        UserRegisterData.getInstance().inviteCode = mView.registeredInviteCode.getText().toString().trim();
    }


    /***
     * 获取验证码
     */

    public void getYZM(){
        checkNumberRight(mView.registeredPhone.getText().toString().trim());
    }



    /***
     * 校正手机号
     * @param number
     */
    @Override
    public void checkNumberRight(String number) {
//        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        if(!UserRegisterData.getInstance().countryCode.equals("+86|")){
            if(number.length()<6){
                mView.showToast(mView.getResources().getString(R.string.error_phonenumber));
                return ;
            }
            checkNumberRegisted(number);
        }else {
            String regex = "^1[3,4,5,7,8]\\d{9}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(number);
            boolean isMatch = m.matches();
            if(!isMatch) {
                mView.showToast(mView.getResources().getString(R.string.error_phonenumber));
            }else{
                checkNumberRegisted(number);
            }
        }

    }


    /**
     * 检查手机号是否已经被注册过
     *
     * @param mobile
     */
    @Override
    public void checkNumberRegisted( final String mobile) {
        LocalServiceUtil.getApiRest().checkIfRegister(mobile).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new AbsAPICallback<RegisterResponseBean>() {
                @Override
                protected void onDone(RegisterResponseBean responseBody) {
                    try {
                        LogUtils.v(TAG,responseBody.toString());
                        if(responseBody.getMessage().equals("SUCCESS")){ //用户未注册
                            EventBus.getDefault().post(new RegisterUserExsistedEvent());
//                        startGetYZM(UserData.getToken(),getSecondTimestampTwo(),getSign(),mobile);
                        }else{
                            mView.showToast(responseBody.getMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    public void startGetYZM(String mobile){
        ToastUtils.showToastCentre(R.string.yzm_has_sent);

        String apiKey = UserData.getApiKey();
        String timestamp= UserData.getSecondTimestampTwo() ;
        String sign = UserData.getSign("apikey","timestamp") ;
       // mobile = "+86|"+mobile ;
        mobile =  UserRegisterData.getInstance().countryCode+mobile;

        LogUtils.v(TAG,"apiKey: "+apiKey);
        LogUtils.v(TAG,"timestamp: "+timestamp);
        LogUtils.v(TAG,"sign: "+sign);
        LogUtils.v(TAG,"mobile: "+mobile);

        LocalServiceUtil.getApiRest().getYZM(apiKey,timestamp,sign,mobile).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new AbsAPICallback<ResponseBody>() {
            @Override
            protected void onDone(ResponseBody responseBody) {
                try {
                    String res = responseBody.toString();
                    LogUtils.v(TAG,res);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     *
     * @return获取加密串
     */
    private static String getSource(String apiKey,String teim){
        //apikey=351dca7e0d23c1b39d8f2e07fecc5561&timestamp=20171025012614

        return  apiKey+"="+getApiKey()+"&"+teim+"="+getSecondTimestampTwo();
    }


    /**
     * token
     * @return
     */
    public   static String getApiKey(){
        return SharedPrefsUtil.getToken();
    }


    public static String getSecondTimestampTwo(){
        Date date = new Date();
        int secends = Integer.parseInt(String.valueOf(date.getTime() / 1000))-8*3600;
        return String.valueOf(secends);
    }


    public static String getSign(String apiKey,String time){
        //用户登陆双重MD5;
        try {
            String key = SharedPrefsUtil.getSignKey();
            String sorceCode = getSource(apiKey,time).trim();
            Log.e("yongyisorceCode",sorceCode);
            String sign = HMacSha256.hash(sorceCode.getBytes(),key.getBytes()).trim();
            Log.e("yongyisign",sign);
            return sign;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null ;
    }

}
