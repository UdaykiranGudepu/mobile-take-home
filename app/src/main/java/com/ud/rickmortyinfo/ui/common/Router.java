package com.ud.rickmortyinfo.ui.common;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Router
 *
 * @author uday.gudepu
 * Created on 2019-09-26
 * Copyright © 2019 RickyMortyInfo. All rights reserved.
 */
public class Router {

    private static final String TAG = Router.class.getSimpleName();

    public static void showFragment(Context context, Fragment fragment, FragmentManager fmanager, @IdRes int container, boolean addBackStack) {
        showFragment(context, fragment, fmanager, container, addBackStack, "");
    }

    public static void showFragment(Context context, Fragment fragment, FragmentManager fmanager, @IdRes int container, boolean addBackStack, String tag) {

        if (((Activity) context).isFinishing() || ((Activity) context).isDestroyed()) {
            Log.e(TAG, "Trying to show fragment on a dead Activity");
        } else {
            FragmentTransaction transaction = fmanager.beginTransaction();
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

            if (TextUtils.isEmpty(tag)) {
                transaction.add(container, fragment);
            } else {
                transaction.add(container, fragment, tag);
            }

            if (addBackStack) {
                transaction.addToBackStack(fragment.getClass().getSimpleName());
            }

            transaction.commitAllowingStateLoss();
        }
    }
}
