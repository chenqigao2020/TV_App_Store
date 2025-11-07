package com.app.ui.ranking.division.app;

import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.base.activity.lifecycle.DistributeLifecycleEventObserver;
import com.app.ui.ranking.RankingActivity;
import com.app.util.load.ILoadController;
import com.app.util.load.TVLoadController;
import com.hhh.appstore.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AppListDivision extends DistributeLifecycleEventObserver {

    private FrameLayout contentLayout;

    private RecyclerView appListView;

    private RankingActivity activity;

    private ListAdapter listAdapter;

    private ILoadController loadController;

    public AppListDivision(FrameLayout contentLayout, RecyclerView appListView) {
        this.contentLayout = contentLayout;
        this.appListView = appListView;
    }

    @Override
    protected void onStateChanged(@NonNull @NotNull LifecycleOwner lifecycleOwner, @NonNull @NotNull Lifecycle.Event event) {
        switch (event) {
            case ON_CREATE:
                activity = (RankingActivity) lifecycleOwner;
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

    private void loadAppList() {
        loadController.showLoading();
        Observer<List<Ranking>> observer = new Observer<List<Ranking>>() {

            private List<Ranking> result;

            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Ranking> result) {
                this.result = result;
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
                if (result != null && !result.isEmpty()) {
                    loadController.showSuccess(null);
                    listAdapter.setNewData(result);
                } else {
                    loadController.showEmpty(null);
                }
            }

        };
        Observable.create(new GetClassifySubscribe())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(new Function<List<Ranking>, ObservableSource<List<Ranking>>>() {
                    @Override
                    public ObservableSource<List<Ranking>> apply(List<Ranking> rankings) throws Throwable {
                        return Observable
                                .create(new GetRankingSubscribe(rankings))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribe(observer);
    }

    private void initView() {
        appListView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        appListView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull @NotNull Rect outRect, @NonNull @NotNull View view, @NonNull @NotNull RecyclerView parent, @NonNull @NotNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.right = (int) parent.getResources().getDimension(R.dimen.margin);
            }
        });
        listAdapter = new ListAdapter();
        appListView.setAdapter(listAdapter);
    }

}
