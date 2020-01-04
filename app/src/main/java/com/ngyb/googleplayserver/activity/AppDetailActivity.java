package com.ngyb.googleplayserver.activity;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.ngyb.googleplayserver.R;
import com.ngyb.mvpbase.BaseMvpActivity;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/12/21 09:30
 */
public class AppDetailActivity extends BaseMvpActivity {
    private Toolbar toolBar;
    private String packageName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_detail;
    }

    @Override
    public void init() {
        initView();
        initToolbar();
    }

    private void initView() {
        toolBar = findViewById(R.id.tool_bar);
    }

    private void initToolbar() {
        setSupportActionBar(toolBar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("应用详情");
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        setStatusBarColor();
    }

    private void setStatusBarColor() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
