package com.yunqukuailian.app.utils;

import android.content.Context;
import android.view.WindowManager;
import android.widget.Toast;

import com.yunqukuailian.app.MyApplication;
import com.yunqukuailian.app.model.KlineBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/3/14/014.
 */

public class Utils {
    //从json中, 读取属性名为str的数据, 到二维数组Data中
    public static long[][] readTwoDimensionData(JSONObject json, String str) {
        long[][] Data = null;
        try {
            if(json.has(str)) {
                JSONArray Array1 = json.getJSONArray(str);          //获取属性名对应的二维数组
                if(Array1 != null){
                    Data = new long[Array1.length()][];
                    for(int i = 0; i < Array1.length(); i ++) {
                        JSONArray Array2 = Array1.getJSONArray(i);      //获取一维数组

                        Data[i] = new long[Array2.length()];
                        for(int j = 0; j < Array2.length(); j ++)
                            Data[i][j] = Array2.getLong(j);           //获取一维数组中的数据
                    }
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return Data;
    }


    public static long[][] getDoubleArray(KlineBean bean){
        long[][] datas = new long[bean.getStr().size()][];
        for (int i = 0; i < bean.getStr().size(); i++) {
            datas[i] = new long[bean.getStr().get(i).size()];
            for (int j = 0; j < bean.getStr().get(i).size(); j++) {
            datas[i][j] = bean.getStr().get(i).get(j);
            }
        }
        return datas;
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
    public static String numberFormatString(double value){
        String str= null;
        double floatvalue;
        if(Math.abs(value) >100000000){
            value = value/100000000;
            floatvalue = Double.parseDouble(new DecimalFormat("0.0").format(value));
            str = floatvalue+"亿";
        }else if(Math.abs(value) >10000){
            value = value/10000;
            floatvalue = Double.parseDouble(new DecimalFormat("0.0").format(value));
            str = floatvalue+"万";
        }else {
            floatvalue = Float.parseFloat(new DecimalFormat("0.0").format(value));
            str = String.valueOf(floatvalue);
        }
        return str;
    }


    public static String getAccount(String  v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        double s = b1.multiply(b2).doubleValue();
        int number = account(v1) + account(v2);
        StringBuilder stringBuilder = new StringBuilder("#0.");
        for (int i = 0 ;i<number;i++){
            stringBuilder.append("0");
        }
        java.text.DecimalFormat df = new java.text.DecimalFormat(stringBuilder.toString());
        return  df.format(s);
    }

    /***
     * 统计小数点后有几位小数
     * @param data
     */
    public static int account(String data){
        BigDecimal bd=new BigDecimal(data);
        return bd.scale();
    }


    /**
     * 毫秒转换成日期
     */
    public static String msToDay(long ms){
        Date date2 = new Date();
        date2.setTime(ms);
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        return sp.format(date2);
    }

    public static String msToDate2(long ms){
        Date date2 = new Date();
        date2.setTime(ms);
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sp.format(date2);
    }


    public static String msToDate(long ms){
        Date date2 = new Date();
        date2.setTime(ms);
        SimpleDateFormat sp = new SimpleDateFormat("yyyy年MM月");
        return sp.format(date2);
    }
    /**
     * 毫秒转换成小时分钟
     *
     */

    public static String msToVehicle(long ms){
        Date date2 = new Date();
        date2.setTime(ms);
        SimpleDateFormat sp = new SimpleDateFormat("HH:mm:ss");
        return sp.format(date2);
    }


    public static String numberFormattoString(float value){
        String str = new BigDecimal(value).setScale(3, BigDecimal.ROUND_HALF_UP).toPlainString();
        return str;
    }


    public static String formatNumber(double value){
        String str= null;
        str = String.format("%.2f", value);
        return str;
    }

    public static int getScreenHeight(){
        WindowManager wm = (WindowManager) MyApplication.getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }
    public static int getScreenWidth(){
        WindowManager wm = (WindowManager) MyApplication.getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

}
