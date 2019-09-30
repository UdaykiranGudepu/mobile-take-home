package com.ud.rickmortyinfo.data.base;

import android.graphics.Bitmap;

import androidx.lifecycle.MediatorLiveData;

import com.ud.rickmortyinfo.data.api.RMRequest;
import com.ud.rickmortyinfo.data.api.RMResponse;
import com.ud.rickmortyinfo.data.common.Resource;

/**
 * BaseRepository
 *
 * @author uday.gudepu
 * Created on 2019-09-26
 * Copyright © 2019 RickyMortyInfo. All rights reserved.
 */
public class BaseRepository {

    /**
     * Executes Generic Http request
     *
     * @param requestUrl - url
     * @param method     - method type - GET
     * @param mediator   - live data to observe
     */
    protected void genericCall(String requestUrl, RMRequest.Method method,
                               final MediatorLiveData<Resource> mediator) {

        mediator.setValue(Resource.loading());
        RMRequest request = new RMRequest(requestUrl, method);
        request.execute(new MediatorLiveData<RMResponse>() {
            @Override
            public void postValue(RMResponse response) {
                if (response.body != null) {
                    mediator.setValue(Resource.success(response.body));
                } else {
                    mediator.setValue(Resource.error(response.error));
                }
            }
        });
        mediator.setValue(Resource.reset());
    }

    /**
     * Executes Download image request
     *
     * @param requestUrl - url
     * @param mediator   - live data to observe
     */
    protected void genericImageRequestCall(final String requestUrl,
                                           final MediatorLiveData<Bitmap> mediator) {
        mediator.setValue(null);
        RMRequest request = new RMRequest(requestUrl);
        request.executeDownloadImageTask(new MediatorLiveData<Bitmap>() {
            @Override
            public void postValue(Bitmap response) {
                mediator.setValue(response);
            }
        });
    }
}
