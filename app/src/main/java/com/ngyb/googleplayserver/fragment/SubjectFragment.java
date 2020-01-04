package com.ngyb.googleplayserver.fragment;

import android.widget.BaseAdapter;
import android.widget.Toast;

import com.ngyb.googleplayserver.adapter.SubjectAdapter;
import com.ngyb.googleplayserver.bean.SubjectItemBean;
import com.ngyb.googleplayserver.net.NgybRetrofit;
import com.ngyb.mvpbase.BaseLoadMoreListFragment;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2020/1/3 22:19
 */
public class SubjectFragment extends BaseLoadMoreListFragment {
    private List<SubjectItemBean> dataList;

    @Override
    protected void loadMoreData() {
        Call<List<SubjectItemBean>> listCall = NgybRetrofit.getInstance().getApi().listSubject(dataList.size());
        listCall.enqueue(new Callback<List<SubjectItemBean>>() {
            @Override
            public void onResponse(Call<List<SubjectItemBean>> call, Response<List<SubjectItemBean>> response) {
                dataList.addAll(response.body());
                getListAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<SubjectItemBean>> call, Throwable t) {

            }
        });
    }

    @Override
    protected BaseAdapter onCreateAdapter() {
        return new SubjectAdapter(getContext(), dataList);
    }

    @Override
    protected void startLoadData() {
        Call<List<SubjectItemBean>> listCall = NgybRetrofit.getInstance().getApi().listSubject(0);
        listCall.enqueue(new Callback<List<SubjectItemBean>>() {
            @Override
            public void onResponse(Call<List<SubjectItemBean>> call, Response<List<SubjectItemBean>> response) {
                dataList = response.body();
                Toasty.info(getContext(), dataList.get(0).getDes(), Toast.LENGTH_LONG).show();
                onLoadDataSuccess();
            }

            @Override
            public void onFailure(Call<List<SubjectItemBean>> call, Throwable t) {
                onDataLoadFailed();
            }
        });
    }
}
