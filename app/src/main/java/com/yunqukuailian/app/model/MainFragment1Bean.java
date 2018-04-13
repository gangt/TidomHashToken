package com.yunqukuailian.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/3/13/013.
 */

public class MainFragment1Bean implements Parcelable{

    /**
     * code : 1
     * message : SUCCESS
     * data : [{"id":1,"code":"MAIN","name":"主交易区","status":1,"createTime":null,"updateTime":null},{"id":2,"code":"HHT","name":"HHT交易区","status":1,"createTime":null,"updateTime":null},{"id":3,"code":"INNOVATION","name":"创新区","status":1,"createTime":null,"updateTime":null}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

    protected MainFragment1Bean(Parcel in) {
        code = in.readInt();
        message = in.readString();
        data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Creator<MainFragment1Bean> CREATOR = new Creator<MainFragment1Bean>() {
        @Override
        public MainFragment1Bean createFromParcel(Parcel in) {
            return new MainFragment1Bean(in);
        }

        @Override
        public MainFragment1Bean[] newArray(int size) {
            return new MainFragment1Bean[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(message);
        dest.writeTypedList(data);
    }

    public static class DataBean implements Parcelable{
        /**
         * id : 1
         * code : MAIN
         * name : 主交易区
         * status : 1
         * createTime : null
         * updateTime : null
         */

        private int id;
        private String code;
        private String name;
        private double status;
        private String createTime;
        private String updateTime;


        protected DataBean(Parcel in) {
            id = in.readInt();
            code = in.readString();
            name = in.readString();
            status = in.readDouble();
            createTime = in.readString();
            updateTime = in.readString();
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(code);
            dest.writeString(name);
            dest.writeDouble(status);
            dest.writeString(createTime);
            dest.writeString(updateTime);
        }
    }
}
