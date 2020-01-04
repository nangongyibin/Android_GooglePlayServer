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
import com.ngyb.googleplayserver.bean.SubjectItemBean;
import com.ngyb.googleplayserver.constant.Constant;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2020/1/2 22:41
 */
public class SubjectItemView extends RelativeLayout {

    private ImageView subjectItemImage;
    private TextView subjectItemTile;

    public SubjectItemView(Context context) {
        this(context, null);
    }

    public SubjectItemView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SubjectItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_subject_item, this);
        initView();
    }

    private void initView() {
        subjectItemImage = findViewById(R.id.subject_item_image);
        subjectItemTile = findViewById(R.id.subject_item_title);
    }

    public void bindView(SubjectItemBean subjectItemBean) {
        subjectItemTile.setText(subjectItemBean.getDes());
        Glide.with(getContext()).load(Constant.IMAGE_URL + subjectItemBean.getUrl()).into(subjectItemImage);
    }
}
