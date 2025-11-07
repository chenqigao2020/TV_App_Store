package com.app.bean;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    public int id;

    @SerializedName("pic")
    public String pic;

    @SerializedName("name")
    public String name;

    @SerializedName("vip")
    public long vip;

    @SerializedName("vip_state")
    public boolean vipState;

    @SerializedName("fen")
    public int fen;

}
