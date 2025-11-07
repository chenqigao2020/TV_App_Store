package com.app.ui.recommend;

import com.app.bean.App;
import com.app.bean.Msg;
import com.app.util.ToolUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import okhttp3.Response;

public class GetAppSubscribe implements ObservableOnSubscribe<List<App>> {

    @Override
    public void subscribe(@NonNull ObservableEmitter<List<App>> emitter) throws Throwable {
        Response response = OkGo
                .<String>post(ToolUtils.setApi("application_recommend"))
                .params("limit", 4)
                .execute();
        String content = response.body().string();
        response.close();
        List<App> apps = null;
        try {
            Msg<List<App>> msg = new Gson()
                    .fromJson(content, new TypeToken<Msg<List<App>>>(){}.getType());
            apps = msg.msg;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        emitter.onNext(apps);
        emitter.onComplete();
    }

}
