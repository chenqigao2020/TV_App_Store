package com.app.ui.share.subscribe;

import com.app.bean.Msg;
import com.app.ui.share.bean.ShareResponse;
import com.app.ui.share.bean.ShareResponseData;
import com.app.util.ToolUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

import java.io.File;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

public class UploadApkSubscribe implements ObservableOnSubscribe<ShareResponseData> {

    private ShareResponseData data;

    public UploadApkSubscribe(ShareResponseData data) {
        this.data = data;
    }

    @Override
    public void subscribe(@NonNull ObservableEmitter<ShareResponseData> emitter) throws Throwable {
        OkGo.<String>post(ToolUtils.setApi("application_share"))
            .params("file", new File(data.filePath))
                .isMultipart(true)
            .execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    String content = response.body();
                    try {
                        Msg<ShareResponse> msg = new Gson()
                                .fromJson(content, new TypeToken<Msg<ShareResponse>>(){}.getType());
                        data.response = msg.msg;
                    } catch (Throwable throwable) {
                        emitter.onError(throwable);
                        return;
                    }
                    emitter.onNext(data);
                    emitter.onComplete();
                }

                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    emitter.onError(new RuntimeException());
                }

                @Override
                public void uploadProgress(Progress progress) {
                    super.uploadProgress(progress);
                    data.progress = progress.fraction;
                    emitter.onNext(data);
                }
            });
    }

}
