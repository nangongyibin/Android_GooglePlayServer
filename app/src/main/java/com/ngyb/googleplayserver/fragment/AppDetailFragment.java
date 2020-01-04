package com.ngyb.googleplayserver.fragment;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.ngyb.googleplayserver.R;
import com.ngyb.googleplayserver.bean.AppDetailBean;
import com.ngyb.googleplayserver.bean.AppItemBean;
import com.ngyb.googleplayserver.net.DownLoadManager;
import com.ngyb.googleplayserver.net.NgybRetrofit;
import com.ngyb.googleplayserver.view.AppDetailDesView;
import com.ngyb.googleplayserver.view.AppDetailGalleryView;
import com.ngyb.googleplayserver.view.AppDetailInfoView;
import com.ngyb.googleplayserver.view.AppDetailSecurityView;
import com.ngyb.googleplayserver.view.DownloadButton;
import com.ngyb.mvpbase.BaseListFragment;
import com.ngyb.mvpbase.BaseLoadDataFragment;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2020/1/3 20:48
 */
public class AppDetailFragment extends BaseLoadDataFragment {
    private AppDetailBean data;

    @Override
    protected View onCreateContentView() {
        View inflate = View.inflate(getContext(), R.layout.fragment_app_detail, null);
        AppDetailInfoView appDetailInfoView = inflate.findViewById(R.id.app_detail_info);
        appDetailInfoView.bindView(data);
        AppDetailSecurityView appDetailSecurity = inflate.findViewById(R.id.app_detail_security);
        appDetailSecurity.bindView(data);
        AppDetailGalleryView appDetailGallery = inflate.findViewById(R.id.app_detail_gallery);
        appDetailGallery.bindView(data);
        AppDetailDesView appDetailDes = inflate.findViewById(R.id.app_detail_des);
        appDetailDes.bindView(data);
        DownloadButton downloadButton = inflate.findViewById(R.id.download_button);
        downloadButton.syncState(data);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownLoadManager.getInstance().handleClick(getContext(), data.getPackageName());
            }
        });
        return inflate;
    }

    @Override
    protected void startLoadData() {
        String packageName = getActivity().getIntent().getStringExtra("package_name");
        Call<AppDetailBean> appDetail = NgybRetrofit.getInstance().getApi().getAppDetail(packageName);
        appDetail.enqueue(new Callback<AppDetailBean>() {
            @Override
            public void onResponse(Call<AppDetailBean> call, Response<AppDetailBean> response) {
                data = response.body();
                Toasty.info(getContext(), "" + data.getName(), Toast.LENGTH_LONG).show();
                onLoadDataSuccess();
            }

            @Override
            public void onFailure(Call<AppDetailBean> call, Throwable t) {
                onDataLoadFailed();
            }
        });
    }
}
