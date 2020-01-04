package com.ngyb.googleplayserver.adapter;

import android.content.Context;
import android.view.View;

import com.ngyb.googleplayserver.bean.CategoryItemBean;
import com.ngyb.googleplayserver.view.CategoryItemView;
import com.ngyb.mvpbase.adapter.BaseListAdapter;

import java.util.List;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2020/1/2 15:15
 */
public class CategoryAdapter extends BaseListAdapter<CategoryItemBean> {
    public CategoryAdapter(Context context, List<CategoryItemBean> dataList) {
        super(context, dataList);
    }

    @Override
    protected void onBindItemView(View itemView, int position) {
        ((CategoryItemView) itemView).bindView(getDataList().get(position));
    }

    @Override
    protected View onCreateItemView(int position) {
        return new CategoryItemView(getContext());
    }
}
