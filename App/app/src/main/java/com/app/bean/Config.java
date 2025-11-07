package com.app.bean;

import com.google.gson.annotations.SerializedName;

public class Config {

    @SerializedName("APP_ID")
    public String appId = "请输入APP_ID";

    @SerializedName("MMM_MMM")
    public String www = "请输入服务器地址";

    @SerializedName("API_KEY")
    public String apiKey = "请输入API_KEY";

}
