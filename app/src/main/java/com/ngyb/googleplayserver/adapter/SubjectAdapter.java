package com.ngyb.googleplayserver.adapter;

import android.content.Context;
import android.view.View;

import com.ngyb.googleplayserver.bean.SubjectItemBean;
import com.ngyb.googleplayserver.view.SubjectItemView;
import com.ngyb.mvpbase.adapter.BaseLoadMoreListAdapter;

import java.util.List;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2020/1/2 15:40
 */
public class SubjectAdapter extends BaseLoadMoreListAdapter<SubjectItemBean> {
    public SubjectAdapter(Context context, List<SubjectItemBean> dataList) {
        super(context, dataList);
    }

    @Override
    protected void onBindNormalItemView(View itemView, int position) {
        ((SubjectItemView) itemView).bindView(getDataList().get(position));
    }

    @Override
    protected View onCreateOneItemView() {
        return new SubjectItemView(getContext());
    }
}
