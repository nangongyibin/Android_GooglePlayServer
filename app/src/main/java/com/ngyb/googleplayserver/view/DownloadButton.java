package com.ngyb.googleplayserver.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.ngyb.googleplayserver.R;
import com.ngyb.googleplayserver.bean.AppDetailBean;
import com.ngyb.googleplayserver.net.DownLoadInfo;
import com.ngyb.googleplayserver.net.DownLoadManager;
import com.ngyb.googleplayserver.net.UpdateDownLoadInfoListener;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2020/1/2 21:58
 */
public class DownloadButton extends AppCompatButton implements UpdateDownLoadInfoListener {
    private Paint paint;
    private int progress;
    private boolean enableProgress = true;
    private Drawable drawable = new ColorDrawable(Color.BLUE);

    public DownloadButton(Context context) {
        this(context, null);
    }

    public DownloadButton(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public DownloadButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (enableProgress) {
            int right = (int) (progress * 1.0f / 100 * getWidth());
            drawable.setBounds(0, 0, right, getHeight());
            drawable.draw(canvas);
        }
        super.onDraw(canvas);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        String progressString = String.format(getContext().getString(R.string.download_progress), progress);
        setText(progress+"");
    }

    @Override
    public void onUpdate(final DownLoadInfo downLoadInfo) {
        post(new Runnable() {
            @Override
            public void run() {
                update(downLoadInfo);
            }
        });
    }

    public void syncState(AppDetailBean data) {
        DownLoadInfo downLoadInfo = DownLoadManager.getInstance().getDownloadInfo(getContext(), data.getPackageName(), data.getSize(), data.getDownloadUrl());
        downLoadInfo.listener = this;
        update(downLoadInfo);
    }

    private void update(DownLoadInfo downLoadInfo) {
        switch (downLoadInfo.downloadStatus) {
            case DownLoadManager.STATE_INSTALLED:
                setText("打开");
                break;
            case DownLoadManager.STATE_DOWNLOADED:
                setText("安装");
                clearProgress();
                break;
            case DownLoadManager.STATE_UN_DOWNLOAD:
                setText("下载");
                break;
            case DownLoadManager.STATE_DOWNLOADING:
                enableProgress = true;
                setProgress(downLoadInfo.progress);
                break;
            case DownLoadManager.STATE_PAUSE:
                setText("继续");
                break;
            case DownLoadManager.STATE_WAITING:
                setText("等待");
                break;
            case DownLoadManager.STATE_FAILED:
                setText("重试");
                clearProgress();
                break;
        }
    }

    private void clearProgress() {
        enableProgress = false;
        invalidate();
    }
}
