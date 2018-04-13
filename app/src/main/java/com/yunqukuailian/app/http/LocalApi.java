package com.yunqukuailian.app.http;


import com.yunqukuailian.app.model.GetAccountResponseBean;
import com.yunqukuailian.app.model.LoginBean;
import com.yunqukuailian.app.model.LoginResponseBean;
import android.util.Log;

import com.yunqukuailian.app.model.LoginRespon;
import com.yunqukuailian.app.model.MainFragment1Bean;
import com.yunqukuailian.app.model.MainFragment1BeanItem;
import com.yunqukuailian.app.model.MainFragment2BuyBean;
import com.yunqukuailian.app.model.MainFragment2CurrentBean;
import com.yunqukuailian.app.model.MainFragment2CurrentResponBean;
import com.yunqukuailian.app.model.PlaceOrderBean;
import com.yunqukuailian.app.model.RegisterBean;
import com.yunqukuailian.app.model.RegisterResponseBean;
import com.yunqukuailian.app.model.SimpleBaseBean;
import com.yunqukuailian.app.model.TBWalletAddress;
import com.yunqukuailian.app.model.Tb_ResponseDataBean;
import com.yunqukuailian.app.model.UserBillBean;
import com.yunqukuailian.app.model.UserCzAddressBean;
import com.yunqukuailian.app.model.VmbTXRequestBean;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by hedong on 2016/4/19.
 */
public interface LocalApi {
    @GET("market/area")//首页分区
    Observable<MainFragment1Bean> getMainFragment1Type();

    @GET("market/{newsId}")
    Observable<MainFragment1BeanItem> getMainFragmentItemData(@Path("newsId") String newsId);

    @GET("market/ticker/{martId}")
    Observable<ResponseBody> getDatilHeaherData(@Path("martId") String martId);

    //获取k线数据
    @GET("market/kline")
    Observable<ResponseBody> getHealthClassify(@QueryMap Map<String, String> parm);

    //检查是否注册
    @GET("user/check/{mobile}")
    Observable<RegisterResponseBean> checkIfRegister(@Path("mobile") String mobile);

    //登录
    @POST("user/login")
    Observable<LoginResponseBean> login(@Body LoginBean loginRequestDTO);


    //注册
    @POST("user/register")
    Observable<RegisterResponseBean> register(@Body RegisterBean registerRequestDTO);

    //获取验证码
    @GET("sms/code/{mobile}")
    Observable<ResponseBody> getYZM(@Header("apikey") String apikey ,
                                    @Header("timestamp") String timestamp,
                                    @Header("sign") String sign,
                                    @Path("mobile") String mobile);

    @GET("account")
    Observable<GetAccountResponseBean> getUserAccount(
            @Header("timestamp") String timestamp ,
            @Header("sign") String sign,
            @Header("apikey") String apikey);


    @GET("market/depths")//市场深度
    Observable<ResponseBody> getDepths(@Query("market") int martId,@Query("limit") int limit);



    @POST("order/create")//下单
    Observable<SimpleBaseBean> getPlaceOrderCreate(@Header("apikey") String apikey ,
                                                @Header("timestamp") String timestamp,
                                                @Header("sign") String sign,
                                                @Body PlaceOrderBean bean);


    @POST("order/entrustList")//获取委托记录
    Observable<ResponseBody> getEntrustedRecord(@Header("apikey") String apikey ,
                                                @Header("timestamp") String timestamp,
                                                @Header("sign") String sign,
                                                @Body MainFragment2CurrentResponBean bean);


    @GET("account/{marketId}")//根据id获取账户冻结金额
    Observable<ResponseBody> getUserAccountById(@Header("timestamp") String timestamp,
                                                                       @Header("sign") String sign,
                                                                       @Header("apikey") String apikey,
                                                                       @Path("marketId") String marketId);

    @POST("order/cancel/{orderId}")//取消订单
    Observable<SimpleBaseBean> deletOrderById(@Header("timestamp") String timestamp,
                                              @Header("sign") String sign,
                                              @Header("apikey") String apikey,
                                              @Path("orderId") String orderId);

    @GET("market/ticker/{marketId}")
    Observable<ResponseBody> startGetNewData(@Path("marketId") String marketId);


    //获取用户钱包地址
    @GET("wallet/address/{coinId}")
    Observable<UserCzAddressBean> getUserWalletAddress(@Header("timestamp") String timestamp ,
                                                       @Header("sign") String sign,
                                                       @Path("coinId") long coinId,
                                                       @Header("apikey") String apikey);



    //获取提币钱包地址
    @GET("wallet/address")
    Observable<TBWalletAddress> getTBwalletAddress(@Header("timestamp") String timestamp ,
                                                   @Header("sign") String sign,
                                                   @Header("apikey") String apikey,
                                                   @Query("coinId") long coinId);

    //虚拟币提现
    @POST("wallet/withdraw")
    Observable<Tb_ResponseDataBean> getVmbTX(@Header("timestamp") String timestamp ,
                                             @Header("sign") String sign,
                                             @Header("apikey") String apikey,
                                             @Body VmbTXRequestBean withdrawRequestDTO);


    //获取用户账单
    @GET("account/bills")
    Observable<UserBillBean> getUserBill(@Header("timestamp") String timestamp ,
                                         @Header("sign") String sign,
                                         @Header("apikey") String apikey,
                                         @Query("coinId") long coinId,
                                         @Query("size") int size,
                                         @Query("page") int page);

}
