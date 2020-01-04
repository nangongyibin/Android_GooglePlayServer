package com.ngyb.googleplayserver.view;

import android.content.Context;
import android.text.format.Formatter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ngyb.googleplayserver.R;
import com.ngyb.googleplayserver.bean.AppDetailBean;
import com.ngyb.googleplayserver.constant.Constant;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2020/1/2 16:56
 */
public class AppDetailInfoView extends RelativeLayout {
    private static final String TAG = "AppDetailInfoView";
    private ImageView appIcon;
    private TextView appName;
    private TextView downloadCount;
    private RatingBar ratingBar;
    private TextView versionCode;
    private TextView appTime;
    private TextView appSize;

    public AppDetailInfoView(Context context) {
        this(context, null);
    }

    public AppDetailInfoView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AppDetailInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_app_detail_info, this);
        initView();
    }

    private void initView() {
        appIcon = findViewById(R.id.app_icon);
        appName = findViewById(R.id.app_name);
        ratingBar = findViewById(R.id.app_ratting);
        downloadCount = findViewById(R.id.download_count);
        versionCode = findViewById(R.id.version_code);
        appTime = findViewById(R.id.app_time);
        appSize = findViewById(R.id.app_size);
    }

    public void bindView(AppDetailBean data) {
        Glide.with(getContext()).load(Constant.IMAGE_URL + data.getIconUrl()).into(appIcon);
        appName.setText(data.getName());
        Log.e(TAG, "bindView: " + data.getStars());
        ratingBar.setRating((float) data.getStars());
        String downloadCount1 = String.format(getResources().getString(R.string.download_count), data.getDownloadNum());
        downloadCount.setText(downloadCount1);
        String versionCode1 = String.format(getResources().getString(R.string.version_code), data.getVersion());
        versionCode.setText(versionCode1);
        String date = String.format(getResources().getString(R.string.time), data.getDate());
        appTime.setText(date);
        String size = String.format(getResources().getString(R.string.app_size), Formatter.formatFileSize(getContext(), data.getSize()));
        appSize.setText(size);
    }
}
