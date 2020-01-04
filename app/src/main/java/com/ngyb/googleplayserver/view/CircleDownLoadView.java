package com.ngyb.googleplayserver.view;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ngyb.googleplayserver.R;
import com.ngyb.googleplayserver.bean.AppItemBean;
import com.ngyb.googleplayserver.net.DownLoadInfo;
import com.ngyb.googleplayserver.net.DownLoadManager;
import com.ngyb.googleplayserver.net.UpdateDownLoadInfoListener;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/12/22 10:37
 */
public class CircleDownLoadView extends FrameLayout implements UpdateDownLoadInfoListener, View.OnClickListener {

    private ImageView downloadIcon;
    private TextView downloadText;
    private RectF rectF;
    private Paint paint;
    private boolean enableProgress = true;
    private int progress;
    private AppItemBean appItemBean;
    private DownLoadInfo downLoadInfo;

    public CircleDownLoadView(@NonNull Context context) {
        this(context, null);
    }

    public CircleDownLoadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CircleDownLoadView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_circle_download, this);
        initView();
        initListener();
        rectF = new RectF();
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.STROKE);
        setWillNotDraw(false);
    }

    private void initListener() {
        downloadIcon.setOnClickListener(this);
    }

    private void initView() {
        downloadIcon = findViewById(R.id.download_icon);
        downloadText = findViewById(R.id.download_text);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (enableProgress) {
            int left = downloadIcon.getLeft() - 5;
            int right = downloadIcon.getRight() + 5;
            int top = downloadIcon.getTop() - 5;
            int bottom = downloadIcon.getBottom() + 5;
            rectF.set(left, top, right, bottom);
            float startAngle = -90;
            float sweepAngle = (progress * 1.0f / 100) * 360;
            boolean userCenter = false;
            canvas.drawArc(rectF, startAngle, sweepAngle, userCenter, paint);
        }
    }

    public void setProgress(int progress) {
        this.progress = progress;
        String progressString = progress + "%";
        downloadText.setText(progressString);
        invalidate();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.download_icon:
                DownLoadManager.getInstance().handleClick(getContext(), appItemBean.getPackageName());
                break;
        }
    }

    public void syncState(AppItemBean appItemBean) {
        if (downLoadInfo != null) {
            downLoadInfo.listener = null;
        }
        this.appItemBean = appItemBean;
        downLoadInfo = DownLoadManager.getInstance().getDownloadInfo(getContext(), appItemBean.getPackageName(), appItemBean.getSize(), appItemBean.getDownloadUrl());
        downLoadInfo.listener = this;
        update(downLoadInfo);
    }

    private void update(DownLoadInfo downLoadInfo) {
        switch (downLoadInfo.downloadStatus) {
            case DownLoadManager.STATE_INSTALLED:
                downloadText.setText("打开");
                downloadIcon.setImageResource(R.mipmap.ic_install);
                break;
            case DownLoadManager.STATE_DOWNLOADED:
                downloadText.setText("安装");
                downloadIcon.setImageResource(R.mipmap.ic_install);
                clearProgress();
                break;
            case DownLoadManager.STATE_UN_DOWNLOAD:
                clearProgress();
                downloadText.setText("下载");
                downloadIcon.setImageResource(R.mipmap.action_download);
                break;
            case DownLoadManager.STATE_WAITING:
                downloadText.setText("等待");
                downloadIcon.setImageResource(R.mipmap.ic_cancel);
                break;
            case DownLoadManager.STATE_DOWNLOADING:
                enableProgress = true;
                setProgress(downLoadInfo.progress);
                downloadIcon.setImageResource(R.mipmap.ic_pause);
                break;
            case DownLoadManager.STATE_PAUSE:
                downloadText.setText("继续");
                downloadIcon.setImageResource(R.mipmap.action_download);
                break;
            case DownLoadManager.STATE_FAILED:
                downloadText.setText("重试");
                downloadIcon.setImageResource(R.mipmap.ic_redownload);
                break;
        }
    }

    private void clearProgress() {
        enableProgress = false;
        invalidate();
    }
}
