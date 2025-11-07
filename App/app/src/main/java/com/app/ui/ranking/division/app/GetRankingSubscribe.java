package com.app.ui.ranking.division.app;

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

public class GetRankingSubscribe implements ObservableOnSubscribe<List<Ranking>> {

    private List<Ranking> rankings;

    public GetRankingSubscribe(List<Ranking> rankings) {
        this.rankings = rankings;
    }

    @Override
    public void subscribe(@NonNull ObservableEmitter<List<Ranking>> emitter) throws Throwable {
        for (int i = 0 ; i < rankings.size() ; i ++) {
            Ranking ranking = rankings.get(i);
            Response response = OkGo
                    .<String>post(ToolUtils.setApi("application_ranking"))
                    .params("classify_id", ranking.classification.id)
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
            if (apps == null || apps.isEmpty()) {
                rankings.remove(i);
                i --;
            }
            ranking.apps = apps;
        }
        emitter.onNext(rankings);
        emitter.onComplete();
    }

}
