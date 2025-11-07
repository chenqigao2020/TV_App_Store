package com.app.base.activity;

import androidx.viewbinding.ViewBinding;

import com.chad.library.adapter.base.BaseViewHolder;

public class BaseViewBindingHolder<VB extends ViewBinding> extends BaseViewHolder {

    public VB binding;

    public BaseViewBindingHolder(VB binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}