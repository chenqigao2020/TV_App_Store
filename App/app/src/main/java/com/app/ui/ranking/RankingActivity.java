package com.app.ui.ranking;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.app.base.activity.BaseActivity;
import com.app.base.activity.division.BackViewDivision;
import com.app.base.activity.lifecycle.DistributeLifecycleEventObserver;
import com.app.ui.ranking.division.app.AppListDivision;
import com.app.util.immersive.ImmersiveWindow;
import com.hhh.appstore.databinding.RankingBinding;

public class RankingActivity extends BaseActivity {

    private RankingBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RankingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        initDistributeLifecycles();
    }

    private void initDistributeLifecycles() {
        DistributeLifecycleEventObserver[] lifecycleEventObservers = new DistributeLifecycleEventObserver[]{
                new BackViewDivision(binding.backImageView),
                new AppListDivision(binding.contentLayout, binding.appListView),
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