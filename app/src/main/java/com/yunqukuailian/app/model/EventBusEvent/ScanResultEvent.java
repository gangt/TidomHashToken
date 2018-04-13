package com.yunqukuailian.app.model.EventBusEvent;

/**
 * Created by Tidom on 2018/3/20/020.
 */

public class ScanResultEvent {
    private String scanResult ;

    public ScanResultEvent(String scanResult) {
        this.scanResult = scanResult;
    }

    public String getScanResult() {
        return scanResult;
    }
}
