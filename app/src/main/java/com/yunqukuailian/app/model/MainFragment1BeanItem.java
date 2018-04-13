package com.yunqukuailian.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by android on 2018/3/7.
 */

public class MainFragment1BeanItem {

    /**
     * code : 1
     * message : SUCCESS
     * data : [{"marketId":43,"xnb":"OEC","rmb":"btcx","title":"OEC/BTCX","name":"OEC_BTCX","round":4,"numRound":4,"volume":0,"newPrice":0.222,"newPriceCNY":0.222,"change":0,"coinImg":"http://image.hht.one/group1/M00/00/01/rB8tr1okDuGABT_dAABMHL0gXJ4248.png"},{"marketId":52,"xnb":"WC","rmb":"btcx","title":"WC/BTCX","name":"WC_btcx","round":4,"numRound":4,"volume":0,"newPrice":7.002,"newPriceCNY":7.002,"change":0,"coinImg":"http://image.hht.one/group1/M00/00/00/rB8t01pY3xmAcYqUAABYlARSrMc777.png"},{"marketId":41,"xnb":"BDS","rmb":"btcx","title":"BDS/BTCX","name":"BDS_BTCX","round":4,"numRound":4,"volume":0,"newPrice":0.24,"newPriceCNY":0.24,"change":0,"coinImg":"http://image.hht.one/group1/M00/00/01/rB8tr1odFk2AFsnNAABFHWxzYy085.jpeg"},{"marketId":28,"xnb":"ATM","rmb":"btcx","title":"ATM/BTCX","name":"ATM_BTCX","round":4,"numRound":4,"volume":0,"newPrice":0.1119,"newPriceCNY":0.1119,"change":0,"coinImg":"http://image.hht.one/group1/M00/00/01/rB8tr1n9JTyAXAZMAABONvf5cEU324.png"},{"marketId":30,"xnb":"EOS","rmb":"btcx","title":"EOS/BTCX","name":"EOS_BTCX","round":4,"numRound":4,"volume":0,"newPrice":75.12,"newPriceCNY":75.12,"change":0,"coinImg":"http://image.hht.one/group1/M00/00/01/rB8tr1n-BAiARMEJAAAH9S9x1t8858.png"},{"marketId":55,"xnb":"EPS","rmb":"btcx","title":"EPS/BTCX","name":"EPS_btcx","round":4,"numRound":4,"volume":0,"newPrice":0.24,"newPriceCNY":0.24,"change":0,"coinImg":"http://image.hht.one/group1/M00/00/00/rB8t01pmKeCAbOjIAAA-Fmg8cjI24.jpeg"},{"marketId":56,"xnb":"ALA","rmb":"btcx","title":"ALA/BTCX","name":"ALA_btcx","round":4,"numRound":4,"volume":0,"newPrice":10.5,"newPriceCNY":10.5,"change":0,"coinImg":"http://image.hht.one/group1/M00/00/00/rB8t01pt34OAVaEdAAA566cFwEs209.png"},{"marketId":53,"xnb":"EIF","rmb":"btcx","title":"EIF/BTCX","name":"EIF_btcx","round":4,"numRound":4,"volume":0,"newPrice":9,"newPriceCNY":9,"change":0,"coinImg":"http://image.hht.one/group1/M00/00/00/rB8t01pbO3KABXmrAAkPhJSdEoA337.png"},{"marketId":54,"xnb":"EGT","rmb":"btcx","title":"EGT/BTCX","name":"EGT_btcx","round":4,"numRound":4,"volume":0,"newPrice":0.65,"newPriceCNY":0.65,"change":0,"coinImg":"http://image.hht.one/group1/M00/00/00/rB8t01pVxwCAcACdAAASW8hIvf0161.png"},{"marketId":57,"xnb":"ZBC","rmb":"btcx","title":"ZBC/BTCX","name":"ZBC_btcx","round":4,"numRound":4,"volume":0,"newPrice":1.03,"newPriceCNY":1.03,"change":0,"coinImg":"http://image.hht.one/group1/M00/00/00/rB8t01prXZKAXgEZAAEMqqQM5yg53.jpeg"},{"marketId":58,"xnb":"VIP","rmb":"btcx","title":"VIP/BTCX","name":"VIP_btcx","round":4,"numRound":4,"volume":0,"newPrice":0.89,"newPriceCNY":0.89,"change":0,"coinImg":"http://image.hht.one/group1/M00/00/00/rB8t01ptWj6AT7F0AAAUBc68Qjk451.png"}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable{
        /**
         * marketId : 43
         * xnb : OEC
         * rmb : btcx
         * title : OEC/BTCX
         * name : OEC_BTCX
         * round : 4
         * numRound : 4
         * volume : 0
         * newPrice : 0.222
         * newPriceCNY : 0.222
         * change : 0
         * coinImg : http://image.hht.one/group1/M00/00/01/rB8tr1okDuGABT_dAABMHL0gXJ4248.png
         */

        private int marketId;
        private String xnb;
        private String rmb;
        private String title;
        private String name;
        private float round;
        private float numRound;
        private float volume;
        private String newPrice;
        private String newPriceCNY;
        private double change;
        private String coinImg;


        protected DataBean(Parcel in) {
            marketId = in.readInt();
            xnb = in.readString();
            rmb = in.readString();
            title = in.readString();
            name = in.readString();
            round = in.readFloat();
            numRound = in.readFloat();
            volume = in.readFloat();
            newPrice = in.readString();
            newPriceCNY = in.readString();
            change = in.readDouble();
            coinImg = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(marketId);
            dest.writeString(xnb);
            dest.writeString(rmb);
            dest.writeString(title);
            dest.writeString(name);
            dest.writeFloat(round);
            dest.writeFloat(numRound);
            dest.writeFloat(volume);
            dest.writeString(newPrice);
            dest.writeString(newPriceCNY);
            dest.writeDouble(change);
            dest.writeString(coinImg);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public int getMarketId() {
            return marketId;
        }

        public void setMarketId(int marketId) {
            this.marketId = marketId;
        }

        public String getXnb() {
            return xnb;
        }

        public void setXnb(String xnb) {
            this.xnb = xnb;
        }

        public String getRmb() {
            return rmb;
        }

        public void setRmb(String rmb) {
            this.rmb = rmb;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getRound() {
            return round;
        }

        public void setRound(int round) {
            this.round = round;
        }

        public double getNumRound() {
            return numRound;
        }

        public void setNumRound(int numRound) {
            this.numRound = numRound;
        }

        public double getVolume() {
            return volume;
        }

        public void setVolume(int volume) {
            this.volume = volume;
        }

        public String getNewPrice() {
            return newPrice;
        }

        public void setNewPrice(String newPrice) {
            this.newPrice = newPrice;
        }

        public String getNewPriceCNY() {
            return newPriceCNY;
        }

        public void setNewPriceCNY(String newPriceCNY) {
            this.newPriceCNY = newPriceCNY;
        }

        public double getChange() {
            return change;
        }

        public void setChange(int change) {
            this.change = change;
        }

        public String getCoinImg() {
            return coinImg;
        }

        public void setCoinImg(String coinImg) {
            this.coinImg = coinImg;
        }
    }
}
