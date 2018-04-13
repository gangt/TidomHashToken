package com.yunqukuailian.app.model.CountryCode;

/**
 * Created by admin on 2018/3/25.
 */

public class SortModel {


    private String name;
    private String sortLetter;
    private String pinyin ;
    private String name_code ;

    public String getName_code() {
        return name_code;
    }

    public void setName_code(String name_code) {
        this.name_code = name_code;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetter() {
        return sortLetter;
    }
    public void setSortLetter(String sortLetters) {
        this.sortLetter = sortLetters;
    }
}