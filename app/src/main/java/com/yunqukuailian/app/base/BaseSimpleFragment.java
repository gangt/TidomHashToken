package com.yunqukuailian.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.yunqukuailian.app.model.ModeView.MessgeDialog;

/**
 * Created by Administrator on 2018/3/31 0031.
 */

public class BaseSimpleFragment extends Fragment {
    protected MessgeDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new MessgeDialog();
    }
}
