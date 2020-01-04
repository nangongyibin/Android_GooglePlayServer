package com.ngyb.googleplayserver.net;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.ngyb.googleplayserver.constant.Constant;
import com.ngyb.utils.StreamUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/12/17 20:57
 */
public class DownLoadManager {
    public static final int STATE_UN_DOWNLOAD = 0;
    public static final int STATE_DOWNLOADING = 1;
    public static final int STATE_PAUSE = 2;
    public static final int STATE_WAITING = 3;
    public static final int STATE_FAILED = 4;
    public static final int STATE_DOWNLOADED = 5;
    public static final int STATE_INSTALLED = 6;
    private static final String TAG = "DownLoadManager";
    private static DownLoadManager downloadManager;
    /*
    Apk下载存放的路径
     */
    private static final String DOWNLOAD_DIR = Environment.getExternalStorageDirectory() + "/Android/data/com.ngyb.googleplayserver/apk/";
    HashMap<String, DownLoadInfo> downLoadInfoHashMap = new HashMap<>();
    private ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

    private DownLoadManager() {

    }

    public static DownLoadManager getInstance() {
        if (downloadManager == null) {
            synchronized (DownloadManager.class) {
                if (downloadManager == null) {
                    downloadManager = new DownLoadManager();
                }
            }
        }
        return downloadManager;
    }

    public void createDownloadDir() {
        File file = new File(DOWNLOAD_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public DownLoadInfo getDownloadInfo(Context context, String packageName, int apkSize, String downloadUrl) {
        if (downLoadInfoHashMap.get(packageName) != null) {
            return downLoadInfoHashMap.get(packageName);
        }
        DownLoadInfo downLoadInfo = new DownLoadInfo();
        downLoadInfo.apkSize = apkSize;
        downLoadInfo.packageName = packageName;
        downLoadInfo.downloadUrl = downloadUrl;
        if (isInstalled(context, packageName)) {
            downLoadInfo.downloadStatus = STATE_INSTALLED;
        } else if (isDownLoaded(downLoadInfo)) {
            downLoadInfo.downloadStatus = STATE_DOWNLOADED;
        } else {
            downLoadInfo.downloadStatus = STATE_UN_DOWNLOAD;
        }
        downLoadInfoHashMap.put(packageName, downLoadInfo);
        return downLoadInfo;

    }

    private boolean isDownLoaded(DownLoadInfo downLoadInfo) {
        String filePath = DOWNLOAD_DIR + downLoadInfo.packageName + ".apk";
        downLoadInfo.filePath = filePath;
        File apk = new File(filePath);
        if (apk.exists()) {
            downLoadInfo.downloadSize = apk.length();
            if (apk.length() == downLoadInfo.apkSize) {
                return true;
            }
        }
        return false;
    }

    private boolean isInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void handleClick(Context context, String packageName) {
        DownLoadInfo downLoadInfo = downLoadInfoHashMap.get(packageName);
        Log.e(TAG, "handleClick: " + downLoadInfo.downloadStatus);
        switch (downLoadInfo.downloadStatus) {
            case STATE_INSTALLED:
                openApp(context, packageName);
                break;
            case STATE_DOWNLOADED:
                installApk(context, packageName);
                break;
            case STATE_UN_DOWNLOAD:
                downloadApk(downLoadInfo);
                break;
            case STATE_DOWNLOADING:
                pauseDownload(downLoadInfo);
                break;
            case STATE_PAUSE:
                downloadApk(downLoadInfo);
                break;
            case STATE_FAILED:
                downloadApk(downLoadInfo);
                break;
            case STATE_WAITING:
                cancelDownload(downLoadInfo);
                break;
        }


    }

    private void cancelDownload(DownLoadInfo downLoadInfo) {
        threadPoolExecutor.remove(downLoadInfo.downloadTask);
        downLoadInfo.downloadStatus = STATE_UN_DOWNLOAD;
        notifyDownloadInfoChange(downLoadInfo);
    }

    private void pauseDownload(DownLoadInfo downLoadInfo) {
        downLoadInfo.downloadStatus = STATE_PAUSE;
        notifyDownloadInfoChange(downLoadInfo);
    }

    private void downloadApk(DownLoadInfo downLoadInfo) {
        downLoadInfo.downloadStatus = STATE_WAITING;
        notifyDownloadInfoChange(downLoadInfo);
        DownloadTask downLoadTask = new DownloadTask(downLoadInfo);
        downLoadInfo.downloadTask = downLoadTask;
        threadPoolExecutor.execute(downLoadTask);
    }

    private void notifyDownloadInfoChange(DownLoadInfo downLoadInfo) {
        if (downLoadInfo != null) {
            downLoadInfo.listener.onUpdate(downLoadInfo);
        }
    }

    private void installApk(Context context, String packageName) {
        String filePath = DOWNLOAD_DIR + packageName + ".apk";
        File file = new File(filePath);
        Uri uri = null;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (file.exists()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(context, "com.ngyb.googleplayserver.fileprovider", file);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                uri = Uri.fromFile(file);
            }
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            context.startActivity(intent);
        }
    }

    private void openApp(Context context, String packageName) {
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(packageName);
        context.startActivity(launchIntentForPackage);
    }

    public class DownloadTask implements Runnable {
        private DownLoadInfo downLoadInfo;
        private StreamUtils streamUtils;

        public DownloadTask(DownLoadInfo downLoadInfo) {
            this.downLoadInfo = downLoadInfo;
            streamUtils = new StreamUtils();
        }

        @Override
        public void run() {
            String url = Constant.DOWN_APK_URL + downLoadInfo.downloadUrl + "&range=" + downLoadInfo.downloadSize;
            Log.e(TAG, "run: "+url );
            Request request = new Request.Builder().get().url(url).build();
            OkHttpClient okHttpClient = new OkHttpClient();
            InputStream inputStream = null;
            FileOutputStream fileOutputStream = null;
            try {
                Response res = okHttpClient.newCall(request).execute();
                Log.e(TAG, "run: "+res.isSuccessful()+"成功的请求码"+res.code() );
                if (res.isSuccessful()) {
                    File file = new File(downLoadInfo.filePath);
                    Log.e(TAG, "run: "+downLoadInfo.filePath );
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    inputStream = res.body().byteStream();
                    fileOutputStream = new FileOutputStream(file, true);
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    while ((len = inputStream.read(buffer)) != -1) {
                        if (downLoadInfo.downloadStatus == STATE_PAUSE) {
                            return;
                        }
                        fileOutputStream.write(buffer, 0, len);
                        downLoadInfo.downloadSize += len;
                        int progress = (int) (downLoadInfo.downloadSize * 1.0f / downLoadInfo.apkSize * 100);
                        if (progress > downLoadInfo.progress) {
                            downLoadInfo.progress = progress;
                            downLoadInfo.downloadStatus = STATE_DOWNLOADING;
                            notifyDownloadInfoChange(downLoadInfo);
                        }
                        if (progress == 100) {
                            break;
                        }
                    }
                    downLoadInfo.downloadStatus = STATE_DOWNLOADED;
                    notifyDownloadInfoChange(downLoadInfo);
                } else {
                    downLoadInfo.downloadStatus = STATE_FAILED;
                    notifyDownloadInfoChange(downLoadInfo);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "run: "+e.getLocalizedMessage() );
                downLoadInfo.downloadStatus = STATE_FAILED;
                notifyDownloadInfoChange(downLoadInfo);
            } finally {
                try {
                    streamUtils.close(inputStream);
                    streamUtils.close(fileOutputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
