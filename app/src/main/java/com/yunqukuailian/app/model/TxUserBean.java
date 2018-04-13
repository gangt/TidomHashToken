package com.yunqukuailian.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Tidom on 2018/3/21/021.
 */

public class TxUserBean{

    private String title ;

    private String price ;

    private  int coinId ;

    private double feeRate ;

    private static TxUserBean instance ;

    private String timeStamp ;

    private List<TBWalletAddress.AddressBean> addressList ;

    private String sxf ;

    private String type ;

    private String txNum ;

    private String kgf ;

    private String address ;


    public String gettype() {
        return type;
    }

    public TxUserBean settype(String type) {
        this.type = type;
        return this ;
    }

    public String getTxNum() {
        return txNum;
    }

    public TxUserBean setTxNum(String txNum) {
        this.txNum = txNum;
        return this ;
    }

    public String getKgf() {
        return kgf;
    }

    public TxUserBean setKgf(String kgf) {
        this.kgf = kgf;
        return this ;
    }

    public String getAddress() {
        return address;
    }

    public TxUserBean setAddress(String address) {
        this.address = address;
        return this ;
    }

    public String gettimeStamp() {
        return timeStamp;
    }

    public TxUserBean settimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
        return this ;
    }

    public String getSxf() {
        return sxf;
    }

    public TxUserBean setSxf(String sxf) {
        this.sxf = sxf;
        return this ;
    }

    private TxUserBean(){}

    public static TxUserBean getInstance(){
        if(instance ==null){
            instance = new TxUserBean();
        }
        return instance ;
    }


    public TxUserBean setInstance(TxUserBean instance) {
        TxUserBean.instance = instance;
        return this ;
    }

    public List<TBWalletAddress.AddressBean> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<TBWalletAddress.AddressBean> addressList) {
        this.addressList = addressList;
    }

    public String getTitle() {
        return title;
    }

    public TxUserBean setTitle(String title) {
        this.title = title;
        return this ;
    }

    public String getPrice() {
        return price;
    }

    public TxUserBean setPrice(String price) {
        this.price = price;
        return  this ;
    }

    public int getCoinId() {
        return coinId;
    }

    public TxUserBean setCoinId(int coinId) {
        this.coinId = coinId;
        return  this ;
    }

    public double getFeeRate() {
        return feeRate;
    }

    public TxUserBean setFeeRate(double feeRate) {
        this.feeRate = feeRate;
        return  this ;
    }
    public static TxUserBean build(){
        return instance ;
    }

    public static void clear(){
        if(instance !=null){
            instance = null ;
        }
    }

}
