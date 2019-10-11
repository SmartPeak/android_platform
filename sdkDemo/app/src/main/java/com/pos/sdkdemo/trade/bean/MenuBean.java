package com.pos.sdkdemo.trade.bean;

import android.support.annotation.DrawableRes;

public class MenuBean {
    private String name="";
    @DrawableRes
    private int resId=0x00;
    private int transType=0;
    private Class activity=null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getTransType() {
        return transType;
    }

    public void setTransType(int transType) {
        this.transType = transType;
    }

    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }
}
