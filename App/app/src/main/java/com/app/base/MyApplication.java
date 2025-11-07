package com.app.base;

import android.app.Application;
import android.content.res.AssetManager;

import com.app.bean.Config;
import com.app.util.AssetFileLoader;
import com.app.util.HawkConfig;
import com.app.util.OkGoHelper;
import com.app.util.SimpleToast;
import com.google.gson.Gson;

/**
 * 此版本由《红火火工作室》开发
 * 二开、源码请联系QQ：1282797911 闲鱼：红火火工作室
 * **/
public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        OkGoHelper.init();
        SimpleToast.get().init(this);
        initConfig();
    }

    private void initConfig() {
        AssetManager assetManager = getAssets();
        AssetFileLoader assetFileLoader = new AssetFileLoader(assetManager);
        try {
            String inputStream = assetFileLoader.loadAssetFile(HawkConfig.CONFIG_FILE_NAME);
            HawkConfig.CONFIG = new Gson().fromJson(inputStream, Config.class);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.currentTimeMillis();
    }

    public static MyApplication get() {
        return instance;
    }

}