package com.yunqukuailian.app.http;

import android.net.ParseException;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.yunqukuailian.app.MyApplication;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by hedong on 2016/4/19.
 */
public abstract class AbsAPICallback<T> extends Subscriber<T> {
    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    protected AbsAPICallback() {

    }

    @Override
    public void onError(Throwable e) {
        Throwable throwable = e;
        //获取最根源的异常
        while (throwable.getCause() != null) {
            e = throwable;
            throwable = throwable.getCause();
        }

        if (e instanceof HttpException) {//HTTP错误
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    //Toast.makeText(App.getInstance(), R.string.server_http_error, Toast.LENGTH_SHORT).show();
                    break;
            }
        } else if (e instanceof SocketTimeoutException) {
            Log.e("yongyi","服务器返回的错误");
        } else if (e instanceof ResultException) {//服务器返回的错误
            ResultException resultException = (ResultException) e;
            Log.e("yongyi","resultException.getMessage(");
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            Log.e("yongyi","解析出错111");
        } else if(e instanceof ConnectException){
            Log.e("yongyi","未知错误");
        } else {//未知错误
        }
//        Toast.makeText(MyApplication.getInstance(),"服务器异常",Toast.LENGTH_SHORT).show();
        onCompleted();

    }

    protected abstract void onDone(T t);

    @Override
    public void onCompleted() {

    }

    @Override
    public void onNext(T t) {
        onDone(t);
    }
}
