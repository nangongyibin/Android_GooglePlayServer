package com.ngyb.googleplayserver.factory;

import android.support.v4.app.Fragment;

import com.ngyb.googleplayserver.fragment.ApplicationFragment;
import com.ngyb.googleplayserver.fragment.CategoryFragment;
import com.ngyb.googleplayserver.fragment.GameFragment;
import com.ngyb.googleplayserver.fragment.HomeFragment;
import com.ngyb.googleplayserver.fragment.HotFragment;
import com.ngyb.googleplayserver.fragment.RecommendFragment;
import com.ngyb.googleplayserver.fragment.SubjectFragment;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/12/17 20:33
 */
public class FragmentFactory {
    public static final int FRAGMENT_HOME = 0;
    public static final int FRAGMENT_APP = 1;
    public static final int FRAGMENT_GAME = 2;
    public static final int FRAGMENT_SUBJECT = 3;
    public static final int FRAGMENT_RECOMMEND = 4;
    public static final int FRAGMENT_CATEGORY = 5;
    public static final int FRAGMENT_HOT = 6;

    public static Fragment getFragment(int position) {
        switch (position) {
            case FRAGMENT_HOME:
                return new HomeFragment();
            case FRAGMENT_APP:
                return new ApplicationFragment();
            case FRAGMENT_GAME:
                return new GameFragment();
            case FRAGMENT_SUBJECT:
                return new SubjectFragment();
            case FRAGMENT_RECOMMEND:
                return new RecommendFragment();
            case FRAGMENT_CATEGORY:
                return new CategoryFragment();
            case FRAGMENT_HOT:
                return new HotFragment();
        }
        return null;
    }
}
