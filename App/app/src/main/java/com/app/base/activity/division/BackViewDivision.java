package com.app.base.activity.division;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.app.base.activity.lifecycle.DistributeLifecycleEventObserver;

import org.jetbrains.annotations.NotNull;

public class BackViewDivision extends DistributeLifecycleEventObserver {

    private View backView;

    private Activity activity;

    public BackViewDivision(View backView) {
        this.backView = backView;
    }

    @Override
    protected void onStateChanged(@NonNull @NotNull LifecycleOwner lifecycleOwner, @NonNull @NotNull Lifecycle.Event event) {
        switch (event) {
            case ON_CREATE:
                if (lifecycleOwner instanceof Activity) {
                    activity = (Activity) lifecycleOwner;
                } else if (lifecycleOwner instanceof Fragment) {
                    activity = ((Fragment)lifecycleOwner).getActivity();
                }
                assert activity != null;
                initView();
                break;
            case ON_PAUSE:
                break;
            case ON_RESUME:
                break;
            case ON_DESTROY:
                break;
        }
    }

    private void initView() {
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
    }

}
