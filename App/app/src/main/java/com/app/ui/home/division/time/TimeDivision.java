package com.app.ui.home.division.time;

import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.app.base.activity.lifecycle.DistributeLifecycleEventObserver;
import com.app.ui.home.HomeActivity;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeDivision extends DistributeLifecycleEventObserver {

    private HomeActivity activity;

    private TextView timeTextView;

    private Handler handler;

    public TimeDivision(TextView timeTextView) {
        this.timeTextView = timeTextView;
        handler = new Handler();
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
                handler.removeCallbacksAndMessages(null);
                break;
        }
    }

    private void initData() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                refreshTime();
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void refreshTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String time = format.format(new Date());
        timeTextView.setText(time);
    }

    private void initView() {
    }

}
