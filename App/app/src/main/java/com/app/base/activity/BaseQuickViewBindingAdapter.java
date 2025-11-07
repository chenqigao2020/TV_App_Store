package com.app.base.activity;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public abstract class BaseQuickViewBindingAdapter<T, VH extends BaseViewHolder> extends BaseQuickAdapter<T, VH> {

    public BaseQuickViewBindingAdapter() {
        super(0);
    }

    @Override
    protected VH onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return getViewBinding(
                viewType,
                LayoutInflater.from(parent.getContext()),
                parent
        );
    }

    protected abstract VH getViewBinding(int viewType, LayoutInflater from, ViewGroup parent);

}
