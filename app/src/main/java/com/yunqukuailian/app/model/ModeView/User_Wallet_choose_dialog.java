package com.yunqukuailian.app.model.ModeView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yunqukuailian.app.R;
import com.yunqukuailian.app.model.TBWalletAddress;
import com.yunqukuailian.app.model.TxUserBean;
import com.yunqukuailian.app.model.WalletAddressListView;
import com.yunqukuailian.app.presenter.UserTBPresenter;
import com.yunqukuailian.app.utils.ListViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tidom on 2018/3/24/024.
 */

public class User_Wallet_choose_dialog {
    ListView walletAddressLv;
    TextView user_tb_cancel;
    private Dialog cardDLG;
    UserTBPresenter presenter;

    private Context context ;

    public void showDialog(Context context, UserTBPresenter presenter) {
        this.context =context ;
        this.presenter = presenter;
        cardDLG = new AlertDialog.Builder(context).create();
        cardDLG.show();

        Window window = cardDLG.getWindow();
        LayoutInflater layout = LayoutInflater.from(context);
        View view = layout.inflate(R.layout.layout_address_choose_dialog, null);
        initView(view);
        window.setContentView(view);
        initListView();
        initlisenter(view);
        cardDLG.getWindow().setBackgroundDrawable(new BitmapDrawable());

        cardDLG.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        cardDLG.getWindow().setWindowAnimations(R.style.AnimBottom);
        WindowManager.LayoutParams wlp = cardDLG.getWindow().getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        cardDLG.setCanceledOnTouchOutside(true);
        cardDLG.getWindow().setAttributes(wlp);

    }

    private void initView(View view) {
        walletAddressLv = view.findViewById(R.id.wallet_address_lv);
        user_tb_cancel = view.findViewById(R.id.user_tb_cancel);
    }

    WalletAddressAdapter adapter ;
    private void initListView() {
        List<TBWalletAddress.AddressBean> addressList = TxUserBean.getInstance().getAddressList();
        ArrayList<String> list = new ArrayList<>();
        for (TBWalletAddress.AddressBean item: addressList) {
           list.add(item.getAddress());
        }
        adapter= new WalletAddressAdapter(list);
        walletAddressLv.setAdapter(adapter);

    }

    private void initlisenter(View view) {
        walletAddressLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                presenter.showWalletAddress(adapter.getItem(position));
                cardDLG.cancel();
            }
        });
        user_tb_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardDLG.cancel();
            }
        });

    }


    class WalletAddressAdapter extends BaseAdapter{

        List<String> list ;
       public  WalletAddressAdapter(List<String> list){
            this.list = list ;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
           if(convertView == null){
               holder = new ViewHolder();
               convertView = View.inflate(context, R.layout.layout_address_item, null);
               holder.textView = convertView.findViewById(R.id.address);
               convertView.setTag(holder);
           }else{
               holder = (ViewHolder) convertView.getTag();
           }
            holder.textView.setText(list.get(position));
            return convertView;
        }


        class ViewHolder {

            TextView textView ;
        }
    }
}
