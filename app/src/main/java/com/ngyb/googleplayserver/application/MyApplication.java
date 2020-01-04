package com.ngyb.googleplayserver.application;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.ngyb.googleplayserver.net.NgybRetrofit;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/12/17 21:30
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NgybRetrofit.getInstance().init(getApplicationContext());
//        android.support.multidex.MultiDex.install(this);
        MultiDex.install(this);
    }
}
