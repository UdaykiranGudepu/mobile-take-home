package com.ud.rickmortyinfo.ui.common.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import androidx.annotation.StringRes;

import com.ud.rickmortyinfo.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Utils
 *
 * @author uday.gudepu
 * Created on 2019-09-26
 * Copyright © 2019 RickyMortyInfo. All rights reserved.
 */
public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    /**
     * @param html html text
     * @return Returns spanned text
     */
    public static Spanned getSpannedHtml(String html) {
        Spanned result;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }

        return result;
    }

    /**
     * Converts date to different format
     *
     * @param actualDate        - actual date
     * @param sourceFormat      - source format
     * @param destinationFormat - destination format
     * @return formatted date
     */
    public static String convertDateFormat(String actualDate, String sourceFormat, String destinationFormat) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sourceFormat, Locale.getDefault());
            Date newDate = simpleDateFormat.parse(actualDate);

            SimpleDateFormat newFormat = new SimpleDateFormat(destinationFormat, Locale.getDefault());
            return newDate != null ? newFormat.format(newDate) : actualDate;

        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
            return actualDate;
        }
    }

    /**
     * Shows alert dialog
     *
     * @param activity        - activity context
     * @param title           - title
     * @param msg             - message
     * @param onClickListener - button click listener
     */
    public static void showDialog(Activity activity, @StringRes int title, @StringRes int msg, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.generic_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    /**
     * Shows alert dialog
     *
     * @param activity - activity context
     * @param title    - title
     * @param msg      - message
     */
    public static void showDialog(Activity activity, @StringRes int title, @StringRes int msg) {
        showDialog(activity, title, msg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
    }
}
