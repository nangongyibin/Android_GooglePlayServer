package com.ngyb.googleplayserver.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ngyb.googleplayserver.R;
import com.ngyb.googleplayserver.constant.Constant;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2020/1/2 20:18
 */
public class CategoryInfoItemView extends RelativeLayout {

    private ImageView categoryInfoImage;
    private TextView categoryInfoTitle;

    public CategoryInfoItemView(Context context) {
        this(context, null);
    }

    public CategoryInfoItemView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CategoryInfoItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_category_info_item, this);
        initView();
    }

    private void initView() {
        categoryInfoImage = findViewById(R.id.category_info_image);
        categoryInfoTitle = findViewById(R.id.category_info_title);
    }

    public void bindView(String name, String url) {
        categoryInfoTitle.setText(name);
        Glide.with(getContext()).load(Constant.IMAGE_URL + url).into(categoryInfoImage);
    }
}
