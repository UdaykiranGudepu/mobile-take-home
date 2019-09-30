package com.ud.rickmortyinfo.data.api;

import android.util.Log;

import androidx.annotation.Nullable;

import com.ud.rickmortyinfo.data.common.RMError;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Response
 *
 * @author uday.gudepu
 * Created on 2019-09-26
 * Copyright © 2019 RickyMortyInfo. All rights reserved.
 */
public class RMResponse {

    private static final String TAG = RMResponse.class.getSimpleName();

    public int code;
    @Nullable
    public String body;
    @Nullable
    public RMError error;

    RMResponse(@Nullable String response) {

        try {
            new JSONTokener(response).nextValue();
            body = response;
            error = null;

        } catch (JSONException e) {
            error = new RMError();
            body = null;
            Log.e(TAG, "Error: " + e.getMessage());
        }

    }

    RMResponse(int code) {
        this.code = code;
        body = null;
    }

}
