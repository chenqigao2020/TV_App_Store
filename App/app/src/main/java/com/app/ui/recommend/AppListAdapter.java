package com.app.ui.recommend;

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
import com.hhh.appstore.databinding.RecommendItemBinding;

public class AppListAdapter extends BaseQuickViewBindingAdapter<App, BaseViewBindingHolder<RecommendItemBinding>> {

    @Override
    protected BaseViewBindingHolder<RecommendItemBinding> getViewBinding(int viewType, LayoutInflater from, ViewGroup parent) {
        RecommendItemBinding binding = RecommendItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new BaseViewBindingHolder<>(binding);
    }

    @Override
    protected void convert(BaseViewBindingHolder<RecommendItemBinding> helper, App item) {
        helper.binding.nameTextView.setText(item.name);
        Glide.with(this.mContext)
                .load(item.icon)
                .error(R.drawable.img_error)
                .placeholder(R.drawable.img_error)
                .fallback(R.drawable.img_error)
                .transform(new MultiTransformation<>(
                        new CenterCrop(),
                        new RoundedCorners((int) mContext.getResources().getDimension(R.dimen.corners))
                ))
                .into(helper.binding.ivApps);
    }

}
