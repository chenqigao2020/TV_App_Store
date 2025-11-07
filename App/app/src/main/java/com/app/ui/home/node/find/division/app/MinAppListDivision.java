package com.app.ui.home.node.find.division.app;

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
import com.app.ui.home.node.find.FindFragment;
import com.app.util.GridSpacingItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hhh.appstore.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MinAppListDivision extends DistributeLifecycleEventObserver {

    private RecyclerView appListView;

    private FindFragment fragment;

    private MinAppListAdapter appListAdapter;

    public MinAppListDivision(RecyclerView appListView) {
        this.appListView = appListView;
    }

    @Override
    protected void onStateChanged(@NonNull @NotNull LifecycleOwner lifecycleOwner, @NonNull @NotNull Lifecycle.Event event) {
        switch (event) {
            case ON_CREATE:
                fragment = (FindFragment) lifecycleOwner;
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

    private void initView() {
        appListAdapter = new MinAppListAdapter();
        appListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                App app = appListAdapter.getItem(position);
                assert app != null;
                Intent intent = new Intent(fragment.getActivity(), AppDetailActivity.class);
                intent.putExtra("id", app.id);
                fragment.startActivity(intent);
            }
        });
        appListView.addItemDecoration(new GridSpacingItemDecoration(
                3,
                (int) (fragment.getResources().getDimension(R.dimen.margin)),
                false)
        );
        appListView.setLayoutManager(new GridLayoutManager(fragment.getContext(), 3));
        appListView.setAdapter(appListAdapter);
    }

    private void initData() {

    }

    public void setNewData(int startIndex, List<App> apps) {
        List<App> appList = new ArrayList<>();
        int size = 3;
        for (int i = startIndex ; i < startIndex + size ; i ++) {
            if (apps != null && apps.size() > i) {
                appList.add(apps.get(i));
            }
        }
        appListAdapter.setNewData(appList);
        appListView.setVisibility(appList.isEmpty() ? View.GONE : View.VISIBLE);
    }

}