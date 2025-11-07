package com.app.ui.ranking.division.app;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.bean.App;
import com.app.ui.detail.AppDetailActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.hhh.appstore.R;
import com.hhh.appstore.databinding.RankingItemViewBinding;
import com.hhh.appstore.databinding.RankingItemViewItemBinding;

public class RankingView extends FrameLayout {

    private RankingItemViewBinding binding;

    public RankingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        binding = RankingItemViewBinding.inflate(LayoutInflater.from(context), this, true);
    }

    public void set(Ranking ranking) {
        binding.classifyTextView.setText(ranking.classification.name);
        RankingItemViewItemBinding[] bindings = new RankingItemViewItemBinding[] {
                RankingItemViewItemBinding.bind(binding.appLayout0.getRoot()),
                binding.appLayout1,
                binding.appLayout2,
                binding.appLayout3,
                binding.appLayout4,
        };
        for (int i = 0 ; i < bindings.length ; i ++) {
            RankingItemViewItemBinding binding = bindings[i];
            App app = ranking.apps != null && ranking.apps.size() > i ? ranking.apps.get(i) : null;
            if (app != null) {
                Glide.with(this)
                        .load(app.icon)
                        .error(R.drawable.img_error)
                        .placeholder(R.drawable.img_error)
                        .fallback(R.drawable.img_error)
                        .transform(new MultiTransformation<>(
                                new CenterCrop(),
                                new RoundedCorners((int) getResources().getDimension(R.dimen.corners))
                        ))
                        .into(binding.appImageView);
                binding.nameTextView.setText(app.name);
                binding.indexTextView.setText(String.valueOf(i + 1));
                binding.appLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), AppDetailActivity.class);
                        intent.putExtra("id", app.id);
                        getContext().startActivity(intent);
                    }
                });
            } else {
                Glide.with(this)
                        .load("")
                        .error(R.drawable.img_error)
                        .placeholder(R.drawable.img_error)
                        .fallback(R.drawable.img_error)
                        .transform(new MultiTransformation<>(
                                new CenterCrop(),
                                new RoundedCorners((int) getResources().getDimension(R.dimen.corners))
                        ))
                        .into(binding.appImageView);
                binding.nameTextView.setText("* * *");
                binding.indexTextView.setText(String.valueOf(i + 1));
                binding.appLayout.setOnClickListener(null);
            }
        }
    }

}
