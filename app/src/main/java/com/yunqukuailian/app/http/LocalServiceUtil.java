package com.yunqukuailian.app.http;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tidom on 2018/3/17/017.
 */

public class LocalServiceUtil {

    public static int PAGESIZE = 10;//每一页的加载数

    public static LocalApi getApiRest() {
        return new Retrofit.Builder().baseUrl(LocalService.API_BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(makeHttpClient())
                .build().create(LocalApi.class);
    }


    private static OkHttpClient makeHttpClient() {
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS).build();

    }


}
