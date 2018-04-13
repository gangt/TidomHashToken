package com.yunqukuailian.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunqukuailian.app.R;
import com.yunqukuailian.app.model.KDeatilHeaderBean;
import com.yunqukuailian.app.model.MainFragment1BeanItem;
import com.yunqukuailian.app.view.kview.KSimpleView;
import com.yunqukuailian.app.view.kview.MarketChartData;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by android on 2018/3/7.
 */

public class MainFragmentAdapter extends BaseExpandableListAdapter {

    private List<MainFragment1BeanItem.DataBean> list;
    public static int postion = 2;
    private Context context;

    private Activity activity;
    private CheckChangeRadioButton checkchangeradiobutton;
    public List<MarketChartData> marketChartDataLists;
    private KDeatilHeaderBean bean;




    public void setCheckChangeRadioButton(CheckChangeRadioButton checkchangeradiobutton){
        this.checkchangeradiobutton = checkchangeradiobutton;
    }

    public MainFragmentAdapter(Context context, List<MainFragment1BeanItem.DataBean> list, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }


    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.mainfragment1itemgroup, parent, false);
        ImageView mainfragmen1image = convertView.findViewById(R.id.mainfragmen1image);
        TextView mainfragment1name = convertView.findViewById(R.id.mainfragment1name);
        TextView mainfragment1sell = convertView.findViewById(R.id.mainfragment1sell);
        TextView dollmoney = convertView.findViewById(R.id.dollmoney);
        TextView renmingbimoney = convertView.findViewById(R.id.renmingbimoney);
        TextView gainstext = convertView.findViewById(R.id.gainstext);
        Glide.with(context)
                .load(list.get(groupPosition).getCoinImg())
                .into(mainfragmen1image);
        mainfragment1name.setText(list.get(groupPosition).getTitle());

        mainfragment1sell.setText("量(24H)"+new DecimalFormat("0.0000").format(list.get(groupPosition).getVolume()));
        dollmoney.setText(list.get(groupPosition).getNewPrice());
        renmingbimoney.setText(list.get(groupPosition).getNewPriceCNY());
        gainstext.setText(new DecimalFormat("0.00").format(list.get(groupPosition).getChange())+"%");
        if(list.get(groupPosition).getChange() >= 0){
            gainstext.setBackgroundResource(R.drawable.redradiobg);
        }else {
            gainstext.setBackgroundResource(R.drawable.greenradiobg);
        }

        return convertView;
    }



    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.mainfragmen1itemchild, parent, false);
        KSimpleView kChartView = convertView.findViewById(R.id.mainfragment1chartview);

        kChartView.showSimpleView();
//        kChartView.setClose();
        kChartView.setUperLatitude(2);
        kChartView.setLongiLatitudeColor(Color.GRAY);
        kChartView.setdrawBorders(false);
        kChartView.setShowLatitude(true);//是否显示横虚线



        TextView mHightPrice= convertView.findViewById(R.id.hight_price);
        TextView mLowPrice= convertView.findViewById(R.id.low_price);
        TextView mVolPrice= convertView.findViewById(R.id.vol_price);
        if(bean!= null&&bean.getData()!= null){
            mHightPrice.setText(String.valueOf(bean.getData().getHigh()));
            mLowPrice.setText(String.valueOf(bean.getData().getLow()));
            mVolPrice.setText(String.valueOf(bean.getData().getVol()));
        }

        final RadioButton timeminute= convertView.findViewById(R.id.timeminute);
        final RadioButton timeminute15= convertView.findViewById(R.id.timeminute15);
        final RadioButton timehour= convertView.findViewById(R.id.timehour);
        final RadioButton timeday= convertView.findViewById(R.id.timeday);
        final RadioButton timeweek= convertView.findViewById(R.id.timeweek);

        RadioGroup radiogroup1 = convertView.findViewById(R.id.radiogroup1);
        if(postion ==0){
            timeminute.setBackgroundResource(R.drawable.lineradbgactivity_bgtyle);
            timeminute15.setBackgroundDrawable(null);
            timehour.setBackgroundDrawable(null);
            timeday.setBackgroundDrawable(null);
            timeweek.setBackgroundDrawable(null);
        }else if(postion ==1){
            timeminute.setBackgroundDrawable(null);
            timeminute15.setBackgroundResource(R.drawable.lineradbgactivity_bgtyle);
            timehour.setBackgroundDrawable(null);
            timeday.setBackgroundDrawable(null);
            timeweek.setBackgroundDrawable(null);
        } else if(postion ==2){
            timehour.setClickable(true);
            timeminute.setBackgroundDrawable(null);
            timeminute15.setBackgroundDrawable(null);
            timehour.setBackgroundResource(R.drawable.lineradbgactivity_bgtyle);
            timeday.setBackgroundDrawable(null);
            timeweek.setBackgroundDrawable(null);
        } else if(postion ==3){
            timeday.setClickable(true);
            timeminute.setBackgroundDrawable(null);
            timeminute15.setBackgroundDrawable(null);
            timehour.setBackgroundDrawable(null);
            timeday.setBackgroundResource(R.drawable.lineradbgactivity_bgtyle);
            timeweek.setBackgroundDrawable(null);
        } else if(postion ==4){
            timeweek.setClickable(true);
            timeminute.setBackgroundDrawable(null);
            timeminute15.setBackgroundDrawable(null);
            timehour.setBackgroundDrawable(null);
            timeday.setBackgroundDrawable(null);
            timeweek.setBackgroundResource(R.drawable.lineradbgactivity_bgtyle);
        }
        radiogroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.timeminute:
                        checkchangeradiobutton.setOnCheckChangeRadioButton("1");
                        postion =0;
                        break;
                    case R.id.timeminute15:
                        checkchangeradiobutton.setOnCheckChangeRadioButton("15");
                        postion =1;
                        break;
                    case R.id.timehour:
                        checkchangeradiobutton.setOnCheckChangeRadioButton("60");
                        postion =2;
                        break;
                    case R.id.timeday:
                        checkchangeradiobutton.setOnCheckChangeRadioButton(String.valueOf(24*60));
                        postion =3;
                        break;
                    case R.id.timeweek:
                        checkchangeradiobutton.setOnCheckChangeRadioButton(String.valueOf(7*24*60));
                        postion =4;
                        break;


                }
            }
        });


        kChartView.setOHLCData(marketChartDataLists);
        kChartView.postInvalidate();



        return convertView;
    }


    public void setKData(List<MarketChartData> marketChartDataLists){
        //更新图表
        this.marketChartDataLists = marketChartDataLists;
        notifyDataSetInvalidated();

    }

    public void setHeader(KDeatilHeaderBean bean){
        this.bean = bean;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public interface CheckChangeRadioButton{
        void setOnCheckChangeRadioButton(String marketId);
    }

}
