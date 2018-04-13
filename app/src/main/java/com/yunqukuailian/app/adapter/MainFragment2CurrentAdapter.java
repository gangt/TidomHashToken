package com.yunqukuailian.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunqukuailian.app.R;
import com.yunqukuailian.app.model.MainFragment2CurrentBean;
import com.yunqukuailian.app.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Administrator on 2018/3/19/019.
 */

public class MainFragment2CurrentAdapter extends RecyclerView.Adapter<MainFragment2CurrentAdapter.MyViewHolder> {
    private List<MainFragment2CurrentBean.DataBean> listbean;
    private WeakReference<Context> context;
    private DeletClick onDeletClick;

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOnDeletClick(DeletClick deletClick){
        this.onDeletClick = deletClick;
    }

    public MainFragment2CurrentAdapter(List<MainFragment2CurrentBean.DataBean> listbean,WeakReference<Context> context) {
        this.listbean = listbean;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context.get()).inflate(R.layout.mainfragment2currentfragmentitem,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.deletesell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeletClick.setOnDeleteClick(position);
            }
        });
        if(listbean.get(position).getType() ==1){
            holder.namemoney.setTextColor(Color.parseColor("#E01324"));
            holder.entrustedprice.setTextColor(Color.parseColor("#E01324"));
            holder.selltextchange.setTextColor(Color.parseColor("#E01324"));
            holder.selltype.setText("买");
            holder.selltype.setBackgroundResource(R.drawable.circlered);
            holder.clinchdealvalence.setTextColor(Color.parseColor("#E01324"));
        }else {
            holder.namemoney.setTextColor(Color.parseColor("#36B58C"));
            holder.entrustedprice.setTextColor(Color.parseColor("#36B58C"));
            holder.selltextchange.setTextColor(Color.parseColor("#36B58C"));
            holder.clinchdealvalence.setTextColor(Color.parseColor("#36B58C"));
            holder.selltype.setText("卖");
            holder.selltype.setBackgroundResource(R.drawable.circlegreen);
        }
        holder.namemoney.setText(title.split("/")[0]+" "+ Utils.formatNumber(listbean.get(position).getAmount()));//名称
        holder.entrustedprice.setText(title.split("/")[1]+" "+Utils.formatNumber(listbean.get(position).getPrice()));//委托价
        holder.clinchdealvalence.setText(title.split("/")[1]+" "+Utils.formatNumber(listbean.get(position).getAvgPrice()));//成交价
        holder.selltextchange.setText(title.split("/")[0]+" "+Utils.formatNumber(listbean.get(position).getAvgPrice())+" = "+title.split("/")[1]+" "+Utils.formatNumber(listbean.get(position).getAvgPrice()));
    }

    @Override
    public int getItemCount() {
        return listbean.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView namemoney;
        TextView selltextchange;
        TextView selltype;
        TextView entrustedprice;
        TextView clinchdealvalence;
        ImageView deletesell;
        public MyViewHolder(View itemView) {
            super(itemView);
            namemoney= itemView.findViewById(R.id.namemoney);
            selltextchange= itemView.findViewById(R.id.selltextchange);
            selltype= itemView.findViewById(R.id.selltype);
            entrustedprice= itemView.findViewById(R.id.entrustedprice);
            clinchdealvalence= itemView.findViewById(R.id.clinchdealvalence);
            deletesell= itemView.findViewById(R.id.deletesell);
        }
    }


    public interface  DeletClick{
        void setOnDeleteClick(int postion);
    }
}
