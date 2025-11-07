package com.app.ui.home.division;

import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.app.base.activity.lifecycle.DistributeLifecycleEventObserver;
import com.app.ui.home.HomeActivity;
import com.app.ui.home.node.find.FindFragment;
import com.hhh.appstore.R;

import org.jetbrains.annotations.NotNull;

public class FindDivision extends DistributeLifecycleEventObserver {

    private HomeActivity activity;

    private FrameLayout contentLayout;

    public FindDivision(FrameLayout contentLayout) {
        this.contentLayout = contentLayout;
    }

    @Override
    protected void onStateChanged(@NonNull @NotNull LifecycleOwner lifecycleOwner, @NonNull @NotNull Lifecycle.Event event) {
        switch (event) {
            case ON_CREATE:
                activity = (HomeActivity) lifecycleOwner;
                initView();
                initData();
                break;
            case ON_PAUSE:
                break;
            case ON_RESUME:
                break;
            case ON_DESTROY:
                break;
        }
    }

    private void initData() {

    }

    private void initView() {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentLayout, new FindFragment())
                .commit();
    }

}
