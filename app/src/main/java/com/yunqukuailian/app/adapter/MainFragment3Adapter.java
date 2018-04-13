package com.yunqukuailian.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunqukuailian.app.MyApplication;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.model.MainFragment3Bean;
import com.yunqukuailian.app.model.TxUserBean;
import com.yunqukuailian.app.model.UserAccout;
import com.yunqukuailian.app.presenter.UserAccountPresenter;
import com.yunqukuailian.app.utils.ToastUtils;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Administrator on 2018/3/10/010.
 */

public class MainFragment3Adapter extends RecyclerView.Adapter {

    public enum Item_Type {
        RECYCLEVIEW_ITEM_TYPE_1,
    }

    private List<MainFragment3Bean> list;
    private WeakReference<Context> context;
    private UserAccountPresenter presenter;
    public boolean isDollar = false;
    private String title;

    public MainFragment3Adapter(List<MainFragment3Bean> list, WeakReference<Context> context, UserAccountPresenter presenter) {
        this.list = list;
        this.context = context;
        this.presenter = presenter;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == Item_Type.RECYCLEVIEW_ITEM_TYPE_1.ordinal()) {
            return new ViewFirstHold(LayoutInflater.from(context.get()).inflate(R.layout.fragment3_first_item, parent, false));
        } else {
            return new ViewHold(LayoutInflater.from(context.get()).inflate(R.layout.mainfragment3item, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (list.size() == 0) {
            return;
        }

        if (holder instanceof ViewHold) {
            ViewHold holder1 = (ViewHold) holder;
            holder1.mainfragment3Itemtitle.setText(list.get(position - 1).getTitle().toUpperCase());
            holder1.mainfragment3Itemprice.setText(list.get(position - 1).getPrice() + "");
            holder1.mainfragment3Itemfreeze.setText("冻结: " + list.get(position - 1).getFreeze());
            holder1.mainfragment3Topup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (list.get(position - 1).getTitle().equals("cny")) {
                        ToastUtils.showToastCentre(R.string.account_cny_cz);
                        return;
                    }

                    getUserTxBean(position - 1);
                    presenter.gotoUserAccountCz();
                }
            });

            holder1.mainfragment3Withdrawal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.get(position - 1).getTitle().equals("cny")) {
                        ToastUtils.showToastCentre(R.string.account_cny_tb);
                        return;
                    }
                    getUserTxBean(position - 1);
                    presenter.gotoUserAccountTb();
                }
            });
            holder1.mainfragment3History.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getUserTxBean(position - 1);
                    presenter.gotoUserBillActivity();
                }
            });
        } else {
            //第一行item = 0
            final ViewFirstHold holder2 = (ViewFirstHold) holder;
            exChangeMoney(isDollar, holder2);
            holder2.mianfragment3login_change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    exChangeMoney(isDollar, holder2);
                }
            });

        }

    }

    /**
     * @param isDollar true ：￥-->$    false： $-->￥
     */
    public void exChangeMoney(boolean isDollar, ViewFirstHold holder2) {
        this.isDollar = !isDollar;
        if (isDollar) {
            holder2.mianfragment3login_money.setText(UserAccout.getDollarAccount());
            holder2.mianfragment3login_text1.setText(MyApplication.getContext().getString(R.string.dollar_unit));
        } else {
            holder2.mianfragment3login_money.setText(UserAccout.getRmbAccount());
            holder2.mianfragment3login_text1.setText(MyApplication.getContext().getString(R.string.rmb_unit));
        }
    }


    public TxUserBean getUserTxBean(int position) {
        return TxUserBean.getInstance().setCoinId(list.get(position).getCoinId())
                .setFeeRate(list.get(position).getFeeRate())
                .setPrice(list.get(position).getPrice())
                .setTitle(list.get(position).getTitle().toUpperCase())
                .build();

//       return new TxUserBean(list.get(position).getTitle().toUpperCase(), list.get(position).getPrice(),list.get(position).getCoinId(), 0);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    //返回值赋值给onCreateViewHolder的参数 viewType
    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return Item_Type.RECYCLEVIEW_ITEM_TYPE_1.ordinal();
        }
        return -1;
    }


    public class ViewFirstHold extends RecyclerView.ViewHolder {
        public TextView mianfragment3login_money; //Y 1200
        public TextView mianfragment3login_text1; //总资产折合
        public ImageView mianfragment3login_change;


        public ViewFirstHold(View itemView) {
            super(itemView);
            mianfragment3login_money = itemView.findViewById(R.id.mianfragment3login_money);
            mianfragment3login_text1 = itemView.findViewById(R.id.mianfragment3login_text1);
            mianfragment3login_change = itemView.findViewById(R.id.mianfragment3login_change);
        }
    }


    class ViewHold extends RecyclerView.ViewHolder {
        TextView mainfragment3Itemtitle;
        TextView mainfragment3Itemprice;
        TextView mainfragment3Itemfreeze;

        TextView mainfragment3Topup;
        TextView mainfragment3Withdrawal;
        TextView mainfragment3History;

        public ViewHold(View itemView) {
            super(itemView);
            mainfragment3Itemtitle = itemView.findViewById(R.id.mainfragment3_itemtitle);
            mainfragment3Itemprice = itemView.findViewById(R.id.mainfragment3_itemprice);
            mainfragment3Itemfreeze = itemView.findViewById(R.id.mainfragment3_itemfreeze);

            mainfragment3Topup = itemView.findViewById(R.id.mainfragment3_topup);
            mainfragment3Withdrawal = itemView.findViewById(R.id.mainfragment3_withdrawal);
            mainfragment3History = itemView.findViewById(R.id.mainfragment3_history);
        }

    }


    public void setTitle(String title) {
        this.title = title;
    }

}
