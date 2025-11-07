package com.app.ui.home.node.find.division.app;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.app.base.activity.BaseQuickViewBindingAdapter;
import com.app.base.activity.BaseViewBindingHolder;
import com.app.bean.App;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.hhh.appstore.R;
import com.hhh.appstore.databinding.FindMinAppItemBinding;

public class MinAppListAdapter extends BaseQuickViewBindingAdapter<App, BaseViewBindingHolder<FindMinAppItemBinding>> {

    @Override
    protected BaseViewBindingHolder<FindMinAppItemBinding> getViewBinding(int viewType, LayoutInflater from, ViewGroup parent) {
        FindMinAppItemBinding binding = FindMinAppItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BaseViewBindingHolder<>(binding);
    }

    @Override
    protected void convert(BaseViewBindingHolder<FindMinAppItemBinding> helper, App item) {
        String backgroundImageUrl = item.pics != null && item.pics.size() > 0 ? item.pics.get(0) : null;
        Glide.with(helper.itemView)
                .load(backgroundImageUrl)
                .error(R.drawable.img_error)
                .placeholder(R.drawable.img_error)
                .fallback(R.drawable.img_error)
                .transform(new MultiTransformation<>(
                        new CenterCrop(),
                        new RoundedCorners((int) mContext.getResources().getDimension(R.dimen.corners))
                ))
                .into(helper.binding.backgroundImageView);
        Glide.with(helper.itemView)
                .load(item.icon)
                .error(R.drawable.img_error)
                .placeholder(R.drawable.img_error)
                .fallback(R.drawable.img_error)
                .transform(new MultiTransformation<>(
                        new CenterCrop(),
                        new RoundedCorners((int) mContext.getResources().getDimension(R.dimen.corners))
                ))
                .into(helper.binding.appImageView);
        helper.binding.nameTextView.setText(item.name);
        helper.binding.detailsTextView.setText(TextUtils.isEmpty(item.details) ? "小编精选！" : item.details);
    }

}
