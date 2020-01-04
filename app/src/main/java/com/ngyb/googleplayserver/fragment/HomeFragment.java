package com.ngyb.googleplayserver.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.leon.loopviewpagerlib.FunBanner;
import com.ngyb.googleplayserver.R;
import com.ngyb.googleplayserver.bean.HomeBean;
import com.ngyb.googleplayserver.constant.Constant;
import com.ngyb.googleplayserver.fragment.BaseAppListFragment;
import com.ngyb.googleplayserver.net.NgybRetrofit;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/12/21 09:31
 */
public class HomeFragment extends BaseAppListFragment {
    private List<String> pictures;
    private static final String TAG = "HomeFragment";
    @Override
    protected void loadMoreData() {
        Call<HomeBean> homeBeanCall = NgybRetrofit.getInstance().getApi().listHome(dataList.size());
        homeBeanCall.enqueue(new Callback<HomeBean>() {
            @Override
            public void onResponse(Call<HomeBean> call, Response<HomeBean> response) {

                dataList.addAll(response.body().getList());
                getListAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<HomeBean> call, Throwable t) {

            }
        });
    }

    @Override
    protected void startLoadData() {
        Call<HomeBean> homeBeanCall = NgybRetrofit.getInstance().getApi().listHome(0);
        homeBeanCall.enqueue(new Callback<HomeBean>() {
            @Override
            public void onResponse(Call<HomeBean> call, Response<HomeBean> response) {
                Toasty.success(getContext(), "success", Toast.LENGTH_LONG).show();
                Log.e(TAG, "onResponse: 请求数据");
                if (response != null) {
                    if (response.body() != null) {
                        if (response.body().getList() != null && response.body().getList().size() > 0) {
                            dataList.addAll(response.body().getList());
                            pictures = response.body().getPicture();
                            onLoadDataSuccess();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<HomeBean> call, Throwable t) {
                Toasty.error(getContext(), "failed: " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                onDataLoadFailed();
            }
        });
    }

    @Override
    protected View onCreateHeaderView() {
        FunBanner banner = new FunBanner.Builder(getContext())
                .setHeightWidthRatio(0.377f)
                .setDotNormalColor(Color.WHITE)
                .setImageUrlHost(Constant.IMAGE_URL)
                .setImageUrls(pictures)
                .setDotSelectedColor(getResources().getColor(R.color.colorPrimary))
                .build();
        return banner;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getListAdapter() != null) {
            getListAdapter().notifyDataSetChanged();
        }
    }
}
