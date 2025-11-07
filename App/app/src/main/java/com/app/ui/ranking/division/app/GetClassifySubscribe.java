package com.app.ui.ranking.division.app;

import com.app.bean.AppClassify;
import com.app.bean.Msg;
import com.app.util.ToolUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import okhttp3.Response;

public class GetClassifySubscribe implements ObservableOnSubscribe<List<Ranking>> {

    @Override
    public void subscribe(@NonNull ObservableEmitter<List<Ranking>> emitter) throws Throwable {
        Response response = OkGo.<String>post(ToolUtils.setApi("application_classify")).execute();
        String content = response.body().string();
        response.close();
        List<AppClassify> appClassifications = null;
        try {
            Msg<List<AppClassify>> msg = new Gson()
                    .fromJson(content, new TypeToken<Msg<List<AppClassify>>>(){}.getType());
            appClassifications = msg.msg;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        List<Ranking> rankings = new ArrayList<>();
        if (appClassifications != null) {
            for (int i = 0 ; i < appClassifications.size() ; i ++) {
                rankings.add(new Ranking(appClassifications.get(i), null));
            }
        }
        emitter.onNext(rankings);
        emitter.onComplete();
    }

}
