package com.ngyb.googleplayserver.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ngyb.googleplayserver.R;
import com.ngyb.googleplayserver.bean.CategoryItemBean;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2020/1/2 20:27
 */
public class CategoryItemView extends RelativeLayout {

    private TextView title;
    private TableLayout tableLayout;

    public CategoryItemView(Context context) {
        this(context, null);
    }

    public CategoryItemView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CategoryItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_category_item, this);
        initView();
    }

    private void initView() {
        title = findViewById(R.id.title);
        tableLayout = findViewById(R.id.table_layout);
    }

    public void bindView(CategoryItemBean categoryItemBean) {
        title.setText(categoryItemBean.getTitle());
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        layoutParams.width = (displayMetrics.widthPixels - tableLayout.getPaddingLeft() - tableLayout.getPaddingRight()) / 3;
        layoutParams.height = TableRow.LayoutParams.WRAP_CONTENT;
        tableLayout.removeAllViews();
        for (int i = 0; i < categoryItemBean.getInfos().size(); i++) {
            TableRow tableRow = new TableRow(getContext());
            CategoryItemBean.InfosBean infosBean = categoryItemBean.getInfos().get(i);
            CategoryInfoItemView categoryInfoItemView = new CategoryInfoItemView(getContext());
            categoryInfoItemView.setLayoutParams(layoutParams);
            categoryInfoItemView.bindView(infosBean.getName1(), infosBean.getUrl1());
            tableRow.addView(categoryInfoItemView);

            if (infosBean.getName2().length() > 0) {
                CategoryInfoItemView categoryInfoItemView1 = new CategoryInfoItemView(getContext());
                categoryInfoItemView1.bindView(infosBean.getName2(), infosBean.getUrl2());
                categoryInfoItemView1.setLayoutParams(layoutParams);
                tableRow.addView(categoryInfoItemView1);
            }
            if (infosBean.getName3().length() > 0) {
                CategoryInfoItemView categoryInfoItemView1 = new CategoryInfoItemView(getContext());
                categoryInfoItemView1.bindView(infosBean.getName3(), infosBean.getUrl3());
                categoryInfoItemView1.setLayoutParams(layoutParams);
                tableRow.addView(categoryInfoItemView1);
            }
            tableLayout.addView(tableRow);
        }
    }
}
