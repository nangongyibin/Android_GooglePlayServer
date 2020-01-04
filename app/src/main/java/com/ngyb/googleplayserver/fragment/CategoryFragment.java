package com.ngyb.googleplayserver.fragment;

import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ngyb.googleplayserver.adapter.CategoryAdapter;
import com.ngyb.googleplayserver.bean.CategoryItemBean;
import com.ngyb.googleplayserver.net.NgybRetrofit;
import com.ngyb.mvpbase.BaseListFragment;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2020/1/3 21:29
 */
public class CategoryFragment extends BaseListFragment {
    private List<CategoryItemBean> dataList;
    private static final String TAG = "CategoryFragment";

    @Override
    protected BaseAdapter onCreateAdapter() {
        return new CategoryAdapter(getContext(), dataList);
    }

    @Override
    protected void startLoadData() {
        Call<List<CategoryItemBean>> listCall = NgybRetrofit.getInstance().getApi().listCategory();
        listCall.enqueue(new Callback<List<CategoryItemBean>>() {
            @Override
            public void onResponse(Call<List<CategoryItemBean>> call, Response<List<CategoryItemBean>> response) {
                dataList = response.body();
                Log.e(TAG, "onResponse: " + new Gson().toJson(dataList));
                Toasty.info(getContext(), dataList.get(0).getTitle() + "", Toast.LENGTH_LONG).show();
                onLoadDataSuccess();
            }

            @Override
            public void onFailure(Call<List<CategoryItemBean>> call, Throwable t) {
                onDataLoadFailed();
            }
        });
    }
}
