package com.ngyb.googleplayserver.net;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/12/22 10:39
 */
public class DownLoadInfo {
    public  int progress;
    public  int downloadStatus;
    public  String packageName;
    public  long apkSize;
    public  long downloadSize;
    public  String filePath;
    public  String downloadUrl;
    public  UpdateDownLoadInfoListener listener;
    public  DownLoadManager.DownloadTask downloadTask;
}
