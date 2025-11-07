package com.app.util.download;

import com.app.util.OkGoHelper;

import okhttp3.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class FileDownloader {

    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .connectTimeout(OkGoHelper.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
            .readTimeout(OkGoHelper.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)
            .build();

    private boolean isPause = false;

    /**
     * 下载文件
     * @param url 文件URL
     * @param destFileDir 目标文件目录
     * @param destFileName 目标文件名
     * @param listener 下载监听器
     */
    public void download(String url, String destFileDir, String destFileName, DownloadListener listener) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        CLIENT.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (listener != null) {
                    listener.onFailure(e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    if (listener != null) {
                        listener.onFailure("下载失败，响应码：" + response.code());
                    }
                    return;
                }

                InputStream is = null;
                FileOutputStream fos = null;
                byte[] buf = new byte[2048];
                int len;
                long total = 0;

                try {
                    ResponseBody body = response.body();
                    if (body == null) {
                        if (listener != null) {
                            listener.onFailure("响应体为空");
                        }
                        return;
                    }

                    long fileSize = body.contentLength();
                    is = body.byteStream();

                    // 创建目录
                    File dir = new File(destFileDir);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    File file = new File(dir, destFileName);
                    fos = new FileOutputStream(file);

                    // 写入文件并更新进度
                    while ((len = is.read(buf)) != -1) {
                        total += len;
                        fos.write(buf, 0, len);
                        if (listener != null) {
                            listener.onProgress((int) (total * 100 / fileSize));
                        }
                        while (isPause) {
                            Thread.sleep(2000);
                        }
                    }

                    fos.flush();
                    if (listener != null) {
                        listener.onSuccess(file.getAbsolutePath());
                    }
                } catch (Throwable e) {
                    if (listener != null) {
                        listener.onFailure(e.getMessage());
                    }
                } finally {
                    // 关闭流
                    try {
                        if (is != null) is.close();
                        if (fos != null) fos.close();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void pause() {
        isPause = true;
    }

    public void resume() {
        isPause = false;
    }

    public boolean isPause() {
        return isPause;
    }

    /**
     * 下载监听器接口
     */
    public interface DownloadListener {
        // 下载成功
        void onSuccess(String filePath);

        // 下载失败
        void onFailure(String errorMsg);

        // 下载进度
        void onProgress(int progress);
    }

}
