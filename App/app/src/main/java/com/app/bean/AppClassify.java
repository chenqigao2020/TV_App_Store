package com.app.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 此版本由《红火火工作室》开发
 * 二开、源码请联系QQ：1282797911 闲鱼：红火火工作室
 * **/
public class AppClassify {

    public boolean isAutoSwitch = true;

    public boolean isSelect = false;

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    public AppClassify(int id, String name, boolean isAutoSwitch) {
        this.id = id;
        this.name = name;
        this.isAutoSwitch = isAutoSwitch;
    }

    public AppClassify() {
    }
}
