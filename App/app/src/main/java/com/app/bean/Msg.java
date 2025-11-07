package com.app.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 此版本由《红火火工作室》开发
 * 二开、源码请联系QQ：1282797911 闲鱼：红火火工作室
 * **/
public class Msg<T> {

    @SerializedName("code")
    public int code;

    @SerializedName("msg")
    public T msg;

}
