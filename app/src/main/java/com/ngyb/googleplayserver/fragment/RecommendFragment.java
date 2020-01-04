package com.ngyb.googleplayserver.fragment;

import android.view.View;
import android.widget.Toast;

import com.leon.stellarmap.lib.StellarMap;
import com.ngyb.googleplayserver.R;
import com.ngyb.googleplayserver.adapter.RecommendAdapter;
import com.ngyb.googleplayserver.net.NgybRetrofit;
import com.ngyb.mvpbase.BaseLoadDataFragment;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2020/1/3 22:07
 */
public class RecommendFragment extends BaseLoadDataFragment {
    private List<String> dataList;
    private Call<List<String>> call;

    @Override
    protected View onCreateContentView() {
        StellarMap stellarMap = new StellarMap(getContext());
        int padding = getResources().getDimensionPixelSize(R.dimen.dp_8);
        stellarMap.setInnerPadding(padding, padding, padding, padding);
        stellarMap.setAdapter(new RecommendAdapter(getContext(), dataList));
        stellarMap.setRegularity(15, 20);
        stellarMap.setGroup(0);
        return stellarMap;
    }

    @Override
    protected void startLoadData() {
        call = NgybRetrofit.getInstance().getApi().listRecommend();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                dataList = response.body();
                Toasty.info(getContext(), dataList.get(0) + "", Toast.LENGTH_LONG).show();
                if (!call.isCanceled()) {
                    onLoadDataSuccess();
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                if (!call.isCanceled()) {
                    onDataLoadFailed();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        call.cancel();
    }
}
