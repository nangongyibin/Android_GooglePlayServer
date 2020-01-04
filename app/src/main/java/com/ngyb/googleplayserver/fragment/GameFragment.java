package com.ngyb.googleplayserver.fragment;

import com.ngyb.googleplayserver.bean.AppItemBean;
import com.ngyb.googleplayserver.net.NgybRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2020/1/3 21:37
 */
public class GameFragment extends BaseAppListFragment {
    @Override
    protected void loadMoreData() {
        Call<List<AppItemBean>> listCall = NgybRetrofit.getInstance().getApi().listGame(dataList.size());
        listCall.enqueue(new Callback<List<AppItemBean>>() {
            @Override
            public void onResponse(Call<List<AppItemBean>> call, Response<List<AppItemBean>> response) {
                dataList.addAll(response.body());
                getListAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<AppItemBean>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void startLoadData() {
        Call<List<AppItemBean>> listCall = NgybRetrofit.getInstance().getApi().listGame(0);
        listCall.enqueue(new Callback<List<AppItemBean>>() {
            @Override
            public void onResponse(Call<List<AppItemBean>> call, Response<List<AppItemBean>> response) {
                if (response != null && response.body() != null && response.body().size() > 0) {
                    dataList.addAll(response.body());
                    onLoadDataSuccess();
                }
            }

            @Override
            public void onFailure(Call<List<AppItemBean>> call, Throwable t) {
                onDataLoadFailed();
            }
        });
    }
}
