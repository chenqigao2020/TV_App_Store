package com.app.ui.home.node.find.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class AppRecyclerView extends RecyclerView {

    public AppRecyclerView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public View focusSearch(View focused, int direction) {
        switch (direction) {
            case FOCUS_RIGHT:
                View view = super.focusSearch(focused, direction);
                if (isChildView(this, view)) {
                    return view;
                }
                return focused;
        }
        return super.focusSearch(focused, direction);
    }

    private boolean isChildView(ViewGroup targetViewGroup, View view) {
        if (view == null) {
            return false;
        }
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        while (viewGroup != null && viewGroup != getRootView()) {
            if (viewGroup == targetViewGroup) {
                return true;
            }
            viewGroup = (ViewGroup) viewGroup.getParent();
        }
        return false;
    }
}
