package com.app.ui.home.node.find;

import android.view.View;

import com.app.base.DemandLoadViewPagerFragment;
import com.app.base.activity.lifecycle.DistributeLifecycleEventObserver;
import com.app.ui.home.node.find.division.RankingDivision;
import com.app.ui.home.node.find.division.RecommendDivision;
import com.app.ui.home.node.find.division.app.AppListDivision;
import com.app.ui.home.node.find.division.app.MinAppListDivision;
import com.hhh.appstore.databinding.FindFragmentBinding;

public class FindFragment extends DemandLoadViewPagerFragment {

    private FindFragmentBinding binding;

    private AppListDivision appListDivision;

    public MinAppListDivision minAppListDivision;

    @Override
    protected View onDemandCreateView() {
        binding = FindFragmentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void onDemandViewCreated() {
        super.onDemandViewCreated();
        DistributeLifecycleEventObserver[] lifecycleEventObservers = new DistributeLifecycleEventObserver[]{
                appListDivision = new AppListDivision(binding.contentLayout, binding.appListView),
                minAppListDivision = new MinAppListDivision(binding.minAppListView),
                new RankingDivision(binding.rankingButton),
                new RecommendDivision(binding.recommendButton)
        };
        for (DistributeLifecycleEventObserver observer : lifecycleEventObservers) {
            getDistributeLifecycle().addObserver(observer);
        }
        initView();
        initData();
    }

    private void initData() {
    }

    private void initView() {
    }

}
