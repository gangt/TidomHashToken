package com.yunqukuailian.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunqukuailian.app.R;
import com.yunqukuailian.app.model.DepthsBean;
import com.yunqukuailian.app.utils.Utils;

import java.util.List;

/**
 * Created by Administrator on 2018/3/16/016.
 */

public class SellDepthsAdapter extends RecyclerView.Adapter <SellDepthsAdapter.MyViewHolder>{
    private List<DepthsBean.DataBean.SellBean> list;
    private BuyDepthsAdapter.OnItemClickLisering onItemClickLisering;
    public void setOnItemClickLisering(BuyDepthsAdapter.OnItemClickLisering onItemClickLisering){
        this.onItemClickLisering = onItemClickLisering;
    }


    public SellDepthsAdapter(List<DepthsBean.DataBean.SellBean> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.simpledepthitem,parent,false));
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.depthitem.getLayoutParams();
        layoutParams.width = RecyclerView.LayoutParams.MATCH_PARENT;
        layoutParams.height = Utils.getScreenHeight()/25;
        holder.depthitem.setLayoutParams(layoutParams);

        holder.buynumber.setText(Utils.subZeroAndDot(list.get(position).getVolum()));
        holder.buyprice.setText(Utils.subZeroAndDot(list.get(position).getPrice()));
        holder.custom.setText("卖"+(position+1));
        holder.depthitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickLisering.setOnItemClickListering(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView buynumber;
        TextView buyprice;
        TextView custom;
        RelativeLayout depthitem;
        public MyViewHolder(View itemView) {
            super(itemView);
            buynumber= itemView.findViewById(R.id.buynumber);
            buyprice = itemView.findViewById(R.id.buyprice);
            custom = itemView.findViewById(R.id.custom);
            depthitem = itemView.findViewById(R.id.depthitem);
        }
    }

    public interface OnItemClickLisering{
        void setOnItemClickListering(int postion);
    }

}
