package com.app.ui.recommend;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.app.base.activity.lifecycle.DistributeLifecycleEventObserver;
import com.app.bean.App;
import com.app.util.load.ILoadController;
import com.app.util.load.TVLoadController;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RefreshDivision extends DistributeLifecycleEventObserver {

    private RecommendActivity activity;

    private FrameLayout contentLayout;

    private Button refreshButton;

    private ILoadController loadController;

    public RefreshDivision(FrameLayout contentLayout, Button refreshButton) {
        this.contentLayout = contentLayout;
        this.refreshButton = refreshButton;
    }

    @Override
    protected void onStateChanged(@NonNull @NotNull LifecycleOwner lifecycleOwner, @NonNull @NotNull Lifecycle.Event event) {
        switch (event) {
            case ON_CREATE:
                activity = (RecommendActivity) lifecycleOwner;
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
        loadController = new TVLoadController(contentLayout);
        Runnable loadRunnable = new Runnable() {
            @Override
            public void run() {
                loadAppList();
            }
        };
        loadController.setLoadTask(loadRunnable);
        loadRunnable.run();
    }

    private void initView() {
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAppList();
            }
        });
    }

    private void loadAppList() {
        loadController.showLoading();
        Observer<List<App>> observer = new Observer<List<App>>() {

            private List<App> apps;

            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<App> result) {
                apps = result;
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                if (activity.getSupportFragmentManager().isDestroyed()) {
                    return;
                }
                loadController.showError(null);
            }

            @Override
            public void onComplete() {
                if (activity.getSupportFragmentManager().isDestroyed()) {
                    return;
                }
                int size = apps != null ? apps.size() : 0;
                if (size > 0) {
                    loadController.showSuccess(refreshButton);
                    activity.appListDivision.set(apps);
                } else {
                    loadController.showEmpty(null);
                }
            }

        };
        Observable.create(new GetAppSubscribe())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
