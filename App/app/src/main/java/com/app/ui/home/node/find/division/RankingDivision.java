package com.app.ui.home.node.find.division;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.app.base.activity.lifecycle.DistributeLifecycleEventObserver;
import com.app.ui.home.node.find.FindFragment;
import com.app.ui.ranking.RankingActivity;

import org.jetbrains.annotations.NotNull;

public class RankingDivision extends DistributeLifecycleEventObserver {

    private ViewGroup RankingButton;

    private FindFragment fragment;

    public RankingDivision(ViewGroup RankingButton) {
        this.RankingButton = RankingButton;
    }

    @Override
    protected void onStateChanged(@NonNull @NotNull LifecycleOwner lifecycleOwner, @NonNull @NotNull Lifecycle.Event event) {
        switch (event) {
            case ON_CREATE:
                fragment = (FindFragment) lifecycleOwner;
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
        RankingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.startActivity(new Intent(fragment.getContext(), RankingActivity.class));
            }
        });
    }

}
