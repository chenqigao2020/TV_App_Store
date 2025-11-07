package com.app.ui.recommend;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.app.base.activity.BaseActivity;
import com.app.base.activity.division.BackViewDivision;
import com.app.base.activity.lifecycle.DistributeLifecycleEventObserver;
import com.app.util.immersive.ImmersiveWindow;
import com.hhh.appstore.databinding.RecommendBinding;

public class RecommendActivity extends BaseActivity {

    private RecommendBinding binding;

    public AppListDivision appListDivision;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RecommendBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        initDistributeLifecycles();
    }

    private void initDistributeLifecycles() {
        DistributeLifecycleEventObserver[] lifecycleEventObservers = new DistributeLifecycleEventObserver[]{
                new BackViewDivision(binding.backImageView),
                appListDivision = new AppListDivision(binding.appListView),
                new RefreshDivision(binding.contentLayout, binding.refreshButton),
        };
        for (DistributeLifecycleEventObserver observer : lifecycleEventObservers) {
            getDistributeLifecycle().addObserver(observer);
        }
    }

    private void initView() {
        immersiveWindow();
    }

    private void immersiveWindow() {
        ImmersiveWindow immersiveWindow = new ImmersiveWindow(getWindow());
        immersiveWindow.updateWindow();
        immersiveWindow.setNavigation(false);
        immersiveWindow.setStatus(false);
    }

}