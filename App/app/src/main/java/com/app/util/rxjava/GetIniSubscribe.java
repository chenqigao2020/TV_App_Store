package com.app.util.rxjava;

import com.app.bean.Ini;
import com.app.util.http.ApiUtils;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

public class GetIniSubscribe implements ObservableOnSubscribe<Ini> {

    @Override
    public void subscribe(@NonNull ObservableEmitter<Ini> emitter) throws Throwable {
        Ini ini = ApiUtils.ini();
        emitter.onNext(ini);
        emitter.onComplete();
    }

}
