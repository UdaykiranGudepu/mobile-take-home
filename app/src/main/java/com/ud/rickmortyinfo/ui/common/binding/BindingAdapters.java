package com.ud.rickmortyinfo.ui.common.binding;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableBoolean;

/**
 * BindingAdapters
 *
 * @author uday.gudepu
 * Created on 2019-09-26
 * Copyright © 2019 RickyMortyInfo. All rights reserved.
 */
public class BindingAdapters {

    @BindingAdapter("bind:imageBitmap")
    public static void bindBitmap(ImageView view, Bitmap bitmap) {
        if (bitmap != null) {
            view.setImageDrawable(new BitmapDrawable(view.getResources(), bitmap));
        }
    }

    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, ObservableBoolean visibility) {
        view.setVisibility(visibility.get() ? View.VISIBLE : View.GONE);
    }
}
