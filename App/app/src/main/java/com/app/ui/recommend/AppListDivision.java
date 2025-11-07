package com.app.ui.recommend;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.base.activity.lifecycle.DistributeLifecycleEventObserver;
import com.app.bean.App;
import com.app.ui.detail.AppDetailActivity;
import com.app.util.GridSpacingItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hhh.appstore.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AppListDivision extends DistributeLifecycleEventObserver {

    private RecommendActivity activity;

    private RecyclerView appListView;

    private AppListAdapter listAdapter;

    public AppListDivision(RecyclerView appListView) {
        this.appListView = appListView;
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

    }

    private void initView() {
        appListView.setLayoutManager(new GridLayoutManager(activity, 4));
        appListView.addItemDecoration(new GridSpacingItemDecoration(
                4,
                (int) (activity.getResources().getDimension(R.dimen.margin)),
                false)
        );
        listAdapter = new AppListAdapter();
        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(activity, AppDetailActivity.class);
                intent.putExtra("id", listAdapter.getData().get(position).id);
                activity.startActivity(intent);
            }
        });
        appListView.setAdapter(listAdapter);
    }

    public void set(List<App> apps) {
        listAdapter.setNewData(apps);
    }

}
