package com.app.ui.detail;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.bean.App;
import com.app.util.AppDetailDownloadManager;
import com.app.util.SimpleToast;
import com.app.util.immersive.ImmersiveWindow;
import com.app.util.load.ILoadController;
import com.app.util.load.TVLoadController;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.hhh.appstore.R;
import com.hhh.appstore.databinding.ActivityAppDetailBinding;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * APP详情页
 * 此版本由《红火火工作室》开发
 * 二开、源码请联系QQ：1282797911 闲鱼：红火火工作室
 * **/
public class AppDetailActivity extends AppCompatActivity {

    private App app;

    private int id;

    private ActivityAppDetailBinding binding;

    private ILoadController loadController;

    private AppDetailDownloadManager.OnListener onListener = new AppDetailDownloadManager.OnListener() {
        @Override
        public void onError() {
            SimpleToast.get().show("下载失败");
            onInstallButtonTextChange();
        }

        @Override
        public void onSuccess(String filePath) {
            onInstallButtonTextChange();
            installApk();
        }

        @Override
        public void onProgressChange(int progress) {
            if (AppDetailDownloadManager.getInstance().isPause(app.download)) {
                return;
            }
            binding.installButton.setText("正在下载 " + progress + "%");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        initData();
    }

    private void initView() {
        binding.backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.backImageView.requestFocus();
        binding.installButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleInstallButtonClick();
            }
        });
        binding.uninstallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri packageURI = Uri.parse("package:"  + app.only);
                    Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                    startActivityForResult(uninstallIntent, 1);
                } catch (Exception e) {
                    SimpleToast.get().show("卸载失败: " + e.getMessage());
                }
            }
        });
        binding.openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getPackageManager().getLaunchIntentForPackage(app.only);
                if (intent != null) {
                    startActivity(intent);
                }
            }
        });
    }

    private void onAppChange() {
        if (app != null) {
            binding.nameTextView.setText(app.name);
            Glide.with(this)
                    .load(app.icon)
                    .error(R.drawable.img_error)
                    .placeholder(R.drawable.img_error)
                    .fallback(R.drawable.img_error)
                    .transform(new MultiTransformation<>(
                            new CenterCrop(),
                            new RoundedCorners((int) getResources().getDimension(R.dimen.corners))
                    ))
                    .into(binding.appImageView);
            binding.infoTextView.setText("大小：" + app.size+"  |  " + "版本：" + app.version);
        }
    }

    private void initData() {
        id = getIntent().getIntExtra("id", 0);
        loadController = new TVLoadController(binding.contentLayout);
        Runnable loadRunnable = new Runnable() {
            @Override
            public void run() {
                loadDetail();
            }
        };
        loadController.setLoadTask(loadRunnable);
        loadRunnable.run();
    }

    private void loadDetail() {
        loadController.showLoading();
        Observer<App> observer = new Observer<App>() {

            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull App result) {
                app = result;
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                if (getSupportFragmentManager().isDestroyed()) {
                    return;
                }
                loadController.showError(null);
            }

            @Override
            public void onComplete() {
                if (getSupportFragmentManager().isDestroyed()) {
                    return;
                }
                if (app != null) {
                    loadController.showSuccess(null);
                    onAppChange();
                    tryDeletePackage();
                    onInstallButtonTextChange();
                } else {
                    loadController.showEmpty(null);
                }
            }

        };
        ObservableOnSubscribe<App> subscribe = new GetAppDetailSubscribe(getIntent());
        Observable.create(subscribe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void immersiveWindow() {
        ImmersiveWindow immersiveWindow = new ImmersiveWindow(getWindow());
        immersiveWindow.updateWindow();
        immersiveWindow.setNavigation(false);
        immersiveWindow.setStatus(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        immersiveWindow();
        onInstallButtonTextChange();
    }

    private void installApk() {
        systemInstall();
    }

    private void systemInstall() {
        String filePath = AppDetailDownloadManager.getInstance().getApkFilePath(app.download);
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            File apkFile = new File(filePath);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", apkFile);
                grantUriPermission(getPackageName(), uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
                startActivity(intent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    boolean hasInstallPermission = getPackageManager().canRequestPackageInstalls();
                    if (!hasInstallPermission) {
                        Intent settingIntent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(settingIntent);
                    }
                }
            } else {
                Uri uri = Uri.fromFile(apkFile);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
                startActivity(intent);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            SimpleToast.get().show("安装失败");
        }
    }

    private void deletePackage() {
        String filePath = AppDetailDownloadManager.getInstance().getApkFilePath(app.download);
        File apkFile = new File(filePath);
        if (apkFile.exists())  {
            apkFile.delete();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        tryDeletePackage();
    }

    private void tryDeletePackage() {
        if (app != null && getPackageManager().getLaunchIntentForPackage(app.only) != null) {
            deletePackage();
            onInstallButtonTextChange();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleInstallButtonClick() {
        AppDetailDownloadManager manager = AppDetailDownloadManager.getInstance();
        if (manager.isDownloaded(app.download)) {
            installApk();
            return;
        }
        if (manager.isDownloading(app.download)) {
            manager.pause(app.download);
            onInstallButtonTextChange();
            return;
        }
        if (manager.isPause(app.download)) {
            manager.resume(this, app.download, onListener);
            onInstallButtonTextChange();
            return;
        }
        if (TextUtils.isEmpty(app.download)) {
            SimpleToast.get().show("下载失败，请联系管理员进行处理");
            return;
        }
        manager.start(this, app.download, onListener);
        onInstallButtonTextChange();
    }

    private void onInstallButtonTextChange() {
        if (app == null) {
            return;
        }
        AppDetailDownloadManager manager = AppDetailDownloadManager.getInstance();
        if (manager.isInstalled(app.only)) {
            binding.installButton.setVisibility(View.GONE);
            binding.uninstallButton.setVisibility(View.VISIBLE);
            binding.openButton.setVisibility(View.VISIBLE);
            return;
        }
        if (manager.isDownloaded(app.download)) {
            binding.installButton.setText("已下载");
            binding.installButton.setVisibility(View.VISIBLE);
            binding.uninstallButton.setVisibility(View.GONE);
            binding.openButton.setVisibility(View.GONE);
            return;
        }
        if (manager.isDownloading(app.download)) {
            binding.installButton.setText("下载中");
            binding.installButton.setVisibility(View.VISIBLE);
            manager.set(this, app.download, onListener);
            binding.uninstallButton.setVisibility(View.GONE);
            binding.openButton.setVisibility(View.GONE);
            return;
        }
        if (manager.isPause(app.download)) {
            binding.installButton.setText("已暂停");
            binding.installButton.setVisibility(View.VISIBLE);
            binding.uninstallButton.setVisibility(View.GONE);
            binding.openButton.setVisibility(View.GONE);
            return;
        }
        binding.installButton.setText("下载");
        binding.installButton.setVisibility(View.VISIBLE);
        binding.uninstallButton.setVisibility(View.GONE);
        binding.openButton.setVisibility(View.GONE);
    }

}
