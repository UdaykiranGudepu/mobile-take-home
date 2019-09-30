package com.ud.rickmortyinfo.data.api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MediatorLiveData;

import com.ud.rickmortyinfo.BuildConfig;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Request
 *
 * @author uday.gudepu
 * Created on 2019-09-26
 * Copyright © 2019 RickyMortyInfo. All rights reserved.
 */
public class RMRequest {

    private final static String TAG = RMRequest.class.getSimpleName();

    private String mRequestUrl;
    private Method mMethod;

    public RMRequest(String requestUrl) {
        this.mRequestUrl = requestUrl;
    }

    public RMRequest(String requestUrl, Method method) {
        this.mRequestUrl = requestUrl;
        this.mMethod = method;
    }

    public void execute(MediatorLiveData<RMResponse> mediator) {
        new RMExecuteTask(BuildConfig.BASE_URL + mRequestUrl, mMethod, mediator).execute((Void) null);
    }

    public void executeDownloadImageTask(MediatorLiveData<Bitmap> mediator) {
        new RMDownloadImageExecuteTask(mRequestUrl, mediator).execute((Void) null);
    }

    public enum Method {
        GET
    }

    /* Generic Async Task  */
    static class RMExecuteTask extends AsyncTask<Void, Void, String> {

        private String requestUrl;
        private MediatorLiveData<RMResponse> mediator;
        private Method method;

        private final int SUCCESS_CODE = 200;
        private int httpStatusCode;

        RMExecuteTask(String requestUrl, Method method, MediatorLiveData<RMResponse> mediator) {
            this.requestUrl = requestUrl;
            this.mediator = mediator;
            this.method = method;
        }

        @Override
        protected String doInBackground(Void... voids) {

            String responseServer;

            try {
                HttpURLConnection httpUrlConnection = (HttpURLConnection) ((new URL(requestUrl).openConnection()));
                httpUrlConnection.setRequestMethod(method.toString());
                httpUrlConnection.setConnectTimeout(50000); //set timeout to 50 seconds
                httpUrlConnection.setReadTimeout(50000); //set timeout to 50 seconds
                httpUrlConnection.setDoOutput(false);
                httpUrlConnection.connect();

                httpStatusCode = httpUrlConnection.getResponseCode();
                Log.i("TAG", "Response code: " + httpStatusCode);

                InputStream inputStream;

                if (httpStatusCode == SUCCESS_CODE)
                    inputStream = httpUrlConnection.getInputStream();
                else
                    inputStream = httpUrlConnection.getErrorStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

                String line;
                StringBuilder sb = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                br.close();

                responseServer = sb.toString();

            } catch (Exception e) {
                Log.e(TAG, "Error: " + e.getMessage());
                responseServer = e.getLocalizedMessage();
            }

            return responseServer;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (httpStatusCode == SUCCESS_CODE) {
                mediator.postValue(new RMResponse(response));
            } else {
                mediator.postValue(new RMResponse(httpStatusCode));
            }

        }
    }

    /* Download image Task */
    static class RMDownloadImageExecuteTask extends AsyncTask<Void, Void, Bitmap> {

        private String requestUrl;
        private MediatorLiveData<Bitmap> mediator;

        RMDownloadImageExecuteTask(String requestUrl, MediatorLiveData<Bitmap> mediator) {
            this.requestUrl = requestUrl;
            this.mediator = mediator;
        }

        protected Bitmap doInBackground(Void... voids) {
            Bitmap responseBitmap = null;
            try {
                InputStream in = new URL(requestUrl).openStream();
                responseBitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e(TAG, "Error: " + e.getMessage());
            }
            return responseBitmap;
        }

        protected void onPostExecute(Bitmap result) {
            mediator.postValue(result);
        }
    }
}
