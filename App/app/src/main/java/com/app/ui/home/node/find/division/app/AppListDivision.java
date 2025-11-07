package com.app.ui.home.node.find.division.app;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.base.activity.lifecycle.DistributeLifecycleEventObserver;
import com.app.bean.App;
import com.app.bean.Msg;
import com.app.ui.detail.AppDetailActivity;
import com.app.ui.home.node.find.FindFragment;
import com.app.util.GridSpacingItemDecoration;
import com.app.util.ToolUtils;
import com.app.util.load.ILoadController;
import com.app.util.load.TVLoadController;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hhh.appstore.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AppListDivision extends DistributeLifecycleEventObserver {

    private RecyclerView appListView;

    private FindFragment fragment;

    private AppListAdapter appListAdapter;

    private ILoadController loadController;

    public AppListDivision(FrameLayout contentLayout, RecyclerView appListView) {
        this.appListView = appListView;
        loadController = new TVLoadController(contentLayout);
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
        appListAdapter = new AppListAdapter();
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
        OkGo.<String>post(ToolUtils.setApi("application_find"))
                .params("limit", 6)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (fragment.getActivity() == null) {
                            return;
                        }
                        List<App> apps = null;
                        try {
                            Msg<List<App>> msg = new Gson()
                                    .fromJson(response.body(), new TypeToken<Msg<List<App>>>(){}.getType());
                            apps = msg.msg;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                        if (apps != null && !apps.isEmpty()) {
                            loadController.showSuccess(null);
                        } else {
                            loadController.showEmpty(null);
                        }
                        List<App> appList = new ArrayList<>();
                        int size = 3;
                        for (int i = 0 ; i < size ; i ++) {
                            if (apps != null && apps.size() > i) {
                                appList.add(apps.get(i));
                            }
                        }
                        appListAdapter.setNewData(appList);
                        fragment.minAppListDivision.setNewData(size, apps);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (fragment.getActivity() == null) {
                            return;
                        }
                        loadController.showError(null);
                    }
                });
    }

}
