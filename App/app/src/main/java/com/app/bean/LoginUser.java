package com.app.bean;

import com.google.gson.annotations.SerializedName;

public class LoginUser {

    @SerializedName("info")
    public User info;

    @SerializedName("token")
    public String token;

}
