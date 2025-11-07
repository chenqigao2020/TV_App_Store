package com.app.base.activity.division;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.app.base.activity.lifecycle.DistributeLifecycleEventObserver;

import org.jetbrains.annotations.NotNull;

public class TitleDivision extends DistributeLifecycleEventObserver {

    private TextView titleTextView;

    private String title;

    public TitleDivision(TextView titleTextView, String title) {
        this.titleTextView = titleTextView;
        this.title = title;
    }

    @Override
    protected void onStateChanged(@NonNull @NotNull LifecycleOwner lifecycleOwner, @NonNull @NotNull Lifecycle.Event event) {
        switch (event) {
            case ON_CREATE:
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
        titleTextView.setText(title);
    }

}
