package com.app.ui.ranking.division.app;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.app.base.activity.BaseQuickViewBindingAdapter;
import com.app.base.activity.BaseViewBindingHolder;
import com.hhh.appstore.databinding.RankingItemBinding;

public class ListAdapter extends BaseQuickViewBindingAdapter<Ranking, BaseViewBindingHolder<RankingItemBinding>> {
    @Override
    protected BaseViewBindingHolder<RankingItemBinding> getViewBinding(int viewType, LayoutInflater from, ViewGroup parent) {
        RankingItemBinding binding = RankingItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BaseViewBindingHolder<>(binding);
    }

    @Override
    protected void convert(BaseViewBindingHolder<RankingItemBinding> helper, Ranking item) {
        helper.binding.rankingView.set(item);
    }
}
