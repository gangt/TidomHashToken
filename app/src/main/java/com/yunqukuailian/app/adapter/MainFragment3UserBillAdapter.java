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
import com.yunqukuailian.app.activity.AccountBillDetailActivity;
import com.yunqukuailian.app.model.MainFragment2CurrentBean;
import com.yunqukuailian.app.model.TxUserBean;
import com.yunqukuailian.app.model.UserBillBean;
import com.yunqukuailian.app.utils.JumpUtils;
import com.yunqukuailian.app.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 *
 */
public class MainFragment3UserBillAdapter extends RecyclerView.Adapter<MainFragment3UserBillAdapter.MyViewHolder> {
    private List<UserBillBean.UserBillBeanItem> listbean;
    private Context context;

    public MainFragment3UserBillAdapter(List<UserBillBean.UserBillBeanItem> listbean, Context context) {
        this.listbean = listbean;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_userbill, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final UserBillBean.UserBillBeanItem beanItem = listbean.get(position);

        holder.user_ll1_day.setText(getDay(beanItem.getTime()));
        holder.user_ll1_time.setText(getTime(beanItem.getTime()));
        holder.user_bill_money.setText(beanItem.getAmount()+"");
        holder.user_bill_coinId.setText(TxUserBean.getInstance().getTitle().toUpperCase());
        holder.user_bii_type.setText(getType(beanItem.getType()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TxUserBean.getInstance().setSxf(beanItem.getAmount()+"").settimeStamp(beanItem.getTime()).settype(getType(beanItem.getType())) ;
                JumpUtils.JumpActivity(context, AccountBillDetailActivity.class);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listbean.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView user_ll1_day;
        TextView user_ll1_time;
        TextView user_bill_money;
        TextView user_bill_coinId;
        TextView user_bii_type;

        public MyViewHolder(View itemView) {
            super(itemView);
            user_ll1_day = itemView.findViewById(R.id.user_ll1_day);
            user_ll1_time = itemView.findViewById(R.id.user_ll1_time);
            user_bill_money = itemView.findViewById(R.id.user_bill_money);
            user_bill_coinId = itemView.findViewById(R.id.user_bill_coinId);
            user_bii_type = itemView.findViewById(R.id.user_bii_type);

        }
    }


    public String getDay(String timeStamp) {
        //"yyyy-MM-dd");

        return Utils.msToDay(Long.parseLong(timeStamp)).substring(5);
    }


    public String getTime(String timeStamp) {
        //"HH-mm-ss");

        return Utils.msToVehicle(Long.parseLong(timeStamp)).substring(0, 5).replace("-",":");
    }

    public String getType(int type) {
        String typeText = null  ;

        switch (type) {
            case 1:
                typeText = "充值" ;
                break;
            case 2:
                typeText = "提币" ;
                break;
            case 3:
                typeText = "手续费" ;
                break;
            case 4:
                typeText = "买入" ;
                break;
            case 5:
                typeText = "卖出" ;
                break;


        }
        return typeText;
    }

}
