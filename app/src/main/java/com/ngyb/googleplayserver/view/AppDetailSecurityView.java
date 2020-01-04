package com.ngyb.googleplayserver.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ngyb.googleplayserver.R;
import com.ngyb.googleplayserver.bean.AppDetailBean;
import com.ngyb.googleplayserver.constant.Constant;
import com.ngyb.utils.AnimationUtils;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2020/1/2 17:21
 */
public class AppDetailSecurityView extends RelativeLayout implements View.OnClickListener {

    private LinearLayout tagContainer;
    private ImageView arrow;
    private LinearLayout descContainer;
    private int expandHeight;
    private boolean isExpand = false;

    public AppDetailSecurityView(Context context) {
        this(context, null);
    }

    public AppDetailSecurityView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AppDetailSecurityView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.view_app_detail_security, this);
        initView();
        initListener();
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
                expandHeight = descContainer.getMeasuredHeight();
                ViewGroup.LayoutParams layoutParams = descContainer.getLayoutParams();
                layoutParams.height = 0;
                descContainer.setLayoutParams(layoutParams);
            }
        });
    }

    private void initListener() {
        arrow.setOnClickListener(this);
    }

    public void bindView(AppDetailBean data) {
        int padding = getResources().getDimensionPixelSize(R.dimen.dp_8);
        for (int i = 0; i < data.getSafe().size(); i++) {
            AppDetailBean.SafeBean safeBean = data.getSafe().get(i);
            ImageView tag = new ImageView(getContext());
            Glide.with(getContext()).load(Constant.IMAGE_URL + safeBean.getSafeUrl()).override(100, 40).into(tag);
            tagContainer.addView(tag);
            LinearLayout line = new LinearLayout(getContext());
            line.setOrientation(LinearLayout.HORIZONTAL);
            line.setPadding(0, padding, 0, 0);
            ImageView desIcon = new ImageView(getContext());
            Glide.with(getContext()).load(Constant.IMAGE_URL + safeBean.getSafeDesUrl()).override(100, 40).into(desIcon);
            line.addView(desIcon);
            TextView des = new TextView(getContext());
            des.setText(safeBean.getSafeDes());
            line.addView(des);
            descContainer.addView(line);
        }
    }

    private void initView() {
        tagContainer = findViewById(R.id.tag_container);
        arrow = findViewById(R.id.arrow);
        descContainer = findViewById(R.id.desc_container);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arrow:
                if (isExpand) {
                    AnimationUtils.animationViewHeight(descContainer, expandHeight, 0);
                    AnimationUtils.rotateView(arrow, -180f, 0);
                } else {
                    AnimationUtils.animationViewHeight(descContainer, 0, expandHeight);
                    AnimationUtils.rotateView(arrow, 0, -180f);
                }
                isExpand = !isExpand;
                break;
        }
    }
}
