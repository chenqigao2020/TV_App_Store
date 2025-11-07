package com.app.ui.share.subscribe;

import android.content.Context;

import com.app.ui.share.bean.ShareResponseData;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

public class GetApkSubscribe implements ObservableOnSubscribe<ShareResponseData> {

    private Context context;

    private String packageName;

    public GetApkSubscribe(Context context, String packageName) {
        this.context = context;
        this.packageName = packageName;
    }

    @Override
    public void subscribe(@NonNull ObservableEmitter<ShareResponseData> emitter) throws Throwable {
        ShareResponseData response = new ShareResponseData();
        response.filePath = context.getPackageManager().getApplicationInfo(packageName, 0).sourceDir;
        emitter.onNext(response);
        emitter.onComplete();
    }
}
