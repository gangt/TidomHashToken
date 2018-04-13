package com.yunqukuailian.app.presenter;

import android.text.TextUtils;

import com.yunqukuailian.app.R;
import com.yunqukuailian.app.fragment.RegisteredFragmentStep2;
import com.yunqukuailian.app.http.LocalServiceUtil;
import com.yunqukuailian.app.model.EventBusEvent.RegisterSuccessEvent;
import com.yunqukuailian.app.model.RegisterBean;
import com.yunqukuailian.app.model.RegisterResponseBean;
import com.yunqukuailian.app.model.UserRegisterData;
import com.yunqukuailian.app.utils.LogUtils;
import com.yunqukuailian.app.utils.MD5Util;

import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/14/014.
 */

public class RegisterconfigPrestener {

    private RegisteredFragmentStep2 mView = null ;
    private static final String TAG = "RegisterconfigPrestener";
    String pwd1;
    String pwd2;

    public void attatchView(RegisteredFragmentStep2 view) {
        this.mView = view;

    }

    /*** 注册
     * @Field("inviteCode") String inviteCode,
     @Field("mobile") String mobile,
     @Field("password") String password,
     @Field("verifyCode") String verifyCode);
     */


    public void register() {
        boolean res = checkNumber();
        String userMobile = UserRegisterData.getInstance().userMobile;
        String verifyCode = UserRegisterData.getInstance().verifyCode;
        //test
        verifyCode = "123456";
        String inviteCode = UserRegisterData.getInstance().inviteCode;
        final RegisterBean registerBean = new RegisterBean();

        registerBean.setInvitaeCode(inviteCode);
        registerBean.setMobile(userMobile);
        registerBean.setPassword(MD5Util.MD5Encode(pwd1));
        registerBean.setVerifyCode(verifyCode);

        if (res) {
            LocalServiceUtil.getApiRest().register(registerBean).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(new Subscriber<RegisterResponseBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            mView.showToast(mView.getResources().getString(R.string.user_exsist));
                        }

                        @Override
                        public void onNext(RegisterResponseBean responseBean) {
                            LogUtils.v(TAG, responseBean.getMessage());
                            String message = responseBean.getMessage();
                            mView.showToast(message);
                            if(message.equals("SUCCESS")){
                                gobackLogin(new RegisterSuccessEvent());
                            }
                        }
                    });
        }
    }

    public void gobackLogin(RegisterSuccessEvent object){
        EventBus.getDefault().post(object);
    }

    /**
     * 确认密码是否一致
     *
     * @return
     */
    public boolean checkNumber() {
        pwd1 = mView.registeredPasswordnew.getText().toString().trim();
        pwd2 = mView.registeredPasswordagain.getText().toString().trim();
        if (TextUtils.isEmpty(pwd1) || TextUtils.isEmpty(pwd2)) {
            mView.showToast(mView.getResources().getString(R.string.not_equal));
            return false;
        }
        return pwd1.equals(pwd2);
    }


}
