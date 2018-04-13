package com.yunqukuailian.app.fragment;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.classic.common.MultipleStatusView;
import com.yunqukuailian.app.MainActivity;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.base.BaseFragment;
import com.yunqukuailian.app.model.EventBusEvent.GetUserWalletAddress;
import com.yunqukuailian.app.model.GlideRoundTransform;
import com.yunqukuailian.app.model.OnBackPressedListener;
import com.yunqukuailian.app.model.TxUserBean;
import com.yunqukuailian.app.presenter.UserAccountPresenter;
import com.yunqukuailian.app.utils.QrCodeUtils;
import com.yunqukuailian.app.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * 充值界面
 */
public class MainFragment3UserAccountFragment extends BaseFragment {

    @BindView(R.id.user_account_title_cancel)
    ImageView userAccountTitleCancel;
    @BindView(R.id.account_title)
    RelativeLayout accountTitle;
    @BindView(R.id.user_account_pay)
    ImageView userAccountPay;
    @BindView(R.id.user_account_pay_copy)
    TextView userAccountPayCopy; //收款地址
    @BindView(R.id.user_account_address)
    TextView userAccountAddress; //钱包
    @BindView(R.id.user_cz_title_ll)
    RelativeLayout userCzTitleLl;
    @BindView(R.id.user_wallet_address)
    TextView userWalletAddress;
  /*  @BindView(R.id.multiplestatusview)
    MultipleStatusView multiplestatusview;*/
    private Unbinder unbinder;
    private ClipboardManager mClipboardManager;
    private ClipData mClipData;
    String content = "";
    private UserAccountPresenter presenter;

    private TxUserBean txUserBean;

    private OnBackPressedListener listener;
    MainActivity mainActivity ;

    @Override
    public int setLayout() {
        return R.layout.fragment_main_fragment3_user_account;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mainActivity.toolbar.setNavigationIcon(R.drawable.back_icon);
        mainActivity.mainfragment4title.setText(txUserBean.getTitle() + getString(R.string.toorbar_user_cz));
        mainActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.getSupportFragmentManager().popBackStack();
                mainActivity.setSelect(2);
            }
        });
    }

    public void initTitleShow() {
        // test
        presenter.getUserWalletAddress(txUserBean.getCoinId());

        setUsefulString();
    }

    @Override
    public void onStart() {
        super.onStart();
        listener.setSelectedFragment(this);
    }

    /**
     * 设置对应的标题内容
     */
    public void setUsefulString() {
        String user_cz_title = getString(R.string.user_cz_title).replaceAll("S", txUserBean.getTitle());
        String user_cz_address = getString(R.string.user_cz_address).replaceAll("S", txUserBean.getTitle());
        userAccountAddress.setText(user_cz_title);
        userAccountAddress.setTextColor(getResources().getColor(R.color.red));
        userAccountPayCopy.setText(user_cz_address);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

        EventBus.getDefault().unregister(this);
        MainActivity activity = (MainActivity) getActivity();
        mainActivity.toolbar.setNavigationIcon(null);
        activity.showRadioGroup(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        mainActivity = (MainActivity) getActivity();
        txUserBean = TxUserBean.getInstance();
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        rootView.setClickable(true);
//        presenter = new UserTBPresenter();
        presenter = new UserAccountPresenter();
        presenter.attatchView(this);
        mClipboardManager = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
        EventBus.getDefault().register(this);
        initTitleShow();

        this.listener = (OnBackPressedListener) getActivity();
        return rootView;
    }


    @Override
    public void onBackPressed() {
        getActivity().getSupportFragmentManager().popBackStack();
        mainActivity.setSelect(2);
    }


    @OnClick({R.id.user_account_title_cancel, R.id.user_account_pay_copy, R.id.user_cz_address_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_account_title_cancel:
                userCzTitleLl.setVisibility(View.INVISIBLE);

                break;
            case R.id.user_cz_address_ll:

            case R.id.user_account_pay_copy:

                mClipData = ClipData.newPlainText("Simple test", content);
                //把clip对象放在剪贴板中
                mClipboardManager.setPrimaryClip(mClipData);
                if (TextUtils.isEmpty(content)) {
                    ToastUtils.showToastCentre(R.string.user_cz_empty_address);
                    return;
                }
                ToastUtils.showToastCentre(R.string.copy_success);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetWalletEvent(GetUserWalletAddress address) {
        content = address.getAddress();
        if (content != null) {
            Bitmap bitmap = QrCodeUtils.generateBitmap(address.getAddress(), 600, 600);
//            userAccountPay.setImageBitmap(bitmap);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes=baos.toByteArray();

            Glide.with(getActivity())
                    .load(bytes)
                    .transform(new GlideRoundTransform(getActivity()))
                    .into(userAccountPay);

        }
        userWalletAddress.setText("地址： " + content);
    }


}
