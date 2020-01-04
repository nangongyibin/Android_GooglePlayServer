package com.ngyb.googleplayserver.fragment;

import android.content.Intent;
import android.widget.BaseAdapter;

import com.ngyb.googleplayserver.activity.AppDetailActivity;
import com.ngyb.googleplayserver.adapter.AppListAdapter;
import com.ngyb.googleplayserver.bean.AppItemBean;
import com.ngyb.mvpbase.BaseLoadMoreListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/12/19 21:30
 */
public abstract class BaseAppListFragment extends BaseLoadMoreListFragment {
    protected List<AppItemBean> dataList = new ArrayList<>();

    @Override
    protected BaseAdapter onCreateAdapter() {
        return new AppListAdapter(getContext(), dataList);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getListAdapter() != null) {
            getListAdapter().notifyDataSetChanged();
        }
    }

    @Override
    protected void onListItemClick(int position) {
        Intent intent = new Intent(getContext(), AppDetailActivity.class);
        intent.putExtra("package_name",dataList.get(position).getPackageName());
        startActivity(intent);
//        super.onListItemClick(position);
    }
}
