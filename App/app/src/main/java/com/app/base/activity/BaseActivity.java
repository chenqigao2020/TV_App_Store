package com.app.base.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.base.activity.lifecycle.DistributeLifecycle;

/**
 * 此版本由《红火火工作室》开发
 * 二开、源码请联系QQ：1282797911 闲鱼：红火火工作室
 * **/
public class BaseActivity extends AppCompatActivity {

    private DistributeLifecycle distributeLifecycle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDistributeLifecycle();
    }

    private void initDistributeLifecycle() {
        distributeLifecycle = new DistributeLifecycle();
        getLifecycle().addObserver(distributeLifecycle);
    }

    public DistributeLifecycle getDistributeLifecycle() {
        return distributeLifecycle;
    }

}
