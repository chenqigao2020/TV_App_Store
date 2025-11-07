package com.app.ui.home.division.notice;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.app.base.activity.lifecycle.DistributeLifecycleEventObserver;
import com.app.bean.Msg;
import com.app.bean.Notice;
import com.app.ui.home.HomeActivity;
import com.app.util.ToolUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hhh.appstore.BuildConfig;
import com.hhh.appstore.R;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NoticeDivision extends DistributeLifecycleEventObserver {

    private HomeActivity activity;

    private TextView noticeTextView;

    public NoticeDivision(TextView noticeTextView) {
        this.noticeTextView = noticeTextView;
    }

    @Override
    protected void onStateChanged(@NonNull @NotNull LifecycleOwner lifecycleOwner, @NonNull @NotNull Lifecycle.Event event) {
        switch (event) {
            case ON_CREATE:
                activity = (HomeActivity) lifecycleOwner;
                initView();
                initData();
                break;
            case ON_PAUSE:
                break;
            case ON_RESUME:
                break;
            case ON_DESTROY:
                break;
        }
    }

    private void initData() {
        OkGo.<String>post(ToolUtils.setApi("notice"))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        List<Notice> list = null;
                        try {
                            Msg<List<Notice>> msg = new Gson()
                                    .fromJson(response.body(), new TypeToken<Msg<List<Notice>>>(){}.getType());
                            list = msg.msg;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                        String content = list != null && !list.isEmpty() ? list.get(0).content : null;
                        onNoticeChange(content);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        onNoticeChange(null);
                    }
                });
    }

    private void onNoticeChange(String content) {
        String app = "欢迎使用" + activity.getString(R.string.app_name) + "！";
        content = content != null ? content : app;
        while (content.length() < 200) {
            content += "                        " + content;
        }
        noticeTextView.setText(content);
    }

    private void initView() {
        onNoticeChange("");
        noticeTextView.setSelected(!BuildConfig.DEBUG);
    }

}
