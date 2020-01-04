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
import com.ngyb.googleplayserver.bean.AppItemBean;
import com.ngyb.googleplayserver.constant.Constant;


/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/12/21 10:59
 */
public class AppItemView extends RelativeLayout {
    private static final String TAG = "AppItemView";
    private ImageView appIcon;
    private TextView appName;
    private RatingBar ratingBar;
    private TextView size;
    private CircleDownLoadView circleDownLoadView;
    private TextView appDesc;

    public AppItemView(Context context) {
        this(context, null);
    }

    public AppItemView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AppItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_app_item_view, this);
        initView();
    }

    private void initView() {
        appIcon = findViewById(R.id.app_icon);
        appName = findViewById(R.id.app_name);
        ratingBar = findViewById(R.id.rating_bar);
        size = findViewById(R.id.size);
        circleDownLoadView = findViewById(R.id.circle_download_view);
        appDesc = findViewById(R.id.app_desc);
    }

    public void bindView(AppItemBean appItemBean) {
        Log.e(TAG, "bindView: " + appItemBean.getIconUrl());
        if (appItemBean.getIconUrl() != null && !appItemBean.getIconUrl().equals("")) {
            String url = Constant.IMAGE_URL + appItemBean.getIconUrl();
            Log.e(TAG, "bindView: "+url );
            Log.e(TAG, "bindView: "+(appIcon ==null) );
            if (appIcon!=null){
                Glide.with(getContext()).load(url).error(R.mipmap.ic_default).into(appIcon);
            }
        }
        appName.setText(appItemBean.getName());
        ratingBar.setRating(appItemBean.getStars());
        size.setText(Formatter.formatFileSize(getContext(), appItemBean.getSize()));
        appDesc.setText(appItemBean.getDes());
        circleDownLoadView.syncState(appItemBean);
    }
}
