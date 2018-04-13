package com.yunqukuailian.app.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.base.BaseActivity;
import com.yunqukuailian.app.presenter.LoginPresenter;
import com.yunqukuailian.app.utils.JumpUtils;
import com.yunqukuailian.app.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.mainfragment4title)
    TextView mainfragment4title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.logintext)
    TextView logintext;
    @BindView(R.id.loginphone)
    public EditText loginphone;
    @BindView(R.id.loginrelativelayout)
    RelativeLayout loginrelativelayout;
    @BindView(R.id.loginpwd)
    public EditText loginpwd;
    @BindView(R.id.login_forgetpwd)
    TextView loginForgetpwd;
    @BindView(R.id.login_login)
    public TextView loginLogin;
    @BindView(R.id.login_registered)
    TextView loginRegistered;
    private static final String TAG = "LoginActivity";
    @BindView(R.id.add_account)
    ImageView addAccount;
    @BindView(R.id.show_hide_password)
    ImageView showHidePassword;
    private LoginPresenter presenter;
    private boolean mbDisplayFlg = false;

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
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        presenter = new LoginPresenter(this);
        presenter.attatchView(this);
    }

    @Override
    protected void initView() {
        super.initView();

        mainfragment4title.setText("账号登陆");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.loginrelativelayout, R.id.login_forgetpwd, R.id.login_login, R.id.login_registered,R.id.show_hide_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginrelativelayout:
            case R.id.login_forgetpwd:
                ToastUtils.showToastBottom(R.string.user_forget_passwprd);
                break;
            case R.id.login_login:
                presenter.tryLogin();
                break;
            case R.id.login_registered:
                JumpUtils.JumpActivity(this, RegisteredActivity.class);
            case R.id.show_hide_password:
                if (!mbDisplayFlg) {
                    // display password text, for example "123456"
                    loginpwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showHidePassword.setImageResource(R.drawable.passwordsee);
                } else {
                    // hide password, display "."
                    loginpwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showHidePassword.setImageResource(R.drawable.passwordclose);
                }
                mbDisplayFlg = !mbDisplayFlg;
                int index = loginpwd.getText().toString().length();
                loginpwd.setSelection(index);
                loginpwd.postInvalidate();
                break;
        }
    }

    public void showToast(String msg) {
        super.showToast(msg);
    }


}
