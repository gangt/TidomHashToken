package com.yunqukuailian.app.model;

/**
 * Created by Administrator on 2018/3/1/001.
 */

public class BaseBean {
    //http://114.119.10.254:8080/youpinserver/teacherPubCoursePage.do?fteacherid=58C5FC4E-2E1C-41A1-8075-55B804FB8979&pageindex=1&pagesize=10&ftype=1
    /**
     * map : {"result":1,"msg":"成功"}
     */

    private MapBean map;

    public MapBean getMap() {
        return map;
    }

    public void setMap(MapBean map) {
        this.map = map;
    }

    public static class MapBean {
        /**
         * result : 1
         * msg : 成功
         */

        private int result;
        private String msg;

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
