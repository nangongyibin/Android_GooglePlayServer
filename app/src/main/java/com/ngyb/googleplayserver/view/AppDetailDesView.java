package com.ngyb.googleplayserver.view;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ngyb.googleplayserver.R;
import com.ngyb.googleplayserver.bean.AppDetailBean;
import com.ngyb.utils.AnimationUtils;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/12/25 21:52
 */
public class AppDetailDesView extends RelativeLayout implements View.OnClickListener {

    private TextView appDesc;
    private TextView appName;
    private ImageView arrow;
    private int expandHeight;
    private boolean isExpand = false;
    private int startHeight;

    public AppDetailDesView(Context context) {
        this(context, null);
    }

    public AppDetailDesView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AppDetailDesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_app_detail_des, this);
        initView();
        initListener();
        expandHeight = appDesc.getMeasuredHeight();
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                expandHeight = appDesc.getMeasuredHeight();
                if (appDesc.getLineCount() > 7) {
                    appDesc.setLines(7);
                }
            }
        });
    }

    private void initListener() {
        arrow.setOnClickListener(this);
    }

    private void initView() {
        appDesc = findViewById(R.id.app_desc);
        appName = findViewById(R.id.app_name);
        arrow = findViewById(R.id.arrow);
    }

    public void bindView(AppDetailBean data) {
        appDesc.setText(data.getDes());
        appName.setText(data.getName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arrow:
                if (isExpand) {
                    AnimationUtils.animationViewHeight(appDesc, expandHeight, startHeight);
                    AnimationUtils.rotateView(arrow, -180f, 0);
                } else {
                    startHeight = appDesc.getMeasuredHeight();
                    AnimationUtils.animationViewHeight(appDesc, startHeight, expandHeight);
                    AnimationUtils.rotateView(arrow, 0, -180f);
                }
                isExpand = !isExpand;
                break;
        }
    }
}
