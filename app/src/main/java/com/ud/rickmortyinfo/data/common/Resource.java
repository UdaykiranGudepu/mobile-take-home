package com.ud.rickmortyinfo.data.common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONObject;

import static com.ud.rickmortyinfo.data.common.RMStatus.DEFAULT;
import static com.ud.rickmortyinfo.data.common.RMStatus.LOADING;
import static com.ud.rickmortyinfo.data.common.RMStatus.SUCCESS;
import static com.ud.rickmortyinfo.data.common.RMStatus.ERROR;

/**
 * Resource
 *
 * @author uday.gudepu
 * Created on 2019-09-26
 * Copyright © 2019 RickyMortyInfo. All rights reserved.
 */
public class Resource<T> {
    public final RMStatus status;
    public final String data;
    public final RMError error;


    private Resource(RMStatus status, String data, RMError error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    /**
     * Success response
     *
     * @param data - success data
     * @return resource wrapper
     */
    public static Resource success(@NonNull String data) {
        return new Resource(SUCCESS, data, null);
    }


    /**
     * Error response
     *
     * @param msg - error object
     * @return resource wrapper
     */
    public static Resource error(RMError msg) {
        return new Resource(ERROR, null, msg);
    }

    /**
     * loading state
     *
     * @return resource wrapper
     */
    public static Resource loading() {
        return new Resource(LOADING, null, null);
    }

    /**
     * default state
     *
     * @return resource wrapper
     */
    public static Resource reset() {
        return new Resource(DEFAULT, null, null);
    }
}
