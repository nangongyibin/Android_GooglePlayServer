package com.ngyb.googleplayserver.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ngyb.googleplayserver.R;
import com.ngyb.googleplayserver.net.NgybRetrofit;
import com.ngyb.googleplayserver.view.FlowLayout;
import com.ngyb.mvpbase.BaseLoadDataFragment;

import java.util.List;
import java.util.Random;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2020/1/3 21:51
 */
public class HotFragment extends BaseLoadDataFragment {
    private List<String> dataList;
    private static final String TAG = "HotFragment";

    @Override
    protected View onCreateContentView() {
        ScrollView scrollView = new ScrollView(getContext());
        FlowLayout flowLayout = new FlowLayout(getContext());
        int padding = getResources().getDimensionPixelSize(R.dimen.dp_8);
        flowLayout.setPadding(padding, padding, padding, padding);
        for (int i = 0; i < dataList.size(); i++) {
            Log.e(TAG, "onCreateContentView: "+i+"===="+dataList.get(i) );
            String data = dataList.get(i);
            TextView textView = getTextView(padding, data);
            StateListDrawable stateListDrawable = getStateListDrawable();
            textView.setBackgroundDrawable(stateListDrawable);
            flowLayout.addView(textView);
        }
        int childCount = flowLayout.getChildCount();
        Log.e(TAG, "onCreateContentView: "+childCount );
        scrollView.addView(flowLayout);
        return scrollView;
    }

    private StateListDrawable getStateListDrawable() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(8);
        gradientDrawable.setColor(getRandomColor());
        GradientDrawable pressedDrawable = new GradientDrawable();
        pressedDrawable.setCornerRadius(8);
        pressedDrawable.setColor(Color.GRAY);
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
        stateListDrawable.addState(new int[]{}, gradientDrawable);
        return stateListDrawable;
    }

    private int getRandomColor() {
        Random random = new Random();
        int alpha = 255;
        int red = 70 + random.nextInt(170);
        int green = 70 + random.nextInt(170);
        int blue = 70 + random.nextInt(170);
        int argb = Color.argb(alpha, red, green, blue);
        return argb;
    }

    private TextView getTextView(int padding, String data) {
        final TextView textView = new TextView(getContext());
        textView.setTextSize(20);
        textView.setTextColor(Color.WHITE);
        textView.setPadding(padding, padding, padding, padding);
        textView.setGravity(Gravity.CENTER);
        textView.setClickable(true);
        textView.setText(data);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                Toasty.info(getContext(), tv.getText() + "", Toast.LENGTH_LONG).show();
            }
        });
        return textView;
    }

    @Override
    protected void startLoadData() {
        Call<List<String>> listCall = NgybRetrofit.getInstance().getApi().listHot();
        listCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                dataList = response.body();
                Log.e(TAG, "onResponse: " + new Gson().toJson(dataList));
                onLoadDataSuccess();
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                onDataLoadFailed();
            }
        });
    }
}
