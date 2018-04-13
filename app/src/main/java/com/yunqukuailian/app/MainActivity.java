package com.yunqukuailian.app;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.yunqukuailian.app.base.BaseActivity;
import com.yunqukuailian.app.base.BaseFragment;
import com.yunqukuailian.app.fragment.MainFragment1;
import com.yunqukuailian.app.fragment.MainFragment2;
import com.yunqukuailian.app.fragment.MainFragment3;
import com.yunqukuailian.app.fragment.MainFragment4;
import com.yunqukuailian.app.fragment.PopupWindowDialogFragment;
import com.yunqukuailian.app.http.AbsAPICallback;
import com.yunqukuailian.app.http.LocalService;
import com.yunqukuailian.app.model.MainFragment1Bean;
import com.yunqukuailian.app.model.ModeView.MessgeDialog;
import com.yunqukuailian.app.model.OnBackPressedListener;
import com.yunqukuailian.app.model.TxUserBean;
import com.yunqukuailian.app.update.UpdateVersionController;
import com.yunqukuailian.app.utils.DisplayUtil;
import com.yunqukuailian.app.utils.JumpUtils;
import com.yunqukuailian.app.utils.LoadingUtils;
import com.yunqukuailian.app.utils.NetWorkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements DialogInterface.OnDismissListener, OnBackPressedListener{
    @BindView(R.id.fragmentmain)
    FrameLayout fragmentmain;
    @BindView(R.id.radio1)
    RadioButton radio1;
    @BindView(R.id.radio2)
    RadioButton radio2;
    @BindView(R.id.radio3)
    RadioButton radio3;
    @BindView(R.id.radio4)
    RadioButton radio4;
    @BindView(R.id.trans)
    public View trans;
    @BindView(R.id.mainradiogroup)
    RadioGroup mainradiogroup;
    @BindView(R.id.mainfragment4title)
    public TextView mainfragment4title;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;



    private MainFragment1 mainfragment1;
    private MainFragment2 mainfragment2;
    private MainFragment3 mainfragment3;
    private MainFragment4 mainfragment4;
    private FragmentTransaction transaction;
    private static int selection = 0;
    private boolean isQuit = false;
    private UpdateVersionController controller = null;
    private PopupWindowDialogFragment poPupWindowFragment;
    List<MainFragment1Bean.DataBean> listbean = new ArrayList<>();

    public List<String > titleList = new ArrayList<>();
    private Bundle bundle;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.setTitleBar(this,toolbar);
    }

    @Override
    protected int setLayoutId() {
        titleList.add("行情");
        titleList.add("交易");
        titleList.add("我的资产");
        titleList.add("我的信息");
        bundle = new Bundle();

        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        setSelect(0);
        setTab(0);
        transaction = getSupportFragmentManager().beginTransaction();  //Activity中
        mainfragment1 = new MainFragment1();
        mainfragment4title.setText(titleList.get(0));
        mainfragment4title.setClickable(false);
    }


    @Override
    protected void initData() {
        super.initData();

        LoadingUtils.showFullProgress(this);
        LocalService.getApi().getMainFragment1Type()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<MainFragment1Bean>() {
                    @Override
                    protected void onDone(MainFragment1Bean bean) {
                        //请求成功，做相应的页面操作
                        List<MainFragment1Bean.DataBean> listbean = bean.getData();
                        MainActivity.this.listbean = listbean;
                        bundle.putParcelableArrayList(JumpUtils.FIRSTTAG, (ArrayList<MainFragment1Bean.DataBean>) listbean);
                        mainfragment1.setArguments(bundle);
                        transaction.add(R.id.fragmentmain, mainfragment1);
                        setSelect(selection);
                        transaction.show(mainfragment1);
                        transaction.commit();
                        trans.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        dialog.showDialg(MainActivity.this,"网络错误");

                    }
                });
    }

    @OnClick(R.id.mainfragment4title)
    public void onViewClicked() {
        trans.setVisibility(View.VISIBLE);
        Drawable drawable1 = getResources().getDrawable(R.drawable.mainfragmenttitleup);
        drawable1.setBounds(0, 0, DisplayUtil.dip2px(this, 15), DisplayUtil.dip2px(this, 10));
        mainfragment4title.setCompoundDrawables(null, null, drawable1, null);
        poPupWindowFragment = new PopupWindowDialogFragment();
        poPupWindowFragment.setData(listbean);

        poPupWindowFragment.show(this.getSupportFragmentManager(), "popup");
    }

    @OnClick({R.id.radio1, R.id.radio2, R.id.radio3, R.id.radio4})
    public void onViewClicked(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.radio1:
                if (mainfragment1 == null) {
                    mainfragment1 = new MainFragment1();
                    mainfragment1.setArguments(bundle);
                    transaction.add(R.id.fragmentmain, mainfragment1);
                }
                transaction.show(mainfragment1);
                if (mainfragment2 != null) {
                    transaction.hide(mainfragment2);
                }

                if (mainfragment3 != null) {
                    transaction.hide(mainfragment3);
                }

                if (mainfragment4 != null) {
                    transaction.hide(mainfragment4);
                }

                transaction.commit();
                setSelect(0);
                selection = 0;
                mainfragment4title.setClickable(false);
                break;
            case R.id.radio2:
                if (mainfragment2 == null) {
                    mainfragment2 = new MainFragment2();
                    if (bundle != null) {
                        mainfragment2.setArguments(bundle);
                    }
                    transaction.add(R.id.fragmentmain, mainfragment2);
                }
                transaction.show(mainfragment2);
                if (mainfragment1 != null) {
                    transaction.hide(mainfragment1);
                }

                if (mainfragment3 != null) {
                    transaction.hide(mainfragment3);
                }

                if (mainfragment4 != null) {
                    transaction.hide(mainfragment4);
                }
                transaction.commit();
                setSelect(1);
                selection = 1;
                mainfragment4title.setClickable(true);
                break;
            case R.id.radio3:
                if (mainfragment3 == null) {
                    mainfragment3 = new MainFragment3();
                    mainfragment3.setArguments(bundle);
                    transaction.add(R.id.fragmentmain, mainfragment3);
                }
                transaction.show(mainfragment3);
                if (mainfragment1 != null) {
                    transaction.hide(mainfragment1);
                }

                if (mainfragment2 != null) {
                    transaction.hide(mainfragment2);
                }

                if (mainfragment4 != null) {
                    transaction.hide(mainfragment4);
                }
                transaction.commit();
                setSelect(2);
                selection = 2;
                mainfragment4title.setClickable(false);
                break;
            case R.id.radio4:
                if (mainfragment4 == null) {
                    mainfragment4 = new MainFragment4();
                    mainfragment1.setArguments(bundle);
                    transaction.add(R.id.fragmentmain, mainfragment4);
                }
                transaction.show(mainfragment4);

                if (mainfragment1 != null) {
                    transaction.hide(mainfragment1);
                }

                if (mainfragment2 != null) {
                    transaction.hide(mainfragment2);
                }

                if (mainfragment3 != null) {
                    transaction.hide(mainfragment3);
                }
                transaction.commit();
                setSelect(3);
                selection = 3;
                mainfragment4title.setClickable(false);
                break;
        }
    }



    public void setSelect(int i) {
        mainfragment4title.setText(titleList.get(i));
        if(i ==1){
            Drawable drawable1 = getResources().getDrawable(R.drawable.mainfragmentitledown);
            drawable1.setBounds(0, 0, DisplayUtil.dip2px(this, 15), DisplayUtil.dip2px(this, 10));
            mainfragment4title.setCompoundDrawables(null, null, drawable1, null);
        }else {
            mainfragment4title.setCompoundDrawables(null, null, null, null);
        }
        setTab(i);
    }

    private void setTab(int i) {
        resetTab();
        switch (i) {
            case 0:
                Drawable drawable1 = getResources().getDrawable(R.drawable.main_market_select);
                drawable1.setBounds(0, 0, DisplayUtil.dip2px(this, 18), DisplayUtil.dip2px(this, 18));
                radio1.setCompoundDrawables(null, drawable1, null, null);
                radio1.setTextColor(Color.parseColor("#E70101"));
                break;
            case 1:

                Drawable drawable2 = getResources().getDrawable(R.drawable.main_trading_select);
                drawable2.setBounds(0, 0, DisplayUtil.dip2px(this, 18), DisplayUtil.dip2px(this, 18));
                radio2.setCompoundDrawables(null, drawable2, null, null);
                radio2.setTextColor(Color.parseColor("#E70101"));
                break;
            case 2:
                Drawable drawable3 = getResources().getDrawable(R.drawable.main_financial_select);
                drawable3.setBounds(0, 0, DisplayUtil.dip2px(this, 18), DisplayUtil.dip2px(this, 18));
                radio3.setCompoundDrawables(null, drawable3, null, null);
                radio3.setTextColor(Color.parseColor("#E70101"));
                break;
            case 3:
                Drawable drawable4 = getResources().getDrawable(R.drawable.main_icon_select);
                drawable4.setBounds(0, 0, DisplayUtil.dip2px(this, 18), DisplayUtil.dip2px(this, 18));
                radio4.setCompoundDrawables(null, drawable4, null, null);
                radio4.setTextColor(Color.parseColor("#E70101"));
                break;
        }


    }


    protected void resetTab() {
        if (radio1 != null) {
            Drawable drawable1 = getResources().getDrawable(R.drawable.main_market);
            drawable1.setBounds(0, 0, DisplayUtil.dip2px(this, 18), DisplayUtil.dip2px(this, 18));
            radio1.setCompoundDrawables(null, drawable1, null, null);

            Drawable drawable2 = getResources().getDrawable(R.drawable.main_trading);
            drawable2.setBounds(0, 0, DisplayUtil.dip2px(this, 18), DisplayUtil.dip2px(this, 18));
            radio2.setCompoundDrawables(null, drawable2, null, null);

            Drawable drawable3 = getResources().getDrawable(R.drawable.main_financial);
            drawable3.setBounds(0, 0, DisplayUtil.dip2px(this, 18), DisplayUtil.dip2px(this, 18));
            radio3.setCompoundDrawables(null, drawable3, null, null);

            Drawable drawable4 = getResources().getDrawable(R.drawable.main_icon);
            drawable4.setBounds(0, 0, DisplayUtil.dip2px(this, 18), DisplayUtil.dip2px(this, 18));
            radio4.setCompoundDrawables(null, drawable4, null, null);

            radio1.setTextColor(Color.parseColor("#797979"));
            radio2.setTextColor(Color.parseColor("#797979"));
            radio3.setTextColor(Color.parseColor("#797979"));
            radio4.setTextColor(Color.parseColor("#797979"));
        }

    }

    @Override
    public void onBackPressed() {

        if (mBackHandedFragment != null && getSupportFragmentManager().getBackStackEntryCount() > 0) {
            mBackHandedFragment.onBackPressed();
            return;
        }

        if (!isQuit) {
            showToast("再按一次退出程序");
            isQuit = true;

            //这段代码意思是,在两秒钟之后isQuit会变成false
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        isQuit = false;
                    }
                }
            }).start();


        } else {
            System.exit(0);
        }


    }

    public void update() {
        //现获取sdcard的读写权限
        checkPer();

        //下载
//        if (false) {
//           //强更
//            controller.forceCheckUpdateInfo(Integer.parseInt(remarkBean.getMap().getList().get(0).getFnum()), remarkBean.getMap().getList().get(0).getFdesc(),  remarkBean.getMap().getList().get(0).getFurl());
//        } else {
//            if (!TextUtils.isEmpty(remarkBean.getMap().getList().get(0).getFnum()) && Integer.parseInt(remarkBean.getMap().getList().get(0).getFnum()) > UpdateVersionController.getVerCode(MainActivity.this)) {
//                //非强更
//                controller.normalCheckUpdateInfo(Integer.parseInt(remarkBean.getMap().getList().get(0).getFnum()), remarkBean.getMap().getList().get(0).getFdesc(),  remarkBean.getMap().getList().get(0).getFurl());
//            }
//        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        trans.setVisibility(View.GONE);
        Drawable drawable1 = getResources().getDrawable(R.drawable.mainfragmentitledown);
        drawable1.setBounds(0, 0, DisplayUtil.dip2px(this, 15), DisplayUtil.dip2px(this, 10));
        mainfragment4title.setCompoundDrawables(null, null, drawable1, null);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
//        SophixManager.getInstance().queryAndLoadNewPatch();
        ButterKnife.bind(this);
    }

    public void showRadioGroup(boolean isshow) {
        mainradiogroup.setVisibility(isshow ? View.VISIBLE : View.GONE);
    }

    private BaseFragment mBackHandedFragment;

    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {
        this.mBackHandedFragment = selectedFragment;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TxUserBean.getInstance().clear();
    }
}
