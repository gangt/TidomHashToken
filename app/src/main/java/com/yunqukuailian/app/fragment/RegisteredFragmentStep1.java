package com.yunqukuailian.app.fragment;

import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunqukuailian.app.R;
import com.yunqukuailian.app.activity.CountryCodeSelectListActivity;
import com.yunqukuailian.app.activity.RegisteredActivity;
import com.yunqukuailian.app.base.BaseFragment;
import com.yunqukuailian.app.model.EventBusEvent.RegisterCountryCodeEvent;
import com.yunqukuailian.app.model.EventBusEvent.RegisterUserExsistedEvent;
import com.yunqukuailian.app.model.ModeView.CountRegisterButton;
import com.yunqukuailian.app.model.OnBackPressedListener;
import com.yunqukuailian.app.model.UserData;
import com.yunqukuailian.app.model.UserRegisterData;
import com.yunqukuailian.app.presenter.RegisterPresenter;
import com.yunqukuailian.app.utils.JumpUtils;
import com.yunqukuailian.app.utils.SharedPrefsUtil;
import com.yunqukuailian.app.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by Administrator on 2018/3/13/013.
 */

public class RegisteredFragmentStep1 extends BaseFragment implements CountRegisterButton.OnButtonClickListener {
    @BindView(R.id.registered_text)
    TextView registeredText;
    @BindView(R.id.registered_country)
    public TextView registeredCountry;
    @BindView(R.id.registered_phone)
    public EditText registeredPhone;
    @BindView(R.id.registered_code)
    public EditText registeredCode;
    @BindView(R.id.registered_invite_code)
    public EditText registeredInviteCode;
    @BindView(R.id.registered_text2)
    TextView registeredText2;
    @BindView(R.id.registered_relative)
    RelativeLayout registeredRelative;
    Unbinder unbinder;
    @BindView(R.id.registered_nextstep)
    TextView registeredNextstep;
    @BindView(R.id.getYZM)
    CountRegisterButton getYZMButton;
    private RegisterPresenter presenter ;

    private RegisteredActivity activity;
    OnBackPressedListener listener ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (RegisteredActivity) getActivity();
        presenter = new RegisterPresenter();
        presenter.attatchView(this);
        UserRegisterData.getInstance().countryCode = "+86|";
        EventBus.getDefault().register(this);

        this.listener = (OnBackPressedListener) getActivity();
        listener.setSelectedFragment(this);
    }

    @Override
    public int setLayout() {
        return R.layout.registeredstep1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        rootView.setClickable(true);
        unbinder = ButterKnife.bind(this, rootView);
        getYZMButton.setClickListener(this);
        return rootView;
    }

    @Override
    public void onBackPressed() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.registered_country, R.id.registered_text2, R.id.registered_nextstep})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.registered_country:
                //
                JumpUtils.JumpActivity(getActivity(), CountryCodeSelectListActivity.class);
                break;
            case R.id.registered_text2:
                break;
            case R.id.registered_nextstep:
                gotoNextStep();
                break;
        }
    }

    private void gotoNextStep() {

        if(TextUtils.isEmpty(registeredPhone.getText().toString().trim()) ){
            showToast(getResources().getString(R.string.error_phonenumber));
            return ;
        }
        if(TextUtils.isEmpty(registeredCode.getText().toString().trim())){
            ToastUtils.showToastCentre(R.string.yzm_not_blank);
            return ;
        }

        presenter.saveTempUserData();
        getYZMButton.clear();
        activity.setNextStep();
    }


    public void showToast(String msg){
        super.showToast(msg);
    }

    @Override
    public void onButtonClick() {
        presenter.getYZM();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserExisted(RegisterUserExsistedEvent event){
        getYZMButton.startAlarm();
        presenter.startGetYZM(registeredPhone.getText().toString().trim());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCountryCodeSelected(RegisterCountryCodeEvent event){
        registeredCountry.setText(event.getName()+event.getCode()+"|");
        UserRegisterData.getInstance().countryCode =event.getCode()+"|";
    }

}
