package com.yunqukuailian.app.presenter;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.yunqukuailian.app.MyApplication;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.activity.LoginActivity;
import com.yunqukuailian.app.http.LocalServiceUtil;
import com.yunqukuailian.app.model.EventBusEvent.LoginSuccessEvent;
import com.yunqukuailian.app.model.LoginBean;
import com.yunqukuailian.app.model.LoginResponseBean;
import com.yunqukuailian.app.model.UserData;
import com.yunqukuailian.app.utils.LoadUtils2;
import com.yunqukuailian.app.utils.LoadingUtils;
import com.yunqukuailian.app.utils.LogUtils;
import com.yunqukuailian.app.utils.MD5Util;
import com.yunqukuailian.app.utils.SharedPrefsUtil;
import com.yunqukuailian.app.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Tidom on 2018/3/14/014.
 */

public class LoginPresenter implements LoginContract.UserLogin {
    private static final String TAG = "LoginPresenter";
    private boolean isDebug = false;

    private LoginActivity mView;
    private Context context;

    public LoginPresenter(Context context) {
        this.context = context;
    }

    public void attatchView(LoginActivity view) {
        this.mView = view;
    }


    public void tryLogin() {
        mView.loginLogin.setClickable(false);
        String mobile = mView.loginphone.getText().toString().trim();
        String password = mView.loginpwd.getText().toString().trim();
        //test
//        password = "12345678" ;
//        mobile="18682351979" ;
        if (TextUtils.isEmpty(mobile) || TextUtils.isEmpty(password)) {
            mView.showToast(context.getResources().getString(R.string.login_notcomlete));
            mView.loginLogin.setClickable(true);
            return;
        }
        login(new LoginBean(mobile, MD5Util.MD5Encode(password)));

    }

    /**
     * @param
     */

    @Override
    public void login(final LoginBean loginBean) {
        LogUtils.v(TAG, loginBean.toString());
//        LoadingUtils.showFullProgress(mView);

        LoadUtils2.getInstance().showFullProgress(mView);
        LocalServiceUtil.getApiRest().login(loginBean).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<LoginResponseBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.loginLogin.setClickable(true);
//                LoadingUtils.dissProgressFull();
                LoadUtils2.getInstance().dissProgressFull();
                mView.showToast("网络请求超时");
            }

            @Override
            public void onNext(LoginResponseBean responseBody) {
                LoadUtils2.getInstance().dissProgressFull();
                mView.loginLogin.setClickable(true);
                try {
                    LogUtils.v(TAG, responseBody.toString());
                    if (responseBody.getData() != null) {
                        UserData.setPassWord(MD5Util.MD5Encode(loginBean.getPassword()));
                        saveUserData(responseBody.getData());
                        mView.showToast("登录成功");
                        mView.finish();
                        EventBus.getDefault().post(new LoginSuccessEvent());
                    }else{
                        mView.showToast("用户名或密码错误");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void saveUserData(LoginResponseBean.UserBean userBean) {
        UserData.setMobile(userBean.getMobile().substring(4));
        UserData.setUserName(userBean.getUserName());
        UserData.setToken(userBean.getToken());

    }


}
