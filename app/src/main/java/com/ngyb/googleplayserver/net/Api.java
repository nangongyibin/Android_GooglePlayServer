package com.ngyb.googleplayserver.net;

import com.ngyb.googleplayserver.bean.AppDetailBean;
import com.ngyb.googleplayserver.bean.AppItemBean;
import com.ngyb.googleplayserver.bean.CategoryItemBean;
import com.ngyb.googleplayserver.bean.HomeBean;
import com.ngyb.googleplayserver.bean.SubjectItemBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/12/21 08:37
 */
public interface Api {
    @GET("HotServlet/hot")
    Call<List<String>> listHot();

    @GET("HomeServlet/home")
    Call<HomeBean> listHome(@Query("index") int index);

    @GET("RecommendServlet/recommend")
    Call<List<String>> listRecommend();

    @GET("CategoryServlet/category")
    Call<List<CategoryItemBean>> listCategory();

    @GET("SubjectServlet/subject")
    Call<List<SubjectItemBean>> listSubject(@Query("index") int index);

    @GET("GameServlet/game")
    Call<List<AppItemBean>> listGame(@Query("index") int index);

    @GET("AppServlet/app")
    Call<List<AppItemBean>> listApp(@Query("index") int index);

    @GET("DetailServlet/detail")
    Call<AppDetailBean> getAppDetail(@Query("packageName") String packageName);
}
