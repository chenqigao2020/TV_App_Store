package com.app.util.load;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.viewbinding.ViewBinding;

import com.hhh.appstore.databinding.EmptyViewBinding;
import com.hhh.appstore.databinding.ErrorViewBinding;
import com.hhh.appstore.databinding.LoadingViewBinding;

/**
 * 此版本由《红火火工作室》开发
 * 二开、源码请联系QQ：1282797911 闲鱼：红火火工作室
 * **/
public class TVLoadController implements ILoadController {

    private final FrameLayout rootLayout;

    private Runnable loadRunnable;

    private View loadView;

    private boolean isLoading;

    public TVLoadController(FrameLayout rootLayout) {
        this.rootLayout = rootLayout;
    }

    @Override
    public void setLoadTask(Runnable loadRunnable) {
        this.loadRunnable = loadRunnable;
    }

    @Override
    public void showLoading() {
        isLoading = true;
        reset();
        LoadingViewBinding binding = LoadingViewBinding.inflate(
                LayoutInflater.from(rootLayout.getContext()),
                rootLayout,
                false
        );
        load(binding);
    }

    @Override
    public void showSuccess(View nextFocusView) {
        isLoading = false;
        reset();
        for (int i = 0 ; i < rootLayout.getChildCount() ; i ++) {
            View view = rootLayout.getChildAt(i);
            view.setVisibility(View.VISIBLE);
        }
        if (nextFocusView != null) {
            nextFocusView.requestFocus();
        }
    }

    @Override
    public void showError(View nextFocusView) {
        isLoading = false;
        reset();
        ErrorViewBinding binding = ErrorViewBinding.inflate(
                LayoutInflater.from(rootLayout.getContext()),
                rootLayout,
                false
        );
        binding.tryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nextFocusView != null) {
                    nextFocusView.requestFocus();
                }
                load();
            }
        });
        load(binding);
    }

    @Override
    public void showEmpty(View nextFocusView) {
        isLoading = false;
        reset();
        EmptyViewBinding binding = EmptyViewBinding.inflate(
                LayoutInflater.from(rootLayout.getContext()),
                rootLayout,
                false
        );
        binding.refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nextFocusView != null) {
                    nextFocusView.requestFocus();
                }
                load();
            }
        });
        load(binding);
    }

    @Override
    public boolean isLoading() {
        return isLoading;
    }

    @Override
    public void load() {
        if (loadRunnable != null) {
            loadRunnable.run();
        }
    }

    private void load(ViewBinding binding) {
        loadView = binding.getRoot();
        rootLayout.addView(loadView);
        hideOtherView(binding);
    }

    private void hideOtherView(ViewBinding binding) {
        int childCount = rootLayout.getChildCount();
        for (int i = 0 ; i < childCount ; i ++) {
            View view = rootLayout.getChildAt(i);
            int visibility = view == binding.getRoot() ? View.VISIBLE : View.INVISIBLE;
            view.setVisibility(visibility);
        }
    }

    private void reset() {
        if (loadView == null) {
            return;
        }
        rootLayout.removeView(loadView);
        loadView = null;
    }

}
