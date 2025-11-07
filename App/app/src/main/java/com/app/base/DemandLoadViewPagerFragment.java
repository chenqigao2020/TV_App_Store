package com.app.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 在ViewPager中按需加载的Fragment
 * 不要使用onCreateView了，使用onDemandCreateView来创建视图
 * **/
public class DemandLoadViewPagerFragment extends ViewPagerFragment {

    private boolean isInit = false;

    private RelativeLayout rootLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootLayout = new RelativeLayout(inflater.getContext());
        return rootLayout;
    }

    @Override
    protected void onViewPagerFragmentResume() {
        super.onViewPagerFragmentResume();
        if (isInit) {
            return;
        }
        isInit = true;
        View view = onDemandCreateView();
        onDemandViewCreated();
        if (view == null) {
            return;
        }
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        rootLayout.addView(view, layoutParams);
    }

    /**
     * 返回延迟创建的View
     * **/
    protected View onDemandCreateView() {
        return null;
    }

    /**
     * 延迟创建的View已创建完成
     * **/
    protected void onDemandViewCreated() {}
}
