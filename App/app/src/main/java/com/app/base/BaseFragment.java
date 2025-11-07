package com.app.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.base.activity.lifecycle.DistributeLifecycle;

public class BaseFragment extends Fragment {

    private DistributeLifecycle distributeLifecycle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
