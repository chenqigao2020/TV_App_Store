package com.app.ui.home;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.app.base.activity.BaseActivity;
import com.app.base.activity.lifecycle.DistributeLifecycleEventObserver;
import com.app.ui.home.division.FindDivision;
import com.app.ui.home.division.notice.NoticeDivision;
import com.app.ui.home.division.time.TimeDivision;
import com.app.util.immersive.ImmersiveWindow;
import com.hhh.appstore.databinding.HomeActivityBinding;

public class HomeActivity extends BaseActivity {

    private HomeActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = HomeActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        initDistributeLifecycles();
    }

    private void initDistributeLifecycles() {
        DistributeLifecycleEventObserver[] lifecycleEventObservers = new DistributeLifecycleEventObserver[]{
                new NoticeDivision(binding.noticeTextView),
                new TimeDivision(binding.timeTextView),
                new FindDivision(binding.contentLayout)
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
