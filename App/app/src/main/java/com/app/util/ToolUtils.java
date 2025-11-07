package com.app.util;

/**
 * 此版本由《红火火工作室》开发
 * 二开、源码请联系QQ：1282797911 闲鱼：红火火工作室
 * **/
public class ToolUtils {

    public static String setApi(String act) {
        String url = HawkConfig.CONFIG.www + "/api.php?act=" + act;
        url += "&app=" + HawkConfig.CONFIG.appId;
        return url;
    }

}

