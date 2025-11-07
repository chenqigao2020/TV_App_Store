package com.app.ui.detail;

import android.content.Intent;

import com.app.bean.App;
import com.app.bean.Msg;
import com.app.util.ToolUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import okhttp3.Response;

public class GetAppDetailSubscribe implements ObservableOnSubscribe<App> {

    private Intent intent;

    public GetAppDetailSubscribe(Intent intent) {
        this.intent = intent;
    }

    @Override
    public void subscribe(@NonNull ObservableEmitter<App> emitter) throws Throwable {
        Response response = OkGo.<String>post(ToolUtils.setApi("application_view"))
                .params("id", intent.getIntExtra("id", 0))
                .execute();
        assert response.body() != null;
        String content = response.body().string();
        response.close();
        Msg<App> msg = new Gson()
                .fromJson(content, new TypeToken<Msg<App>>(){}.getType());
        App app = msg.msg;
        emitter.onNext(app);
        emitter.onComplete();
    }
}
