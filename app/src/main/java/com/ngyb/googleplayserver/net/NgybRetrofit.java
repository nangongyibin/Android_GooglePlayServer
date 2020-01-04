package com.ngyb.googleplayserver.net;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ngyb.googleplayserver.constant.Constant;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/12/17 21:31
 */
public class NgybRetrofit {
    private static NgybRetrofit ngybRetrofit;
    private Api api;
    private static final int DEFAULT_CACHE_SIZE = 5 * 1024 * 1024;

    private NgybRetrofit() {

    }

    public Api getApi() {
        return api;
    }

    public static NgybRetrofit getInstance() {
        if (ngybRetrofit == null) {
            synchronized (NgybRetrofit.class) {
                if (ngybRetrofit == null) {
                    ngybRetrofit = new NgybRetrofit();
                }
            }
        }
        return ngybRetrofit;
    }

    public void init(Context context) {
        Gson gson = new GsonBuilder().setLenient().create();
        String cacheDir = context.getCacheDir() + "/responses";
        File file = new File(cacheDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(new Cache(file, DEFAULT_CACHE_SIZE))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl(Constant.BASE_URL)
                .build();
        api = retrofit.create(Api.class);
    }

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            return response.newBuilder().header("Cache-Control", "max-age=300").build();
        }
    };
}
