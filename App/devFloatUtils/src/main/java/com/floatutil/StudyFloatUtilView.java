package com.floatutil;

import android.content.Context;
import android.graphics.Color;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.content.Context.WINDOW_SERVICE;

/**
 * author: xujiajia
 * created on: 2020/9/4 3:58 PM
 * description:
 * 全局悬浮框
 */
public class StudyFloatUtilView extends RelativeLayout implements View.OnClickListener {

  //ui
  private TextView tvInfo;
  private TextView tvHide;
  private TextView tvShow;
  private View contentView;
  //data
  private WindowManager windowManager;

  public StudyFloatUtilView(Context context) {
    this(context, null);
  }

  public StudyFloatUtilView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public StudyFloatUtilView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);

    contentView = LayoutInflater.from(context).inflate(R.layout.view_float_util, this, false);
    contentView.getLayoutParams().width = WindowManager.LayoutParams.WRAP_CONTENT;
    contentView.getLayoutParams().height = WindowManager.LayoutParams.WRAP_CONTENT;
    addView(contentView);
    initViews();
  }

  private void initViews() {
    tvInfo = findViewById(R.id.tv_info_float_util);
    tvHide = findViewById(R.id.tv_hide_float_util);
    tvShow = findViewById(R.id.tv_show_float_util);

    tvInfo.setMovementMethod(ScrollingMovementMethod.getInstance());
    tvHide.setOnClickListener(this);
    tvShow.setOnClickListener(this);
    setInfoShow(false);
  }

  public void appendInfo(String text) {
    if (tvInfo == null) {
      return;
    }
    tvInfo.append(text);
  }

  @Override public void onClick(View v) {
    int id = v.getId();
    if (id == R.id.tv_hide_float_util) {
      setInfoShow(false);
    } else if (id == R.id.tv_show_float_util) {
      setInfoShow(true);
    }
  }

  private void setInfoShow(boolean show) {
    if (show) {
      tvInfo.setVisibility(VISIBLE);
      tvHide.setVisibility(VISIBLE);
      tvShow.setVisibility(GONE);
      if (getLayoutParams() != null) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        windowManager.updateViewLayout(this, layoutParams);
        contentView.getLayoutParams().width = WindowManager.LayoutParams.MATCH_PARENT;
        contentView.getLayoutParams().height = WindowManager.LayoutParams.MATCH_PARENT;
        contentView.setLayoutParams(contentView.getLayoutParams());
      }
    } else {
      tvInfo.setVisibility(GONE);
      tvHide.setVisibility(GONE);
      tvShow.setVisibility(VISIBLE);
      if (getLayoutParams() != null) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        windowManager.updateViewLayout(this, layoutParams);
        contentView.getLayoutParams().width = WindowManager.LayoutParams.WRAP_CONTENT;
        contentView.getLayoutParams().height = WindowManager.LayoutParams.WRAP_CONTENT;
        contentView.setLayoutParams(contentView.getLayoutParams());
      }
    }
  }
}
