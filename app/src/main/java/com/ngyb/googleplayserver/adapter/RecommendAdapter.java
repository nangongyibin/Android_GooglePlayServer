package com.ngyb.googleplayserver.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.leon.stellarmap.lib.StellarMap;

import java.util.List;
import java.util.Random;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2020/1/2 15:27
 */
public class RecommendAdapter implements StellarMap.Adapter {
    private final Context context;
    private final List<String> list;
    public static final int DEFAULT_PAGE_SIZE = 15;
    private final Random random;

    public RecommendAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        random = new Random();
    }

    @Override
    public int getGroupCount() {
        int count = list.size() / DEFAULT_PAGE_SIZE;
        if (list.size() % DEFAULT_PAGE_SIZE != 0) {
            count++;
        }
        return count;
    }

    @Override
    public int getCount(int i) {
        if (list.size() % DEFAULT_PAGE_SIZE != 0) {
            if (i == getGroupCount() - 1) {
                return list.size() % DEFAULT_PAGE_SIZE;
            }
        }
        return DEFAULT_PAGE_SIZE;
    }

    @Override
    public View getView(int i, int i1, View view) {
        if (view == null) {
            view = new TextView(context);
        }
        TextView textView = (TextView) view;
        int i2 = i * DEFAULT_PAGE_SIZE + i1;
        String data = list.get(i2);
        textView.setText(data);
        int textSize = 14 + random.nextInt(6);
        textView.setTextSize(textSize);
        textView.setTextColor(getRandomColor());
        return view;
    }

    private int getRandomColor() {
        int alpha = 255;
        int red = 30 + random.nextInt(170);
        int green = 30 + random.nextInt(170);
        int blue = 30 + random.nextInt(170);
        int argb = Color.argb(alpha, red, green, blue);
        return argb;
    }

    @Override
    public int getNextGroupOnZoom(int i, boolean b) {
        if (b) {
            return (i + 1) % getGroupCount();
        } else {
            return (i - 1 + getGroupCount()) % getGroupCount();
        }
    }
}
