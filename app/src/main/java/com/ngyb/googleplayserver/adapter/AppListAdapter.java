package com.ngyb.googleplayserver.adapter;

import android.content.Context;
import android.view.View;

import com.ngyb.googleplayserver.bean.AppItemBean;
import com.ngyb.googleplayserver.view.AppItemView;
import com.ngyb.mvpbase.adapter.BaseLoadMoreListAdapter;

import java.util.List;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/12/21 08:44
 */
public class AppListAdapter extends BaseLoadMoreListAdapter<AppItemBean> {
    public AppListAdapter(Context context, List<AppItemBean> dataList) {
        super(context, dataList);
    }

    @Override
    protected void onBindNormalItemView(View itemView, int position) {
        ((AppItemView) itemView).bindView(getDataList().get(position));
    }

    @Override
    protected View onCreateOneItemView() {
        return new AppItemView(getContext());
    }
}
