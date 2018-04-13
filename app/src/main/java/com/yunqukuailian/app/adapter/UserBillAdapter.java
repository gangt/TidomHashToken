package com.yunqukuailian.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunqukuailian.app.R;
import com.yunqukuailian.app.model.UserBillBean;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tidom on 2018/3/22/022.
 */

public class UserBillAdapter extends RecyclerView.Adapter {
    List<UserBillBean> list;
    WeakReference<Context> context;


    public UserBillAdapter(List<UserBillBean> list, WeakReference<Context> context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHold(LayoutInflater.from(context.get()).inflate(R.layout.mainfragment3item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHold extends RecyclerView.ViewHolder {

        public ViewHold(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }
}
