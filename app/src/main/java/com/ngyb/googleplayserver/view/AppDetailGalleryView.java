package com.ngyb.googleplayserver.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.ngyb.googleplayserver.R;
import com.ngyb.googleplayserver.bean.AppDetailBean;
import com.ngyb.googleplayserver.constant.Constant;

import java.util.List;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2020/1/2 16:31
 */
public class AppDetailGalleryView extends RelativeLayout {

    private LinearLayout imageContainer;

    public AppDetailGalleryView(Context context) {
        this(context, null);
    }

    public AppDetailGalleryView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public AppDetailGalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_app_detail_gallery, this);
        initView();
    }

    private void initView() {
        imageContainer = findViewById(R.id.image_container);
    }

    public void bindView(AppDetailBean data) {
        List<String> screen = data.getScreen();
        int padding = getResources().getDimensionPixelSize(R.dimen.dp_8);
        for (int i = 0; i < screen.size(); i++) {
            String url = screen.get(i);
            ImageView imageView = new ImageView(getContext());
            if (i == screen.size() - 1) {
                imageView.setPadding(padding, padding, padding, padding);
            } else {
                imageView.setPadding(padding, padding, 0, padding);
            }
            Glide.with(getContext()).load(Constant.IMAGE_URL + url).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(imageView);
            imageContainer.addView(imageView);
        }
    }
}
