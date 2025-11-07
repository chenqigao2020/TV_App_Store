package com.app.base.activity.lifecycle;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.ArrayList;
import java.util.List;

/**
 * 分发Activity的Lifecycle事件，用于解决LifecycleEventObserver总是未被混淆的问题
 * **/
public class DistributeLifecycle implements LifecycleEventObserver {

    private final List<DistributeLifecycleEventObserver> observerList = new ArrayList<>();

    private final List<Lifecycle.Event> eventCacheList = new ArrayList<>();

    private LifecycleOwner lifecycleOwner;

    @Override
    public void onStateChanged(@NonNull LifecycleOwner lifecycleOwner, @NonNull Lifecycle.Event event) {
        this.lifecycleOwner = lifecycleOwner;
        eventCacheList.remove(event);
        eventCacheList.add(event);
        for (DistributeLifecycleEventObserver observer : observerList) {
            observer.onStateChanged(lifecycleOwner, event);
        }
    }

    public void addObserver(DistributeLifecycleEventObserver observer) {
        observerList.add(observer);
        for (Lifecycle.Event event : eventCacheList) {
            observer.onStateChanged(lifecycleOwner, event);
        }
    }

    public void removeObserver(DistributeLifecycleEventObserver observer) {
        observerList.remove(observer);
    }

}
